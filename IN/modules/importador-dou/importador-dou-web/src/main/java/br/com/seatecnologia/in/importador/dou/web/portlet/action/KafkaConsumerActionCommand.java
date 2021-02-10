package br.com.seatecnologia.in.importador.dou.web.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.kafka.DouImporterKafkaService;
import br.com.seatecnologia.in.importador.dou.kafka.SourceFormat;
import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;
import br.com.seatecnologia.in.importador.dou.web.portlet.util.ConfigurationsUtil;
import br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + ImportadorDouPortletKeys.ImportadorDou,
			"mvc.command.name=/kafka_consumer"
		},
		service = MVCActionCommand.class
	)

public class KafkaConsumerActionCommand extends BaseMVCActionCommand {
	private static Log log = LogFactoryUtil.getLog(KafkaConsumerActionCommand.class);

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		PortletPreferences prefs = actionRequest.getPreferences();

		String path = ParamUtil.get(actionRequest, "path", StringPool.BLANK);
		if (path != StringPool.BLANK) {
			log.debug("path : " + path);
			if (path.equalsIgnoreCase("start_kafka_consumer")) {
				if(!savePreferencences(actionRequest)) {
					return;
				}

				String brokers = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_BROKERS, "localhost:9092");
				String topic = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_TOPIC, "xml.materia.publicada");
				String format = prefs.getValue(ImportadorDouPortletKeys.P_SRC_ORIGIN_KAFKA_MESSAGE_OPTIONS_FORMAT, SourceFormat.XML.toString());
				if (!_douImporterKafkaService.isKafkaConsumerRunning()) {
					log.debug("Starting Kafka consumer...");

					Long groupId = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));
					Long idSecaoVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_SECAO_VOCAB, "0"));
					Long idArranjoSecaoVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ARRANJO_SECAO_VOCAB, "0"));
					Long idTipoMateriaVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TIPO_MATERIA_VOCAB, "0"));
					Long idEstrutura = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, "0"));
					Long idTemplate = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TEMPLATE, "0"));
					Long idOwner = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_USUARIO, "0"));
					Long threadSize = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_THREAD_SIZE, "1"));
					Long poolSize = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_POOL_SIZE, "1"));

					if (!ConfigurationsUtil.checkSocialNetworkConfiguration(prefs, actionRequest, _itemPublicacaoLocalService)) {
						log.error("Erro ao configurar publicação nas redes sociais");
						return;
					}

					Boolean xmlOriginKafkaMessageOptionsUsePropertiesFile = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_USE_PROPERTIES_FILE, "false"));
					String xmlOriginKafkaMessageOptionsPropertiesFileLocation = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_PROPERTIES_FILE_LOCATION, StringPool.BLANK);
					String xmlOriginKafkaMessageOptionsImgsPath = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_IMGS_PATH, StringPool.BLANK);
					String xmlOriginKafkaMessageOptionsGroup = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_GROUP, StringPool.BLANK);

					Map<String, String> kafkaCustomConfigurations = new HashMap<>();
					String configurationKeys = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_CUSTOM_PROPERTIES_NAMES, null);
					if (configurationKeys != null && configurationKeys.length() > 0) {
						String[] configurationKeysArray = StringUtil.split(configurationKeys, ',');
						for (int i = 0; i < configurationKeysArray.length; i++) {
							String value = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_CUSTOM_PROPERTY_PREFIX + configurationKeysArray[i], StringPool.BLANK);
							if (value != null && value.length() > 0) {
								kafkaCustomConfigurations.put(configurationKeysArray[i], value);
							}
						}
					}

					int assetDisplayPageType = Integer.parseInt(prefs.getValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_TYPE, "0"));
					long assetDisplayPageId = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_ID, "0"));
					String layoutUuid = prefs.getValue(ImportadorDouPortletKeys.P_LAYOUT_UUID, null);

					SourceFormat srcFormat;
					try {
						srcFormat = SourceFormat.valueOf(format);
					} catch (Exception e) {
						log.error("Invalid source format: " + format);
						SessionErrors.add(actionRequest, "error-xml-origin-kafka-consumer-not-connected");
						return;
					}

					// Overrides portal URL of the current thread service context
					String portalURL = prefs.getValue(ImportadorDouPortletKeys.P_HIGHLIGHTS_LINK_PORTAL_URL, null);
					if (portalURL != null && portalURL.length() > 0) {
						ServiceContext serviceContext = ServiceContextThreadLocal.popServiceContext();
						serviceContext.setPortalURL(portalURL);
						ServiceContextThreadLocal.pushServiceContext(serviceContext);
					}

					try {
						if (xmlOriginKafkaMessageOptionsUsePropertiesFile) {
							_douImporterKafkaService.startConsumer(
									topic,
									xmlOriginKafkaMessageOptionsPropertiesFileLocation,
									srcFormat,
									groupId,
									idSecaoVocab,
									idArranjoSecaoVocab,
									idTipoMateriaVocab,
									idEstrutura,
									idTemplate,
									idOwner,
									assetDisplayPageType,
									assetDisplayPageId,
									layoutUuid,
									(threadSize < poolSize ? threadSize : poolSize),
									xmlOriginKafkaMessageOptionsImgsPath);
						} else {
							_douImporterKafkaService.startConsumer(
									topic,
									brokers,
									xmlOriginKafkaMessageOptionsGroup,
									kafkaCustomConfigurations,
									srcFormat,
									groupId,
									idSecaoVocab,
									idArranjoSecaoVocab,
									idTipoMateriaVocab,
									idEstrutura,
									idTemplate,
									idOwner,
									assetDisplayPageType,
									assetDisplayPageId,
									layoutUuid,
									(threadSize < poolSize ? threadSize : poolSize),
									xmlOriginKafkaMessageOptionsImgsPath);
						}
					} catch (SystemException e) {
						SessionErrors.add(actionRequest, "error-xml-origin-kafka-consumer-not-connected");
						log.error("Impossible to start Kafka xml consumer", e);
					}
				}
			} else if (path.equalsIgnoreCase("stop_kafka_consumer")) {
				if (_douImporterKafkaService.isKafkaConsumerRunning()) {
					log.debug("Stoping Kafka consumer...");
					_douImporterKafkaService.stopConsumer();
				}
			} else {
				SessionErrors.add(actionRequest, "erro-inesperado");
				log.error("Invalid path value: " + path);
			}
		} else {
			SessionErrors.add(actionRequest, "erro-inesperado");
			log.error("Invalid call: missing path parameter");
		}
	}

	private Boolean savePreferencences(ActionRequest actionRequest) {

		PortletPreferences prefs = actionRequest.getPreferences();

		Boolean xmlOriginKafkaMessageOptionsUsePropertiesFile = ParamUtil.getBoolean(actionRequest, ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_USE_PROPERTIES_FILE, false);
		String xmlOriginKafkaMessageOptionsPropertiesFileLocation = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_PROPERTIES_FILE_LOCATION, StringPool.BLANK);
		String xmlOriginKafkaMessageOptionsBrokers = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_BROKERS, StringPool.BLANK);
		String xmlOriginKafkaMessageOptionsGroup = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_GROUP, StringPool.BLANK);
		String xmlOriginKafkaMessageOptionsImgsPath = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_IMGS_PATH, StringPool.BLANK);
		String xmlOriginKafkaMessageOptionsTopic = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_TOPIC, StringPool.BLANK);
		String srcOriginKafkaMessageOptionsFormat = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_SRC_ORIGIN_KAFKA_MESSAGE_OPTIONS_FORMAT, StringPool.BLANK);

		String rowIndexes = ParamUtil.get(actionRequest, "xmlOriginKafkaMessageOptionsCustomIndexes", StringPool.BLANK);
		String[] indexOfRows = rowIndexes.split(StringPool.COMMA);

		Map<String, String>kafkaCustomConfigurationEntries = new HashMap<>();
		for (int i = 0; i < indexOfRows.length; i++) {
			String key = ParamUtil.get(actionRequest, "customKey" + indexOfRows[i], StringPool.BLANK).trim();
			String value = ParamUtil.get(actionRequest, "customValue" + indexOfRows[i], StringPool.BLANK).trim();
			if (key.length() > 0) {
				kafkaCustomConfigurationEntries.put(key, value);
			}
		}

		try {
			prefs.setValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_USE_PROPERTIES_FILE, xmlOriginKafkaMessageOptionsUsePropertiesFile.toString());
			prefs.setValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_PROPERTIES_FILE_LOCATION, xmlOriginKafkaMessageOptionsPropertiesFileLocation);
			prefs.setValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_BROKERS ,xmlOriginKafkaMessageOptionsBrokers);
			prefs.setValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_GROUP ,xmlOriginKafkaMessageOptionsGroup);
			prefs.setValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_IMGS_PATH ,xmlOriginKafkaMessageOptionsImgsPath);
			prefs.setValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_TOPIC ,xmlOriginKafkaMessageOptionsTopic);
			prefs.setValue(ImportadorDouPortletKeys.P_SRC_ORIGIN_KAFKA_MESSAGE_OPTIONS_FORMAT, srcOriginKafkaMessageOptionsFormat);

			for (Entry<String, String> currentEntry : kafkaCustomConfigurationEntries.entrySet()) {
				prefs.setValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_CUSTOM_PROPERTY_PREFIX + currentEntry.getKey(), currentEntry.getValue());
			}

			String customConfigurationKeys = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_CUSTOM_PROPERTIES_NAMES, null);
			if (customConfigurationKeys != null && customConfigurationKeys.length() > 0) {
				String[] keys = StringUtil.split(customConfigurationKeys, StringPool.COMMA);
				for (int i = 0; i < keys.length; i++) {
					if (!kafkaCustomConfigurationEntries.containsKey(keys[i])) {
						prefs.reset(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_CUSTOM_PROPERTY_PREFIX + keys[i]);
					}
				}
			}

			customConfigurationKeys = String.join(StringPool.COMMA, kafkaCustomConfigurationEntries.keySet().toArray(new String[kafkaCustomConfigurationEntries.size()]));
			prefs.setValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_CUSTOM_PROPERTIES_NAMES, customConfigurationKeys);

			prefs.store();
		} catch (ReadOnlyException | ValidatorException | IOException e) {
			SessionErrors.add(actionRequest, "erro-inesperado");
			log.error(e.getMessage());
			return false;
		}

		if (!validatePrefs(prefs, actionRequest)) {
			log.error("Invalid parameters");
			return false;
		}

		return true;
	}

	private boolean validatePrefs(PortletPreferences prefs, PortletRequest request) {
		boolean valid = true;

		Boolean xmlOriginKafkaMessageOptionsUsePropertiesFile = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_USE_PROPERTIES_FILE, "false"));
		if (xmlOriginKafkaMessageOptionsUsePropertiesFile) {
			String xmlOriginKafkaMessageOptionsPropertiesFileLocation = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_PROPERTIES_FILE_LOCATION, StringPool.BLANK);
			if (xmlOriginKafkaMessageOptionsPropertiesFileLocation != null && xmlOriginKafkaMessageOptionsPropertiesFileLocation.length() > 0) {
				File kafkaConsumerPropertiesFile = new File(xmlOriginKafkaMessageOptionsPropertiesFileLocation);
				if(!kafkaConsumerPropertiesFile.exists() || !kafkaConsumerPropertiesFile.isFile()) {
					valid = false;
					SessionErrors.add(request, "error-xml-origin-kafka-properties-file-missing");
				}
			} else {
				valid = false;
				SessionErrors.add(request, "error-xml-origin-kafka-properties-file-missing");
			}
		} else {
			String xmlOriginKafkaMessageOptionsBrokers = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_BROKERS, StringPool.BLANK);
			if (xmlOriginKafkaMessageOptionsBrokers == null || xmlOriginKafkaMessageOptionsBrokers.length() == 0) {
				valid = false;
				SessionErrors.add(request, "error-xml-origin-kafka-brokers-missing");
			}
			String xmlOriginKafkaMessageOptionsTopic = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_TOPIC, StringPool.BLANK);
			if (xmlOriginKafkaMessageOptionsTopic == null || xmlOriginKafkaMessageOptionsTopic.length() == 0) {
				valid = false;
				SessionErrors.add(request, "error-xml-origin-kafka-topic-missing");
			}
			String xmlOriginKafkaMessageOptionsGroup = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_GROUP, StringPool.BLANK);
			if (xmlOriginKafkaMessageOptionsGroup == null || xmlOriginKafkaMessageOptionsGroup.length() == 0) {
				SessionMessages.add(request, "warning-xml-origin-kafka-group-missing");
			}
		}
		String xmlOriginKafkaMessageOptionsImgsPath = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_IMGS_PATH, StringPool.BLANK);
		if (xmlOriginKafkaMessageOptionsImgsPath != null && xmlOriginKafkaMessageOptionsImgsPath.length() > 0) {
			File imgsDir = new File(xmlOriginKafkaMessageOptionsImgsPath);
			if(!imgsDir.exists() || !imgsDir.isDirectory()) {
				valid = false;
				SessionErrors.add(request, "warning-xml-origin-kafka-img-path-missing");
			}
		}

		return valid;
	}

	@Reference
	private DouImporterKafkaService _douImporterKafkaService;
	@Reference
	private ItemPublicacaoLocalService _itemPublicacaoLocalService;
}

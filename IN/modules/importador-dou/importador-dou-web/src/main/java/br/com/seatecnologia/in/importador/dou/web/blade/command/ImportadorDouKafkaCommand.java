package br.com.seatecnologia.in.importador.dou.web.blade.command;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.felix.service.command.Descriptor;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.kafka.DouImporterKafkaService;
import br.com.seatecnologia.in.importador.dou.kafka.SourceFormat;
import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;
import br.com.seatecnologia.in.importador.dou.web.portlet.util.ConfigurationsUtil;
import br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService;

@Component(
	property = {
		"osgi.command.function=importadordoukafka",
		"osgi.command.scope=blade"
	},
	service = ImportadorDouKafkaCommand.class
)
public class ImportadorDouKafkaCommand {

	@Descriptor("controls Importador DOU Kafka consumer")
	public void importadordoukafka(@Descriptor("command: [start|stop|status]") String cmd) {
		if ("status".equals(cmd)) {
			System.out.println(
					"Importador DOU Kafka is " +
							(getDouImporterKafkaService().isKafkaConsumerRunning() ? "running" : "stopped")
					);
		}
		else if ("start".equals(cmd)) {
			if (!getDouImporterKafkaService().isKafkaConsumerRunning()) {
				System.out.println("Starting kafka consumer...");
				PortletPreferences prefs = getPortlePreferences();

				if (prefs == null) {
					System.out.println("ERROR: Could not get kafka consumer configurations");
					return;
				}

				String brokers = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_BROKERS, "localhost:9092");
				String topic = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_TOPIC, "dou");
				String format = prefs.getValue(ImportadorDouPortletKeys.P_SRC_ORIGIN_KAFKA_MESSAGE_OPTIONS_FORMAT, SourceFormat.XML.toString());

				long groupId = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));
				long idSecaoVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_SECAO_VOCAB, "0"));
				long idArranjoSecaoVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ARRANJO_SECAO_VOCAB, "0"));
				long idTipoMateriaVocab = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TIPO_MATERIA_VOCAB, "0"));
				long idEstrutura = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, "0"));
				long idTemplate = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TEMPLATE, "0"));
				long idOwner = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_USUARIO, "0"));
				long threadSize = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_THREAD_SIZE, "1"));
				long poolSize = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_POOL_SIZE, "1"));

				if (!ConfigurationsUtil.checkSocialNetworkConfiguration(prefs, null, _itemPublicacaoLocalService)) {
					System.out.println("ERROR: Erro ao configurar publicação nas redes sociais");
					return;
				}

				boolean xmlOriginKafkaMessageOptionsUsePropertiesFile = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_USE_PROPERTIES_FILE, "false"));
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
					System.out.println("ERROR: Invalid source format: " + format + ". Please check configurations");
					return;
				}

				Locale locale;
				try {
					locale = PortalUtil.getSiteDefaultLocale(groupId);
				} catch (PortalException e1) {
					System.out.println("ERROR: could not get site locale (" + e1.getMessage() + "). Please check configurations");
					return;
				}

				ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();
				serviceContext.setUserId(idOwner);
				serviceContext.setScopeGroupId(groupId);
				serviceContext.setLanguageId(LocaleUtil.toLanguageId(locale));

				// Overrides portal URL of the current thread service context
				String portalURL = prefs.getValue(ImportadorDouPortletKeys.P_HIGHLIGHTS_LINK_PORTAL_URL, null);
				if (portalURL != null && portalURL.length() > 0) {
					serviceContext.setPortalURL(portalURL);
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
					System.out.println("ERROR: Impossible to start Kafka xml consumer (" + e.getMessage() + "). Please check configurations");
					return;
				}
			}
			System.out.println("Kafka consumer started!");
		}
		else if ("stop".equals(cmd)) {
			if (getDouImporterKafkaService().isKafkaConsumerRunning()) {
				System.out.println("Stopping kafka consumer...");
				getDouImporterKafkaService().stopConsumer();
			}
			System.out.println("Kafka consumer stopped!");
		}
		else {
			System.out.println("ERROR: Unsupported command: " + cmd);
		}
	}

	private PortletPreferences getPortlePreferences() {
		try {
			long companyId = PortalUtil.getDefaultCompanyId();
			long plid = PortalUtil.getControlPanelPlid(companyId);
			PortletPreferencesIds portletPreferencesIds =
					PortletPreferencesFactoryUtil.getPortletPreferencesIds(
							companyId, 0, plid,
							ImportadorDouPortletKeys.ImportadorDou,
							PortletPreferencesFactoryConstants.SETTINGS_SCOPE_PORTLET_INSTANCE);

			PortletPreferences portletPreferences =
					PortletPreferencesLocalServiceUtil.getStrictPreferences(portletPreferencesIds);

			ServiceContext serviceContext = new ServiceContext();
			serviceContext.setCompanyId(companyId);
			serviceContext.setPlid(plid);
			serviceContext.setPortletPreferencesIds(portletPreferencesIds);

			serviceContext.setPathMain(PortalUtil.getPathMain());
			serviceContext.setPathFriendlyURLPrivateGroup(
				PortalUtil.getPathFriendlyURLPrivateGroup());
			serviceContext.setPathFriendlyURLPrivateUser(
				PortalUtil.getPathFriendlyURLPrivateUser());
			serviceContext.setPathFriendlyURLPublic(
				PortalUtil.getPathFriendlyURLPublic());

			ServiceContextThreadLocal.pushServiceContext(serviceContext);

			return portletPreferences;
		}
		catch (PortalException e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	private DouImporterKafkaService getDouImporterKafkaService() {
		return _douImporterKafkaService;
	}

	@Reference
	private ItemPublicacaoLocalService _itemPublicacaoLocalService;

	@Reference
	private DouImporterKafkaService _douImporterKafkaService;

	@Reference
	private PortletPreferencesLocalService _portletPreferencesLocalService;
}

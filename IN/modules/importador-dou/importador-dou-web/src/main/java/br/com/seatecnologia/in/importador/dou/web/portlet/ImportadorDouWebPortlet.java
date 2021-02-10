package br.com.seatecnologia.in.importador.dou.web.portlet;

import com.liferay.asset.display.page.item.selector.criterion.AssetDisplayPageSelectorCriterion;
import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.ClassType;
import com.liferay.asset.kernel.model.ClassTypeReader;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.item.selector.ItemSelector;
import com.liferay.item.selector.ItemSelectorCriterion;
import com.liferay.item.selector.ItemSelectorReturnType;
import com.liferay.item.selector.criteria.UUIDItemSelectorReturnType;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.item.selector.criterion.LayoutItemSelectorCriterion;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactory;
import com.liferay.portal.kernel.portlet.RequestBackedPortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.file.service.ImportadorDouFileService;
import br.com.seatecnologia.in.importador.dou.kafka.DouImporterKafkaService;
import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;
import br.com.seatecnologia.migracao.service.ItemMigracaoLocalService;
import br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService;

/**
 * @author mgsasaki	
 */
@Component(
	immediate = true,
	property = {
		"com.liferay.portlet.add-default-resource=true",
		"com.liferay.portlet.display-category=category.hidden",
		"com.liferay.portlet.scopeable=true",
		"javax.portlet.display-name=Importador DOU",
		"javax.portlet.expiration-cache=0",
		"javax.portlet.init-param.portlet-title-based-navigation=true",
		"javax.portlet.init-param.template-path=/",
		"javax.portlet.init-param.view-template=/view.jsp",
		"javax.portlet.init-param.edit-template=/edit.jsp",
		"javax.portlet.name=" + ImportadorDouPortletKeys.ImportadorDou,
		"javax.portlet.portlet-mode=text/html;view,edit",
		"javax.portlet.resource-bundle=content.Language",
		"javax.portlet.security-role-ref=administrator",
		"javax.portlet.supports.mime-type=text/html"
	},
	service = Portlet.class
)

public class ImportadorDouWebPortlet extends MVCPortlet {

	private static Log log = LogFactoryUtil.getLog(ImportadorDouWebPortlet.class);

	private Boolean isKafkaConsumerRunning() {
		Boolean kafkaConsumerIsRunning = false;

		kafkaConsumerIsRunning = _douImporterKafkaService.isKafkaConsumerRunning();

		return kafkaConsumerIsRunning;
	}

	private String checkAuthorization(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException
	{
		_itemPublicacaoLocalService.facebookCancelAuthorizationProccess();

		String auth = ParamUtil.get(renderRequest, "auth", "");
		if (auth!= "") {
			log.info("AUTH: " + auth);
		}
		return editTemplate;
	}

	@Override
	public void doView(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		log.debug("Entering");

		try {
			PortletPreferences prefs = renderRequest.getPreferences();
			if(prefs.getValue(ImportadorDouPortletKeys.P_BASE_PATH_XMLS, "").equals("")) {
				doEdit(renderRequest, renderResponse);
				return;
			}

			String basePathXMLs = prefs.getValue(ImportadorDouPortletKeys.P_BASE_PATH_XMLS, "");

			if(!basePathXMLs.isEmpty()){
				long countItemImportacao = _importadorDouFileService.contarItensParaImportacao(basePathXMLs);
				renderRequest.setAttribute("countItemImportacao", countItemImportacao);
			}else{
				SessionErrors.add(renderRequest, "erro-diretorio-xml");
			}

			long countItemMigracao = _itemMigracaoLocalService.contarItensParaMigracao();
			renderRequest.setAttribute("countItemMigracao", countItemMigracao);

			Boolean enableAutomation = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_AUTOMATION, "false"));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ENABLE_AUTOMATION, enableAutomation);

			String taskTime = prefs.getValue(ImportadorDouPortletKeys.P_TASK_TIME, "00:00");
			renderRequest.setAttribute("proximoAgendamento", taskTime);

			renderRequest.setAttribute(ImportadorDouPortletKeys.ATTR_KAFKA_CONSUMER_IS_RUNNING, isKafkaConsumerRunning());

			Date lastMigrationDate = getLastMigrationDate();
			if (lastMigrationDate != null) {
				renderRequest.setAttribute("latestMigrationDate", lastMigrationDate);

				long latestMigrationCount = getMigrationDateCount(lastMigrationDate);
				renderRequest.setAttribute("latestMigrationCount", latestMigrationCount);
			}
		} catch (SystemException e) {
			SessionErrors.add(renderRequest, "erro-inesperado");
			log.error(e.getMessage());
		}

		super.doView(renderRequest, renderResponse);
	}

	private long getMigrationDateCount(Date date) {
		long count = 0;

		DynamicQuery itemMigracaoQuery = _itemMigracaoLocalService.dynamicQuery();
		itemMigracaoQuery.add(RestrictionsFactoryUtil.eq("dataReferencia", date));
		itemMigracaoQuery.add(RestrictionsFactoryUtil.eq("migrado", true));

		count = _itemMigracaoLocalService.dynamicQueryCount(itemMigracaoQuery);

		return count;
	}

	private Date getLastMigrationDate() {
		Date latestMigrationDate = null;
		DynamicQuery itemMigracaoQuery = _itemMigracaoLocalService.dynamicQuery();
		itemMigracaoQuery.add(RestrictionsFactoryUtil.eq("migrado", true));
		itemMigracaoQuery.setProjection(ProjectionFactoryUtil.max("dataReferencia"));

		try {
			List<Date>list = _itemMigracaoLocalService.dynamicQuery(itemMigracaoQuery);
			if (list.size() > 0 && list.get(0) != null) {
				latestMigrationDate = list.get(0);
			}
		} catch (SystemException e2) {
			e2.printStackTrace();
		}

		return latestMigrationDate;
	}

	@Override
	public void doEdit(RenderRequest renderRequest, RenderResponse renderResponse)
			throws IOException, PortletException {
		log.debug("Entering");

		try {
			String path = checkAuthorization(renderRequest, renderResponse);
			if (path != editTemplate) {
				log.debug("PATH: " + path);
				include(path, renderRequest, renderResponse);
				return;
			}

			PortletPreferences prefs = renderRequest.getPreferences();

//			RequestBackedPortletURLFactory requestBackedPortletURLFactory = RequestBackedPortletURLFactoryUtil.create(renderRequest);
//			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes = new ArrayList<>();
//			desiredItemSelectorReturnTypes.add(new SiteItemSelectorReturnType());
//			SiteItemSelectorCriterion siteItemSelectorCriterion = new SiteItemSelectorCriterion();
//			siteItemSelectorCriterion.setDesiredItemSelectorReturnTypes(desiredItemSelectorReturnTypes);
//			PortletURL itemSelectorURL = _itemSelector.getItemSelectorURL(requestBackedPortletURLFactory, "sampleTestSelectItem",
//					siteItemSelectorCriterion);

			// Carega as estruturas
			List<DDMStructure> estruturas = _ddmStructureLocalService.getStructures();
//			Collections.sort(estruturas, new PropertyComparator("name"));
			renderRequest.setAttribute("estruturas", estruturas);
			
			// Carrega os templates existentes
			Long idEstrutura = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, "0"));
			if(idEstrutura != null && idEstrutura != 0) {
				List<DDMTemplate> templates = _ddmTemplateLocalService.getTemplates(idEstrutura);
//				Collections.sort(templates, new PropertyComparator("name"));
				renderRequest.setAttribute("templates", templates);
			}

			Long idTemplate = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TEMPLATE, "0"));
			if(idTemplate == null || idTemplate == 0) {
				SessionMessages.add(renderRequest, "msg-selecione-template");
			}

			// Recupera a lista de usuários para seleção do dono do conteúdo migrado
			DynamicQuery queryUsuariosAtivos = DynamicQueryFactoryUtil.forClass(User.class);
			queryUsuariosAtivos.add(PropertyFactoryUtil.forName("status").eq(0));
			List<User> usuarios = new ArrayList<User>(_userLocalService.dynamicQuery(queryUsuariosAtivos));
//			Collections.sort(usuarios, new PropertyComparator("fullName"));
			renderRequest.setAttribute("usuarios", usuarios);

			// Recupera a lista de grupos para seleção do local de destino do conteúdo migrado
			DynamicQuery queryGruposAtivos = DynamicQueryFactoryUtil.forClass(Group.class);
			queryGruposAtivos.add(PropertyFactoryUtil.forName("active").eq(true));
			List<Group> grupos = new ArrayList<Group>(_groupLocalService.dynamicQuery(queryGruposAtivos));
//			Collections.sort(grupos, new PropertyComparator("name"));
			renderRequest.setAttribute("grupos", grupos);

			// Recupera a lista de vocabulários para serem preenchidos
			List<AssetVocabulary> assetVocabularies = _assetVocabularyLocalService.getAssetVocabularies(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
			renderRequest.setAttribute("vocabs", assetVocabularies);

			String xmlOrigin = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN, ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_FILE_SYSTEM);

			Boolean xmlOriginKafkaMessageOptionsUsePropertiesFile = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_USE_PROPERTIES_FILE, "false"));
			String xmlOriginKafkaMessageOptionsPropertiesFileLocation = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_PROPERTIES_FILE_LOCATION, "");
			String xmlOriginKafkaMessageOptionsBrokers = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_BROKERS, "");
			String xmlOriginKafkaMessageOptionsGroup = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_GROUP, "");
			String xmlOriginKafkaMessageOptionsTopic = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_TOPIC, "");
			String url = prefs.getValue(ImportadorDouPortletKeys.URL_SERVER, "");
			Boolean enableNotification = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.ENABLE_NOTIFICATION, "false"));

			String xmlOriginKafkaMessageOptionsImgsPath = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_IMGS_PATH, "");

			//Carrega as preferencias
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_THREAD_SIZE, prefs.getValue(ImportadorDouPortletKeys.P_THREAD_SIZE, "21"));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_POOL_SIZE, prefs.getValue(ImportadorDouPortletKeys.P_POOL_SIZE, "500"));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_BASE_PATH_XMLS, prefs.getValue(ImportadorDouPortletKeys.P_BASE_PATH_XMLS, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ENABLE_AUTOMATION, prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_AUTOMATION, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_TASK_TIME, prefs.getValue(ImportadorDouPortletKeys.P_TASK_TIME, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ID_ESTRUTURA, idEstrutura);
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ID_TEMPLATE, idTemplate);
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ID_USUARIO, Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_USUARIO, "0")));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ID_GRUPO, Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0")));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ID_SECAO_VOCAB, Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_SECAO_VOCAB, "0")));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ID_ARRANJO_SECAO_VOCAB, Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ARRANJO_SECAO_VOCAB, "0")));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ID_TIPO_MATERIA_VOCAB, Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TIPO_MATERIA_VOCAB, "0")));

			renderRequest.setAttribute(ImportadorDouPortletKeys.P_IMPORT_XML_FILE_TIMEOUT, Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_IMPORT_XML_FILE_TIMEOUT, "10")));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_MIGRATION_TIMEOUT, Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_MIGRATION_TIMEOUT, "10")));

			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_TYPE, Integer.parseInt(prefs.getValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_TYPE, "0")));
			Long assetDisplayPageId = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_ID, "0"));
			if (assetDisplayPageId != 0) {
				renderRequest.setAttribute(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_ID, assetDisplayPageId);
			}
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_LAYOUT_UUID, prefs.getValue(ImportadorDouPortletKeys.P_LAYOUT_UUID, StringPool.BLANK));

			renderRequest.setAttribute(ImportadorDouPortletKeys.P_HIGHLIGHTS_CATEGORY_IDS, prefs.getValue(ImportadorDouPortletKeys.P_HIGHLIGHTS_CATEGORY_IDS, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_HIGHLIGHTS_LINK_PORTAL_URL, prefs.getValue(ImportadorDouPortletKeys.P_HIGHLIGHTS_LINK_PORTAL_URL, ""));

			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ENABLE_TWITTER, Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_TWITTER, "false")));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY, prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET, prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN, prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET, prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET, ""));
			String twitterScreenName = _getTwitterScreenName(prefs);
			if (twitterScreenName != null) {
				renderRequest.setAttribute(ImportadorDouPortletKeys.P_TWITTER_SCREEN_NAME, twitterScreenName);
			}

			renderRequest.setAttribute(ImportadorDouPortletKeys.P_ENABLE_FACEBOOK, Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_FACEBOOK, "false")));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID, prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_FACEBOOK_APP_ID, prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_ID, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_FACEBOOK_APP_SECRET, prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_SECRET, ""));
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_FACEBOOK_CLIENT_TOKEN, prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_CLIENT_TOKEN, ""));
			String facebookPageName = _getFacebookPageName(prefs);
			if (facebookPageName != null) {
				renderRequest.setAttribute(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_NAME, facebookPageName);
			}

			renderRequest.setAttribute(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_USE_PROPERTIES_FILE, xmlOriginKafkaMessageOptionsUsePropertiesFile);
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_PROPERTIES_FILE_LOCATION, xmlOriginKafkaMessageOptionsPropertiesFileLocation);
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_BROKERS, xmlOriginKafkaMessageOptionsBrokers);
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_GROUP, xmlOriginKafkaMessageOptionsGroup);
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_TOPIC, xmlOriginKafkaMessageOptionsTopic);
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_IMGS_PATH, xmlOriginKafkaMessageOptionsImgsPath);

			renderRequest.setAttribute(ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_FILE_SYSTEM, ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_FILE_SYSTEM);
			renderRequest.setAttribute(ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_KAFKA_MESSAGE, ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_KAFKA_MESSAGE);
			renderRequest.setAttribute(ImportadorDouPortletKeys.ATTR_XML_ORIGIN_IS_FILE_SYSTEM, xmlOrigin.equals(ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_FILE_SYSTEM));
			renderRequest.setAttribute(ImportadorDouPortletKeys.ATTR_XML_ORIGIN_IS_KAFKA_MESSAGE, xmlOrigin.equals(ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_KAFKA_MESSAGE));
			renderRequest.setAttribute(ImportadorDouPortletKeys.URL_SERVER, url);
			renderRequest.setAttribute(ImportadorDouPortletKeys.ENABLE_NOTIFICATION, enableNotification);

			
			renderRequest.setAttribute(ImportadorDouPortletKeys.ATTR_KAFKA_CONSUMER_IS_RUNNING, isKafkaConsumerRunning());

			String configurationKeys = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_CUSTOM_PROPERTIES_NAMES, null);
			if (configurationKeys != null && configurationKeys.length() > 0) {
				String[] configurationKeysArray = StringUtil.split(configurationKeys, ',');
				Map<String, String> kafkaCustomConfigurations = new HashMap<>();
				for (int i = 0; i < configurationKeysArray.length; i++) {
					String value = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN_KAFKA_MESSAGE_OPTIONS_CUSTOM_PROPERTY_PREFIX + configurationKeysArray[i], "");
					if (value != null && value.length() > 0) {
						kafkaCustomConfigurations.put(configurationKeysArray[i], value);
					}
				}
				if (kafkaCustomConfigurations.size() > 0) {
					renderRequest.setAttribute(ImportadorDouPortletKeys.ATTR_KAFKA_CUSTOM_CONFIGURATIONS, kafkaCustomConfigurations);
				}
			}

			String assetDisplayPageItemSelectorURL = _getAssetDisplayPageItemSelectorURL(renderRequest);
			renderRequest.setAttribute("itemSelectorURL", assetDisplayPageItemSelectorURL);
			String assetDisplayPageName = _getAssetDisplayPageName(prefs);
			renderRequest.setAttribute("assetDisplayPageName", assetDisplayPageName);
			String defaultAssetDisplayPageName = _getDefaultAssetDisplayPageName(prefs);
			renderRequest.setAttribute("defaultAssetDisplayPageName", defaultAssetDisplayPageName);
			String assetTypeName = _getAssetTypeName(prefs);
			renderRequest.setAttribute("assetTypeName", assetTypeName);
		} catch (SystemException | PortalException e) {
			SessionErrors.add(renderRequest, "erro-inesperado");
			log.error(e.getMessage());
		}

		super.doEdit(renderRequest, renderResponse);
	}

	private String _getTwitterScreenName(PortletPreferences prefs) {
		Boolean twitterEnabled = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_TWITTER, "false"));
		String twitterConsumerKey = prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY, null);
		String twitterConsumerSecret = prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET, null);
		String twitterAccessToken = prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN, null);
		String twitterAccessTokenSecret = prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET, null);
		String twitterScreenName = null;

		if (twitterConsumerKey != null && twitterConsumerSecret != null && twitterAccessToken != null && twitterAccessTokenSecret != null) {
			_itemPublicacaoLocalService.configureTwitter(true, twitterConsumerKey, twitterConsumerSecret, twitterAccessToken, twitterAccessTokenSecret);

			twitterScreenName = _itemPublicacaoLocalService.getTwitterAccountName();
			if (!twitterEnabled) {
				_itemPublicacaoLocalService.configureTwitter(false, null, null, null, null);
			}
		}

		return twitterScreenName;
	}

	private String _getFacebookPageName(PortletPreferences prefs) {
		Boolean enableFacebook = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_FACEBOOK, "false"));
		String facebookPageId = prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID, null);
		String facebookPageAccessToken = prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, null);
		_itemPublicacaoLocalService.configureFacebook(true, facebookPageId, facebookPageAccessToken);

		String facebookPageName = _itemPublicacaoLocalService.getFacebookPageName();
		if (!enableFacebook) {
			_itemPublicacaoLocalService.configureFacebook(false, null, null);
		}
		return facebookPageName;
	}

	public void limparXMLs(ActionRequest actionRequest, ActionResponse actionResponse)
			throws PortalException {
		log.debug("entering");
		try {
			_itemMigracaoLocalService.limparItemsMigracao();
		}
		catch (Exception pe) {
			Logger.getLogger(ImportadorDouWebPortlet.class.getName()).log(
					Level.SEVERE, null, pe);
			SessionErrors.add(actionRequest, "erro-inesperado");
		}
	}

	public void removeAuthorization(ActionRequest actionRequest, ActionResponse actionResponse) {
		String socialNetwork = ParamUtil.get(actionRequest, "auth", "");

		PortletPreferences prefs = actionRequest.getPreferences();
		if (socialNetwork.equalsIgnoreCase("facebook")) {
			try {
				prefs.reset(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN);
				prefs.reset(ImportadorDouPortletKeys.P_ENABLE_FACEBOOK);
				prefs.store();
				SessionMessages.add(actionRequest, "msg-auth-success");
			} catch (IOException | ReadOnlyException | ValidatorException e) {
				SessionErrors.add(actionRequest, "erro-inesperado");
				log.error(e.getMessage());
			}
		} else if (socialNetwork.equalsIgnoreCase("twitter")) {
			try {
				prefs.reset(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN);
				prefs.reset(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET);
				prefs.reset(ImportadorDouPortletKeys.P_ENABLE_TWITTER);
				prefs.store();
				SessionMessages.add(actionRequest, "msg-auth-success");
			} catch (IOException | ReadOnlyException | ValidatorException e) {
				SessionErrors.add(actionRequest, "erro-inesperado");
				log.error(e.getMessage());
			}
		}
	}

	@SuppressWarnings("deprecation")
	private String _getAssetDisplayPageItemSelectorURL(RenderRequest request) throws PortalException {
		PortletPreferences prefs = request.getPreferences();
		Long idEstrutura = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, "0"));
		Long idGrupo = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));
		if (idGrupo == 0) {
			return null;
		}
		String _eventName = "importadorDouWebAssetDisplayPageItemSelectorEvent";
		Boolean _showPortletLayouts = true;
		String _layoutUuid = prefs.getValue(ImportadorDouPortletKeys.P_LAYOUT_UUID, null);
		////////////////////////////////////////
		
		ItemSelector itemSelector = _itemSelector;

		List<ItemSelectorCriterion> criteria = new ArrayList<>();

		AssetDisplayPageSelectorCriterion assetDisplayPageSelectorCriterion =
			new AssetDisplayPageSelectorCriterion();

		assetDisplayPageSelectorCriterion.setClassNameId(PortalUtil.getClassNameId(JournalArticle.class));
		assetDisplayPageSelectorCriterion.setClassTypeId(idEstrutura);

		List<ItemSelectorReturnType>
			desiredAssetDisplayPageItemSelectorReturnTypes = new ArrayList<>();

		desiredAssetDisplayPageItemSelectorReturnTypes.add(
			new UUIDItemSelectorReturnType());

		assetDisplayPageSelectorCriterion.setDesiredItemSelectorReturnTypes(
			desiredAssetDisplayPageItemSelectorReturnTypes);

		criteria.add(assetDisplayPageSelectorCriterion);

		if (_showPortletLayouts) {
			LayoutItemSelectorCriterion layoutItemSelectorCriterion =
				new LayoutItemSelectorCriterion();

			layoutItemSelectorCriterion.setCheckDisplayPage(true);
//			layoutItemSelectorCriterion.setShowHiddenPages(true);

			List<ItemSelectorReturnType> desiredItemSelectorReturnTypes =
				new ArrayList<>();

			desiredItemSelectorReturnTypes.add(
				new UUIDItemSelectorReturnType());

			layoutItemSelectorCriterion.setDesiredItemSelectorReturnTypes(
				desiredItemSelectorReturnTypes);

			criteria.add(layoutItemSelectorCriterion);
		}

		ThemeDisplay themeDisplay = (ThemeDisplay) request.getAttribute(WebKeys.THEME_DISPLAY);
		themeDisplay.setScopeGroupId(idGrupo);
		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		RequestBackedPortletURLFactory requestBackedPortletURLFactory = 
				RequestBackedPortletURLFactoryUtil.create(request);
		PortletURL itemSelectorURL = itemSelector.getItemSelectorURL(
				requestBackedPortletURLFactory,
			_eventName, criteria.toArray(new ItemSelectorCriterion[0]));

		itemSelectorURL.setParameter("layoutUuid", _layoutUuid);

		return itemSelectorURL.toString();
	}
	
	private String _getAssetDisplayPageName(long assetDisplayPageId) {
		if (assetDisplayPageId == 0) {
			return StringPool.BLANK;
		}

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.
				fetchLayoutPageTemplateEntry(assetDisplayPageId);

		if (layoutPageTemplateEntry == null) {
			return StringPool.BLANK;
		}

		return layoutPageTemplateEntry.getName();
	}
	
	private String _getLayoutBreadcrumb(Layout layout) throws PortalException {
		Locale locale = PortalUtil.getSiteDefaultLocale(layout.getGroupId());

		List<Layout> ancestors = layout.getAncestors();

		StringBundler sb = new StringBundler(4 * ancestors.size() + 5);

		if (layout.isPrivateLayout()) {
			sb.append(LanguageUtil.get(locale, "private-pages"));
		}
		else {
			sb.append(LanguageUtil.get(locale, "public-pages"));
		}

		sb.append(StringPool.SPACE);
		sb.append(StringPool.GREATER_THAN);
		sb.append(StringPool.SPACE);

		Collections.reverse(ancestors);

		for (Layout ancestor : ancestors) {
			sb.append(HtmlUtil.escape(ancestor.getName(locale)));
			sb.append(StringPool.SPACE);
			sb.append(StringPool.GREATER_THAN);
			sb.append(StringPool.SPACE);
		}

		sb.append(HtmlUtil.escape(layout.getName(locale)));

		return sb.toString();
	}
	
	private String _getAssetDisplayPageName(PortletPreferences portletPreferences) throws PortalException {
		Long assetDisplayPageId = Long.valueOf(portletPreferences.getValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_ID, "0"));
		String assetDisplayPageName = _getAssetDisplayPageName(assetDisplayPageId);

		if (Validator.isNotNull(assetDisplayPageName)) {
			return assetDisplayPageName;
		}

		String layoutUuid = portletPreferences.getValue(ImportadorDouPortletKeys.P_LAYOUT_UUID, null);

		if (Validator.isNull(layoutUuid)) {
			return StringPool.BLANK;
		}

		Long groupId = Long.valueOf(portletPreferences.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));

		Layout selLayout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
			layoutUuid, groupId, false);

		if (selLayout == null) {
			selLayout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
				layoutUuid, groupId, true);
		}

		if (selLayout != null) {
			return _getLayoutBreadcrumb(selLayout);
		}

		return StringPool.BLANK;
	}
	
	private String _getDefaultAssetDisplayPageName(PortletPreferences portletPreferences) {
		String _defaultAssetDisplayPageName = null;

		Long _groupId = Long.valueOf(portletPreferences.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));
		Long _classNameId = PortalUtil.getClassNameId(JournalArticle.class);
		Long _classTypeId = Long.valueOf(portletPreferences.getValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, "0"));

		LayoutPageTemplateEntry layoutPageTemplateEntry = null;

		layoutPageTemplateEntry =
			LayoutPageTemplateEntryServiceUtil.
				fetchDefaultLayoutPageTemplateEntry(
					_groupId, _classNameId, _classTypeId);

		if (layoutPageTemplateEntry != null) {
			_defaultAssetDisplayPageName = layoutPageTemplateEntry.getName();
		}

		return _defaultAssetDisplayPageName;
	}
	
	private String _getAssetTypeName(PortletPreferences portletPreferences) throws PortalException {
		String _assetTypeName = null;
		Long _groupId = Long.valueOf(portletPreferences.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));
		Long _classTypeId = Long.valueOf(portletPreferences.getValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, "0"));

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.
				getAssetRendererFactoryByClassNameId(PortalUtil.getClassNameId(JournalArticle.class));

		_assetTypeName = assetRendererFactory.getTypeName(
			PortalUtil.getSiteDefaultLocale(_groupId));

		if (_classTypeId > 0) {
			ClassTypeReader classTypeReader =
				assetRendererFactory.getClassTypeReader();

			ClassType classType = classTypeReader.getClassType(
				_classTypeId, PortalUtil.getSiteDefaultLocale(_groupId));

			_assetTypeName = classType.getName();
		}

		return _assetTypeName;
	}

	@Reference(unbind = "-")
	protected void setItemMigracaoService(ItemMigracaoLocalService itemMigracaoLocalService) {
		_itemMigracaoLocalService = itemMigracaoLocalService;
	}

	@Reference(unbind = "-")
	protected void setItemPublicacaoService(ItemPublicacaoLocalService itemPublicacaoLocalService) {
		_itemPublicacaoLocalService = itemPublicacaoLocalService;
	}

	private ItemMigracaoLocalService _itemMigracaoLocalService;
	private ItemPublicacaoLocalService _itemPublicacaoLocalService;

	@Reference
	private DouImporterKafkaService _douImporterKafkaService;
	@Reference
	private ImportadorDouFileService _importadorDouFileService;
	@Reference
	private LayoutPageTemplateEntryLocalService _layoutPageTemplateEntryLocalService;
	@Reference
	private LayoutLocalService _layoutLocalService;
	@Reference
	private ItemSelector _itemSelector;
	@Reference
	private GroupLocalService _groupLocalService;
	@Reference
	private UserLocalService _userLocalService;
	@Reference
	AssetVocabularyLocalService _assetVocabularyLocalService;
	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;
	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;
}
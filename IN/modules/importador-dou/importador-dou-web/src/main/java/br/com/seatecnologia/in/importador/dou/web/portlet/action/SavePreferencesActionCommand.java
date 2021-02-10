package br.com.seatecnologia.in.importador.dou.web.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.File;
import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;
import br.com.seatecnologia.in.importador.dou.web.portlet.util.ConfigurationsUtil;
import br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + ImportadorDouPortletKeys.ImportadorDou,
			"mvc.command.name=/save_preferences"
		},
		service = MVCActionCommand.class
	)

public class SavePreferencesActionCommand extends BaseMVCActionCommand {
	private static Log log = LogFactoryUtil.getLog(SavePreferencesActionCommand.class);

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {

		log.debug("Entering");
		PortletPreferences prefs = actionRequest.getPreferences();

		String threadSize = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_THREAD_SIZE, "");
		String poolSize = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_POOL_SIZE, "");
		String basePathXMLs = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_BASE_PATH_XMLS, "");
		String enableAutomation = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_ENABLE_AUTOMATION, "");
		String taskTime = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_TASK_TIME, "");
		Long idEstrutura = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_ID_ESTRUTURA);
		Long idTemplate= ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_ID_TEMPLATE);
		Long idUsuario = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_ID_USUARIO);
		Long idGrupo = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_ID_GRUPO);
		Long idSecaoVocab = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_ID_SECAO_VOCAB);
		Long idArranjoSecaoVocab = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_ID_ARRANJO_SECAO_VOCAB);
		Long idTipoMateriaVocab = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_ID_TIPO_MATERIA_VOCAB);

		Long importXmlFileTimeout = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_IMPORT_XML_FILE_TIMEOUT);
		Long migrationTimeout = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_MIGRATION_TIMEOUT);

		Integer assetDisplayPageType = ParamUtil.getInteger(actionRequest, ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_TYPE);
		Long assetDisplayPageId = ParamUtil.getLong(actionRequest, ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_ID);
		String layoutUuid = ParamUtil.getString(actionRequest, ImportadorDouPortletKeys.P_LAYOUT_UUID);

		String idHighlightsCategoryIds = ParamUtil.get(actionRequest, "assetCategoryIds", "");
		String highlightLinkPortalURL = ParamUtil.get(actionRequest,ImportadorDouPortletKeys.P_HIGHLIGHTS_LINK_PORTAL_URL, "");

		Boolean enableTwitter = ParamUtil.getBoolean(actionRequest, ImportadorDouPortletKeys.P_ENABLE_TWITTER, false);

		Boolean enableFacebook = ParamUtil.getBoolean(actionRequest, ImportadorDouPortletKeys.P_ENABLE_FACEBOOK, false);
		String url = ParamUtil.getString(actionRequest, ImportadorDouPortletKeys.URL_SERVER);
		Boolean enableNotification = ParamUtil.getBoolean(actionRequest, ImportadorDouPortletKeys.ENABLE_NOTIFICATION, false);

		try {
			prefs.setValue(ImportadorDouPortletKeys.P_THREAD_SIZE, String.valueOf(threadSize));
			prefs.setValue(ImportadorDouPortletKeys.P_POOL_SIZE, String.valueOf(poolSize));
			prefs.setValue(ImportadorDouPortletKeys.P_BASE_PATH_XMLS, basePathXMLs);
			prefs.setValue(ImportadorDouPortletKeys.P_ENABLE_AUTOMATION, enableAutomation);
			prefs.setValue(ImportadorDouPortletKeys.P_TASK_TIME, taskTime);
			prefs.setValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, String.valueOf(idEstrutura));
			prefs.setValue(ImportadorDouPortletKeys.P_ID_TEMPLATE, String.valueOf(idTemplate));
			prefs.setValue(ImportadorDouPortletKeys.P_ID_USUARIO, String.valueOf(idUsuario));
			prefs.setValue(ImportadorDouPortletKeys.P_ID_GRUPO, String.valueOf(idGrupo));
			prefs.setValue(ImportadorDouPortletKeys.P_ID_SECAO_VOCAB, String.valueOf(idSecaoVocab));
			prefs.setValue(ImportadorDouPortletKeys.P_ID_ARRANJO_SECAO_VOCAB, String.valueOf(idArranjoSecaoVocab));
			prefs.setValue(ImportadorDouPortletKeys.P_ID_TIPO_MATERIA_VOCAB, String.valueOf(idTipoMateriaVocab));

			prefs.setValue(ImportadorDouPortletKeys.P_IMPORT_XML_FILE_TIMEOUT, String.valueOf(importXmlFileTimeout));
			prefs.setValue(ImportadorDouPortletKeys.P_MIGRATION_TIMEOUT, String.valueOf(migrationTimeout));

			prefs.setValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_TYPE, String.valueOf(assetDisplayPageType));
			prefs.setValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_ID, String.valueOf(assetDisplayPageId));
			prefs.setValue(ImportadorDouPortletKeys.P_LAYOUT_UUID, layoutUuid);

			prefs.setValue(ImportadorDouPortletKeys.P_HIGHLIGHTS_CATEGORY_IDS, idHighlightsCategoryIds);
			prefs.setValue(ImportadorDouPortletKeys.P_HIGHLIGHTS_LINK_PORTAL_URL, highlightLinkPortalURL);

			prefs.setValue(ImportadorDouPortletKeys.P_ENABLE_TWITTER, enableTwitter.toString());

			prefs.setValue(ImportadorDouPortletKeys.P_ENABLE_FACEBOOK, enableFacebook.toString());
			
			prefs.setValue(ImportadorDouPortletKeys.URL_SERVER, String.valueOf(url));
			
			prefs.setValue(ImportadorDouPortletKeys.ENABLE_NOTIFICATION, String.valueOf(enableNotification));

			prefs.store();
		} catch (ReadOnlyException | ValidatorException | IOException e) {
			SessionErrors.add(actionRequest, "erro-inesperado");
			log.error(e.getMessage());
		}

		if((idEstrutura != null && idEstrutura != 0) && (idTemplate == null || idTemplate == 0)) {
			SessionMessages.add(actionRequest, "msg-selecione-template");
			actionResponse.setPortletMode(PortletMode.EDIT);
		} else if(!validatePrefs(prefs, actionRequest)) {
			actionResponse.setPortletMode(PortletMode.EDIT);
		} else {
			if(enableAutomation.equals("true")){
//				try {
//					ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
//					Agendador.iniciar(
//							themeDisplay.getCompanyId(), 
//							themeDisplay.getLanguageId(),
//							themeDisplay.getScopeGroupId(),
//							themeDisplay.getUserId(),
//							themeDisplay.getPlid(),
//							taskTime);
//
//					SessionMessages.add(actionRequest, "msg-preferencias-definidas-sucesso");
//					actionResponse.setPortletMode(PortletMode.VIEW);
//				} catch(SchedulerException e) {
//					SessionMessages.add(actionRequest, "erro-inesperado");
//					log.error(e.getMessage());
//				} catch(InterruptedException e) {
//					SessionMessages.add(actionRequest, "erro-inesperado");
//					log.error(e.getMessage());
//				}
			}
		}
	}

	private boolean validatePrefs(PortletPreferences prefs, PortletRequest request) {
		boolean valid = true;
		
		String xmlOrigin = prefs.getValue(ImportadorDouPortletKeys.P_XML_ORIGIN, ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_FILE_SYSTEM);
		
		if (xmlOrigin.equals(ImportadorDouPortletKeys.P_XML_ORIGIN_OPTION_VALUE_FILE_SYSTEM)) {
			String basePathXMLs = prefs.getValue(ImportadorDouPortletKeys.P_BASE_PATH_XMLS, "");
			File dirXMLs = new File(basePathXMLs);
			if(!dirXMLs.exists() || !dirXMLs.isDirectory()) {
				valid = false;
				SessionErrors.add(request, "erro-base-path-xmls-invalido");
			}

			Boolean enableAutomation = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_AUTOMATION, Boolean.FALSE.toString()));
			if (enableAutomation) {
				String taskTime = prefs.getValue(ImportadorDouPortletKeys.P_TASK_TIME, StringPool.BLANK);
				if(taskTime == null || taskTime.trim().isEmpty()) {
					valid = false;
					SessionErrors.add(request, "erro-task-time-obrigatorio");
				}
			}
		}
		
		Long idGrupo = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));
		if(idGrupo == null || idGrupo.equals(0L)) {
			valid = false;
			SessionErrors.add(request, "erro-grupo-obrigatorio");
		}
		
		Long idUsuario = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_USUARIO, "0"));
		if(idUsuario == null || idUsuario.equals(0L)) {
			valid = false;
			SessionErrors.add(request, "erro-usuario-obrigatorio");
		}
		
		Long idEstrutura = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, "0"));
		if(idEstrutura == null || idEstrutura.equals(0L)) {
			valid = false;
			SessionErrors.add(request, "erro-estrutura-obrigatorio");
		}
		
		Long idTemplate = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TEMPLATE, "0"));
		if(idTemplate == null || idTemplate.equals(0L)) {
			valid = false;
			SessionErrors.add(request, "erro-template-obrigatorio"); 
		}

		ConfigurationsUtil.checkSocialNetworkConfiguration(prefs, request, _itemPublicacaoLocalService);

		return valid;
	}

	@Reference(unbind = "-")
	protected void setItemPublicacaoService(ItemPublicacaoLocalService itemPublicacaoLocalService) {
		_itemPublicacaoLocalService = itemPublicacaoLocalService;
	}

	private ItemPublicacaoLocalService _itemPublicacaoLocalService;
}

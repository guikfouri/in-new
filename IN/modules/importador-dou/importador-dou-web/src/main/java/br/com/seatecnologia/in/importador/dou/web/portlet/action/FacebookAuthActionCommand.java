package br.com.seatecnologia.in.importador.dou.web.portlet.action;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.ValidatorException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;
import br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + ImportadorDouPortletKeys.ImportadorDou,
			"mvc.command.name=/auth_facebook"
		},
		service = MVCActionCommand.class
	)

public class FacebookAuthActionCommand extends BaseMVCActionCommand {
	private static Log log = LogFactoryUtil.getLog(FacebookAuthActionCommand.class);

	@SuppressWarnings("deprecation")
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		log.debug("Entering");

		PortletPreferences prefs = actionRequest.getPreferences();
		actionResponse.setRenderParameter("mvcPath", "/auth/facebook.jsp");

		String auth = ParamUtil.get(actionRequest, "auth", "");
		if (auth!= "") {
			log.info("AUTH: " + auth);

			if (auth.equals("facebook")) {
				String state = ParamUtil.get(actionRequest, "state", "");
				log.info("\tSTATE: " + state);

				String facebookPageId = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID,
						prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID, ""));
				String facebookPageAccessToken = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, 
						prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, ""));
				String facebookAppId = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_FACEBOOK_APP_ID,
						prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_ID, ""));
				String facebookAppSecret = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_FACEBOOK_APP_SECRET,
						prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_SECRET, ""));
				String facebookClientToken = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_FACEBOOK_CLIENT_TOKEN,
						prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_CLIENT_TOKEN, ""));

				if (state != "") { 
					if (state.equalsIgnoreCase("start")) {
						Map<String,String> facebookDeviceCodeInfo = _itemPublicacaoLocalService.facebookGetDeviceCode(facebookAppId, facebookClientToken);
						for (String key : facebookDeviceCodeInfo.keySet()) {
							log.debug("Facebook device code info: " + key + " = " + facebookDeviceCodeInfo.get(key));
							actionRequest.setAttribute("fb_" + key, facebookDeviceCodeInfo.get(key));

							prefs.setValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID, facebookPageId);
							prefs.setValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, facebookPageAccessToken);
							prefs.setValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_ID, facebookAppId);
							prefs.setValue(ImportadorDouPortletKeys.P_FACEBOOK_APP_SECRET, facebookAppSecret);
							prefs.setValue(ImportadorDouPortletKeys.P_FACEBOOK_CLIENT_TOKEN, facebookClientToken);

							prefs.store();
						}
					} else if (state.equalsIgnoreCase("wait")) {
						log.debug("facebookAppId: " + facebookAppId);
						log.debug("facebookAppSecret: " + facebookAppSecret);
						log.debug("facebookPageId: " + facebookPageId);
						facebookPageAccessToken = _itemPublicacaoLocalService.facebookGetPageAccessToken(facebookAppId, facebookAppSecret, facebookPageId);
						if (facebookPageAccessToken == null) {
							SessionErrors.add(actionRequest, "erro-inesperado");
							log.error("Invalid Facebook Page Access Token");
							return;
						}

						try {
							prefs.setValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, facebookPageAccessToken);
							prefs.store();
							SessionMessages.add(actionRequest, "msg-auth-success");
						} catch (IOException | ReadOnlyException | ValidatorException e) {
							SessionErrors.add(actionRequest, "erro-inesperado");
							log.error(e.getMessage());
						}
					} else if (state.equalsIgnoreCase("finish")) {
						log.debug("Facebook is done waiting for authorization");
					}
				}
			}
		}
	}

	@Reference(unbind = "-")
	protected void setItemPublicacaoService(ItemPublicacaoLocalService itemPublicacaoLocalService) {
		_itemPublicacaoLocalService = itemPublicacaoLocalService;
	}

	private ItemPublicacaoLocalService _itemPublicacaoLocalService;
}

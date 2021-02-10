package br.com.seatecnologia.in.importador.dou.web.portlet.action;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
			"mvc.command.name=/auth_twitter"
		},
		service = MVCActionCommand.class
	)

public class TwitterAuthActionCommand extends BaseMVCActionCommand {
	private static Log log = LogFactoryUtil.getLog(TwitterAuthActionCommand.class);

	@SuppressWarnings("deprecation")
	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		log.debug("Entering");

		actionResponse.setRenderParameter("mvcPath", "/auth/twitter.jsp");

		PortletPreferences prefs = actionRequest.getPreferences();

		String twitterConsumerKey = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY,
				prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY, StringPool.BLANK));
		String twitterConsumerSecret = ParamUtil.get(actionRequest, ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET,
				prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET, StringPool.BLANK));

		String auth = ParamUtil.get(actionRequest, "auth", StringPool.BLANK);
		if (!auth.equals(StringPool.BLANK)) {
			log.info("AUTH: " + auth);

			if (auth.equalsIgnoreCase("twitter")) {
				String state = ParamUtil.get(actionRequest, "state", StringPool.BLANK);
				log.info("\tSTATE: " + state);

				if (state != "") { 
					if (state.equalsIgnoreCase("start")) {
						String twitterStartAuthURL = StringPool.BLANK;

						if (twitterConsumerKey.length() > 0 && twitterConsumerSecret.length() > 0) {
							twitterStartAuthURL = _itemPublicacaoLocalService.twitterGetLoginUrl(twitterConsumerKey, twitterConsumerSecret, "");
						}
						actionRequest.setAttribute("auth_url", twitterStartAuthURL);

						prefs.setValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY, twitterConsumerKey);
						prefs.setValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET, twitterConsumerSecret);

						prefs.store();
					} else if (state.equalsIgnoreCase("enter_pin")) {
						String twitterPin = ParamUtil.get(actionRequest, "pin", StringPool.BLANK);

						Map<String,String> accessTokenInfo = null;;
						try {
							accessTokenInfo = _itemPublicacaoLocalService.twitterGetAccessToken(twitterPin);
						} catch (PortalException e) {
							SessionErrors.add(actionRequest, "erro-inesperado");
							log.error("Failed to get Twitter Access Token Info: " + e.getMessage());
						}

						if (accessTokenInfo == null) {
							SessionErrors.add(actionRequest, "erro-inesperado");
							log.error("Invalid Twitter Access Token Info");
							return;
						}

						for (String key : accessTokenInfo.keySet()) {
							log.debug("Twitter Access Token Info: " + key + " = " + accessTokenInfo.get(key));
						}

						try {
							prefs.setValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN, accessTokenInfo.get("access_token"));
							prefs.setValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET, accessTokenInfo.get("access_token_secret"));
							prefs.store();
							SessionMessages.add(actionRequest, "msg-auth-success");
						} catch (IOException | ReadOnlyException | ValidatorException e) {
							SessionErrors.add(actionRequest, "erro-inesperado");
							log.error(e.getMessage());
						}
					} else if (state.equalsIgnoreCase("finish")) {
						log.debug("Twitter is done waiting for authorization");
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

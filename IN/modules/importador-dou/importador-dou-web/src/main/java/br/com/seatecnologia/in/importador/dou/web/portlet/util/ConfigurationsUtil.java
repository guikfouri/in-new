package br.com.seatecnologia.in.importador.dou.web.portlet.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferencesIds;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;
import br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService;

public class ConfigurationsUtil {
	private static final Log _log = LogFactoryUtil.getLog(ConfigurationsUtil.class); 

	public static boolean checkSocialNetworkConfiguration(PortletPreferences prefs, PortletRequest request, ItemPublicacaoLocalService itemPublicacaoLocalService) {
		_log.debug("Checking social network configuration...");

		boolean valid = true;

		Boolean enableTwitter = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_TWITTER, "false"));
		String twitterConsumerKey = prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_KEY, null);
		String twitterConsumerSecret = prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_CONSUMER_SECRET, null);
		String twitterAccessToken = prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN, null);
		String twitterAccessTokenSecret = prefs.getValue(ImportadorDouPortletKeys.P_TWITTER_ACCESS_TOKEN_SECRET, null);
		if (!itemPublicacaoLocalService.configureTwitter(enableTwitter, twitterConsumerKey, twitterConsumerSecret, twitterAccessToken, twitterAccessTokenSecret)) {
			valid = false;
			if (request != null) {
				SessionErrors.add(request, "erro-configuracao-twitter");
			}
		}

		Boolean enableFacebook = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.P_ENABLE_FACEBOOK, "false"));
		String facebookPageId = prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ID, null);
		String facebookPageAccessToken = prefs.getValue(ImportadorDouPortletKeys.P_FACEBOOK_PAGE_ACCESS_TOKEN, null);
		if (!itemPublicacaoLocalService.configureFacebook(enableFacebook, facebookPageId, facebookPageAccessToken)) {
			valid = false;
			if (request != null) {
				SessionErrors.add(request, "erro-configuracao-facebook");
			}
		}

		String highlightLinkPortalURL = prefs.getValue(ImportadorDouPortletKeys.P_HIGHLIGHTS_LINK_PORTAL_URL, "");
		if (enableTwitter || enableFacebook) {
			if (highlightLinkPortalURL.length() == 0) {
				valid = false;
				if (request != null) {
					SessionErrors.add(request, "erro-sem-url-para-link-destaque");
				}
			}
		}

		return valid;
	}

	public static boolean checkSocialNetworkConfiguration() throws InterruptedException {
		return checkSocialNetworkConfiguration(
				FrameworkUtil.getBundle(ConfigurationsUtil.class).getBundleContext());
	}

	public static boolean checkSocialNetworkConfiguration(BundleContext bundleContext) throws InterruptedException {
		boolean retval = false;

		ServiceTracker<ItemPublicacaoLocalService, ItemPublicacaoLocalService> serviceTracker =
				new ServiceTracker<>(bundleContext, ItemPublicacaoLocalService.class, null);
		serviceTracker.open();

		try {
			ItemPublicacaoLocalService itemPublicacaoLocalService = serviceTracker.waitForService(500);
			if (itemPublicacaoLocalService == null) {
				serviceTracker.close();
				return false;
			}

			_log.debug(itemPublicacaoLocalService.getOSGiServiceIdentifier());

			long companyId = PortalUtil.getDefaultCompanyId();
			long plid = PortalUtil.getControlPanelPlid(companyId);
			PortletPreferencesIds portletPreferencesIds =
					PortletPreferencesFactoryUtil.getPortletPreferencesIds(
							companyId, 0, plid,
							ImportadorDouPortletKeys.ImportadorDou,
							PortletPreferencesFactoryConstants.SETTINGS_SCOPE_PORTLET_INSTANCE);

			PortletPreferences portletPreferences =
					PortletPreferencesLocalServiceUtil.getStrictPreferences(portletPreferencesIds);

			retval = checkSocialNetworkConfiguration(portletPreferences, null, itemPublicacaoLocalService);
		}
		catch (Exception e) {
			_log.warn(e);
		}
		finally {
			serviceTracker.close();
		}

		return retval;
	}
}

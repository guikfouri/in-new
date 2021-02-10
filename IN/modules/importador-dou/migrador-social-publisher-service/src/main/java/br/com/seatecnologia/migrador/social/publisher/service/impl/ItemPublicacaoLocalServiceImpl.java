/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package br.com.seatecnologia.migrador.social.publisher.service.impl;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.expando.kernel.exception.NoSuchTableException;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.model.ExpandoValue;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalArticleResource;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.exception.FacebookException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenCodeExpiredException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenDeclinedException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenPendingException;
import com.restfb.exception.devicetoken.FacebookDeviceTokenSlowdownException;
import com.restfb.scope.FacebookPermissions;
import com.restfb.scope.ScopeBuilder;
import com.restfb.types.DeviceCode;
import com.restfb.types.GraphResponse;
import com.restfb.types.Page;
import com.restfb.types.Post;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import br.com.seatecnologia.migracao.model.ItemMigracao;
import br.com.seatecnologia.migracao.service.ItemMigracaoLocalService;
import br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException;
import br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao;
import br.com.seatecnologia.migrador.social.publisher.service.base.ItemPublicacaoLocalServiceBaseImpl;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import twitter4j.util.CharacterUtil;

/**
 * The implementation of the item publicacao local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemPublicacaoLocalServiceBaseImpl
 * @see br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalServiceUtil
 */
public class ItemPublicacaoLocalServiceImpl
	extends ItemPublicacaoLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalServiceUtil} to access the item publicacao local service.
	 */
	private static Log _log = LogFactoryUtil.getLog(ItemPublicacaoLocalServiceImpl.class);
	final private static boolean IS_DEBUG = false;
	final private static Version FACEBOOK_API_VERSION = Version.VERSION_3_0;

	public enum SocialNetwork {
		NONE, TWITTER, FACEBOOK;
	}


	private Twitter _twitter = null;
	private RequestToken _twitterRequestToken = null;

	private FacebookClient _facebookClient = null;
	private Page _facebookPage = null;
	private String _facebookReturnUri = null;
	private volatile DeviceCode _facebookDeviceCode = null;
	private volatile int _facebookAuthProcessCounter = 0;

	private String getFormatedSocialPublicationText(JournalArticle journalArticle, Locale locale)
			throws PortalException, SystemException {
		StringBuffer socialPublicationText = new StringBuffer();

		try {
			Document document = SAXReaderUtil.read(journalArticle.getContentByLocale(locale.toString()));

			// pubDate
			String pubDate = document.selectSingleNode("/root/dynamic-element[@name='pubDate']/dynamic-content")
					.getText();
			if (pubDate == null || pubDate.length() == 0) {
				return null;
			}
			_log.debug("pubDate: " + pubDate);
			socialPublicationText.append(pubDate);
			socialPublicationText.append(" - ");

			// highlightType
			String highlightType = document
					.selectSingleNode("/root/dynamic-element[@name='highlightType']/dynamic-content").getText();
			if (highlightType == null || highlightType.length() == 0) {
				return null;
			}
			_log.debug("highlightType: " + highlightType);
			socialPublicationText.append(highlightType.toUpperCase(locale));
			socialPublicationText.append(StringPool.RETURN_NEW_LINE);

			// artSection
			String artSection = document.selectSingleNode("/root/dynamic-element[@name='artSection']/dynamic-content")
					.getText();
			if (artSection == null || artSection.length() == 0) {
				return null;
			}
			_log.debug("artSection: " + artSection);
			socialPublicationText.append(artSection.toUpperCase(locale));
			socialPublicationText.append(" - ");

			// highlight
			String highlight = document.selectSingleNode("/root/dynamic-element[@name='highlight']/dynamic-content")
					.getText();
			if (highlight == null || highlight.length() == 0) {
				return null;
			}
			_log.debug("highlight: " + highlight);
			socialPublicationText.append(highlight);
		} catch (DocumentException e) {
			_log.error("Invalid article(" + journalArticle.getArticleId() + " v" + journalArticle.getVersion()
					+ ") content");
		}

		return socialPublicationText.toString();
	}

	// TODO: Verificar a performance dessa função para grande numero de artigos
	private List<AssetEntry> filterListByLastPublicationDate(List<AssetEntry> original)
			throws PortalException, SystemException {
		Date latestDate = new Date(0);
		Map<AssetEntry, Date> assetList = new HashMap<>();
		for (AssetEntry ae : original) {
			JournalArticleResource journalArticleResource = journalArticleResourceLocalService
					.getJournalArticleResource(ae.getClassPK());
			JournalArticle journalArticle = journalArticleLocalService
					.getLatestArticle(journalArticleResource.getResourcePrimKey());

			try {
				Document document = SAXReaderUtil.read(journalArticle.getContent());
				// pubDate
				String pubDate = document.selectSingleNode("/root/dynamic-element[@name='pubDate']/dynamic-content")
						.getText();
				if (pubDate != null && pubDate.length() > 0) {
					Date douPublishDate;
					try {
						douPublishDate = new SimpleDateFormat("dd/MM/yyyy").parse(pubDate);
						assetList.put(ae, douPublishDate);
						if (douPublishDate.compareTo(latestDate) > 0) {
							latestDate = douPublishDate;
						}
					} catch (ParseException e) {
						_log.error("Invalid format of date on Journal Article (" + journalArticle.getArticleId() + " v"
								+ journalArticle.getVersion() + ") content");
						e.printStackTrace();
					}
				}
			} catch (DocumentException e) {
				_log.error("Invalid format on Journal Article (" + journalArticle.getArticleId() + " v"
						+ journalArticle.getVersion() + ") content");
				e.printStackTrace();
			}
		}

		List<AssetEntry> result = new ArrayList<>();
		for (AssetEntry currentAssetEntry : assetList.keySet()) {
			if (assetList.get(currentAssetEntry).compareTo(latestDate) >= 0) {
				result.add(currentAssetEntry);
			}
		}
		return result;
	}

	private String getArticleURL(JournalArticle journalArticle, ServiceContext serviceContext)
			throws PortalException, SystemException {
		String articleUrl = null;
		Group group = groupLocalService.getGroup(journalArticle.getGroupId());
		_log.debug("Group: " + group.getName());
		
		long classNameId = _portal.getClassNameId(JournalArticle.class);

		AssetDisplayPageEntry assetDisplayPageEntry =
				_assetDisplayPageEntryLocalService.fetchAssetDisplayPageEntry(
						group.getGroupId(), classNameId, journalArticle.getResourcePrimKey());

		if (assetDisplayPageEntry.getType() != AssetDisplayPageConstants.TYPE_NONE) {
			articleUrl = serviceContext.getPortalURL() +
					serviceContext.getPathFriendlyURLPublic() +
					group.getFriendlyURL() +
					JournalArticleConstants.CANONICAL_URL_SEPARATOR +
					journalArticle.getUrlTitle(serviceContext.getLocale());
		}

		if (articleUrl == null) {
			String pagina = group.getExpandoBridge().getAttribute("paginaBusca").toString();
			String portletInstanceId = group.getExpandoBridge().getAttribute("idPublicadorConteudo").toString();

			AssetEntry assetEntry = assetEntryLocalService.getEntry(journalArticle.getGroupId(),
					journalArticle.getArticleResourceUuid());

			articleUrl = new String(serviceContext.getPortalURL() + "/" + pagina + "/-/asset_publisher/"
					+ portletInstanceId + "/content/id/" + String.valueOf(assetEntry.getEntryId()));
		}

		_log.debug("Article URL: " + articleUrl);

		return articleUrl;
	}

	private List<JournalArticle> findArticlesWithCategoryIds(String[] categoriesIds, ServiceContext serviceContext)
			throws PortalException, SystemException {
		List<JournalArticle> journalArticles = new ArrayList<>();

		_log.debug("Looking for articles on the categories: ");

		long[] highlightCategoryIds = new long[categoriesIds.length];
		for (int i = 0; i < categoriesIds.length; i++) {
			try {
				highlightCategoryIds[i] = Long.parseLong(categoriesIds[i]);
			} catch (NumberFormatException e) {
				_log.error("Invalid categoryId: \"" + categoriesIds[i] + "\"");
				throw new PortalException();
			}
			_log.debug(" * " + assetCategoryLocalService.getCategory(highlightCategoryIds[i]).getName());
		}

		// Date lastPublicationDate = getLastPublicationDate();

		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();
		assetEntryQuery.setClassName(JournalArticle.class.getName());
		assetEntryQuery.setAnyCategoryIds(highlightCategoryIds);
		// assetEntryQuery.setAttribute(Field.PUBLISH_DATE, lastPublicationDate);

		List<AssetEntry> assetEntryList = assetEntryLocalService.getEntries(assetEntryQuery);
		_log.debug("ListAssetEntryCount: " + assetEntryList.size());

		// TODO: That function reads the publication date from the JournalArticle
		// content field, may be slow
		assetEntryList = filterListByLastPublicationDate(assetEntryList);
		_log.debug("ListAssetEntryCount[filtered]: " + assetEntryList.size());

		for (AssetEntry assetEntry : assetEntryList) {
			// if (assetEntry.getPublishDate().after(lastPublicationDate)) {
			// Verifica se o artigo já não foi publicado em todas as redes sociais
			// TODO: Verificar lógica no caso do AssetEntryId não existir na tabela
			List<ItemPublicacao> itemPublicacaos = itemPublicacaoPersistence
					.findByAssetEntryId(assetEntry.getEntryId());
			if ((itemPublicacaos != null) && (itemPublicacaos.size() > 0)) {
				Boolean isPublished = true;
				for (ItemPublicacao item : itemPublicacaos) {
					if (item.getPostId().length() == 0) {
						isPublished = false;
					}
				}
				if (isPublished) {
					_log.info("Asset " + assetEntry.getEntryId() + " already published!");
					continue;
				}
			}
			// Recupera o JournalArticle referente ao AssetEntry
			JournalArticleResource journalArticleResource = journalArticleResourceLocalService
					.getJournalArticleResource(assetEntry.getClassPK());
			JournalArticle journalArticle = journalArticleLocalService
					.getLatestArticle(journalArticleResource.getResourcePrimKey());
			journalArticles.add(journalArticle);
		}

		return journalArticles;
	}

	private Long nextId() throws SystemException {
		return IS_DEBUG ? 0L : counterLocalService.increment();
	}

	/**
	 * Método que publica, ou atualiza um status na rede social Twitter
	 * 
	 * <p>
	 * Recebe Um texto a ser publicado no Twitter na conta configurada, como
	 * atualização de status.
	 * </p>
	 * 
	 * @param text
	 *            Texto que será publicado como atualização de status.
	 * 
	 * @return Retorna o ID unico do status publicado na rede social.
	 * @throws PortalException
	 */
	private String publishOnTwitter(String text) throws PortalException {
		String statusId = null;
		if (text == null || text.length() == 0) {
			throw new PortalException("Twitter status cannot be empty");
		}

		if (CharacterUtil.isExceedingLengthLimitation(text)) {
			// TODO: Diminuir tamanho do tweet
			_log.debug("Tweet char count: " + CharacterUtil.count(text));
		}

		try {
			Status status = _twitter.updateStatus(text);
			statusId = String.valueOf(status.getId());
			_log.debug("Successfully updated the status (" + statusId + ") to [" + status.getText() + "].");
		} catch (TwitterException e) {
			_log.error("Could not publish on Twitter: " + e.getErrorMessage() + "(" + e.getErrorCode() + ")");
			if (e.getErrorCode() == 187) {
				// Duplicate Status
				// TODO: Find the ID of the status
			}
		}

		return statusId;
	}

	/**
	 * Método que publica uma postagem na rede social Facebook
	 * 
	 * <p>
	 * Recebe Um texto a ser publicado no Facebook na página configurada, como nova
	 * postagem.
	 * </p>
	 * 
	 * @param text
	 *            Texto que será publicado como nova postagem na página.
	 * 
	 * @return Retorna o ID unico da postagem publicado na rede social.
	 * @throws PortalException
	 */
	private String publishOnFacebook(String text) throws PortalException {
		String postId = null;
		if (text == null || text.length() == 0) {
			throw new PortalException("Facebook status cannot be empty");
		}

		try {
			GraphResponse publishMessageResponse = _facebookClient.publish(_facebookPage.getId() + "/feed",
					GraphResponse.class, Parameter.with("message", text));
			postId = publishMessageResponse.getId();
			_log.debug("Successfully updated the status (" + postId + ") to [" + text + "].");
		} catch (FacebookException e) {
			_log.error("Could not publish on Facebook page: " + e.getMessage());
		}

		return postId;
	}

	/**
	 * Método de configuração para postagem na rede social Twitter
	 * 
	 * @param enable
	 *            habilita/desabilita postagem automatica no Twitter
	 * @param OAuthConsumerKey
	 *            Comsumer Key para acesso a API do Twitter
	 * @param OAuthConsumerSecret
	 *            consumer secret para acesso a API do Twitter
	 * @param OAuthAccessToken
	 *            token de acesso a conta de publicação no Twitter
	 * @param OAuthAccessTokenSecret
	 *            token secret para acesso a conta de publicação no Twitter
	 * 
	 * @return Retorna verdadeiro quando a configuração é realizada com sucesso
	 */
	public Boolean configureTwitter(Boolean enable, String OAuthConsumerKey, String OAuthConsumerSecret,
			String OAuthAccessToken, String OAuthAccessTokenSecret) {
		_twitter = null;

		if (enable) {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true).setOAuthConsumerKey(OAuthConsumerKey).setOAuthConsumerSecret(OAuthConsumerSecret)
					.setOAuthAccessToken(OAuthAccessToken).setOAuthAccessTokenSecret(OAuthAccessTokenSecret);

			TwitterFactory tf = new TwitterFactory(cb.build());

			_twitter = tf.getInstance();

			try {
				User user = _twitter.verifyCredentials();
				_log.info("Successfully configured access to Twitter account: @" + user.getScreenName());
			} catch (Exception e) {
				_log.error("Twitter configuration error: " + e.getMessage());
				return false;
			}
		}

		return true;
	}

	/**
	 * Método de configuração para postagem em página do Facebook
	 * 
	 * @param enable
	 *            habilita/desabilita postagem automática em página do Facebook
	 * @param pageAccessToken
	 *            token de acesso, com permissão de gerenciamento e publicação, à
	 *            página do Facebook
	 * @param pageId
	 *            identificador da página do Facebook onde a publicação será
	 *            postada.
	 * 
	 * @return Retorna verdadeiro quando a configuração é realizada com sucesso
	 */
	public Boolean configureFacebook(Boolean enable, String pageId, String pageAccessToken) {
		_facebookClient = null;
		_facebookPage = null;

		if (enable) {
			if (pageId == null || pageId.length() == 0) {
				return false;
			}
			try {
				_facebookClient = new DefaultFacebookClient(pageAccessToken, FACEBOOK_API_VERSION);
				_facebookPage = _facebookClient.fetchObject(pageId, Page.class,
						Parameter.with("fields", "name,link,access_token"));
				_log.info("Successfully configured publishing on \"" + _facebookPage.getName() + "\" Facebook page ("
						+ _facebookPage.getLink() + ")");

//				Date tokenExpiration = _facebookClient.debugToken(pageAccessToken).getExpiresAt();
//				_log.debug("Current Facebook page access token will expire on " + tokenExpiration);
//				if (tokenExpiration.before(new Date(System.currentTimeMillis() + (24L * 60L * 60L * 1000L)))) {
//					_log.warn("The Facebook page access token will expire in less than 24 hours");
//				}
			} catch (FacebookException fbe) {
				_log.error("Facebook configuration error: " + fbe.getMessage());
				return false;
			}
		}

		return true;
	}

	public String getSocialNetworkName(ItemPublicacao itemPublicacao) {
		String socialNetwork = "";

		int social = itemPublicacao.getSocialNetwork();

		if (social == SocialNetwork.TWITTER.ordinal()) {
			socialNetwork = "Twitter";
		} else if (social == SocialNetwork.FACEBOOK.ordinal()) {
			socialNetwork = "Facebook";
		}

		return socialNetwork;
	}

	public String getSocialNetworkPublicationUrl(ItemPublicacao itemPublicacao) {
		String url = null;

		int social = itemPublicacao.getSocialNetwork();

		if (social == SocialNetwork.TWITTER.ordinal()) {
			url = "https://twitter.com/statuses/" + itemPublicacao.getPostId();
		} else if (social == SocialNetwork.FACEBOOK.ordinal()) {
			url = "https://www.facebook.com/" + itemPublicacao.getPostId();
		}
		return url;
	}

	public String twitterGetLoginUrl(String OAuthConsumerKey, String OAuthConsumerSecret, String redirectUri) {
		String url = "";

		_twitter = TwitterFactory.getSingleton();
		if (_twitter.getConfiguration().getOAuthConsumerKey() != OAuthConsumerKey && 
				_twitter.getConfiguration().getOAuthConsumerSecret() != OAuthConsumerSecret) {
			ConfigurationBuilder cb = new ConfigurationBuilder();
			cb.setDebugEnabled(true)
			.setOAuthConsumerKey(OAuthConsumerKey)
			.setOAuthConsumerSecret(OAuthConsumerSecret);

			_twitter = new TwitterFactory(cb.build()).getInstance();
		}

		try {
			if (redirectUri != null && redirectUri.length() > 0) {
				_twitterRequestToken = _twitter.getOAuthRequestToken(redirectUri);
			} else {
				_twitterRequestToken = _twitter.getOAuthRequestToken();
			}
		} catch (TwitterException e) {
			_log.error("Could not get Twitter request access token: error (" +
					e.getErrorCode() + ") "+ e.getErrorMessage());
			e.printStackTrace();
		}

		if (_twitterRequestToken != null) {
			url = _twitterRequestToken.getAuthorizationURL();
		}
		_log.debug("Twitter Login Dialog URL: " + url);

		return url;
	}

	public Map<String,String> twitterGetAccessToken(String pin) throws PortalException {
		Map<String,String> resultToken = null;

		try {
			twitter4j.auth.AccessToken accessToken = null;
			if (pin.length() > 0) {
				accessToken = _twitter.getOAuthAccessToken(_twitterRequestToken, pin);
			} else {
				accessToken = _twitter.getOAuthAccessToken();
			}

			if (accessToken == null) {
				_log.error("Could not get Twitter access token");
				return null;
			}

			_log.debug("Twitter access token: \"" + accessToken.getToken() + "\"" + StringPool.RETURN_NEW_LINE
					+ "Twitter access token secret: \"" + accessToken.getTokenSecret() + "\"");

			User user = _twitter.verifyCredentials();
			if (user != null) {
				_log.info("Successfully granted access to Twitter acount @" + user.getScreenName());
			}

			resultToken = new HashMap<>();
			resultToken.put("access_token", accessToken.getToken());
			resultToken.put("access_token_secret", accessToken.getTokenSecret());
		} catch (TwitterException e) {
			throw new PortalException(e);
		}

		return resultToken;
	}

	public synchronized Map<String,String> facebookGetDeviceCode(String appId, String clientToken) {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(FacebookPermissions.MANAGE_PAGES);
		scopeBuilder.addPermission(FacebookPermissions.PUBLISH_PAGES);

		_facebookClient = new DefaultFacebookClient(appId + "|" + clientToken, FACEBOOK_API_VERSION);

		if (_facebookDeviceCode == null) {
			_facebookDeviceCode = _facebookClient.fetchDeviceCode(scopeBuilder);
		}

		_log.debug("Facebook device code info: " + _facebookDeviceCode);
		_log.info("Facebook Device Login: Go to " + _facebookDeviceCode.getVerificationUri() + " and enter the code: " + _facebookDeviceCode.getUserCode());

		Map<String,String> deviceCodeInfo = new HashMap<>();
		deviceCodeInfo.put("verification_uri", _facebookDeviceCode.getVerificationUri());
		deviceCodeInfo.put("user_code", _facebookDeviceCode.getUserCode());

		return deviceCodeInfo;
	}

	public String facebookGetLoginUrl(String appId, String redirectUri) {
		ScopeBuilder scopeBuilder = new ScopeBuilder();
		scopeBuilder.addPermission(FacebookPermissions.MANAGE_PAGES);
		scopeBuilder.addPermission(FacebookPermissions.PUBLISH_PAGES);

		if (_facebookClient == null) {
			_facebookClient = new DefaultFacebookClient(FACEBOOK_API_VERSION);
		}
		String loginDialogUrlString = _facebookClient.getLoginDialogUrl(appId, redirectUri, scopeBuilder,
				Parameter.with("response_type", "code"));
		_log.debug("Facebook Login Dialog URL: " + loginDialogUrlString);
		_facebookReturnUri = redirectUri;

		return loginDialogUrlString;
	}

	public String facebookGetPageAccessToken(String appId, String appSecret, String pageId) {
		String pageAccessToken = null;
		
		if (_facebookDeviceCode == null || _facebookClient == null) {
			_log.error("Invalid call, please fetch a device code");
			_facebookDeviceCode = null;
			return null;  // TODO: Create more especific exceptions!
		}

		synchronized (this) {
			_facebookAuthProcessCounter += 1;
		}

		_log.debug("Process counter is " + _facebookAuthProcessCounter);

		AccessToken deviceAccessToken = null;
		int additionalSeconds = 0;
		int counter = 0;
		while (deviceAccessToken == null) {
			try {
				if (_facebookDeviceCode == null) {
					_log.debug("Cancelling polling for Facebook authorization");
					break;
				}
				deviceAccessToken = _facebookClient.obtainDeviceAccessToken(_facebookDeviceCode.getCode());
			} catch (FacebookDeviceTokenCodeExpiredException e) {
				_log.error("Facebook device code expired");
				break;
			} catch (FacebookDeviceTokenPendingException e) {
				_log.debug("Waiting for the user authorization (" + counter + ")");
			} catch (FacebookDeviceTokenDeclinedException e) {
				_log.error("User declined access");
				break;
			} catch (FacebookDeviceTokenSlowdownException e) {
				additionalSeconds += (_facebookDeviceCode.getInterval() / 2);
				_log.error("Polling is too fast, slowing down (+" + additionalSeconds + "s)");
			}
			++counter;

			try {
				Thread.sleep((_facebookDeviceCode.getInterval() + additionalSeconds) * 1000);
			} catch (InterruptedException e1) {
				_log.error("Failed to sleep: " + e1.getMessage());
				break;
			}
		}

		synchronized (this) {
			if (--_facebookAuthProcessCounter == 0) {
				_facebookDeviceCode = null;
			}
		}

		if (deviceAccessToken == null) {
			_log.error("Device authorization process failed");
			return null;
		}

		_log.debug("Device Access Token: \"" + deviceAccessToken.getAccessToken() + "\" will expire on "
				+ deviceAccessToken.getExpires());

		_facebookClient = new DefaultFacebookClient(deviceAccessToken.getAccessToken(), appSecret,
				FACEBOOK_API_VERSION);

		_facebookReturnUri = null;

		try {
			if (_facebookPage == null || _facebookPage.getAccessToken().length() == 0) {
				_facebookPage = _facebookClient.fetchObject(pageId, Page.class,
						Parameter.with("fields", "name,link,access_token"));
			}

			pageAccessToken = _facebookPage.getAccessToken();

			_log.debug("Page Access Token: \"" + pageAccessToken + "\" will expire on "
					+ deviceAccessToken.getExpires());
		} catch (Exception fbe) {
			_log.error("Could not get access token for Facebook: " + fbe.getMessage());
		}

		return pageAccessToken;
	}

	public synchronized void facebookCancelAuthorizationProccess () {
		_log.debug("Entering");
		if (_facebookDeviceCode != null) {
			_facebookDeviceCode = null;
			_log.debug("Cancelling...");
		}
	}

	public String facebookGetPageAccessToken(String appId, String appSecret, String pageId, String code) {
		String pageAccessToken = null;

		if (_facebookClient == null || _facebookReturnUri == null) {
			_log.error("Invalid call, must request Authorization");
			return null; // TODO: Create more especific exceptions!
		}
		try {
			AccessToken userAccessToken = _facebookClient.obtainUserAccessToken(appId, appSecret, _facebookReturnUri,
					code);
			_log.debug("User Access Token: \"" + userAccessToken.getAccessToken() + "\" will expire on "
					+ userAccessToken.getExpires());

			_facebookClient = new DefaultFacebookClient(userAccessToken.getAccessToken(), appSecret,
					FACEBOOK_API_VERSION);

			_facebookReturnUri = null;

			if (_facebookPage == null || _facebookPage.getAccessToken().length() == 0) {
				_facebookPage = _facebookClient.fetchObject(pageId, Page.class,
						Parameter.with("fields", "name,link,access_token"));
			}

			pageAccessToken = _facebookPage.getAccessToken();

			_log.debug("Page Access Token: \"" + pageAccessToken + "\"");
		} catch (FacebookException fbe) {
			_log.error("Could not get access token for Facebook: " + fbe.getMessage());
		}

		return pageAccessToken;
	}

	/**
	 * Método chamado após importação para publicar destaque de um conteúdo, se
	 * houver, nas redes sociais configuradas.
	 * 
	 * @param itemMigracao
	 *            objeto da estrutura de migração a qual o conteúdo pertence
	 * @param journalArticle
	 *            conteúdo publicado
	 * @param serviceContext
	 *            contexto do serviço que executou a ação
	 * 
	 * @return Retorna verdadeiro quando a publicação de destaque foi realizada em
	 *         pelo menois uma das redes sociais configuradas.
	 */
	public Boolean publishArticleHighlightsOnSocialNetworks(ItemMigracao itemMigracao, JournalArticle journalArticle,
			ServiceContext serviceContext) throws PortalException, SystemException {
		AssetEntry assetEntry = assetEntryLocalService.getEntry(journalArticle.getGroupId(),
				journalArticle.getArticleResourceUuid());
		Boolean articlePublished = false;

		ItemPublicacao itemPublicacaoTwitter = null;
		ItemPublicacao itemPublicacaoFacebook = null;

		if (_twitter != null) {
			try {
				itemPublicacaoTwitter = itemPublicacaoPersistence
						.findByAssetEntryIdSocialNetwork(assetEntry.getEntryId(), SocialNetwork.TWITTER.ordinal());
			} catch (NoSuchItemPublicacaoException e) {
				itemPublicacaoTwitter = itemPublicacaoLocalService.createItemPublicacao(nextId());
				itemPublicacaoTwitter.setEntryId(assetEntry.getEntryId());
				itemPublicacaoTwitter.setSocialNetwork(SocialNetwork.TWITTER.ordinal());
				itemPublicacaoTwitter
						.setPublishDate(itemMigracao != null ? itemMigracao.getDataReferencia() : new Date());
				itemPublicacaoTwitter.setItemMigracaoId(itemMigracao != null ? itemMigracao.getItemMigracaoId() : 0);
				itemPublicacaoTwitter.setPostId("");
			}
		}

		if (_facebookClient != null) {
			try {
				itemPublicacaoFacebook = itemPublicacaoPersistence
						.findByAssetEntryIdSocialNetwork(assetEntry.getEntryId(), SocialNetwork.FACEBOOK.ordinal());
			} catch (NoSuchItemPublicacaoException e) {
				itemPublicacaoFacebook = itemPublicacaoLocalService.createItemPublicacao(nextId());
				itemPublicacaoFacebook.setEntryId(assetEntry.getEntryId());
				itemPublicacaoFacebook.setSocialNetwork(SocialNetwork.FACEBOOK.ordinal());
				itemPublicacaoFacebook
						.setPublishDate(itemMigracao != null ? itemMigracao.getDataReferencia() : new Date());
				itemPublicacaoFacebook.setItemMigracaoId(itemMigracao != null ? itemMigracao.getItemMigracaoId() : 0);
				itemPublicacaoFacebook.setPostId("");
			}
		}

		if ((itemPublicacaoTwitter == null || itemPublicacaoTwitter.getPostId() != "")
				&& (itemPublicacaoFacebook == null || itemPublicacaoFacebook.getPostId() != "")) {
			// The article is already published or it does not need to be published
			_log.debug("The article is already published or it does not need to be published");
			if (itemPublicacaoTwitter != null && itemPublicacaoTwitter.getPostId().length() > 0) {
				try {
					Status status = _twitter.showStatus(Long.parseLong(itemPublicacaoTwitter.getPostId()));
					if (status == null) { //
						// don't know if needed - T4J docs are very bad
					} else {
						_log.debug("Twitter URL: https://twitter.com/" + status.getUser().getScreenName() + "/status/"
								+ status.getId());
					}
				} catch (TwitterException e) {
					_log.error("Failed to search tweets: " + e.getMessage());
				}
			}
			if (itemPublicacaoFacebook != null && itemPublicacaoFacebook.getPostId().length() > 0) {
				_log.debug("Facebook URL: https://www.facebook.com/" + itemPublicacaoFacebook.getPostId());
			}
			return false;
		}

		// Formantando texto da publicação
		String socialStatusText = getFormatedSocialPublicationText(journalArticle,
				serviceContext.getLocale());

		if (socialStatusText == null) {
			_log.debug("The article does not have a valid highlight on its contents");
			return false;
		}

		_log.info("Highlight found for article " + journalArticle.getArticleId());

		String articleUrl = getArticleURL(journalArticle, serviceContext);

		socialStatusText = socialStatusText + StringPool.RETURN_NEW_LINE + articleUrl;

		Date now = new Date();

		if (itemPublicacaoTwitter != null && itemPublicacaoTwitter.getPostId().length() == 0) {
			String status = publishOnTwitter(socialStatusText);
			if (status != null) {
				itemPublicacaoTwitter.setPostId(status);
				articlePublished = true;
			}
			itemPublicacaoTwitter.setModifiedDate(now);

			if (!IS_DEBUG) {
				itemPublicacaoLocalService.updateItemPublicacao(itemPublicacaoTwitter);
			}
		}

		if (itemPublicacaoFacebook != null && itemPublicacaoFacebook.getPostId().length() == 0) {
			String status = publishOnFacebook(socialStatusText);
			if (status != null) {
				itemPublicacaoFacebook.setPostId(status);
				articlePublished = true;
			}
			itemPublicacaoFacebook.setModifiedDate(now);

			if (!IS_DEBUG) {
				itemPublicacaoLocalService.updateItemPublicacao(itemPublicacaoFacebook);
			}
		}

		return articlePublished;
	}

	public int publishLatestArticlesHighlightsFromCategories(String[] categoriesIds, ServiceContext serviceContext)
			throws PortalException, SystemException {
		int countPublished = 0;
		List<JournalArticle> journalArticles = findArticlesWithCategoryIds(categoriesIds, serviceContext);

		long journalArticleClassNameId = classNameLocalService.getClassNameId(JournalArticle.class);

		for (JournalArticle journalArticle : journalArticles) {
			// log.debug("Found article: " +
			// journalArticle.getTitle(serviceContext.getLanguageId()));

			ExpandoTable table = null;
			try {
				table = expandoTableLocalService.getDefaultTable(journalArticle.getCompanyId(),
						journalArticleClassNameId);
			} catch (NoSuchTableException e) {
				e.printStackTrace();
				throw new PortalException();
			}
			ExpandoValue value = expandoValueLocalService.getValue(journalArticle.getCompanyId(),
					journalArticleClassNameId, table.getName(), "paginaId",
					Long.parseLong(journalArticle.getArticleId()));

			// log.debug("IdentificadorAtualizacao: " + value.getData());

			List<ItemMigracao> itemMigracaos = _itemMigracaoLocalService
					.recuperarItensPorIdentificadorAtualizacao(value.getData());

			if (itemMigracaos.size() > 0) {
				if (publishArticleHighlightsOnSocialNetworks(itemMigracaos.get(0), journalArticle, serviceContext)) {
					countPublished++;
				}
			}
		}
		return countPublished;
	}

	public Date getLastArticlesHighlightsPublicationDate() throws SystemException {
		Date latestDate = new Date(0);

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ItemPublicacao.class);
		dynamicQuery.setProjection(ProjectionFactoryUtil.max("publishDate"));

		List<Date> maxDate = itemPublicacaoLocalService.dynamicQuery(dynamicQuery);
		_log.debug("List count: " + maxDate.size());
		for (Date item : maxDate) {
			if (item != null) {
				_log.debug("Date: " + item.toString());
				latestDate = item;
			}
		}

		return latestDate;
	}

	public String getLatestArticleURL(ItemPublicacao itemPublicacao, ServiceContext serviceContext)
			throws PortalException, SystemException {
		String url = null;
		AssetEntry assetEntry = assetEntryLocalService.getAssetEntry(itemPublicacao.getEntryId());
		JournalArticleResource journalArticleResource = journalArticleResourceLocalService
				.getJournalArticleResource(assetEntry.getClassPK());
		JournalArticle journalArticle = journalArticleLocalService
				.getLatestArticle(journalArticleResource.getResourcePrimKey());

		url = getArticleURL(journalArticle, serviceContext);

		return url;
	}

	public void deleteArticleHighlightsFromSocialNetworks(int start, int end) throws SystemException {
		List<ItemPublicacao> itens = itemPublicacaoPersistence.findAll(start, end);
		if (itens.size() > 0) {
			Date now = new Date();
			for (ItemPublicacao item : itens) {
				if (item.getPostId().length() == 0) {
					continue;
				}
				Boolean success = false;
				if (item.getSocialNetwork() == SocialNetwork.TWITTER.ordinal()) {
					try {
						Status status = _twitter.destroyStatus(Long.parseLong(item.getPostId()));
						_log.debug("Successfully removed the Twitter status " + status.getText());
						success = true;
					} catch (TwitterException e) {
						_log.error("Could not remove status on Twitter: " + e.getErrorMessage());
					}
				} else if (item.getSocialNetwork() == SocialNetwork.FACEBOOK.ordinal()) {
					try {
						Post post = _facebookClient.fetchObject(item.getPostId(), Post.class);
						if (_facebookClient.deleteObject(item.getPostId())) {
							_log.debug("Successfully removed the Facebook status " + post.getMessage());
							success = true;
						}
					} catch (FacebookException fbe) {
						_log.error("Could not remove status on Facebook: " + fbe.getMessage());
					}
				}

				if (success) {
					item.setPostId("");
					item.setModifiedDate(now);

					itemPublicacaoLocalService.updateItemPublicacao(item);
				}
			}
		}
	}

	public String getTwitterAccountName() {
		String screenName = null;

		if (_twitter != null) {
			try {
				User user = _twitter.verifyCredentials();
				screenName = user.getScreenName();
				_log.info("Twitter account: @" + screenName);
			} catch (Exception e) {
				_log.error("Twitter configuration error: " + e.getMessage());
			}
		}

		return screenName;
	}

	public String getFacebookPageName() {
		String pageName = null;

		if (_facebookPage != null) {
			pageName = _facebookPage.getName();
			_log.info("Facebook page: \"" + _facebookPage.getName() + "\" ("
					+ _facebookPage.getLink() + ")");
		}

		return pageName;
	}
	
	@ServiceReference(type = AssetDisplayPageEntryLocalService.class)
	private AssetDisplayPageEntryLocalService _assetDisplayPageEntryLocalService;
	@ServiceReference(type = Portal.class)
	private Portal _portal;
	@ServiceReference(type = ItemMigracaoLocalService.class)
	private ItemMigracaoLocalService _itemMigracaoLocalService;
}

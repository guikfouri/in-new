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

package br.com.seatecnologia.migrador.social.publisher.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ItemPublicacao. This utility wraps
 * {@link br.com.seatecnologia.migrador.social.publisher.service.impl.ItemPublicacaoLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author SEA Tecnologia
 * @see ItemPublicacaoLocalService
 * @see br.com.seatecnologia.migrador.social.publisher.service.base.ItemPublicacaoLocalServiceBaseImpl
 * @see br.com.seatecnologia.migrador.social.publisher.service.impl.ItemPublicacaoLocalServiceImpl
 * @generated
 */
@ProviderType
public class ItemPublicacaoLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link br.com.seatecnologia.migrador.social.publisher.service.impl.ItemPublicacaoLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the item publicacao to the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was added
	*/
	public static br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao addItemPublicacao(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return getService().addItemPublicacao(itemPublicacao);
	}

	/**
	* Método de configuração para postagem em página do Facebook
	*
	* @param enable
	habilita/desabilita postagem automática em página do Facebook
	* @param pageAccessToken
	token de acesso, com permissão de gerenciamento e publicação, à
	página do Facebook
	* @param pageId
	identificador da página do Facebook onde a publicação será
	postada.
	* @return Retorna verdadeiro quando a configuração é realizada com sucesso
	*/
	public static Boolean configureFacebook(Boolean enable, String pageId,
		String pageAccessToken) {
		return getService().configureFacebook(enable, pageId, pageAccessToken);
	}

	/**
	* Método de configuração para postagem na rede social Twitter
	*
	* @param enable
	habilita/desabilita postagem automatica no Twitter
	* @param OAuthConsumerKey
	Comsumer Key para acesso a API do Twitter
	* @param OAuthConsumerSecret
	consumer secret para acesso a API do Twitter
	* @param OAuthAccessToken
	token de acesso a conta de publicação no Twitter
	* @param OAuthAccessTokenSecret
	token secret para acesso a conta de publicação no Twitter
	* @return Retorna verdadeiro quando a configuração é realizada com sucesso
	*/
	public static Boolean configureTwitter(Boolean enable,
		String OAuthConsumerKey, String OAuthConsumerSecret,
		String OAuthAccessToken, String OAuthAccessTokenSecret) {
		return getService()
				   .configureTwitter(enable, OAuthConsumerKey,
			OAuthConsumerSecret, OAuthAccessToken, OAuthAccessTokenSecret);
	}

	/**
	* Creates a new item publicacao with the primary key. Does not add the item publicacao to the database.
	*
	* @param itemPublicacaoId the primary key for the new item publicacao
	* @return the new item publicacao
	*/
	public static br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao createItemPublicacao(
		long itemPublicacaoId) {
		return getService().createItemPublicacao(itemPublicacaoId);
	}

	public static void deleteArticleHighlightsFromSocialNetworks(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteArticleHighlightsFromSocialNetworks(start, end);
	}

	/**
	* Deletes the item publicacao from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was removed
	*/
	public static br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao deleteItemPublicacao(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return getService().deleteItemPublicacao(itemPublicacao);
	}

	/**
	* Deletes the item publicacao with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao that was removed
	* @throws PortalException if a item publicacao with the primary key could not be found
	*/
	public static br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao deleteItemPublicacao(
		long itemPublicacaoId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteItemPublicacao(itemPublicacaoId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static void facebookCancelAuthorizationProccess() {
		getService().facebookCancelAuthorizationProccess();
	}

	public static java.util.Map<String, String> facebookGetDeviceCode(
		String appId, String clientToken) {
		return getService().facebookGetDeviceCode(appId, clientToken);
	}

	public static String facebookGetLoginUrl(String appId, String redirectUri) {
		return getService().facebookGetLoginUrl(appId, redirectUri);
	}

	public static String facebookGetPageAccessToken(String appId,
		String appSecret, String pageId) {
		return getService().facebookGetPageAccessToken(appId, appSecret, pageId);
	}

	public static String facebookGetPageAccessToken(String appId,
		String appSecret, String pageId, String code) {
		return getService()
				   .facebookGetPageAccessToken(appId, appSecret, pageId, code);
	}

	public static br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao fetchItemPublicacao(
		long itemPublicacaoId) {
		return getService().fetchItemPublicacao(itemPublicacaoId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static String getFacebookPageName() {
		return getService().getFacebookPageName();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the item publicacao with the primary key.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao
	* @throws PortalException if a item publicacao with the primary key could not be found
	*/
	public static br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao getItemPublicacao(
		long itemPublicacaoId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getItemPublicacao(itemPublicacaoId);
	}

	/**
	* Returns a range of all the item publicacaos.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @return the range of item publicacaos
	*/
	public static java.util.List<br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao> getItemPublicacaos(
		int start, int end) {
		return getService().getItemPublicacaos(start, end);
	}

	/**
	* Returns the number of item publicacaos.
	*
	* @return the number of item publicacaos
	*/
	public static int getItemPublicacaosCount() {
		return getService().getItemPublicacaosCount();
	}

	public static java.util.Date getLastArticlesHighlightsPublicationDate()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLastArticlesHighlightsPublicationDate();
	}

	public static String getLatestArticleURL(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestArticleURL(itemPublicacao, serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static String getSocialNetworkName(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return getService().getSocialNetworkName(itemPublicacao);
	}

	public static String getSocialNetworkPublicationUrl(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return getService().getSocialNetworkPublicationUrl(itemPublicacao);
	}

	public static String getTwitterAccountName() {
		return getService().getTwitterAccountName();
	}

	/**
	* Método chamado após importação para publicar destaque de um conteúdo, se
	* houver, nas redes sociais configuradas.
	*
	* @param itemMigracao
	objeto da estrutura de migração a qual o conteúdo pertence
	* @param journalArticle
	conteúdo publicado
	* @param serviceContext
	contexto do serviço que executou a ação
	* @return Retorna verdadeiro quando a publicação de destaque foi realizada em
	pelo menois uma das redes sociais configuradas.
	*/
	public static Boolean publishArticleHighlightsOnSocialNetworks(
		br.com.seatecnologia.migracao.model.ItemMigracao itemMigracao,
		com.liferay.journal.model.JournalArticle journalArticle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .publishArticleHighlightsOnSocialNetworks(itemMigracao,
			journalArticle, serviceContext);
	}

	public static int publishLatestArticlesHighlightsFromCategories(
		String[] categoriesIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .publishLatestArticlesHighlightsFromCategories(categoriesIds,
			serviceContext);
	}

	public static java.util.Map<String, String> twitterGetAccessToken(
		String pin) throws com.liferay.portal.kernel.exception.PortalException {
		return getService().twitterGetAccessToken(pin);
	}

	public static String twitterGetLoginUrl(String OAuthConsumerKey,
		String OAuthConsumerSecret, String redirectUri) {
		return getService()
				   .twitterGetLoginUrl(OAuthConsumerKey, OAuthConsumerSecret,
			redirectUri);
	}

	/**
	* Updates the item publicacao in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was updated
	*/
	public static br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao updateItemPublicacao(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return getService().updateItemPublicacao(itemPublicacao);
	}

	public static ItemPublicacaoLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ItemPublicacaoLocalService, ItemPublicacaoLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ItemPublicacaoLocalService.class);

		ServiceTracker<ItemPublicacaoLocalService, ItemPublicacaoLocalService> serviceTracker =
			new ServiceTracker<ItemPublicacaoLocalService, ItemPublicacaoLocalService>(bundle.getBundleContext(),
				ItemPublicacaoLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
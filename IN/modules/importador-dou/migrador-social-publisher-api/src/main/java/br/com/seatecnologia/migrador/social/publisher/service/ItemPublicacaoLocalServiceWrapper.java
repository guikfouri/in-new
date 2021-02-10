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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link ItemPublicacaoLocalService}.
 *
 * @author SEA Tecnologia
 * @see ItemPublicacaoLocalService
 * @generated
 */
@ProviderType
public class ItemPublicacaoLocalServiceWrapper
	implements ItemPublicacaoLocalService,
		ServiceWrapper<ItemPublicacaoLocalService> {
	public ItemPublicacaoLocalServiceWrapper(
		ItemPublicacaoLocalService itemPublicacaoLocalService) {
		_itemPublicacaoLocalService = itemPublicacaoLocalService;
	}

	/**
	* Adds the item publicacao to the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was added
	*/
	@Override
	public br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao addItemPublicacao(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return _itemPublicacaoLocalService.addItemPublicacao(itemPublicacao);
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
	@Override
	public Boolean configureFacebook(Boolean enable, String pageId,
		String pageAccessToken) {
		return _itemPublicacaoLocalService.configureFacebook(enable, pageId,
			pageAccessToken);
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
	@Override
	public Boolean configureTwitter(Boolean enable, String OAuthConsumerKey,
		String OAuthConsumerSecret, String OAuthAccessToken,
		String OAuthAccessTokenSecret) {
		return _itemPublicacaoLocalService.configureTwitter(enable,
			OAuthConsumerKey, OAuthConsumerSecret, OAuthAccessToken,
			OAuthAccessTokenSecret);
	}

	/**
	* Creates a new item publicacao with the primary key. Does not add the item publicacao to the database.
	*
	* @param itemPublicacaoId the primary key for the new item publicacao
	* @return the new item publicacao
	*/
	@Override
	public br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao createItemPublicacao(
		long itemPublicacaoId) {
		return _itemPublicacaoLocalService.createItemPublicacao(itemPublicacaoId);
	}

	@Override
	public void deleteArticleHighlightsFromSocialNetworks(int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		_itemPublicacaoLocalService.deleteArticleHighlightsFromSocialNetworks(start,
			end);
	}

	/**
	* Deletes the item publicacao from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was removed
	*/
	@Override
	public br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao deleteItemPublicacao(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return _itemPublicacaoLocalService.deleteItemPublicacao(itemPublicacao);
	}

	/**
	* Deletes the item publicacao with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao that was removed
	* @throws PortalException if a item publicacao with the primary key could not be found
	*/
	@Override
	public br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao deleteItemPublicacao(
		long itemPublicacaoId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemPublicacaoLocalService.deleteItemPublicacao(itemPublicacaoId);
	}

	/**
	* @throws PortalException
	*/
	@Override
	public com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemPublicacaoLocalService.deletePersistedModel(persistedModel);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return _itemPublicacaoLocalService.dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _itemPublicacaoLocalService.dynamicQuery(dynamicQuery);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return _itemPublicacaoLocalService.dynamicQuery(dynamicQuery, start, end);
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
	@Override
	public <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return _itemPublicacaoLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return _itemPublicacaoLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Override
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return _itemPublicacaoLocalService.dynamicQueryCount(dynamicQuery,
			projection);
	}

	@Override
	public void facebookCancelAuthorizationProccess() {
		_itemPublicacaoLocalService.facebookCancelAuthorizationProccess();
	}

	@Override
	public java.util.Map<String, String> facebookGetDeviceCode(String appId,
		String clientToken) {
		return _itemPublicacaoLocalService.facebookGetDeviceCode(appId,
			clientToken);
	}

	@Override
	public String facebookGetLoginUrl(String appId, String redirectUri) {
		return _itemPublicacaoLocalService.facebookGetLoginUrl(appId,
			redirectUri);
	}

	@Override
	public String facebookGetPageAccessToken(String appId, String appSecret,
		String pageId) {
		return _itemPublicacaoLocalService.facebookGetPageAccessToken(appId,
			appSecret, pageId);
	}

	@Override
	public String facebookGetPageAccessToken(String appId, String appSecret,
		String pageId, String code) {
		return _itemPublicacaoLocalService.facebookGetPageAccessToken(appId,
			appSecret, pageId, code);
	}

	@Override
	public br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao fetchItemPublicacao(
		long itemPublicacaoId) {
		return _itemPublicacaoLocalService.fetchItemPublicacao(itemPublicacaoId);
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return _itemPublicacaoLocalService.getActionableDynamicQuery();
	}

	@Override
	public String getFacebookPageName() {
		return _itemPublicacaoLocalService.getFacebookPageName();
	}

	@Override
	public com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return _itemPublicacaoLocalService.getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the item publicacao with the primary key.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao
	* @throws PortalException if a item publicacao with the primary key could not be found
	*/
	@Override
	public br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao getItemPublicacao(
		long itemPublicacaoId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemPublicacaoLocalService.getItemPublicacao(itemPublicacaoId);
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
	@Override
	public java.util.List<br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao> getItemPublicacaos(
		int start, int end) {
		return _itemPublicacaoLocalService.getItemPublicacaos(start, end);
	}

	/**
	* Returns the number of item publicacaos.
	*
	* @return the number of item publicacaos
	*/
	@Override
	public int getItemPublicacaosCount() {
		return _itemPublicacaoLocalService.getItemPublicacaosCount();
	}

	@Override
	public java.util.Date getLastArticlesHighlightsPublicationDate()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _itemPublicacaoLocalService.getLastArticlesHighlightsPublicationDate();
	}

	@Override
	public String getLatestArticleURL(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _itemPublicacaoLocalService.getLatestArticleURL(itemPublicacao,
			serviceContext);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _itemPublicacaoLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemPublicacaoLocalService.getPersistedModel(primaryKeyObj);
	}

	@Override
	public String getSocialNetworkName(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return _itemPublicacaoLocalService.getSocialNetworkName(itemPublicacao);
	}

	@Override
	public String getSocialNetworkPublicationUrl(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return _itemPublicacaoLocalService.getSocialNetworkPublicationUrl(itemPublicacao);
	}

	@Override
	public String getTwitterAccountName() {
		return _itemPublicacaoLocalService.getTwitterAccountName();
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
	@Override
	public Boolean publishArticleHighlightsOnSocialNetworks(
		br.com.seatecnologia.migracao.model.ItemMigracao itemMigracao,
		com.liferay.journal.model.JournalArticle journalArticle,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _itemPublicacaoLocalService.publishArticleHighlightsOnSocialNetworks(itemMigracao,
			journalArticle, serviceContext);
	}

	@Override
	public int publishLatestArticlesHighlightsFromCategories(
		String[] categoriesIds,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _itemPublicacaoLocalService.publishLatestArticlesHighlightsFromCategories(categoriesIds,
			serviceContext);
	}

	@Override
	public java.util.Map<String, String> twitterGetAccessToken(String pin)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _itemPublicacaoLocalService.twitterGetAccessToken(pin);
	}

	@Override
	public String twitterGetLoginUrl(String OAuthConsumerKey,
		String OAuthConsumerSecret, String redirectUri) {
		return _itemPublicacaoLocalService.twitterGetLoginUrl(OAuthConsumerKey,
			OAuthConsumerSecret, redirectUri);
	}

	/**
	* Updates the item publicacao in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was updated
	*/
	@Override
	public br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao updateItemPublicacao(
		br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao itemPublicacao) {
		return _itemPublicacaoLocalService.updateItemPublicacao(itemPublicacao);
	}

	@Override
	public ItemPublicacaoLocalService getWrappedService() {
		return _itemPublicacaoLocalService;
	}

	@Override
	public void setWrappedService(
		ItemPublicacaoLocalService itemPublicacaoLocalService) {
		_itemPublicacaoLocalService = itemPublicacaoLocalService;
	}

	private ItemPublicacaoLocalService _itemPublicacaoLocalService;
}
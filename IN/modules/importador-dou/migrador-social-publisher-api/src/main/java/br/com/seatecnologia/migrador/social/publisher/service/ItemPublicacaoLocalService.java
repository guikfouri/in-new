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

import br.com.seatecnologia.migracao.model.ItemMigracao;
import br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao;

import com.liferay.journal.model.JournalArticle;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalService;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Provides the local service interface for ItemPublicacao. Methods of this
 * service will not have security checks based on the propagated JAAS
 * credentials because this service can only be accessed from within the same
 * VM.
 *
 * @author SEA Tecnologia
 * @see ItemPublicacaoLocalServiceUtil
 * @see br.com.seatecnologia.migrador.social.publisher.service.base.ItemPublicacaoLocalServiceBaseImpl
 * @see br.com.seatecnologia.migrador.social.publisher.service.impl.ItemPublicacaoLocalServiceImpl
 * @generated
 */
@ProviderType
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface ItemPublicacaoLocalService extends BaseLocalService,
	PersistedModelLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ItemPublicacaoLocalServiceUtil} to access the item publicacao local service. Add custom service methods to {@link br.com.seatecnologia.migrador.social.publisher.service.impl.ItemPublicacaoLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the item publicacao to the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was added
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ItemPublicacao addItemPublicacao(ItemPublicacao itemPublicacao);

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
	public Boolean configureFacebook(Boolean enable, String pageId,
		String pageAccessToken);

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
	public Boolean configureTwitter(Boolean enable, String OAuthConsumerKey,
		String OAuthConsumerSecret, String OAuthAccessToken,
		String OAuthAccessTokenSecret);

	/**
	* Creates a new item publicacao with the primary key. Does not add the item publicacao to the database.
	*
	* @param itemPublicacaoId the primary key for the new item publicacao
	* @return the new item publicacao
	*/
	@Transactional(enabled = false)
	public ItemPublicacao createItemPublicacao(long itemPublicacaoId);

	public void deleteArticleHighlightsFromSocialNetworks(int start, int end)
		throws SystemException;

	/**
	* Deletes the item publicacao from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was removed
	*/
	@Indexable(type = IndexableType.DELETE)
	public ItemPublicacao deleteItemPublicacao(ItemPublicacao itemPublicacao);

	/**
	* Deletes the item publicacao with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao that was removed
	* @throws PortalException if a item publicacao with the primary key could not be found
	*/
	@Indexable(type = IndexableType.DELETE)
	public ItemPublicacao deleteItemPublicacao(long itemPublicacaoId)
		throws PortalException;

	/**
	* @throws PortalException
	*/
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public DynamicQuery dynamicQuery();

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end);

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery);

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection);

	public void facebookCancelAuthorizationProccess();

	public Map<String, String> facebookGetDeviceCode(String appId,
		String clientToken);

	public String facebookGetLoginUrl(String appId, String redirectUri);

	public String facebookGetPageAccessToken(String appId, String appSecret,
		String pageId);

	public String facebookGetPageAccessToken(String appId, String appSecret,
		String pageId, String code);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ItemPublicacao fetchItemPublicacao(long itemPublicacaoId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ActionableDynamicQuery getActionableDynamicQuery();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getFacebookPageName();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery();

	/**
	* Returns the item publicacao with the primary key.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao
	* @throws PortalException if a item publicacao with the primary key could not be found
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ItemPublicacao getItemPublicacao(long itemPublicacaoId)
		throws PortalException;

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
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ItemPublicacao> getItemPublicacaos(int start, int end);

	/**
	* Returns the number of item publicacaos.
	*
	* @return the number of item publicacaos
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getItemPublicacaosCount();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public Date getLastArticlesHighlightsPublicationDate()
		throws SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getLatestArticleURL(ItemPublicacao itemPublicacao,
		ServiceContext serviceContext) throws PortalException, SystemException;

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public String getOSGiServiceIdentifier();

	@Override
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getSocialNetworkName(ItemPublicacao itemPublicacao);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getSocialNetworkPublicationUrl(ItemPublicacao itemPublicacao);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public String getTwitterAccountName();

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
	public Boolean publishArticleHighlightsOnSocialNetworks(
		ItemMigracao itemMigracao, JournalArticle journalArticle,
		ServiceContext serviceContext) throws PortalException, SystemException;

	public int publishLatestArticlesHighlightsFromCategories(
		String[] categoriesIds, ServiceContext serviceContext)
		throws PortalException, SystemException;

	public Map<String, String> twitterGetAccessToken(String pin)
		throws PortalException;

	public String twitterGetLoginUrl(String OAuthConsumerKey,
		String OAuthConsumerSecret, String redirectUri);

	/**
	* Updates the item publicacao in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacao the item publicacao
	* @return the item publicacao that was updated
	*/
	@Indexable(type = IndexableType.REINDEX)
	public ItemPublicacao updateItemPublicacao(ItemPublicacao itemPublicacao);
}
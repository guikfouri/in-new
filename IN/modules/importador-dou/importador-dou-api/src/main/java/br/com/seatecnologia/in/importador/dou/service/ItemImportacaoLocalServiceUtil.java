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

package br.com.seatecnologia.in.importador.dou.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for ItemImportacao. This utility wraps
 * {@link br.com.seatecnologia.in.importador.dou.service.impl.ItemImportacaoLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author SEA Tecnologia
 * @see ItemImportacaoLocalService
 * @see br.com.seatecnologia.in.importador.dou.service.base.ItemImportacaoLocalServiceBaseImpl
 * @see br.com.seatecnologia.in.importador.dou.service.impl.ItemImportacaoLocalServiceImpl
 * @generated
 */
@ProviderType
public class ItemImportacaoLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link br.com.seatecnologia.in.importador.dou.service.impl.ItemImportacaoLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the item importacao to the database. Also notifies the appropriate model listeners.
	*
	* @param itemImportacao the item importacao
	* @return the item importacao that was added
	*/
	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao addItemImportacao(
		br.com.seatecnologia.in.importador.dou.model.ItemImportacao itemImportacao) {
		return getService().addItemImportacao(itemImportacao);
	}

	public static int contarItensImportacaoPorIdentificadorAtualizacao(
		String identificadorAtualizacao) {
		return getService()
				   .contarItensImportacaoPorIdentificadorAtualizacao(identificadorAtualizacao);
	}

	/**
	* Creates a new item importacao with the primary key. Does not add the item importacao to the database.
	*
	* @param itemImportacaoId the primary key for the new item importacao
	* @return the new item importacao
	*/
	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao createItemImportacao(
		long itemImportacaoId) {
		return getService().createItemImportacao(itemImportacaoId);
	}

	/**
	* Deletes the item importacao from the database. Also notifies the appropriate model listeners.
	*
	* @param itemImportacao the item importacao
	* @return the item importacao that was removed
	*/
	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao deleteItemImportacao(
		br.com.seatecnologia.in.importador.dou.model.ItemImportacao itemImportacao) {
		return getService().deleteItemImportacao(itemImportacao);
	}

	/**
	* Deletes the item importacao with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemImportacaoId the primary key of the item importacao
	* @return the item importacao that was removed
	* @throws PortalException if a item importacao with the primary key could not be found
	*/
	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao deleteItemImportacao(
		long itemImportacaoId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteItemImportacao(itemImportacaoId);
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao fetchItemImportacao(
		long itemImportacaoId) {
		return getService().fetchItemImportacao(itemImportacaoId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* Returns the item importacao with the primary key.
	*
	* @param itemImportacaoId the primary key of the item importacao
	* @return the item importacao
	* @throws PortalException if a item importacao with the primary key could not be found
	*/
	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao getItemImportacao(
		long itemImportacaoId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getItemImportacao(itemImportacaoId);
	}

	/**
	* Returns a range of all the item importacaos.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of item importacaos
	* @param end the upper bound of the range of item importacaos (not inclusive)
	* @return the range of item importacaos
	*/
	public static java.util.List<br.com.seatecnologia.in.importador.dou.model.ItemImportacao> getItemImportacaos(
		int start, int end) {
		return getService().getItemImportacaos(start, end);
	}

	/**
	* Returns the number of item importacaos.
	*
	* @return the number of item importacaos
	*/
	public static int getItemImportacaosCount() {
		return getService().getItemImportacaosCount();
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

	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao importarXML(
		br.com.seatecnologia.in.importador.dou.context.ItemImportacaoServiceContext itemImportacaoServiceContext,
		String identificadorAtualizacao,
		br.com.seatecnologia.in.importador.dou.article.Article douArticle,
		java.util.Map<String, String> additionalXmlTxt,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return getService()
				   .importarXML(itemImportacaoServiceContext,
			identificadorAtualizacao, douArticle, additionalXmlTxt,
			serviceContext);
	}

	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao importarXML(
		br.com.seatecnologia.in.importador.dou.context.ItemImportacaoServiceContext itemImportacaoServiceContext,
		String identificadorAtualizacao, String xmlContent,
		java.util.Map<String, String> additionalXmlTxt,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return getService()
				   .importarXML(itemImportacaoServiceContext,
			identificadorAtualizacao, xmlContent, additionalXmlTxt,
			serviceContext);
	}

	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao importarXML(
		Long groupId, Long idSecaoVocab, Long idArranjoSecaoVocab,
		Long idTipoMateriaVocab, String basePathImgs,
		String identificadorAtualizacao,
		br.com.seatecnologia.in.importador.dou.article.Article douArticle,
		java.util.Map<String, String> additionalXmlTxt,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return getService()
				   .importarXML(groupId, idSecaoVocab, idArranjoSecaoVocab,
			idTipoMateriaVocab, basePathImgs, identificadorAtualizacao,
			douArticle, additionalXmlTxt, serviceContext);
	}

	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao importarXML(
		Long groupId, Long idSecaoVocab, Long idArranjoSecaoVocab,
		Long idTipoMateriaVocab, String basePathImgs,
		String identificadorAtualizacao, String xmlContent,
		java.util.Map<String, String> additionalXmlTxt,
		com.liferay.portal.kernel.service.ServiceContext serviceContext) {
		return getService()
				   .importarXML(groupId, idSecaoVocab, idArranjoSecaoVocab,
			idTipoMateriaVocab, basePathImgs, identificadorAtualizacao,
			xmlContent, additionalXmlTxt, serviceContext);
	}

	public static void limparItemsImportacao()
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().limparItemsImportacao();
	}

	/**
	* Updates the item importacao in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param itemImportacao the item importacao
	* @return the item importacao that was updated
	*/
	public static br.com.seatecnologia.in.importador.dou.model.ItemImportacao updateItemImportacao(
		br.com.seatecnologia.in.importador.dou.model.ItemImportacao itemImportacao) {
		return getService().updateItemImportacao(itemImportacao);
	}

	public static ItemImportacaoLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ItemImportacaoLocalService, ItemImportacaoLocalService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ItemImportacaoLocalService.class);

		ServiceTracker<ItemImportacaoLocalService, ItemImportacaoLocalService> serviceTracker =
			new ServiceTracker<ItemImportacaoLocalService, ItemImportacaoLocalService>(bundle.getBundleContext(),
				ItemImportacaoLocalService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
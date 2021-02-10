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

package br.com.seatecnologia.migrador.social.publisher.service.persistence;

import aQute.bnd.annotation.ProviderType;

import br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.Date;
import java.util.List;

/**
 * The persistence utility for the item publicacao service. This utility wraps {@link br.com.seatecnologia.migrador.social.publisher.service.persistence.impl.ItemPublicacaoPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemPublicacaoPersistence
 * @see br.com.seatecnologia.migrador.social.publisher.service.persistence.impl.ItemPublicacaoPersistenceImpl
 * @generated
 */
@ProviderType
public class ItemPublicacaoUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(ItemPublicacao itemPublicacao) {
		getPersistence().clearCache(itemPublicacao);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<ItemPublicacao> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ItemPublicacao> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ItemPublicacao> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ItemPublicacao update(ItemPublicacao itemPublicacao) {
		return getPersistence().update(itemPublicacao);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ItemPublicacao update(ItemPublicacao itemPublicacao,
		ServiceContext serviceContext) {
		return getPersistence().update(itemPublicacao, serviceContext);
	}

	/**
	* Returns all the item publicacaos where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @return the matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryIdPublishDate(
		long entryId, Date publishDate) {
		return getPersistence()
				   .findByAssetEntryIdPublishDate(entryId, publishDate);
	}

	/**
	* Returns a range of all the item publicacaos where entryId = &#63; and publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @return the range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryIdPublishDate(
		long entryId, Date publishDate, int start, int end) {
		return getPersistence()
				   .findByAssetEntryIdPublishDate(entryId, publishDate, start,
			end);
	}

	/**
	* Returns an ordered range of all the item publicacaos where entryId = &#63; and publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryIdPublishDate(
		long entryId, Date publishDate, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .findByAssetEntryIdPublishDate(entryId, publishDate, start,
			end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the item publicacaos where entryId = &#63; and publishDate = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryIdPublishDate(
		long entryId, Date publishDate, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByAssetEntryIdPublishDate(entryId, publishDate, start,
			end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public static ItemPublicacao findByAssetEntryIdPublishDate_First(
		long entryId, Date publishDate,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryIdPublishDate_First(entryId, publishDate,
			orderByComparator);
	}

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public static ItemPublicacao fetchByAssetEntryIdPublishDate_First(
		long entryId, Date publishDate,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryIdPublishDate_First(entryId, publishDate,
			orderByComparator);
	}

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public static ItemPublicacao findByAssetEntryIdPublishDate_Last(
		long entryId, Date publishDate,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryIdPublishDate_Last(entryId, publishDate,
			orderByComparator);
	}

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public static ItemPublicacao fetchByAssetEntryIdPublishDate_Last(
		long entryId, Date publishDate,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryIdPublishDate_Last(entryId, publishDate,
			orderByComparator);
	}

	/**
	* Returns the item publicacaos before and after the current item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param itemPublicacaoId the primary key of the current item publicacao
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next item publicacao
	* @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	*/
	public static ItemPublicacao[] findByAssetEntryIdPublishDate_PrevAndNext(
		long itemPublicacaoId, long entryId, Date publishDate,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryIdPublishDate_PrevAndNext(itemPublicacaoId,
			entryId, publishDate, orderByComparator);
	}

	/**
	* Removes all the item publicacaos where entryId = &#63; and publishDate = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	*/
	public static void removeByAssetEntryIdPublishDate(long entryId,
		Date publishDate) {
		getPersistence().removeByAssetEntryIdPublishDate(entryId, publishDate);
	}

	/**
	* Returns the number of item publicacaos where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @return the number of matching item publicacaos
	*/
	public static int countByAssetEntryIdPublishDate(long entryId,
		Date publishDate) {
		return getPersistence()
				   .countByAssetEntryIdPublishDate(entryId, publishDate);
	}

	/**
	* Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or throws a {@link NoSuchItemPublicacaoException} if it could not be found.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @return the matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public static ItemPublicacao findByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryIdSocialNetwork(entryId, socialNetwork);
	}

	/**
	* Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @return the matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public static ItemPublicacao fetchByAssetEntryIdSocialNetwork(
		long entryId, int socialNetwork) {
		return getPersistence()
				   .fetchByAssetEntryIdSocialNetwork(entryId, socialNetwork);
	}

	/**
	* Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public static ItemPublicacao fetchByAssetEntryIdSocialNetwork(
		long entryId, int socialNetwork, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByAssetEntryIdSocialNetwork(entryId, socialNetwork,
			retrieveFromCache);
	}

	/**
	* Removes the item publicacao where entryId = &#63; and socialNetwork = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @return the item publicacao that was removed
	*/
	public static ItemPublicacao removeByAssetEntryIdSocialNetwork(
		long entryId, int socialNetwork)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .removeByAssetEntryIdSocialNetwork(entryId, socialNetwork);
	}

	/**
	* Returns the number of item publicacaos where entryId = &#63; and socialNetwork = &#63;.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @return the number of matching item publicacaos
	*/
	public static int countByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork) {
		return getPersistence()
				   .countByAssetEntryIdSocialNetwork(entryId, socialNetwork);
	}

	/**
	* Returns all the item publicacaos where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryId(long entryId) {
		return getPersistence().findByAssetEntryId(entryId);
	}

	/**
	* Returns a range of all the item publicacaos where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @return the range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryId(long entryId,
		int start, int end) {
		return getPersistence().findByAssetEntryId(entryId, start, end);
	}

	/**
	* Returns an ordered range of all the item publicacaos where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryId(long entryId,
		int start, int end, OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .findByAssetEntryId(entryId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the item publicacaos where entryId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryId(long entryId,
		int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByAssetEntryId(entryId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public static ItemPublicacao findByAssetEntryId_First(long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public static ItemPublicacao fetchByAssetEntryId_First(long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryId_First(entryId, orderByComparator);
	}

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public static ItemPublicacao findByAssetEntryId_Last(long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public static ItemPublicacao fetchByAssetEntryId_Last(long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryId_Last(entryId, orderByComparator);
	}

	/**
	* Returns the item publicacaos before and after the current item publicacao in the ordered set where entryId = &#63;.
	*
	* @param itemPublicacaoId the primary key of the current item publicacao
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next item publicacao
	* @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	*/
	public static ItemPublicacao[] findByAssetEntryId_PrevAndNext(
		long itemPublicacaoId, long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryId_PrevAndNext(itemPublicacaoId, entryId,
			orderByComparator);
	}

	/**
	* Removes all the item publicacaos where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	*/
	public static void removeByAssetEntryId(long entryId) {
		getPersistence().removeByAssetEntryId(entryId);
	}

	/**
	* Returns the number of item publicacaos where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching item publicacaos
	*/
	public static int countByAssetEntryId(long entryId) {
		return getPersistence().countByAssetEntryId(entryId);
	}

	/**
	* Returns all the item publicacaos where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @return the matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryIdPostId(long entryId,
		String postId) {
		return getPersistence().findByAssetEntryIdPostId(entryId, postId);
	}

	/**
	* Returns a range of all the item publicacaos where entryId = &#63; and postId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @return the range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryIdPostId(long entryId,
		String postId, int start, int end) {
		return getPersistence()
				   .findByAssetEntryIdPostId(entryId, postId, start, end);
	}

	/**
	* Returns an ordered range of all the item publicacaos where entryId = &#63; and postId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryIdPostId(long entryId,
		String postId, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .findByAssetEntryIdPostId(entryId, postId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the item publicacaos where entryId = &#63; and postId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching item publicacaos
	*/
	public static List<ItemPublicacao> findByAssetEntryIdPostId(long entryId,
		String postId, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByAssetEntryIdPostId(entryId, postId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public static ItemPublicacao findByAssetEntryIdPostId_First(long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryIdPostId_First(entryId, postId,
			orderByComparator);
	}

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public static ItemPublicacao fetchByAssetEntryIdPostId_First(long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryIdPostId_First(entryId, postId,
			orderByComparator);
	}

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public static ItemPublicacao findByAssetEntryIdPostId_Last(long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryIdPostId_Last(entryId, postId,
			orderByComparator);
	}

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public static ItemPublicacao fetchByAssetEntryIdPostId_Last(long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence()
				   .fetchByAssetEntryIdPostId_Last(entryId, postId,
			orderByComparator);
	}

	/**
	* Returns the item publicacaos before and after the current item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param itemPublicacaoId the primary key of the current item publicacao
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next item publicacao
	* @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	*/
	public static ItemPublicacao[] findByAssetEntryIdPostId_PrevAndNext(
		long itemPublicacaoId, long entryId, String postId,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence()
				   .findByAssetEntryIdPostId_PrevAndNext(itemPublicacaoId,
			entryId, postId, orderByComparator);
	}

	/**
	* Removes all the item publicacaos where entryId = &#63; and postId = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	*/
	public static void removeByAssetEntryIdPostId(long entryId, String postId) {
		getPersistence().removeByAssetEntryIdPostId(entryId, postId);
	}

	/**
	* Returns the number of item publicacaos where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @return the number of matching item publicacaos
	*/
	public static int countByAssetEntryIdPostId(long entryId, String postId) {
		return getPersistence().countByAssetEntryIdPostId(entryId, postId);
	}

	/**
	* Caches the item publicacao in the entity cache if it is enabled.
	*
	* @param itemPublicacao the item publicacao
	*/
	public static void cacheResult(ItemPublicacao itemPublicacao) {
		getPersistence().cacheResult(itemPublicacao);
	}

	/**
	* Caches the item publicacaos in the entity cache if it is enabled.
	*
	* @param itemPublicacaos the item publicacaos
	*/
	public static void cacheResult(List<ItemPublicacao> itemPublicacaos) {
		getPersistence().cacheResult(itemPublicacaos);
	}

	/**
	* Creates a new item publicacao with the primary key. Does not add the item publicacao to the database.
	*
	* @param itemPublicacaoId the primary key for the new item publicacao
	* @return the new item publicacao
	*/
	public static ItemPublicacao create(long itemPublicacaoId) {
		return getPersistence().create(itemPublicacaoId);
	}

	/**
	* Removes the item publicacao with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao that was removed
	* @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	*/
	public static ItemPublicacao remove(long itemPublicacaoId)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence().remove(itemPublicacaoId);
	}

	public static ItemPublicacao updateImpl(ItemPublicacao itemPublicacao) {
		return getPersistence().updateImpl(itemPublicacao);
	}

	/**
	* Returns the item publicacao with the primary key or throws a {@link NoSuchItemPublicacaoException} if it could not be found.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao
	* @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	*/
	public static ItemPublicacao findByPrimaryKey(long itemPublicacaoId)
		throws br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException {
		return getPersistence().findByPrimaryKey(itemPublicacaoId);
	}

	/**
	* Returns the item publicacao with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao, or <code>null</code> if a item publicacao with the primary key could not be found
	*/
	public static ItemPublicacao fetchByPrimaryKey(long itemPublicacaoId) {
		return getPersistence().fetchByPrimaryKey(itemPublicacaoId);
	}

	public static java.util.Map<java.io.Serializable, ItemPublicacao> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the item publicacaos.
	*
	* @return the item publicacaos
	*/
	public static List<ItemPublicacao> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the item publicacaos.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @return the range of item publicacaos
	*/
	public static List<ItemPublicacao> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the item publicacaos.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of item publicacaos
	*/
	public static List<ItemPublicacao> findAll(int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the item publicacaos.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemPublicacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of item publicacaos
	* @param end the upper bound of the range of item publicacaos (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of item publicacaos
	*/
	public static List<ItemPublicacao> findAll(int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the item publicacaos from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of item publicacaos.
	*
	* @return the number of item publicacaos
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ItemPublicacaoPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ItemPublicacaoPersistence, ItemPublicacaoPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ItemPublicacaoPersistence.class);

		ServiceTracker<ItemPublicacaoPersistence, ItemPublicacaoPersistence> serviceTracker =
			new ServiceTracker<ItemPublicacaoPersistence, ItemPublicacaoPersistence>(bundle.getBundleContext(),
				ItemPublicacaoPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
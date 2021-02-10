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

import br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException;
import br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import java.util.Date;

/**
 * The persistence interface for the item publicacao service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author SEA Tecnologia
 * @see br.com.seatecnologia.migrador.social.publisher.service.persistence.impl.ItemPublicacaoPersistenceImpl
 * @see ItemPublicacaoUtil
 * @generated
 */
@ProviderType
public interface ItemPublicacaoPersistence extends BasePersistence<ItemPublicacao> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ItemPublicacaoUtil} to access the item publicacao persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the item publicacaos where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @return the matching item publicacaos
	*/
	public java.util.List<ItemPublicacao> findByAssetEntryIdPublishDate(
		long entryId, Date publishDate);

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
	public java.util.List<ItemPublicacao> findByAssetEntryIdPublishDate(
		long entryId, Date publishDate, int start, int end);

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
	public java.util.List<ItemPublicacao> findByAssetEntryIdPublishDate(
		long entryId, Date publishDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

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
	public java.util.List<ItemPublicacao> findByAssetEntryIdPublishDate(
		long entryId, Date publishDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public ItemPublicacao findByAssetEntryIdPublishDate_First(long entryId,
		Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public ItemPublicacao fetchByAssetEntryIdPublishDate_First(long entryId,
		Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public ItemPublicacao findByAssetEntryIdPublishDate_Last(long entryId,
		Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public ItemPublicacao fetchByAssetEntryIdPublishDate_Last(long entryId,
		Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

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
	public ItemPublicacao[] findByAssetEntryIdPublishDate_PrevAndNext(
		long itemPublicacaoId, long entryId, Date publishDate,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Removes all the item publicacaos where entryId = &#63; and publishDate = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	*/
	public void removeByAssetEntryIdPublishDate(long entryId, Date publishDate);

	/**
	* Returns the number of item publicacaos where entryId = &#63; and publishDate = &#63;.
	*
	* @param entryId the entry ID
	* @param publishDate the publish date
	* @return the number of matching item publicacaos
	*/
	public int countByAssetEntryIdPublishDate(long entryId, Date publishDate);

	/**
	* Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or throws a {@link NoSuchItemPublicacaoException} if it could not be found.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @return the matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public ItemPublicacao findByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork) throws NoSuchItemPublicacaoException;

	/**
	* Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @return the matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public ItemPublicacao fetchByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork);

	/**
	* Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public ItemPublicacao fetchByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork, boolean retrieveFromCache);

	/**
	* Removes the item publicacao where entryId = &#63; and socialNetwork = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @return the item publicacao that was removed
	*/
	public ItemPublicacao removeByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork) throws NoSuchItemPublicacaoException;

	/**
	* Returns the number of item publicacaos where entryId = &#63; and socialNetwork = &#63;.
	*
	* @param entryId the entry ID
	* @param socialNetwork the social network
	* @return the number of matching item publicacaos
	*/
	public int countByAssetEntryIdSocialNetwork(long entryId, int socialNetwork);

	/**
	* Returns all the item publicacaos where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the matching item publicacaos
	*/
	public java.util.List<ItemPublicacao> findByAssetEntryId(long entryId);

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
	public java.util.List<ItemPublicacao> findByAssetEntryId(long entryId,
		int start, int end);

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
	public java.util.List<ItemPublicacao> findByAssetEntryId(long entryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

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
	public java.util.List<ItemPublicacao> findByAssetEntryId(long entryId,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public ItemPublicacao findByAssetEntryId_First(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public ItemPublicacao fetchByAssetEntryId_First(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public ItemPublicacao findByAssetEntryId_Last(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public ItemPublicacao fetchByAssetEntryId_Last(long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

	/**
	* Returns the item publicacaos before and after the current item publicacao in the ordered set where entryId = &#63;.
	*
	* @param itemPublicacaoId the primary key of the current item publicacao
	* @param entryId the entry ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next item publicacao
	* @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	*/
	public ItemPublicacao[] findByAssetEntryId_PrevAndNext(
		long itemPublicacaoId, long entryId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Removes all the item publicacaos where entryId = &#63; from the database.
	*
	* @param entryId the entry ID
	*/
	public void removeByAssetEntryId(long entryId);

	/**
	* Returns the number of item publicacaos where entryId = &#63;.
	*
	* @param entryId the entry ID
	* @return the number of matching item publicacaos
	*/
	public int countByAssetEntryId(long entryId);

	/**
	* Returns all the item publicacaos where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @return the matching item publicacaos
	*/
	public java.util.List<ItemPublicacao> findByAssetEntryIdPostId(
		long entryId, String postId);

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
	public java.util.List<ItemPublicacao> findByAssetEntryIdPostId(
		long entryId, String postId, int start, int end);

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
	public java.util.List<ItemPublicacao> findByAssetEntryIdPostId(
		long entryId, String postId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

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
	public java.util.List<ItemPublicacao> findByAssetEntryIdPostId(
		long entryId, String postId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public ItemPublicacao findByAssetEntryIdPostId_First(long entryId,
		String postId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Returns the first item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public ItemPublicacao fetchByAssetEntryIdPostId_First(long entryId,
		String postId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao
	* @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	*/
	public ItemPublicacao findByAssetEntryIdPostId_Last(long entryId,
		String postId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Returns the last item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	*/
	public ItemPublicacao fetchByAssetEntryIdPostId_Last(long entryId,
		String postId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

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
	public ItemPublicacao[] findByAssetEntryIdPostId_PrevAndNext(
		long itemPublicacaoId, long entryId, String postId,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException;

	/**
	* Removes all the item publicacaos where entryId = &#63; and postId = &#63; from the database.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	*/
	public void removeByAssetEntryIdPostId(long entryId, String postId);

	/**
	* Returns the number of item publicacaos where entryId = &#63; and postId = &#63;.
	*
	* @param entryId the entry ID
	* @param postId the post ID
	* @return the number of matching item publicacaos
	*/
	public int countByAssetEntryIdPostId(long entryId, String postId);

	/**
	* Caches the item publicacao in the entity cache if it is enabled.
	*
	* @param itemPublicacao the item publicacao
	*/
	public void cacheResult(ItemPublicacao itemPublicacao);

	/**
	* Caches the item publicacaos in the entity cache if it is enabled.
	*
	* @param itemPublicacaos the item publicacaos
	*/
	public void cacheResult(java.util.List<ItemPublicacao> itemPublicacaos);

	/**
	* Creates a new item publicacao with the primary key. Does not add the item publicacao to the database.
	*
	* @param itemPublicacaoId the primary key for the new item publicacao
	* @return the new item publicacao
	*/
	public ItemPublicacao create(long itemPublicacaoId);

	/**
	* Removes the item publicacao with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao that was removed
	* @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	*/
	public ItemPublicacao remove(long itemPublicacaoId)
		throws NoSuchItemPublicacaoException;

	public ItemPublicacao updateImpl(ItemPublicacao itemPublicacao);

	/**
	* Returns the item publicacao with the primary key or throws a {@link NoSuchItemPublicacaoException} if it could not be found.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao
	* @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	*/
	public ItemPublicacao findByPrimaryKey(long itemPublicacaoId)
		throws NoSuchItemPublicacaoException;

	/**
	* Returns the item publicacao with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemPublicacaoId the primary key of the item publicacao
	* @return the item publicacao, or <code>null</code> if a item publicacao with the primary key could not be found
	*/
	public ItemPublicacao fetchByPrimaryKey(long itemPublicacaoId);

	@Override
	public java.util.Map<java.io.Serializable, ItemPublicacao> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the item publicacaos.
	*
	* @return the item publicacaos
	*/
	public java.util.List<ItemPublicacao> findAll();

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
	public java.util.List<ItemPublicacao> findAll(int start, int end);

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
	public java.util.List<ItemPublicacao> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator);

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
	public java.util.List<ItemPublicacao> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the item publicacaos from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of item publicacaos.
	*
	* @return the number of item publicacaos
	*/
	public int countAll();
}
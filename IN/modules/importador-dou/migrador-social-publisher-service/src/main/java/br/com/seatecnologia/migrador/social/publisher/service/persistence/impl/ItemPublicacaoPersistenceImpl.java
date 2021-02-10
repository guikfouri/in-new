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

package br.com.seatecnologia.migrador.social.publisher.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import br.com.seatecnologia.migrador.social.publisher.exception.NoSuchItemPublicacaoException;
import br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao;
import br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoImpl;
import br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoModelImpl;
import br.com.seatecnologia.migrador.social.publisher.service.persistence.ItemPublicacaoPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the item publicacao service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemPublicacaoPersistence
 * @see br.com.seatecnologia.migrador.social.publisher.service.persistence.ItemPublicacaoUtil
 * @generated
 */
@ProviderType
public class ItemPublicacaoPersistenceImpl extends BasePersistenceImpl<ItemPublicacao>
	implements ItemPublicacaoPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ItemPublicacaoUtil} to access the item publicacao persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ItemPublicacaoImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYIDPUBLISHDATE =
		new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAssetEntryIdPublishDate",
			new String[] {
				Long.class.getName(), Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPUBLISHDATE =
		new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAssetEntryIdPublishDate",
			new String[] { Long.class.getName(), Date.class.getName() },
			ItemPublicacaoModelImpl.ENTRYID_COLUMN_BITMASK |
			ItemPublicacaoModelImpl.PUBLISHDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYIDPUBLISHDATE = new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetEntryIdPublishDate",
			new String[] { Long.class.getName(), Date.class.getName() });

	/**
	 * Returns all the item publicacaos where entryId = &#63; and publishDate = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param publishDate the publish date
	 * @return the matching item publicacaos
	 */
	@Override
	public List<ItemPublicacao> findByAssetEntryIdPublishDate(long entryId,
		Date publishDate) {
		return findByAssetEntryIdPublishDate(entryId, publishDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryIdPublishDate(long entryId,
		Date publishDate, int start, int end) {
		return findByAssetEntryIdPublishDate(entryId, publishDate, start, end,
			null);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryIdPublishDate(long entryId,
		Date publishDate, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return findByAssetEntryIdPublishDate(entryId, publishDate, start, end,
			orderByComparator, true);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryIdPublishDate(long entryId,
		Date publishDate, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPUBLISHDATE;
			finderArgs = new Object[] { entryId, _getTime(publishDate) };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYIDPUBLISHDATE;
			finderArgs = new Object[] {
					entryId, _getTime(publishDate),
					
					start, end, orderByComparator
				};
		}

		List<ItemPublicacao> list = null;

		if (retrieveFromCache) {
			list = (List<ItemPublicacao>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ItemPublicacao itemPublicacao : list) {
					if ((entryId != itemPublicacao.getEntryId()) ||
							!Objects.equals(publishDate,
								itemPublicacao.getPublishDate())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ITEMPUBLICACAO_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_ENTRYID_2);

			boolean bindPublishDate = false;

			if (publishDate == null) {
				query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_PUBLISHDATE_1);
			}
			else {
				bindPublishDate = true;

				query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_PUBLISHDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ItemPublicacaoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindPublishDate) {
					qPos.add(new Timestamp(publishDate.getTime()));
				}

				if (!pagination) {
					list = (List<ItemPublicacao>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ItemPublicacao>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public ItemPublicacao findByAssetEntryIdPublishDate_First(long entryId,
		Date publishDate, OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = fetchByAssetEntryIdPublishDate_First(entryId,
				publishDate, orderByComparator);

		if (itemPublicacao != null) {
			return itemPublicacao;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", publishDate=");
		msg.append(publishDate);

		msg.append("}");

		throw new NoSuchItemPublicacaoException(msg.toString());
	}

	/**
	 * Returns the first item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao fetchByAssetEntryIdPublishDate_First(long entryId,
		Date publishDate, OrderByComparator<ItemPublicacao> orderByComparator) {
		List<ItemPublicacao> list = findByAssetEntryIdPublishDate(entryId,
				publishDate, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ItemPublicacao findByAssetEntryIdPublishDate_Last(long entryId,
		Date publishDate, OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = fetchByAssetEntryIdPublishDate_Last(entryId,
				publishDate, orderByComparator);

		if (itemPublicacao != null) {
			return itemPublicacao;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", publishDate=");
		msg.append(publishDate);

		msg.append("}");

		throw new NoSuchItemPublicacaoException(msg.toString());
	}

	/**
	 * Returns the last item publicacao in the ordered set where entryId = &#63; and publishDate = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param publishDate the publish date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao fetchByAssetEntryIdPublishDate_Last(long entryId,
		Date publishDate, OrderByComparator<ItemPublicacao> orderByComparator) {
		int count = countByAssetEntryIdPublishDate(entryId, publishDate);

		if (count == 0) {
			return null;
		}

		List<ItemPublicacao> list = findByAssetEntryIdPublishDate(entryId,
				publishDate, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ItemPublicacao[] findByAssetEntryIdPublishDate_PrevAndNext(
		long itemPublicacaoId, long entryId, Date publishDate,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = findByPrimaryKey(itemPublicacaoId);

		Session session = null;

		try {
			session = openSession();

			ItemPublicacao[] array = new ItemPublicacaoImpl[3];

			array[0] = getByAssetEntryIdPublishDate_PrevAndNext(session,
					itemPublicacao, entryId, publishDate, orderByComparator,
					true);

			array[1] = itemPublicacao;

			array[2] = getByAssetEntryIdPublishDate_PrevAndNext(session,
					itemPublicacao, entryId, publishDate, orderByComparator,
					false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ItemPublicacao getByAssetEntryIdPublishDate_PrevAndNext(
		Session session, ItemPublicacao itemPublicacao, long entryId,
		Date publishDate, OrderByComparator<ItemPublicacao> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ITEMPUBLICACAO_WHERE);

		query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_ENTRYID_2);

		boolean bindPublishDate = false;

		if (publishDate == null) {
			query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_PUBLISHDATE_1);
		}
		else {
			bindPublishDate = true;

			query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_PUBLISHDATE_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ItemPublicacaoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (bindPublishDate) {
			qPos.add(new Timestamp(publishDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(itemPublicacao);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ItemPublicacao> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the item publicacaos where entryId = &#63; and publishDate = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param publishDate the publish date
	 */
	@Override
	public void removeByAssetEntryIdPublishDate(long entryId, Date publishDate) {
		for (ItemPublicacao itemPublicacao : findByAssetEntryIdPublishDate(
				entryId, publishDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(itemPublicacao);
		}
	}

	/**
	 * Returns the number of item publicacaos where entryId = &#63; and publishDate = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param publishDate the publish date
	 * @return the number of matching item publicacaos
	 */
	@Override
	public int countByAssetEntryIdPublishDate(long entryId, Date publishDate) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYIDPUBLISHDATE;

		Object[] finderArgs = new Object[] { entryId, _getTime(publishDate) };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ITEMPUBLICACAO_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_ENTRYID_2);

			boolean bindPublishDate = false;

			if (publishDate == null) {
				query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_PUBLISHDATE_1);
			}
			else {
				bindPublishDate = true;

				query.append(_FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_PUBLISHDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindPublishDate) {
					qPos.add(new Timestamp(publishDate.getTime()));
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_ENTRYID_2 =
		"itemPublicacao.entryId = ? AND ";
	private static final String _FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_PUBLISHDATE_1 =
		"itemPublicacao.publishDate IS NULL";
	private static final String _FINDER_COLUMN_ASSETENTRYIDPUBLISHDATE_PUBLISHDATE_2 =
		"itemPublicacao.publishDate = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK =
		new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByAssetEntryIdSocialNetwork",
			new String[] { Long.class.getName(), Integer.class.getName() },
			ItemPublicacaoModelImpl.ENTRYID_COLUMN_BITMASK |
			ItemPublicacaoModelImpl.SOCIALNETWORK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYIDSOCIALNETWORK =
		new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetEntryIdSocialNetwork",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or throws a {@link NoSuchItemPublicacaoException} if it could not be found.
	 *
	 * @param entryId the entry ID
	 * @param socialNetwork the social network
	 * @return the matching item publicacao
	 * @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao findByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork) throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = fetchByAssetEntryIdSocialNetwork(entryId,
				socialNetwork);

		if (itemPublicacao == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("entryId=");
			msg.append(entryId);

			msg.append(", socialNetwork=");
			msg.append(socialNetwork);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchItemPublicacaoException(msg.toString());
		}

		return itemPublicacao;
	}

	/**
	 * Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param entryId the entry ID
	 * @param socialNetwork the social network
	 * @return the matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao fetchByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork) {
		return fetchByAssetEntryIdSocialNetwork(entryId, socialNetwork, true);
	}

	/**
	 * Returns the item publicacao where entryId = &#63; and socialNetwork = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param entryId the entry ID
	 * @param socialNetwork the social network
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao fetchByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { entryId, socialNetwork };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK,
					finderArgs, this);
		}

		if (result instanceof ItemPublicacao) {
			ItemPublicacao itemPublicacao = (ItemPublicacao)result;

			if ((entryId != itemPublicacao.getEntryId()) ||
					(socialNetwork != itemPublicacao.getSocialNetwork())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_ITEMPUBLICACAO_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYIDSOCIALNETWORK_ENTRYID_2);

			query.append(_FINDER_COLUMN_ASSETENTRYIDSOCIALNETWORK_SOCIALNETWORK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				qPos.add(socialNetwork);

				List<ItemPublicacao> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK,
						finderArgs, list);
				}
				else {
					ItemPublicacao itemPublicacao = list.get(0);

					result = itemPublicacao;

					cacheResult(itemPublicacao);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK,
					finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (ItemPublicacao)result;
		}
	}

	/**
	 * Removes the item publicacao where entryId = &#63; and socialNetwork = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param socialNetwork the social network
	 * @return the item publicacao that was removed
	 */
	@Override
	public ItemPublicacao removeByAssetEntryIdSocialNetwork(long entryId,
		int socialNetwork) throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = findByAssetEntryIdSocialNetwork(entryId,
				socialNetwork);

		return remove(itemPublicacao);
	}

	/**
	 * Returns the number of item publicacaos where entryId = &#63; and socialNetwork = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param socialNetwork the social network
	 * @return the number of matching item publicacaos
	 */
	@Override
	public int countByAssetEntryIdSocialNetwork(long entryId, int socialNetwork) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYIDSOCIALNETWORK;

		Object[] finderArgs = new Object[] { entryId, socialNetwork };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ITEMPUBLICACAO_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYIDSOCIALNETWORK_ENTRYID_2);

			query.append(_FINDER_COLUMN_ASSETENTRYIDSOCIALNETWORK_SOCIALNETWORK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				qPos.add(socialNetwork);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ASSETENTRYIDSOCIALNETWORK_ENTRYID_2 =
		"itemPublicacao.entryId = ? AND ";
	private static final String _FINDER_COLUMN_ASSETENTRYIDSOCIALNETWORK_SOCIALNETWORK_2 =
		"itemPublicacao.socialNetwork = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAssetEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID =
		new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAssetEntryId",
			new String[] { Long.class.getName() },
			ItemPublicacaoModelImpl.ENTRYID_COLUMN_BITMASK |
			ItemPublicacaoModelImpl.PUBLISHDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYID = new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAssetEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the item publicacaos where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the matching item publicacaos
	 */
	@Override
	public List<ItemPublicacao> findByAssetEntryId(long entryId) {
		return findByAssetEntryId(entryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryId(long entryId, int start,
		int end) {
		return findByAssetEntryId(entryId, start, end, null);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryId(long entryId, int start,
		int end, OrderByComparator<ItemPublicacao> orderByComparator) {
		return findByAssetEntryId(entryId, start, end, orderByComparator, true);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryId(long entryId, int start,
		int end, OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID;
			finderArgs = new Object[] { entryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYID;
			finderArgs = new Object[] { entryId, start, end, orderByComparator };
		}

		List<ItemPublicacao> list = null;

		if (retrieveFromCache) {
			list = (List<ItemPublicacao>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ItemPublicacao itemPublicacao : list) {
					if ((entryId != itemPublicacao.getEntryId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_ITEMPUBLICACAO_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ItemPublicacaoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (!pagination) {
					list = (List<ItemPublicacao>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ItemPublicacao>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first item publicacao in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching item publicacao
	 * @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao findByAssetEntryId_First(long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = fetchByAssetEntryId_First(entryId,
				orderByComparator);

		if (itemPublicacao != null) {
			return itemPublicacao;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchItemPublicacaoException(msg.toString());
	}

	/**
	 * Returns the first item publicacao in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao fetchByAssetEntryId_First(long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		List<ItemPublicacao> list = findByAssetEntryId(entryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last item publicacao in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching item publicacao
	 * @throws NoSuchItemPublicacaoException if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao findByAssetEntryId_Last(long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = fetchByAssetEntryId_Last(entryId,
				orderByComparator);

		if (itemPublicacao != null) {
			return itemPublicacao;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append("}");

		throw new NoSuchItemPublicacaoException(msg.toString());
	}

	/**
	 * Returns the last item publicacao in the ordered set where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao fetchByAssetEntryId_Last(long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		int count = countByAssetEntryId(entryId);

		if (count == 0) {
			return null;
		}

		List<ItemPublicacao> list = findByAssetEntryId(entryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ItemPublicacao[] findByAssetEntryId_PrevAndNext(
		long itemPublicacaoId, long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = findByPrimaryKey(itemPublicacaoId);

		Session session = null;

		try {
			session = openSession();

			ItemPublicacao[] array = new ItemPublicacaoImpl[3];

			array[0] = getByAssetEntryId_PrevAndNext(session, itemPublicacao,
					entryId, orderByComparator, true);

			array[1] = itemPublicacao;

			array[2] = getByAssetEntryId_PrevAndNext(session, itemPublicacao,
					entryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ItemPublicacao getByAssetEntryId_PrevAndNext(Session session,
		ItemPublicacao itemPublicacao, long entryId,
		OrderByComparator<ItemPublicacao> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ITEMPUBLICACAO_WHERE);

		query.append(_FINDER_COLUMN_ASSETENTRYID_ENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ItemPublicacaoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(itemPublicacao);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ItemPublicacao> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the item publicacaos where entryId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 */
	@Override
	public void removeByAssetEntryId(long entryId) {
		for (ItemPublicacao itemPublicacao : findByAssetEntryId(entryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(itemPublicacao);
		}
	}

	/**
	 * Returns the number of item publicacaos where entryId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @return the number of matching item publicacaos
	 */
	@Override
	public int countByAssetEntryId(long entryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYID;

		Object[] finderArgs = new Object[] { entryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ITEMPUBLICACAO_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYID_ENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ASSETENTRYID_ENTRYID_2 = "itemPublicacao.entryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYIDPOSTID =
		new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAssetEntryIdPostId",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPOSTID =
		new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemPublicacaoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByAssetEntryIdPostId",
			new String[] { Long.class.getName(), String.class.getName() },
			ItemPublicacaoModelImpl.ENTRYID_COLUMN_BITMASK |
			ItemPublicacaoModelImpl.POSTID_COLUMN_BITMASK |
			ItemPublicacaoModelImpl.PUBLISHDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ASSETENTRYIDPOSTID = new FinderPath(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByAssetEntryIdPostId",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the item publicacaos where entryId = &#63; and postId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param postId the post ID
	 * @return the matching item publicacaos
	 */
	@Override
	public List<ItemPublicacao> findByAssetEntryIdPostId(long entryId,
		String postId) {
		return findByAssetEntryIdPostId(entryId, postId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryIdPostId(long entryId,
		String postId, int start, int end) {
		return findByAssetEntryIdPostId(entryId, postId, start, end, null);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryIdPostId(long entryId,
		String postId, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return findByAssetEntryIdPostId(entryId, postId, start, end,
			orderByComparator, true);
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
	@Override
	public List<ItemPublicacao> findByAssetEntryIdPostId(long entryId,
		String postId, int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPOSTID;
			finderArgs = new Object[] { entryId, postId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ASSETENTRYIDPOSTID;
			finderArgs = new Object[] {
					entryId, postId,
					
					start, end, orderByComparator
				};
		}

		List<ItemPublicacao> list = null;

		if (retrieveFromCache) {
			list = (List<ItemPublicacao>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ItemPublicacao itemPublicacao : list) {
					if ((entryId != itemPublicacao.getEntryId()) ||
							!Objects.equals(postId, itemPublicacao.getPostId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_ITEMPUBLICACAO_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_ENTRYID_2);

			boolean bindPostId = false;

			if (postId == null) {
				query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_1);
			}
			else if (postId.equals("")) {
				query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_3);
			}
			else {
				bindPostId = true;

				query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ItemPublicacaoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindPostId) {
					qPos.add(postId);
				}

				if (!pagination) {
					list = (List<ItemPublicacao>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ItemPublicacao>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
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
	@Override
	public ItemPublicacao findByAssetEntryIdPostId_First(long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = fetchByAssetEntryIdPostId_First(entryId,
				postId, orderByComparator);

		if (itemPublicacao != null) {
			return itemPublicacao;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", postId=");
		msg.append(postId);

		msg.append("}");

		throw new NoSuchItemPublicacaoException(msg.toString());
	}

	/**
	 * Returns the first item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param postId the post ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao fetchByAssetEntryIdPostId_First(long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator) {
		List<ItemPublicacao> list = findByAssetEntryIdPostId(entryId, postId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ItemPublicacao findByAssetEntryIdPostId_Last(long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = fetchByAssetEntryIdPostId_Last(entryId,
				postId, orderByComparator);

		if (itemPublicacao != null) {
			return itemPublicacao;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("entryId=");
		msg.append(entryId);

		msg.append(", postId=");
		msg.append(postId);

		msg.append("}");

		throw new NoSuchItemPublicacaoException(msg.toString());
	}

	/**
	 * Returns the last item publicacao in the ordered set where entryId = &#63; and postId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param postId the post ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching item publicacao, or <code>null</code> if a matching item publicacao could not be found
	 */
	@Override
	public ItemPublicacao fetchByAssetEntryIdPostId_Last(long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator) {
		int count = countByAssetEntryIdPostId(entryId, postId);

		if (count == 0) {
			return null;
		}

		List<ItemPublicacao> list = findByAssetEntryIdPostId(entryId, postId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
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
	@Override
	public ItemPublicacao[] findByAssetEntryIdPostId_PrevAndNext(
		long itemPublicacaoId, long entryId, String postId,
		OrderByComparator<ItemPublicacao> orderByComparator)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = findByPrimaryKey(itemPublicacaoId);

		Session session = null;

		try {
			session = openSession();

			ItemPublicacao[] array = new ItemPublicacaoImpl[3];

			array[0] = getByAssetEntryIdPostId_PrevAndNext(session,
					itemPublicacao, entryId, postId, orderByComparator, true);

			array[1] = itemPublicacao;

			array[2] = getByAssetEntryIdPostId_PrevAndNext(session,
					itemPublicacao, entryId, postId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ItemPublicacao getByAssetEntryIdPostId_PrevAndNext(
		Session session, ItemPublicacao itemPublicacao, long entryId,
		String postId, OrderByComparator<ItemPublicacao> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_ITEMPUBLICACAO_WHERE);

		query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_ENTRYID_2);

		boolean bindPostId = false;

		if (postId == null) {
			query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_1);
		}
		else if (postId.equals("")) {
			query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_3);
		}
		else {
			bindPostId = true;

			query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(ItemPublicacaoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(entryId);

		if (bindPostId) {
			qPos.add(postId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(itemPublicacao);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ItemPublicacao> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the item publicacaos where entryId = &#63; and postId = &#63; from the database.
	 *
	 * @param entryId the entry ID
	 * @param postId the post ID
	 */
	@Override
	public void removeByAssetEntryIdPostId(long entryId, String postId) {
		for (ItemPublicacao itemPublicacao : findByAssetEntryIdPostId(entryId,
				postId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(itemPublicacao);
		}
	}

	/**
	 * Returns the number of item publicacaos where entryId = &#63; and postId = &#63;.
	 *
	 * @param entryId the entry ID
	 * @param postId the post ID
	 * @return the number of matching item publicacaos
	 */
	@Override
	public int countByAssetEntryIdPostId(long entryId, String postId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ASSETENTRYIDPOSTID;

		Object[] finderArgs = new Object[] { entryId, postId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_ITEMPUBLICACAO_WHERE);

			query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_ENTRYID_2);

			boolean bindPostId = false;

			if (postId == null) {
				query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_1);
			}
			else if (postId.equals("")) {
				query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_3);
			}
			else {
				bindPostId = true;

				query.append(_FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(entryId);

				if (bindPostId) {
					qPos.add(postId);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ASSETENTRYIDPOSTID_ENTRYID_2 = "itemPublicacao.entryId = ? AND ";
	private static final String _FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_1 = "itemPublicacao.postId IS NULL";
	private static final String _FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_2 = "itemPublicacao.postId = ?";
	private static final String _FINDER_COLUMN_ASSETENTRYIDPOSTID_POSTID_3 = "(itemPublicacao.postId IS NULL OR itemPublicacao.postId = '')";

	public ItemPublicacaoPersistenceImpl() {
		setModelClass(ItemPublicacao.class);
	}

	/**
	 * Caches the item publicacao in the entity cache if it is enabled.
	 *
	 * @param itemPublicacao the item publicacao
	 */
	@Override
	public void cacheResult(ItemPublicacao itemPublicacao) {
		entityCache.putResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoImpl.class, itemPublicacao.getPrimaryKey(),
			itemPublicacao);

		finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK,
			new Object[] {
				itemPublicacao.getEntryId(), itemPublicacao.getSocialNetwork()
			}, itemPublicacao);

		itemPublicacao.resetOriginalValues();
	}

	/**
	 * Caches the item publicacaos in the entity cache if it is enabled.
	 *
	 * @param itemPublicacaos the item publicacaos
	 */
	@Override
	public void cacheResult(List<ItemPublicacao> itemPublicacaos) {
		for (ItemPublicacao itemPublicacao : itemPublicacaos) {
			if (entityCache.getResult(
						ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
						ItemPublicacaoImpl.class, itemPublicacao.getPrimaryKey()) == null) {
				cacheResult(itemPublicacao);
			}
			else {
				itemPublicacao.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all item publicacaos.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ItemPublicacaoImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the item publicacao.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ItemPublicacao itemPublicacao) {
		entityCache.removeResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoImpl.class, itemPublicacao.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((ItemPublicacaoModelImpl)itemPublicacao, true);
	}

	@Override
	public void clearCache(List<ItemPublicacao> itemPublicacaos) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ItemPublicacao itemPublicacao : itemPublicacaos) {
			entityCache.removeResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
				ItemPublicacaoImpl.class, itemPublicacao.getPrimaryKey());

			clearUniqueFindersCache((ItemPublicacaoModelImpl)itemPublicacao,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		ItemPublicacaoModelImpl itemPublicacaoModelImpl) {
		Object[] args = new Object[] {
				itemPublicacaoModelImpl.getEntryId(),
				itemPublicacaoModelImpl.getSocialNetwork()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDSOCIALNETWORK,
			args, Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK,
			args, itemPublicacaoModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		ItemPublicacaoModelImpl itemPublicacaoModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					itemPublicacaoModelImpl.getEntryId(),
					itemPublicacaoModelImpl.getSocialNetwork()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDSOCIALNETWORK,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK,
				args);
		}

		if ((itemPublicacaoModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					itemPublicacaoModelImpl.getOriginalEntryId(),
					itemPublicacaoModelImpl.getOriginalSocialNetwork()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDSOCIALNETWORK,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_ASSETENTRYIDSOCIALNETWORK,
				args);
		}
	}

	/**
	 * Creates a new item publicacao with the primary key. Does not add the item publicacao to the database.
	 *
	 * @param itemPublicacaoId the primary key for the new item publicacao
	 * @return the new item publicacao
	 */
	@Override
	public ItemPublicacao create(long itemPublicacaoId) {
		ItemPublicacao itemPublicacao = new ItemPublicacaoImpl();

		itemPublicacao.setNew(true);
		itemPublicacao.setPrimaryKey(itemPublicacaoId);

		return itemPublicacao;
	}

	/**
	 * Removes the item publicacao with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param itemPublicacaoId the primary key of the item publicacao
	 * @return the item publicacao that was removed
	 * @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	 */
	@Override
	public ItemPublicacao remove(long itemPublicacaoId)
		throws NoSuchItemPublicacaoException {
		return remove((Serializable)itemPublicacaoId);
	}

	/**
	 * Removes the item publicacao with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the item publicacao
	 * @return the item publicacao that was removed
	 * @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	 */
	@Override
	public ItemPublicacao remove(Serializable primaryKey)
		throws NoSuchItemPublicacaoException {
		Session session = null;

		try {
			session = openSession();

			ItemPublicacao itemPublicacao = (ItemPublicacao)session.get(ItemPublicacaoImpl.class,
					primaryKey);

			if (itemPublicacao == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchItemPublicacaoException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(itemPublicacao);
		}
		catch (NoSuchItemPublicacaoException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected ItemPublicacao removeImpl(ItemPublicacao itemPublicacao) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(itemPublicacao)) {
				itemPublicacao = (ItemPublicacao)session.get(ItemPublicacaoImpl.class,
						itemPublicacao.getPrimaryKeyObj());
			}

			if (itemPublicacao != null) {
				session.delete(itemPublicacao);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (itemPublicacao != null) {
			clearCache(itemPublicacao);
		}

		return itemPublicacao;
	}

	@Override
	public ItemPublicacao updateImpl(ItemPublicacao itemPublicacao) {
		boolean isNew = itemPublicacao.isNew();

		if (!(itemPublicacao instanceof ItemPublicacaoModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(itemPublicacao.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(itemPublicacao);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in itemPublicacao proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ItemPublicacao implementation " +
				itemPublicacao.getClass());
		}

		ItemPublicacaoModelImpl itemPublicacaoModelImpl = (ItemPublicacaoModelImpl)itemPublicacao;

		Session session = null;

		try {
			session = openSession();

			if (itemPublicacao.isNew()) {
				session.save(itemPublicacao);

				itemPublicacao.setNew(false);
			}
			else {
				itemPublicacao = (ItemPublicacao)session.merge(itemPublicacao);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ItemPublicacaoModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					itemPublicacaoModelImpl.getEntryId(),
					itemPublicacaoModelImpl.getPublishDate()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDPUBLISHDATE,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPUBLISHDATE,
				args);

			args = new Object[] { itemPublicacaoModelImpl.getEntryId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
				args);

			args = new Object[] {
					itemPublicacaoModelImpl.getEntryId(),
					itemPublicacaoModelImpl.getPostId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDPOSTID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPOSTID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((itemPublicacaoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPUBLISHDATE.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						itemPublicacaoModelImpl.getOriginalEntryId(),
						itemPublicacaoModelImpl.getOriginalPublishDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDPUBLISHDATE,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPUBLISHDATE,
					args);

				args = new Object[] {
						itemPublicacaoModelImpl.getEntryId(),
						itemPublicacaoModelImpl.getPublishDate()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDPUBLISHDATE,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPUBLISHDATE,
					args);
			}

			if ((itemPublicacaoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						itemPublicacaoModelImpl.getOriginalEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);

				args = new Object[] { itemPublicacaoModelImpl.getEntryId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYID,
					args);
			}

			if ((itemPublicacaoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPOSTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						itemPublicacaoModelImpl.getOriginalEntryId(),
						itemPublicacaoModelImpl.getOriginalPostId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDPOSTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPOSTID,
					args);

				args = new Object[] {
						itemPublicacaoModelImpl.getEntryId(),
						itemPublicacaoModelImpl.getPostId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ASSETENTRYIDPOSTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ASSETENTRYIDPOSTID,
					args);
			}
		}

		entityCache.putResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemPublicacaoImpl.class, itemPublicacao.getPrimaryKey(),
			itemPublicacao, false);

		clearUniqueFindersCache(itemPublicacaoModelImpl, false);
		cacheUniqueFindersCache(itemPublicacaoModelImpl);

		itemPublicacao.resetOriginalValues();

		return itemPublicacao;
	}

	/**
	 * Returns the item publicacao with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the item publicacao
	 * @return the item publicacao
	 * @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	 */
	@Override
	public ItemPublicacao findByPrimaryKey(Serializable primaryKey)
		throws NoSuchItemPublicacaoException {
		ItemPublicacao itemPublicacao = fetchByPrimaryKey(primaryKey);

		if (itemPublicacao == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchItemPublicacaoException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return itemPublicacao;
	}

	/**
	 * Returns the item publicacao with the primary key or throws a {@link NoSuchItemPublicacaoException} if it could not be found.
	 *
	 * @param itemPublicacaoId the primary key of the item publicacao
	 * @return the item publicacao
	 * @throws NoSuchItemPublicacaoException if a item publicacao with the primary key could not be found
	 */
	@Override
	public ItemPublicacao findByPrimaryKey(long itemPublicacaoId)
		throws NoSuchItemPublicacaoException {
		return findByPrimaryKey((Serializable)itemPublicacaoId);
	}

	/**
	 * Returns the item publicacao with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the item publicacao
	 * @return the item publicacao, or <code>null</code> if a item publicacao with the primary key could not be found
	 */
	@Override
	public ItemPublicacao fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
				ItemPublicacaoImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ItemPublicacao itemPublicacao = (ItemPublicacao)serializable;

		if (itemPublicacao == null) {
			Session session = null;

			try {
				session = openSession();

				itemPublicacao = (ItemPublicacao)session.get(ItemPublicacaoImpl.class,
						primaryKey);

				if (itemPublicacao != null) {
					cacheResult(itemPublicacao);
				}
				else {
					entityCache.putResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
						ItemPublicacaoImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
					ItemPublicacaoImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return itemPublicacao;
	}

	/**
	 * Returns the item publicacao with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param itemPublicacaoId the primary key of the item publicacao
	 * @return the item publicacao, or <code>null</code> if a item publicacao with the primary key could not be found
	 */
	@Override
	public ItemPublicacao fetchByPrimaryKey(long itemPublicacaoId) {
		return fetchByPrimaryKey((Serializable)itemPublicacaoId);
	}

	@Override
	public Map<Serializable, ItemPublicacao> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ItemPublicacao> map = new HashMap<Serializable, ItemPublicacao>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ItemPublicacao itemPublicacao = fetchByPrimaryKey(primaryKey);

			if (itemPublicacao != null) {
				map.put(primaryKey, itemPublicacao);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
					ItemPublicacaoImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ItemPublicacao)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ITEMPUBLICACAO_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (ItemPublicacao itemPublicacao : (List<ItemPublicacao>)q.list()) {
				map.put(itemPublicacao.getPrimaryKeyObj(), itemPublicacao);

				cacheResult(itemPublicacao);

				uncachedPrimaryKeys.remove(itemPublicacao.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ItemPublicacaoModelImpl.ENTITY_CACHE_ENABLED,
					ItemPublicacaoImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the item publicacaos.
	 *
	 * @return the item publicacaos
	 */
	@Override
	public List<ItemPublicacao> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<ItemPublicacao> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<ItemPublicacao> findAll(int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<ItemPublicacao> findAll(int start, int end,
		OrderByComparator<ItemPublicacao> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<ItemPublicacao> list = null;

		if (retrieveFromCache) {
			list = (List<ItemPublicacao>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ITEMPUBLICACAO);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ITEMPUBLICACAO;

				if (pagination) {
					sql = sql.concat(ItemPublicacaoModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ItemPublicacao>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ItemPublicacao>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the item publicacaos from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ItemPublicacao itemPublicacao : findAll()) {
			remove(itemPublicacao);
		}
	}

	/**
	 * Returns the number of item publicacaos.
	 *
	 * @return the number of item publicacaos
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ITEMPUBLICACAO);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ItemPublicacaoModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the item publicacao persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ItemPublicacaoImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_ITEMPUBLICACAO = "SELECT itemPublicacao FROM ItemPublicacao itemPublicacao";
	private static final String _SQL_SELECT_ITEMPUBLICACAO_WHERE_PKS_IN = "SELECT itemPublicacao FROM ItemPublicacao itemPublicacao WHERE itemPublicacaoId IN (";
	private static final String _SQL_SELECT_ITEMPUBLICACAO_WHERE = "SELECT itemPublicacao FROM ItemPublicacao itemPublicacao WHERE ";
	private static final String _SQL_COUNT_ITEMPUBLICACAO = "SELECT COUNT(itemPublicacao) FROM ItemPublicacao itemPublicacao";
	private static final String _SQL_COUNT_ITEMPUBLICACAO_WHERE = "SELECT COUNT(itemPublicacao) FROM ItemPublicacao itemPublicacao WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "itemPublicacao.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ItemPublicacao exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ItemPublicacao exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ItemPublicacaoPersistenceImpl.class);
}
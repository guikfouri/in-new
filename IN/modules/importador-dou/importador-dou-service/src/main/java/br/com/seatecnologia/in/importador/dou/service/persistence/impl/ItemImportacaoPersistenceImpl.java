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

package br.com.seatecnologia.in.importador.dou.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException;
import br.com.seatecnologia.in.importador.dou.model.ItemImportacao;
import br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoImpl;
import br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoModelImpl;
import br.com.seatecnologia.in.importador.dou.service.persistence.ItemImportacaoPersistence;

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

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the item importacao service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemImportacaoPersistence
 * @see br.com.seatecnologia.in.importador.dou.service.persistence.ItemImportacaoUtil
 * @generated
 */
@ProviderType
public class ItemImportacaoPersistenceImpl extends BasePersistenceImpl<ItemImportacao>
	implements ItemImportacaoPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link ItemImportacaoUtil} to access the item importacao persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = ItemImportacaoImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemImportacaoImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemImportacaoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_IMPORTADO =
		new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemImportacaoImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByImportado",
			new String[] {
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IMPORTADO =
		new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemImportacaoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByImportado",
			new String[] { Boolean.class.getName() },
			ItemImportacaoModelImpl.IMPORTADO_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_IMPORTADO = new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByImportado",
			new String[] { Boolean.class.getName() });

	/**
	 * Returns all the item importacaos where importado = &#63;.
	 *
	 * @param importado the importado
	 * @return the matching item importacaos
	 */
	@Override
	public List<ItemImportacao> findByImportado(boolean importado) {
		return findByImportado(importado, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the item importacaos where importado = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param importado the importado
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @return the range of matching item importacaos
	 */
	@Override
	public List<ItemImportacao> findByImportado(boolean importado, int start,
		int end) {
		return findByImportado(importado, start, end, null);
	}

	/**
	 * Returns an ordered range of all the item importacaos where importado = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param importado the importado
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching item importacaos
	 */
	@Override
	public List<ItemImportacao> findByImportado(boolean importado, int start,
		int end, OrderByComparator<ItemImportacao> orderByComparator) {
		return findByImportado(importado, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the item importacaos where importado = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param importado the importado
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching item importacaos
	 */
	@Override
	public List<ItemImportacao> findByImportado(boolean importado, int start,
		int end, OrderByComparator<ItemImportacao> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IMPORTADO;
			finderArgs = new Object[] { importado };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_IMPORTADO;
			finderArgs = new Object[] { importado, start, end, orderByComparator };
		}

		List<ItemImportacao> list = null;

		if (retrieveFromCache) {
			list = (List<ItemImportacao>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ItemImportacao itemImportacao : list) {
					if ((importado != itemImportacao.isImportado())) {
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

			query.append(_SQL_SELECT_ITEMIMPORTACAO_WHERE);

			query.append(_FINDER_COLUMN_IMPORTADO_IMPORTADO_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ItemImportacaoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(importado);

				if (!pagination) {
					list = (List<ItemImportacao>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ItemImportacao>)QueryUtil.list(q,
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
	 * Returns the first item importacao in the ordered set where importado = &#63;.
	 *
	 * @param importado the importado
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching item importacao
	 * @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	 */
	@Override
	public ItemImportacao findByImportado_First(boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException {
		ItemImportacao itemImportacao = fetchByImportado_First(importado,
				orderByComparator);

		if (itemImportacao != null) {
			return itemImportacao;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("importado=");
		msg.append(importado);

		msg.append("}");

		throw new NoSuchItemImportacaoException(msg.toString());
	}

	/**
	 * Returns the first item importacao in the ordered set where importado = &#63;.
	 *
	 * @param importado the importado
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching item importacao, or <code>null</code> if a matching item importacao could not be found
	 */
	@Override
	public ItemImportacao fetchByImportado_First(boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator) {
		List<ItemImportacao> list = findByImportado(importado, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last item importacao in the ordered set where importado = &#63;.
	 *
	 * @param importado the importado
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching item importacao
	 * @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	 */
	@Override
	public ItemImportacao findByImportado_Last(boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException {
		ItemImportacao itemImportacao = fetchByImportado_Last(importado,
				orderByComparator);

		if (itemImportacao != null) {
			return itemImportacao;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("importado=");
		msg.append(importado);

		msg.append("}");

		throw new NoSuchItemImportacaoException(msg.toString());
	}

	/**
	 * Returns the last item importacao in the ordered set where importado = &#63;.
	 *
	 * @param importado the importado
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching item importacao, or <code>null</code> if a matching item importacao could not be found
	 */
	@Override
	public ItemImportacao fetchByImportado_Last(boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator) {
		int count = countByImportado(importado);

		if (count == 0) {
			return null;
		}

		List<ItemImportacao> list = findByImportado(importado, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the item importacaos before and after the current item importacao in the ordered set where importado = &#63;.
	 *
	 * @param itemImportacaoId the primary key of the current item importacao
	 * @param importado the importado
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next item importacao
	 * @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	 */
	@Override
	public ItemImportacao[] findByImportado_PrevAndNext(long itemImportacaoId,
		boolean importado, OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException {
		ItemImportacao itemImportacao = findByPrimaryKey(itemImportacaoId);

		Session session = null;

		try {
			session = openSession();

			ItemImportacao[] array = new ItemImportacaoImpl[3];

			array[0] = getByImportado_PrevAndNext(session, itemImportacao,
					importado, orderByComparator, true);

			array[1] = itemImportacao;

			array[2] = getByImportado_PrevAndNext(session, itemImportacao,
					importado, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ItemImportacao getByImportado_PrevAndNext(Session session,
		ItemImportacao itemImportacao, boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ITEMIMPORTACAO_WHERE);

		query.append(_FINDER_COLUMN_IMPORTADO_IMPORTADO_2);

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
			query.append(ItemImportacaoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(importado);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(itemImportacao);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ItemImportacao> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the item importacaos where importado = &#63; from the database.
	 *
	 * @param importado the importado
	 */
	@Override
	public void removeByImportado(boolean importado) {
		for (ItemImportacao itemImportacao : findByImportado(importado,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(itemImportacao);
		}
	}

	/**
	 * Returns the number of item importacaos where importado = &#63;.
	 *
	 * @param importado the importado
	 * @return the number of matching item importacaos
	 */
	@Override
	public int countByImportado(boolean importado) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_IMPORTADO;

		Object[] finderArgs = new Object[] { importado };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ITEMIMPORTACAO_WHERE);

			query.append(_FINDER_COLUMN_IMPORTADO_IMPORTADO_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(importado);

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

	private static final String _FINDER_COLUMN_IMPORTADO_IMPORTADO_2 = "itemImportacao.importado = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_IDENTIFICADORATUALIZACAO =
		new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemImportacaoImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByIdentificadorAtualizacao",
			new String[] {
				String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IDENTIFICADORATUALIZACAO =
		new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED,
			ItemImportacaoImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByIdentificadorAtualizacao",
			new String[] { String.class.getName() },
			ItemImportacaoModelImpl.IDENTIFICADORATUALIZACAO_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_IDENTIFICADORATUALIZACAO =
		new FinderPath(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByIdentificadorAtualizacao",
			new String[] { String.class.getName() });

	/**
	 * Returns all the item importacaos where identificadorAtualizacao = &#63;.
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @return the matching item importacaos
	 */
	@Override
	public List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao) {
		return findByIdentificadorAtualizacao(identificadorAtualizacao,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the item importacaos where identificadorAtualizacao = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @return the range of matching item importacaos
	 */
	@Override
	public List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end) {
		return findByIdentificadorAtualizacao(identificadorAtualizacao, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the item importacaos where identificadorAtualizacao = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching item importacaos
	 */
	@Override
	public List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return findByIdentificadorAtualizacao(identificadorAtualizacao, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the item importacaos where identificadorAtualizacao = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching item importacaos
	 */
	@Override
	public List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IDENTIFICADORATUALIZACAO;
			finderArgs = new Object[] { identificadorAtualizacao };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_IDENTIFICADORATUALIZACAO;
			finderArgs = new Object[] {
					identificadorAtualizacao,
					
					start, end, orderByComparator
				};
		}

		List<ItemImportacao> list = null;

		if (retrieveFromCache) {
			list = (List<ItemImportacao>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ItemImportacao itemImportacao : list) {
					if (!Objects.equals(identificadorAtualizacao,
								itemImportacao.getIdentificadorAtualizacao())) {
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

			query.append(_SQL_SELECT_ITEMIMPORTACAO_WHERE);

			boolean bindIdentificadorAtualizacao = false;

			if (identificadorAtualizacao == null) {
				query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_1);
			}
			else if (identificadorAtualizacao.equals("")) {
				query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_3);
			}
			else {
				bindIdentificadorAtualizacao = true;

				query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(ItemImportacaoModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindIdentificadorAtualizacao) {
					qPos.add(identificadorAtualizacao);
				}

				if (!pagination) {
					list = (List<ItemImportacao>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ItemImportacao>)QueryUtil.list(q,
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
	 * Returns the first item importacao in the ordered set where identificadorAtualizacao = &#63;.
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching item importacao
	 * @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	 */
	@Override
	public ItemImportacao findByIdentificadorAtualizacao_First(
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException {
		ItemImportacao itemImportacao = fetchByIdentificadorAtualizacao_First(identificadorAtualizacao,
				orderByComparator);

		if (itemImportacao != null) {
			return itemImportacao;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("identificadorAtualizacao=");
		msg.append(identificadorAtualizacao);

		msg.append("}");

		throw new NoSuchItemImportacaoException(msg.toString());
	}

	/**
	 * Returns the first item importacao in the ordered set where identificadorAtualizacao = &#63;.
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching item importacao, or <code>null</code> if a matching item importacao could not be found
	 */
	@Override
	public ItemImportacao fetchByIdentificadorAtualizacao_First(
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator) {
		List<ItemImportacao> list = findByIdentificadorAtualizacao(identificadorAtualizacao,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last item importacao in the ordered set where identificadorAtualizacao = &#63;.
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching item importacao
	 * @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	 */
	@Override
	public ItemImportacao findByIdentificadorAtualizacao_Last(
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException {
		ItemImportacao itemImportacao = fetchByIdentificadorAtualizacao_Last(identificadorAtualizacao,
				orderByComparator);

		if (itemImportacao != null) {
			return itemImportacao;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("identificadorAtualizacao=");
		msg.append(identificadorAtualizacao);

		msg.append("}");

		throw new NoSuchItemImportacaoException(msg.toString());
	}

	/**
	 * Returns the last item importacao in the ordered set where identificadorAtualizacao = &#63;.
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching item importacao, or <code>null</code> if a matching item importacao could not be found
	 */
	@Override
	public ItemImportacao fetchByIdentificadorAtualizacao_Last(
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator) {
		int count = countByIdentificadorAtualizacao(identificadorAtualizacao);

		if (count == 0) {
			return null;
		}

		List<ItemImportacao> list = findByIdentificadorAtualizacao(identificadorAtualizacao,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the item importacaos before and after the current item importacao in the ordered set where identificadorAtualizacao = &#63;.
	 *
	 * @param itemImportacaoId the primary key of the current item importacao
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next item importacao
	 * @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	 */
	@Override
	public ItemImportacao[] findByIdentificadorAtualizacao_PrevAndNext(
		long itemImportacaoId, String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException {
		ItemImportacao itemImportacao = findByPrimaryKey(itemImportacaoId);

		Session session = null;

		try {
			session = openSession();

			ItemImportacao[] array = new ItemImportacaoImpl[3];

			array[0] = getByIdentificadorAtualizacao_PrevAndNext(session,
					itemImportacao, identificadorAtualizacao,
					orderByComparator, true);

			array[1] = itemImportacao;

			array[2] = getByIdentificadorAtualizacao_PrevAndNext(session,
					itemImportacao, identificadorAtualizacao,
					orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected ItemImportacao getByIdentificadorAtualizacao_PrevAndNext(
		Session session, ItemImportacao itemImportacao,
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_ITEMIMPORTACAO_WHERE);

		boolean bindIdentificadorAtualizacao = false;

		if (identificadorAtualizacao == null) {
			query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_1);
		}
		else if (identificadorAtualizacao.equals("")) {
			query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_3);
		}
		else {
			bindIdentificadorAtualizacao = true;

			query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_2);
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
			query.append(ItemImportacaoModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindIdentificadorAtualizacao) {
			qPos.add(identificadorAtualizacao);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(itemImportacao);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<ItemImportacao> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the item importacaos where identificadorAtualizacao = &#63; from the database.
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 */
	@Override
	public void removeByIdentificadorAtualizacao(
		String identificadorAtualizacao) {
		for (ItemImportacao itemImportacao : findByIdentificadorAtualizacao(
				identificadorAtualizacao, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(itemImportacao);
		}
	}

	/**
	 * Returns the number of item importacaos where identificadorAtualizacao = &#63;.
	 *
	 * @param identificadorAtualizacao the identificador atualizacao
	 * @return the number of matching item importacaos
	 */
	@Override
	public int countByIdentificadorAtualizacao(String identificadorAtualizacao) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_IDENTIFICADORATUALIZACAO;

		Object[] finderArgs = new Object[] { identificadorAtualizacao };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_ITEMIMPORTACAO_WHERE);

			boolean bindIdentificadorAtualizacao = false;

			if (identificadorAtualizacao == null) {
				query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_1);
			}
			else if (identificadorAtualizacao.equals("")) {
				query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_3);
			}
			else {
				bindIdentificadorAtualizacao = true;

				query.append(_FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindIdentificadorAtualizacao) {
					qPos.add(identificadorAtualizacao);
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

	private static final String _FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_1 =
		"itemImportacao.identificadorAtualizacao IS NULL";
	private static final String _FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_2 =
		"itemImportacao.identificadorAtualizacao = ?";
	private static final String _FINDER_COLUMN_IDENTIFICADORATUALIZACAO_IDENTIFICADORATUALIZACAO_3 =
		"(itemImportacao.identificadorAtualizacao IS NULL OR itemImportacao.identificadorAtualizacao = '')";

	public ItemImportacaoPersistenceImpl() {
		setModelClass(ItemImportacao.class);
	}

	/**
	 * Caches the item importacao in the entity cache if it is enabled.
	 *
	 * @param itemImportacao the item importacao
	 */
	@Override
	public void cacheResult(ItemImportacao itemImportacao) {
		entityCache.putResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoImpl.class, itemImportacao.getPrimaryKey(),
			itemImportacao);

		itemImportacao.resetOriginalValues();
	}

	/**
	 * Caches the item importacaos in the entity cache if it is enabled.
	 *
	 * @param itemImportacaos the item importacaos
	 */
	@Override
	public void cacheResult(List<ItemImportacao> itemImportacaos) {
		for (ItemImportacao itemImportacao : itemImportacaos) {
			if (entityCache.getResult(
						ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
						ItemImportacaoImpl.class, itemImportacao.getPrimaryKey()) == null) {
				cacheResult(itemImportacao);
			}
			else {
				itemImportacao.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all item importacaos.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ItemImportacaoImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the item importacao.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ItemImportacao itemImportacao) {
		entityCache.removeResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoImpl.class, itemImportacao.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<ItemImportacao> itemImportacaos) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ItemImportacao itemImportacao : itemImportacaos) {
			entityCache.removeResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
				ItemImportacaoImpl.class, itemImportacao.getPrimaryKey());
		}
	}

	/**
	 * Creates a new item importacao with the primary key. Does not add the item importacao to the database.
	 *
	 * @param itemImportacaoId the primary key for the new item importacao
	 * @return the new item importacao
	 */
	@Override
	public ItemImportacao create(long itemImportacaoId) {
		ItemImportacao itemImportacao = new ItemImportacaoImpl();

		itemImportacao.setNew(true);
		itemImportacao.setPrimaryKey(itemImportacaoId);

		return itemImportacao;
	}

	/**
	 * Removes the item importacao with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param itemImportacaoId the primary key of the item importacao
	 * @return the item importacao that was removed
	 * @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	 */
	@Override
	public ItemImportacao remove(long itemImportacaoId)
		throws NoSuchItemImportacaoException {
		return remove((Serializable)itemImportacaoId);
	}

	/**
	 * Removes the item importacao with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the item importacao
	 * @return the item importacao that was removed
	 * @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	 */
	@Override
	public ItemImportacao remove(Serializable primaryKey)
		throws NoSuchItemImportacaoException {
		Session session = null;

		try {
			session = openSession();

			ItemImportacao itemImportacao = (ItemImportacao)session.get(ItemImportacaoImpl.class,
					primaryKey);

			if (itemImportacao == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchItemImportacaoException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(itemImportacao);
		}
		catch (NoSuchItemImportacaoException nsee) {
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
	protected ItemImportacao removeImpl(ItemImportacao itemImportacao) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(itemImportacao)) {
				itemImportacao = (ItemImportacao)session.get(ItemImportacaoImpl.class,
						itemImportacao.getPrimaryKeyObj());
			}

			if (itemImportacao != null) {
				session.delete(itemImportacao);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (itemImportacao != null) {
			clearCache(itemImportacao);
		}

		return itemImportacao;
	}

	@Override
	public ItemImportacao updateImpl(ItemImportacao itemImportacao) {
		boolean isNew = itemImportacao.isNew();

		if (!(itemImportacao instanceof ItemImportacaoModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(itemImportacao.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(itemImportacao);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in itemImportacao proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ItemImportacao implementation " +
				itemImportacao.getClass());
		}

		ItemImportacaoModelImpl itemImportacaoModelImpl = (ItemImportacaoModelImpl)itemImportacao;

		Session session = null;

		try {
			session = openSession();

			if (itemImportacao.isNew()) {
				session.save(itemImportacao);

				itemImportacao.setNew(false);
			}
			else {
				itemImportacao = (ItemImportacao)session.merge(itemImportacao);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!ItemImportacaoModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { itemImportacaoModelImpl.isImportado() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_IMPORTADO, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IMPORTADO,
				args);

			args = new Object[] {
					itemImportacaoModelImpl.getIdentificadorAtualizacao()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_IDENTIFICADORATUALIZACAO,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IDENTIFICADORATUALIZACAO,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((itemImportacaoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IMPORTADO.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						itemImportacaoModelImpl.getOriginalImportado()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_IMPORTADO, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IMPORTADO,
					args);

				args = new Object[] { itemImportacaoModelImpl.isImportado() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_IMPORTADO, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IMPORTADO,
					args);
			}

			if ((itemImportacaoModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IDENTIFICADORATUALIZACAO.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						itemImportacaoModelImpl.getOriginalIdentificadorAtualizacao()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_IDENTIFICADORATUALIZACAO,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IDENTIFICADORATUALIZACAO,
					args);

				args = new Object[] {
						itemImportacaoModelImpl.getIdentificadorAtualizacao()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_IDENTIFICADORATUALIZACAO,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_IDENTIFICADORATUALIZACAO,
					args);
			}
		}

		entityCache.putResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
			ItemImportacaoImpl.class, itemImportacao.getPrimaryKey(),
			itemImportacao, false);

		itemImportacao.resetOriginalValues();

		return itemImportacao;
	}

	/**
	 * Returns the item importacao with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the item importacao
	 * @return the item importacao
	 * @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	 */
	@Override
	public ItemImportacao findByPrimaryKey(Serializable primaryKey)
		throws NoSuchItemImportacaoException {
		ItemImportacao itemImportacao = fetchByPrimaryKey(primaryKey);

		if (itemImportacao == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchItemImportacaoException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return itemImportacao;
	}

	/**
	 * Returns the item importacao with the primary key or throws a {@link NoSuchItemImportacaoException} if it could not be found.
	 *
	 * @param itemImportacaoId the primary key of the item importacao
	 * @return the item importacao
	 * @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	 */
	@Override
	public ItemImportacao findByPrimaryKey(long itemImportacaoId)
		throws NoSuchItemImportacaoException {
		return findByPrimaryKey((Serializable)itemImportacaoId);
	}

	/**
	 * Returns the item importacao with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the item importacao
	 * @return the item importacao, or <code>null</code> if a item importacao with the primary key could not be found
	 */
	@Override
	public ItemImportacao fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
				ItemImportacaoImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		ItemImportacao itemImportacao = (ItemImportacao)serializable;

		if (itemImportacao == null) {
			Session session = null;

			try {
				session = openSession();

				itemImportacao = (ItemImportacao)session.get(ItemImportacaoImpl.class,
						primaryKey);

				if (itemImportacao != null) {
					cacheResult(itemImportacao);
				}
				else {
					entityCache.putResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
						ItemImportacaoImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
					ItemImportacaoImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return itemImportacao;
	}

	/**
	 * Returns the item importacao with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param itemImportacaoId the primary key of the item importacao
	 * @return the item importacao, or <code>null</code> if a item importacao with the primary key could not be found
	 */
	@Override
	public ItemImportacao fetchByPrimaryKey(long itemImportacaoId) {
		return fetchByPrimaryKey((Serializable)itemImportacaoId);
	}

	@Override
	public Map<Serializable, ItemImportacao> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, ItemImportacao> map = new HashMap<Serializable, ItemImportacao>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			ItemImportacao itemImportacao = fetchByPrimaryKey(primaryKey);

			if (itemImportacao != null) {
				map.put(primaryKey, itemImportacao);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
					ItemImportacaoImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (ItemImportacao)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_ITEMIMPORTACAO_WHERE_PKS_IN);

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

			for (ItemImportacao itemImportacao : (List<ItemImportacao>)q.list()) {
				map.put(itemImportacao.getPrimaryKeyObj(), itemImportacao);

				cacheResult(itemImportacao);

				uncachedPrimaryKeys.remove(itemImportacao.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(ItemImportacaoModelImpl.ENTITY_CACHE_ENABLED,
					ItemImportacaoImpl.class, primaryKey, nullModel);
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
	 * Returns all the item importacaos.
	 *
	 * @return the item importacaos
	 */
	@Override
	public List<ItemImportacao> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the item importacaos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @return the range of item importacaos
	 */
	@Override
	public List<ItemImportacao> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the item importacaos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of item importacaos
	 */
	@Override
	public List<ItemImportacao> findAll(int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the item importacaos.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link ItemImportacaoModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of item importacaos
	 * @param end the upper bound of the range of item importacaos (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of item importacaos
	 */
	@Override
	public List<ItemImportacao> findAll(int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator,
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

		List<ItemImportacao> list = null;

		if (retrieveFromCache) {
			list = (List<ItemImportacao>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_ITEMIMPORTACAO);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_ITEMIMPORTACAO;

				if (pagination) {
					sql = sql.concat(ItemImportacaoModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ItemImportacao>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ItemImportacao>)QueryUtil.list(q,
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
	 * Removes all the item importacaos from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ItemImportacao itemImportacao : findAll()) {
			remove(itemImportacao);
		}
	}

	/**
	 * Returns the number of item importacaos.
	 *
	 * @return the number of item importacaos
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_ITEMIMPORTACAO);

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
		return ItemImportacaoModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the item importacao persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(ItemImportacaoImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_ITEMIMPORTACAO = "SELECT itemImportacao FROM ItemImportacao itemImportacao";
	private static final String _SQL_SELECT_ITEMIMPORTACAO_WHERE_PKS_IN = "SELECT itemImportacao FROM ItemImportacao itemImportacao WHERE itemImportacaoId IN (";
	private static final String _SQL_SELECT_ITEMIMPORTACAO_WHERE = "SELECT itemImportacao FROM ItemImportacao itemImportacao WHERE ";
	private static final String _SQL_COUNT_ITEMIMPORTACAO = "SELECT COUNT(itemImportacao) FROM ItemImportacao itemImportacao";
	private static final String _SQL_COUNT_ITEMIMPORTACAO_WHERE = "SELECT COUNT(itemImportacao) FROM ItemImportacao itemImportacao WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "itemImportacao.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No ItemImportacao exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No ItemImportacao exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(ItemImportacaoPersistenceImpl.class);
}
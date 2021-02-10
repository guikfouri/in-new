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

package br.com.seatecnologia.in.importador.dou.service.persistence;

import aQute.bnd.annotation.ProviderType;

import br.com.seatecnologia.in.importador.dou.model.ItemImportacao;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the item importacao service. This utility wraps {@link br.com.seatecnologia.in.importador.dou.service.persistence.impl.ItemImportacaoPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemImportacaoPersistence
 * @see br.com.seatecnologia.in.importador.dou.service.persistence.impl.ItemImportacaoPersistenceImpl
 * @generated
 */
@ProviderType
public class ItemImportacaoUtil {
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
	public static void clearCache(ItemImportacao itemImportacao) {
		getPersistence().clearCache(itemImportacao);
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
	public static List<ItemImportacao> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<ItemImportacao> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<ItemImportacao> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static ItemImportacao update(ItemImportacao itemImportacao) {
		return getPersistence().update(itemImportacao);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static ItemImportacao update(ItemImportacao itemImportacao,
		ServiceContext serviceContext) {
		return getPersistence().update(itemImportacao, serviceContext);
	}

	/**
	* Returns all the item importacaos where importado = &#63;.
	*
	* @param importado the importado
	* @return the matching item importacaos
	*/
	public static List<ItemImportacao> findByImportado(boolean importado) {
		return getPersistence().findByImportado(importado);
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
	public static List<ItemImportacao> findByImportado(boolean importado,
		int start, int end) {
		return getPersistence().findByImportado(importado, start, end);
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
	public static List<ItemImportacao> findByImportado(boolean importado,
		int start, int end, OrderByComparator<ItemImportacao> orderByComparator) {
		return getPersistence()
				   .findByImportado(importado, start, end, orderByComparator);
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
	public static List<ItemImportacao> findByImportado(boolean importado,
		int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByImportado(importado, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first item importacao in the ordered set where importado = &#63;.
	*
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item importacao
	* @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	*/
	public static ItemImportacao findByImportado_First(boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException {
		return getPersistence()
				   .findByImportado_First(importado, orderByComparator);
	}

	/**
	* Returns the first item importacao in the ordered set where importado = &#63;.
	*
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item importacao, or <code>null</code> if a matching item importacao could not be found
	*/
	public static ItemImportacao fetchByImportado_First(boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return getPersistence()
				   .fetchByImportado_First(importado, orderByComparator);
	}

	/**
	* Returns the last item importacao in the ordered set where importado = &#63;.
	*
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item importacao
	* @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	*/
	public static ItemImportacao findByImportado_Last(boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException {
		return getPersistence()
				   .findByImportado_Last(importado, orderByComparator);
	}

	/**
	* Returns the last item importacao in the ordered set where importado = &#63;.
	*
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item importacao, or <code>null</code> if a matching item importacao could not be found
	*/
	public static ItemImportacao fetchByImportado_Last(boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return getPersistence()
				   .fetchByImportado_Last(importado, orderByComparator);
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
	public static ItemImportacao[] findByImportado_PrevAndNext(
		long itemImportacaoId, boolean importado,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException {
		return getPersistence()
				   .findByImportado_PrevAndNext(itemImportacaoId, importado,
			orderByComparator);
	}

	/**
	* Removes all the item importacaos where importado = &#63; from the database.
	*
	* @param importado the importado
	*/
	public static void removeByImportado(boolean importado) {
		getPersistence().removeByImportado(importado);
	}

	/**
	* Returns the number of item importacaos where importado = &#63;.
	*
	* @param importado the importado
	* @return the number of matching item importacaos
	*/
	public static int countByImportado(boolean importado) {
		return getPersistence().countByImportado(importado);
	}

	/**
	* Returns all the item importacaos where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @return the matching item importacaos
	*/
	public static List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao) {
		return getPersistence()
				   .findByIdentificadorAtualizacao(identificadorAtualizacao);
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
	public static List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end) {
		return getPersistence()
				   .findByIdentificadorAtualizacao(identificadorAtualizacao,
			start, end);
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
	public static List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return getPersistence()
				   .findByIdentificadorAtualizacao(identificadorAtualizacao,
			start, end, orderByComparator);
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
	public static List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByIdentificadorAtualizacao(identificadorAtualizacao,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item importacao
	* @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	*/
	public static ItemImportacao findByIdentificadorAtualizacao_First(
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException {
		return getPersistence()
				   .findByIdentificadorAtualizacao_First(identificadorAtualizacao,
			orderByComparator);
	}

	/**
	* Returns the first item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item importacao, or <code>null</code> if a matching item importacao could not be found
	*/
	public static ItemImportacao fetchByIdentificadorAtualizacao_First(
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return getPersistence()
				   .fetchByIdentificadorAtualizacao_First(identificadorAtualizacao,
			orderByComparator);
	}

	/**
	* Returns the last item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item importacao
	* @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	*/
	public static ItemImportacao findByIdentificadorAtualizacao_Last(
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException {
		return getPersistence()
				   .findByIdentificadorAtualizacao_Last(identificadorAtualizacao,
			orderByComparator);
	}

	/**
	* Returns the last item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item importacao, or <code>null</code> if a matching item importacao could not be found
	*/
	public static ItemImportacao fetchByIdentificadorAtualizacao_Last(
		String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return getPersistence()
				   .fetchByIdentificadorAtualizacao_Last(identificadorAtualizacao,
			orderByComparator);
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
	public static ItemImportacao[] findByIdentificadorAtualizacao_PrevAndNext(
		long itemImportacaoId, String identificadorAtualizacao,
		OrderByComparator<ItemImportacao> orderByComparator)
		throws br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException {
		return getPersistence()
				   .findByIdentificadorAtualizacao_PrevAndNext(itemImportacaoId,
			identificadorAtualizacao, orderByComparator);
	}

	/**
	* Removes all the item importacaos where identificadorAtualizacao = &#63; from the database.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	*/
	public static void removeByIdentificadorAtualizacao(
		String identificadorAtualizacao) {
		getPersistence()
			.removeByIdentificadorAtualizacao(identificadorAtualizacao);
	}

	/**
	* Returns the number of item importacaos where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @return the number of matching item importacaos
	*/
	public static int countByIdentificadorAtualizacao(
		String identificadorAtualizacao) {
		return getPersistence()
				   .countByIdentificadorAtualizacao(identificadorAtualizacao);
	}

	/**
	* Caches the item importacao in the entity cache if it is enabled.
	*
	* @param itemImportacao the item importacao
	*/
	public static void cacheResult(ItemImportacao itemImportacao) {
		getPersistence().cacheResult(itemImportacao);
	}

	/**
	* Caches the item importacaos in the entity cache if it is enabled.
	*
	* @param itemImportacaos the item importacaos
	*/
	public static void cacheResult(List<ItemImportacao> itemImportacaos) {
		getPersistence().cacheResult(itemImportacaos);
	}

	/**
	* Creates a new item importacao with the primary key. Does not add the item importacao to the database.
	*
	* @param itemImportacaoId the primary key for the new item importacao
	* @return the new item importacao
	*/
	public static ItemImportacao create(long itemImportacaoId) {
		return getPersistence().create(itemImportacaoId);
	}

	/**
	* Removes the item importacao with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemImportacaoId the primary key of the item importacao
	* @return the item importacao that was removed
	* @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	*/
	public static ItemImportacao remove(long itemImportacaoId)
		throws br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException {
		return getPersistence().remove(itemImportacaoId);
	}

	public static ItemImportacao updateImpl(ItemImportacao itemImportacao) {
		return getPersistence().updateImpl(itemImportacao);
	}

	/**
	* Returns the item importacao with the primary key or throws a {@link NoSuchItemImportacaoException} if it could not be found.
	*
	* @param itemImportacaoId the primary key of the item importacao
	* @return the item importacao
	* @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	*/
	public static ItemImportacao findByPrimaryKey(long itemImportacaoId)
		throws br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException {
		return getPersistence().findByPrimaryKey(itemImportacaoId);
	}

	/**
	* Returns the item importacao with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemImportacaoId the primary key of the item importacao
	* @return the item importacao, or <code>null</code> if a item importacao with the primary key could not be found
	*/
	public static ItemImportacao fetchByPrimaryKey(long itemImportacaoId) {
		return getPersistence().fetchByPrimaryKey(itemImportacaoId);
	}

	public static java.util.Map<java.io.Serializable, ItemImportacao> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the item importacaos.
	*
	* @return the item importacaos
	*/
	public static List<ItemImportacao> findAll() {
		return getPersistence().findAll();
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
	public static List<ItemImportacao> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
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
	public static List<ItemImportacao> findAll(int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
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
	public static List<ItemImportacao> findAll(int start, int end,
		OrderByComparator<ItemImportacao> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the item importacaos from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of item importacaos.
	*
	* @return the number of item importacaos
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static ItemImportacaoPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ItemImportacaoPersistence, ItemImportacaoPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(ItemImportacaoPersistence.class);

		ServiceTracker<ItemImportacaoPersistence, ItemImportacaoPersistence> serviceTracker =
			new ServiceTracker<ItemImportacaoPersistence, ItemImportacaoPersistence>(bundle.getBundleContext(),
				ItemImportacaoPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}
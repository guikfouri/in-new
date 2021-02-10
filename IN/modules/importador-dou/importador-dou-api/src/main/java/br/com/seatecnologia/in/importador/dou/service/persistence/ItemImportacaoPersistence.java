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

import br.com.seatecnologia.in.importador.dou.exception.NoSuchItemImportacaoException;
import br.com.seatecnologia.in.importador.dou.model.ItemImportacao;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

/**
 * The persistence interface for the item importacao service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author SEA Tecnologia
 * @see br.com.seatecnologia.in.importador.dou.service.persistence.impl.ItemImportacaoPersistenceImpl
 * @see ItemImportacaoUtil
 * @generated
 */
@ProviderType
public interface ItemImportacaoPersistence extends BasePersistence<ItemImportacao> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ItemImportacaoUtil} to access the item importacao persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the item importacaos where importado = &#63;.
	*
	* @param importado the importado
	* @return the matching item importacaos
	*/
	public java.util.List<ItemImportacao> findByImportado(boolean importado);

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
	public java.util.List<ItemImportacao> findByImportado(boolean importado,
		int start, int end);

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
	public java.util.List<ItemImportacao> findByImportado(boolean importado,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator);

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
	public java.util.List<ItemImportacao> findByImportado(boolean importado,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first item importacao in the ordered set where importado = &#63;.
	*
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item importacao
	* @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	*/
	public ItemImportacao findByImportado_First(boolean importado,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException;

	/**
	* Returns the first item importacao in the ordered set where importado = &#63;.
	*
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item importacao, or <code>null</code> if a matching item importacao could not be found
	*/
	public ItemImportacao fetchByImportado_First(boolean importado,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator);

	/**
	* Returns the last item importacao in the ordered set where importado = &#63;.
	*
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item importacao
	* @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	*/
	public ItemImportacao findByImportado_Last(boolean importado,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException;

	/**
	* Returns the last item importacao in the ordered set where importado = &#63;.
	*
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item importacao, or <code>null</code> if a matching item importacao could not be found
	*/
	public ItemImportacao fetchByImportado_Last(boolean importado,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator);

	/**
	* Returns the item importacaos before and after the current item importacao in the ordered set where importado = &#63;.
	*
	* @param itemImportacaoId the primary key of the current item importacao
	* @param importado the importado
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next item importacao
	* @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	*/
	public ItemImportacao[] findByImportado_PrevAndNext(long itemImportacaoId,
		boolean importado,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException;

	/**
	* Removes all the item importacaos where importado = &#63; from the database.
	*
	* @param importado the importado
	*/
	public void removeByImportado(boolean importado);

	/**
	* Returns the number of item importacaos where importado = &#63;.
	*
	* @param importado the importado
	* @return the number of matching item importacaos
	*/
	public int countByImportado(boolean importado);

	/**
	* Returns all the item importacaos where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @return the matching item importacaos
	*/
	public java.util.List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao);

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
	public java.util.List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end);

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
	public java.util.List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator);

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
	public java.util.List<ItemImportacao> findByIdentificadorAtualizacao(
		String identificadorAtualizacao, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Returns the first item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item importacao
	* @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	*/
	public ItemImportacao findByIdentificadorAtualizacao_First(
		String identificadorAtualizacao,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException;

	/**
	* Returns the first item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching item importacao, or <code>null</code> if a matching item importacao could not be found
	*/
	public ItemImportacao fetchByIdentificadorAtualizacao_First(
		String identificadorAtualizacao,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator);

	/**
	* Returns the last item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item importacao
	* @throws NoSuchItemImportacaoException if a matching item importacao could not be found
	*/
	public ItemImportacao findByIdentificadorAtualizacao_Last(
		String identificadorAtualizacao,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException;

	/**
	* Returns the last item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching item importacao, or <code>null</code> if a matching item importacao could not be found
	*/
	public ItemImportacao fetchByIdentificadorAtualizacao_Last(
		String identificadorAtualizacao,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator);

	/**
	* Returns the item importacaos before and after the current item importacao in the ordered set where identificadorAtualizacao = &#63;.
	*
	* @param itemImportacaoId the primary key of the current item importacao
	* @param identificadorAtualizacao the identificador atualizacao
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next item importacao
	* @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	*/
	public ItemImportacao[] findByIdentificadorAtualizacao_PrevAndNext(
		long itemImportacaoId, String identificadorAtualizacao,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator)
		throws NoSuchItemImportacaoException;

	/**
	* Removes all the item importacaos where identificadorAtualizacao = &#63; from the database.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	*/
	public void removeByIdentificadorAtualizacao(
		String identificadorAtualizacao);

	/**
	* Returns the number of item importacaos where identificadorAtualizacao = &#63;.
	*
	* @param identificadorAtualizacao the identificador atualizacao
	* @return the number of matching item importacaos
	*/
	public int countByIdentificadorAtualizacao(String identificadorAtualizacao);

	/**
	* Caches the item importacao in the entity cache if it is enabled.
	*
	* @param itemImportacao the item importacao
	*/
	public void cacheResult(ItemImportacao itemImportacao);

	/**
	* Caches the item importacaos in the entity cache if it is enabled.
	*
	* @param itemImportacaos the item importacaos
	*/
	public void cacheResult(java.util.List<ItemImportacao> itemImportacaos);

	/**
	* Creates a new item importacao with the primary key. Does not add the item importacao to the database.
	*
	* @param itemImportacaoId the primary key for the new item importacao
	* @return the new item importacao
	*/
	public ItemImportacao create(long itemImportacaoId);

	/**
	* Removes the item importacao with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param itemImportacaoId the primary key of the item importacao
	* @return the item importacao that was removed
	* @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	*/
	public ItemImportacao remove(long itemImportacaoId)
		throws NoSuchItemImportacaoException;

	public ItemImportacao updateImpl(ItemImportacao itemImportacao);

	/**
	* Returns the item importacao with the primary key or throws a {@link NoSuchItemImportacaoException} if it could not be found.
	*
	* @param itemImportacaoId the primary key of the item importacao
	* @return the item importacao
	* @throws NoSuchItemImportacaoException if a item importacao with the primary key could not be found
	*/
	public ItemImportacao findByPrimaryKey(long itemImportacaoId)
		throws NoSuchItemImportacaoException;

	/**
	* Returns the item importacao with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param itemImportacaoId the primary key of the item importacao
	* @return the item importacao, or <code>null</code> if a item importacao with the primary key could not be found
	*/
	public ItemImportacao fetchByPrimaryKey(long itemImportacaoId);

	@Override
	public java.util.Map<java.io.Serializable, ItemImportacao> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the item importacaos.
	*
	* @return the item importacaos
	*/
	public java.util.List<ItemImportacao> findAll();

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
	public java.util.List<ItemImportacao> findAll(int start, int end);

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
	public java.util.List<ItemImportacao> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator);

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
	public java.util.List<ItemImportacao> findAll(int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<ItemImportacao> orderByComparator,
		boolean retrieveFromCache);

	/**
	* Removes all the item importacaos from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of item importacaos.
	*
	* @return the number of item importacaos
	*/
	public int countAll();
}
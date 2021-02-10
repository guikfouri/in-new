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

package br.com.seatecnologia.in.importador.dou.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ItemImportacao}.
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemImportacao
 * @generated
 */
@ProviderType
public class ItemImportacaoWrapper implements ItemImportacao,
	ModelWrapper<ItemImportacao> {
	public ItemImportacaoWrapper(ItemImportacao itemImportacao) {
		_itemImportacao = itemImportacao;
	}

	@Override
	public Class<?> getModelClass() {
		return ItemImportacao.class;
	}

	@Override
	public String getModelClassName() {
		return ItemImportacao.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("itemImportacaoId", getItemImportacaoId());
		attributes.put("tipoItem", getTipoItem());
		attributes.put("dataImportacao", getDataImportacao());
		attributes.put("identificadorAtualizacao", getIdentificadorAtualizacao());
		attributes.put("importado", isImportado());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long itemImportacaoId = (Long)attributes.get("itemImportacaoId");

		if (itemImportacaoId != null) {
			setItemImportacaoId(itemImportacaoId);
		}

		String tipoItem = (String)attributes.get("tipoItem");

		if (tipoItem != null) {
			setTipoItem(tipoItem);
		}

		Date dataImportacao = (Date)attributes.get("dataImportacao");

		if (dataImportacao != null) {
			setDataImportacao(dataImportacao);
		}

		String identificadorAtualizacao = (String)attributes.get(
				"identificadorAtualizacao");

		if (identificadorAtualizacao != null) {
			setIdentificadorAtualizacao(identificadorAtualizacao);
		}

		Boolean importado = (Boolean)attributes.get("importado");

		if (importado != null) {
			setImportado(importado);
		}
	}

	@Override
	public Object clone() {
		return new ItemImportacaoWrapper((ItemImportacao)_itemImportacao.clone());
	}

	@Override
	public int compareTo(ItemImportacao itemImportacao) {
		return _itemImportacao.compareTo(itemImportacao);
	}

	/**
	* Returns the data importacao of this item importacao.
	*
	* @return the data importacao of this item importacao
	*/
	@Override
	public Date getDataImportacao() {
		return _itemImportacao.getDataImportacao();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _itemImportacao.getExpandoBridge();
	}

	/**
	* Returns the identificador atualizacao of this item importacao.
	*
	* @return the identificador atualizacao of this item importacao
	*/
	@Override
	public String getIdentificadorAtualizacao() {
		return _itemImportacao.getIdentificadorAtualizacao();
	}

	/**
	* Returns the importado of this item importacao.
	*
	* @return the importado of this item importacao
	*/
	@Override
	public boolean getImportado() {
		return _itemImportacao.getImportado();
	}

	/**
	* Returns the item importacao ID of this item importacao.
	*
	* @return the item importacao ID of this item importacao
	*/
	@Override
	public long getItemImportacaoId() {
		return _itemImportacao.getItemImportacaoId();
	}

	/**
	* Returns the primary key of this item importacao.
	*
	* @return the primary key of this item importacao
	*/
	@Override
	public long getPrimaryKey() {
		return _itemImportacao.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _itemImportacao.getPrimaryKeyObj();
	}

	/**
	* Returns the tipo item of this item importacao.
	*
	* @return the tipo item of this item importacao
	*/
	@Override
	public String getTipoItem() {
		return _itemImportacao.getTipoItem();
	}

	@Override
	public int hashCode() {
		return _itemImportacao.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _itemImportacao.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _itemImportacao.isEscapedModel();
	}

	/**
	* Returns <code>true</code> if this item importacao is importado.
	*
	* @return <code>true</code> if this item importacao is importado; <code>false</code> otherwise
	*/
	@Override
	public boolean isImportado() {
		return _itemImportacao.isImportado();
	}

	@Override
	public boolean isNew() {
		return _itemImportacao.isNew();
	}

	@Override
	public void persist() {
		_itemImportacao.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_itemImportacao.setCachedModel(cachedModel);
	}

	/**
	* Sets the data importacao of this item importacao.
	*
	* @param dataImportacao the data importacao of this item importacao
	*/
	@Override
	public void setDataImportacao(Date dataImportacao) {
		_itemImportacao.setDataImportacao(dataImportacao);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_itemImportacao.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_itemImportacao.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_itemImportacao.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the identificador atualizacao of this item importacao.
	*
	* @param identificadorAtualizacao the identificador atualizacao of this item importacao
	*/
	@Override
	public void setIdentificadorAtualizacao(String identificadorAtualizacao) {
		_itemImportacao.setIdentificadorAtualizacao(identificadorAtualizacao);
	}

	/**
	* Sets whether this item importacao is importado.
	*
	* @param importado the importado of this item importacao
	*/
	@Override
	public void setImportado(boolean importado) {
		_itemImportacao.setImportado(importado);
	}

	/**
	* Sets the item importacao ID of this item importacao.
	*
	* @param itemImportacaoId the item importacao ID of this item importacao
	*/
	@Override
	public void setItemImportacaoId(long itemImportacaoId) {
		_itemImportacao.setItemImportacaoId(itemImportacaoId);
	}

	@Override
	public void setNew(boolean n) {
		_itemImportacao.setNew(n);
	}

	/**
	* Sets the primary key of this item importacao.
	*
	* @param primaryKey the primary key of this item importacao
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_itemImportacao.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_itemImportacao.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the tipo item of this item importacao.
	*
	* @param tipoItem the tipo item of this item importacao
	*/
	@Override
	public void setTipoItem(String tipoItem) {
		_itemImportacao.setTipoItem(tipoItem);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ItemImportacao> toCacheModel() {
		return _itemImportacao.toCacheModel();
	}

	@Override
	public ItemImportacao toEscapedModel() {
		return new ItemImportacaoWrapper(_itemImportacao.toEscapedModel());
	}

	@Override
	public String toString() {
		return _itemImportacao.toString();
	}

	@Override
	public ItemImportacao toUnescapedModel() {
		return new ItemImportacaoWrapper(_itemImportacao.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _itemImportacao.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ItemImportacaoWrapper)) {
			return false;
		}

		ItemImportacaoWrapper itemImportacaoWrapper = (ItemImportacaoWrapper)obj;

		if (Objects.equals(_itemImportacao,
					itemImportacaoWrapper._itemImportacao)) {
			return true;
		}

		return false;
	}

	@Override
	public ItemImportacao getWrappedModel() {
		return _itemImportacao;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _itemImportacao.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _itemImportacao.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_itemImportacao.resetOriginalValues();
	}

	private final ItemImportacao _itemImportacao;
}
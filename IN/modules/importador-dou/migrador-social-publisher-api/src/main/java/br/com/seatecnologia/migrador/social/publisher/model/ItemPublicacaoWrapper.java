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

package br.com.seatecnologia.migrador.social.publisher.model;

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
 * This class is a wrapper for {@link ItemPublicacao}.
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemPublicacao
 * @generated
 */
@ProviderType
public class ItemPublicacaoWrapper implements ItemPublicacao,
	ModelWrapper<ItemPublicacao> {
	public ItemPublicacaoWrapper(ItemPublicacao itemPublicacao) {
		_itemPublicacao = itemPublicacao;
	}

	@Override
	public Class<?> getModelClass() {
		return ItemPublicacao.class;
	}

	@Override
	public String getModelClassName() {
		return ItemPublicacao.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("itemPublicacaoId", getItemPublicacaoId());
		attributes.put("entryId", getEntryId());
		attributes.put("postId", getPostId());
		attributes.put("publishDate", getPublishDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("socialNetwork", getSocialNetwork());
		attributes.put("itemMigracaoId", getItemMigracaoId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long itemPublicacaoId = (Long)attributes.get("itemPublicacaoId");

		if (itemPublicacaoId != null) {
			setItemPublicacaoId(itemPublicacaoId);
		}

		Long entryId = (Long)attributes.get("entryId");

		if (entryId != null) {
			setEntryId(entryId);
		}

		String postId = (String)attributes.get("postId");

		if (postId != null) {
			setPostId(postId);
		}

		Date publishDate = (Date)attributes.get("publishDate");

		if (publishDate != null) {
			setPublishDate(publishDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Integer socialNetwork = (Integer)attributes.get("socialNetwork");

		if (socialNetwork != null) {
			setSocialNetwork(socialNetwork);
		}

		Long itemMigracaoId = (Long)attributes.get("itemMigracaoId");

		if (itemMigracaoId != null) {
			setItemMigracaoId(itemMigracaoId);
		}
	}

	@Override
	public Object clone() {
		return new ItemPublicacaoWrapper((ItemPublicacao)_itemPublicacao.clone());
	}

	@Override
	public int compareTo(ItemPublicacao itemPublicacao) {
		return _itemPublicacao.compareTo(itemPublicacao);
	}

	/**
	* Returns the entry ID of this item publicacao.
	*
	* @return the entry ID of this item publicacao
	*/
	@Override
	public long getEntryId() {
		return _itemPublicacao.getEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _itemPublicacao.getExpandoBridge();
	}

	/**
	* Returns the item migracao ID of this item publicacao.
	*
	* @return the item migracao ID of this item publicacao
	*/
	@Override
	public long getItemMigracaoId() {
		return _itemPublicacao.getItemMigracaoId();
	}

	/**
	* Returns the item publicacao ID of this item publicacao.
	*
	* @return the item publicacao ID of this item publicacao
	*/
	@Override
	public long getItemPublicacaoId() {
		return _itemPublicacao.getItemPublicacaoId();
	}

	/**
	* Returns the modified date of this item publicacao.
	*
	* @return the modified date of this item publicacao
	*/
	@Override
	public Date getModifiedDate() {
		return _itemPublicacao.getModifiedDate();
	}

	/**
	* Returns the post ID of this item publicacao.
	*
	* @return the post ID of this item publicacao
	*/
	@Override
	public String getPostId() {
		return _itemPublicacao.getPostId();
	}

	/**
	* Returns the primary key of this item publicacao.
	*
	* @return the primary key of this item publicacao
	*/
	@Override
	public long getPrimaryKey() {
		return _itemPublicacao.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _itemPublicacao.getPrimaryKeyObj();
	}

	/**
	* Returns the publish date of this item publicacao.
	*
	* @return the publish date of this item publicacao
	*/
	@Override
	public Date getPublishDate() {
		return _itemPublicacao.getPublishDate();
	}

	/**
	* Returns the social network of this item publicacao.
	*
	* @return the social network of this item publicacao
	*/
	@Override
	public int getSocialNetwork() {
		return _itemPublicacao.getSocialNetwork();
	}

	@Override
	public int hashCode() {
		return _itemPublicacao.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _itemPublicacao.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _itemPublicacao.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _itemPublicacao.isNew();
	}

	@Override
	public void persist() {
		_itemPublicacao.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_itemPublicacao.setCachedModel(cachedModel);
	}

	/**
	* Sets the entry ID of this item publicacao.
	*
	* @param entryId the entry ID of this item publicacao
	*/
	@Override
	public void setEntryId(long entryId) {
		_itemPublicacao.setEntryId(entryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_itemPublicacao.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_itemPublicacao.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_itemPublicacao.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the item migracao ID of this item publicacao.
	*
	* @param itemMigracaoId the item migracao ID of this item publicacao
	*/
	@Override
	public void setItemMigracaoId(long itemMigracaoId) {
		_itemPublicacao.setItemMigracaoId(itemMigracaoId);
	}

	/**
	* Sets the item publicacao ID of this item publicacao.
	*
	* @param itemPublicacaoId the item publicacao ID of this item publicacao
	*/
	@Override
	public void setItemPublicacaoId(long itemPublicacaoId) {
		_itemPublicacao.setItemPublicacaoId(itemPublicacaoId);
	}

	/**
	* Sets the modified date of this item publicacao.
	*
	* @param modifiedDate the modified date of this item publicacao
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_itemPublicacao.setModifiedDate(modifiedDate);
	}

	@Override
	public void setNew(boolean n) {
		_itemPublicacao.setNew(n);
	}

	/**
	* Sets the post ID of this item publicacao.
	*
	* @param postId the post ID of this item publicacao
	*/
	@Override
	public void setPostId(String postId) {
		_itemPublicacao.setPostId(postId);
	}

	/**
	* Sets the primary key of this item publicacao.
	*
	* @param primaryKey the primary key of this item publicacao
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_itemPublicacao.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_itemPublicacao.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the publish date of this item publicacao.
	*
	* @param publishDate the publish date of this item publicacao
	*/
	@Override
	public void setPublishDate(Date publishDate) {
		_itemPublicacao.setPublishDate(publishDate);
	}

	/**
	* Sets the social network of this item publicacao.
	*
	* @param socialNetwork the social network of this item publicacao
	*/
	@Override
	public void setSocialNetwork(int socialNetwork) {
		_itemPublicacao.setSocialNetwork(socialNetwork);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ItemPublicacao> toCacheModel() {
		return _itemPublicacao.toCacheModel();
	}

	@Override
	public ItemPublicacao toEscapedModel() {
		return new ItemPublicacaoWrapper(_itemPublicacao.toEscapedModel());
	}

	@Override
	public String toString() {
		return _itemPublicacao.toString();
	}

	@Override
	public ItemPublicacao toUnescapedModel() {
		return new ItemPublicacaoWrapper(_itemPublicacao.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _itemPublicacao.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ItemPublicacaoWrapper)) {
			return false;
		}

		ItemPublicacaoWrapper itemPublicacaoWrapper = (ItemPublicacaoWrapper)obj;

		if (Objects.equals(_itemPublicacao,
					itemPublicacaoWrapper._itemPublicacao)) {
			return true;
		}

		return false;
	}

	@Override
	public ItemPublicacao getWrappedModel() {
		return _itemPublicacao;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _itemPublicacao.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _itemPublicacao.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_itemPublicacao.resetOriginalValues();
	}

	private final ItemPublicacao _itemPublicacao;
}
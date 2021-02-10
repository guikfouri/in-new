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

import com.liferay.portal.kernel.bean.AutoEscape;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;

/**
 * The base model interface for the ItemPublicacao service. Represents a row in the &quot;IN_ItemPublicacao&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This interface and its corresponding implementation {@link br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoModelImpl} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoImpl}.
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemPublicacao
 * @see br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoImpl
 * @see br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoModelImpl
 * @generated
 */
@ProviderType
public interface ItemPublicacaoModel extends BaseModel<ItemPublicacao> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. All methods that expect a item publicacao model instance should use the {@link ItemPublicacao} interface instead.
	 */

	/**
	 * Returns the primary key of this item publicacao.
	 *
	 * @return the primary key of this item publicacao
	 */
	public long getPrimaryKey();

	/**
	 * Sets the primary key of this item publicacao.
	 *
	 * @param primaryKey the primary key of this item publicacao
	 */
	public void setPrimaryKey(long primaryKey);

	/**
	 * Returns the item publicacao ID of this item publicacao.
	 *
	 * @return the item publicacao ID of this item publicacao
	 */
	public long getItemPublicacaoId();

	/**
	 * Sets the item publicacao ID of this item publicacao.
	 *
	 * @param itemPublicacaoId the item publicacao ID of this item publicacao
	 */
	public void setItemPublicacaoId(long itemPublicacaoId);

	/**
	 * Returns the entry ID of this item publicacao.
	 *
	 * @return the entry ID of this item publicacao
	 */
	public long getEntryId();

	/**
	 * Sets the entry ID of this item publicacao.
	 *
	 * @param entryId the entry ID of this item publicacao
	 */
	public void setEntryId(long entryId);

	/**
	 * Returns the post ID of this item publicacao.
	 *
	 * @return the post ID of this item publicacao
	 */
	@AutoEscape
	public String getPostId();

	/**
	 * Sets the post ID of this item publicacao.
	 *
	 * @param postId the post ID of this item publicacao
	 */
	public void setPostId(String postId);

	/**
	 * Returns the publish date of this item publicacao.
	 *
	 * @return the publish date of this item publicacao
	 */
	public Date getPublishDate();

	/**
	 * Sets the publish date of this item publicacao.
	 *
	 * @param publishDate the publish date of this item publicacao
	 */
	public void setPublishDate(Date publishDate);

	/**
	 * Returns the modified date of this item publicacao.
	 *
	 * @return the modified date of this item publicacao
	 */
	public Date getModifiedDate();

	/**
	 * Sets the modified date of this item publicacao.
	 *
	 * @param modifiedDate the modified date of this item publicacao
	 */
	public void setModifiedDate(Date modifiedDate);

	/**
	 * Returns the social network of this item publicacao.
	 *
	 * @return the social network of this item publicacao
	 */
	public int getSocialNetwork();

	/**
	 * Sets the social network of this item publicacao.
	 *
	 * @param socialNetwork the social network of this item publicacao
	 */
	public void setSocialNetwork(int socialNetwork);

	/**
	 * Returns the item migracao ID of this item publicacao.
	 *
	 * @return the item migracao ID of this item publicacao
	 */
	public long getItemMigracaoId();

	/**
	 * Sets the item migracao ID of this item publicacao.
	 *
	 * @param itemMigracaoId the item migracao ID of this item publicacao
	 */
	public void setItemMigracaoId(long itemMigracaoId);

	@Override
	public boolean isNew();

	@Override
	public void setNew(boolean n);

	@Override
	public boolean isCachedModel();

	@Override
	public void setCachedModel(boolean cachedModel);

	@Override
	public boolean isEscapedModel();

	@Override
	public Serializable getPrimaryKeyObj();

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj);

	@Override
	public ExpandoBridge getExpandoBridge();

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel);

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge);

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	@Override
	public Object clone();

	@Override
	public int compareTo(ItemPublicacao itemPublicacao);

	@Override
	public int hashCode();

	@Override
	public CacheModel<ItemPublicacao> toCacheModel();

	@Override
	public ItemPublicacao toEscapedModel();

	@Override
	public ItemPublicacao toUnescapedModel();

	@Override
	public String toString();

	@Override
	public String toXmlString();
}
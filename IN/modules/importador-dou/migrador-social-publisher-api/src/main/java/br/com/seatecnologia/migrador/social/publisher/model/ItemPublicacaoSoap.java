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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author SEA Tecnologia
 * @generated
 */
@ProviderType
public class ItemPublicacaoSoap implements Serializable {
	public static ItemPublicacaoSoap toSoapModel(ItemPublicacao model) {
		ItemPublicacaoSoap soapModel = new ItemPublicacaoSoap();

		soapModel.setItemPublicacaoId(model.getItemPublicacaoId());
		soapModel.setEntryId(model.getEntryId());
		soapModel.setPostId(model.getPostId());
		soapModel.setPublishDate(model.getPublishDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setSocialNetwork(model.getSocialNetwork());
		soapModel.setItemMigracaoId(model.getItemMigracaoId());

		return soapModel;
	}

	public static ItemPublicacaoSoap[] toSoapModels(ItemPublicacao[] models) {
		ItemPublicacaoSoap[] soapModels = new ItemPublicacaoSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ItemPublicacaoSoap[][] toSoapModels(ItemPublicacao[][] models) {
		ItemPublicacaoSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ItemPublicacaoSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ItemPublicacaoSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ItemPublicacaoSoap[] toSoapModels(List<ItemPublicacao> models) {
		List<ItemPublicacaoSoap> soapModels = new ArrayList<ItemPublicacaoSoap>(models.size());

		for (ItemPublicacao model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ItemPublicacaoSoap[soapModels.size()]);
	}

	public ItemPublicacaoSoap() {
	}

	public long getPrimaryKey() {
		return _itemPublicacaoId;
	}

	public void setPrimaryKey(long pk) {
		setItemPublicacaoId(pk);
	}

	public long getItemPublicacaoId() {
		return _itemPublicacaoId;
	}

	public void setItemPublicacaoId(long itemPublicacaoId) {
		_itemPublicacaoId = itemPublicacaoId;
	}

	public long getEntryId() {
		return _entryId;
	}

	public void setEntryId(long entryId) {
		_entryId = entryId;
	}

	public String getPostId() {
		return _postId;
	}

	public void setPostId(String postId) {
		_postId = postId;
	}

	public Date getPublishDate() {
		return _publishDate;
	}

	public void setPublishDate(Date publishDate) {
		_publishDate = publishDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public int getSocialNetwork() {
		return _socialNetwork;
	}

	public void setSocialNetwork(int socialNetwork) {
		_socialNetwork = socialNetwork;
	}

	public long getItemMigracaoId() {
		return _itemMigracaoId;
	}

	public void setItemMigracaoId(long itemMigracaoId) {
		_itemMigracaoId = itemMigracaoId;
	}

	private long _itemPublicacaoId;
	private long _entryId;
	private String _postId;
	private Date _publishDate;
	private Date _modifiedDate;
	private int _socialNetwork;
	private long _itemMigracaoId;
}
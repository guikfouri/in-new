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

package br.com.seatecnologia.migrador.social.publisher.model.impl;

import aQute.bnd.annotation.ProviderType;

import br.com.seatecnologia.migrador.social.publisher.model.ItemPublicacao;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ItemPublicacao in entity cache.
 *
 * @author SEA Tecnologia
 * @see ItemPublicacao
 * @generated
 */
@ProviderType
public class ItemPublicacaoCacheModel implements CacheModel<ItemPublicacao>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ItemPublicacaoCacheModel)) {
			return false;
		}

		ItemPublicacaoCacheModel itemPublicacaoCacheModel = (ItemPublicacaoCacheModel)obj;

		if (itemPublicacaoId == itemPublicacaoCacheModel.itemPublicacaoId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, itemPublicacaoId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{itemPublicacaoId=");
		sb.append(itemPublicacaoId);
		sb.append(", entryId=");
		sb.append(entryId);
		sb.append(", postId=");
		sb.append(postId);
		sb.append(", publishDate=");
		sb.append(publishDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", socialNetwork=");
		sb.append(socialNetwork);
		sb.append(", itemMigracaoId=");
		sb.append(itemMigracaoId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ItemPublicacao toEntityModel() {
		ItemPublicacaoImpl itemPublicacaoImpl = new ItemPublicacaoImpl();

		itemPublicacaoImpl.setItemPublicacaoId(itemPublicacaoId);
		itemPublicacaoImpl.setEntryId(entryId);

		if (postId == null) {
			itemPublicacaoImpl.setPostId("");
		}
		else {
			itemPublicacaoImpl.setPostId(postId);
		}

		if (publishDate == Long.MIN_VALUE) {
			itemPublicacaoImpl.setPublishDate(null);
		}
		else {
			itemPublicacaoImpl.setPublishDate(new Date(publishDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			itemPublicacaoImpl.setModifiedDate(null);
		}
		else {
			itemPublicacaoImpl.setModifiedDate(new Date(modifiedDate));
		}

		itemPublicacaoImpl.setSocialNetwork(socialNetwork);
		itemPublicacaoImpl.setItemMigracaoId(itemMigracaoId);

		itemPublicacaoImpl.resetOriginalValues();

		return itemPublicacaoImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		itemPublicacaoId = objectInput.readLong();

		entryId = objectInput.readLong();
		postId = objectInput.readUTF();
		publishDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		socialNetwork = objectInput.readInt();

		itemMigracaoId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(itemPublicacaoId);

		objectOutput.writeLong(entryId);

		if (postId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(postId);
		}

		objectOutput.writeLong(publishDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeInt(socialNetwork);

		objectOutput.writeLong(itemMigracaoId);
	}

	public long itemPublicacaoId;
	public long entryId;
	public String postId;
	public long publishDate;
	public long modifiedDate;
	public int socialNetwork;
	public long itemMigracaoId;
}
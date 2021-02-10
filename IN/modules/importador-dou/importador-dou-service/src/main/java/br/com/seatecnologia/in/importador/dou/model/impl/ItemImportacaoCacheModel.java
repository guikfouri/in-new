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

package br.com.seatecnologia.in.importador.dou.model.impl;

import aQute.bnd.annotation.ProviderType;

import br.com.seatecnologia.in.importador.dou.model.ItemImportacao;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing ItemImportacao in entity cache.
 *
 * @author SEA Tecnologia
 * @see ItemImportacao
 * @generated
 */
@ProviderType
public class ItemImportacaoCacheModel implements CacheModel<ItemImportacao>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ItemImportacaoCacheModel)) {
			return false;
		}

		ItemImportacaoCacheModel itemImportacaoCacheModel = (ItemImportacaoCacheModel)obj;

		if (itemImportacaoId == itemImportacaoCacheModel.itemImportacaoId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, itemImportacaoId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{itemImportacaoId=");
		sb.append(itemImportacaoId);
		sb.append(", tipoItem=");
		sb.append(tipoItem);
		sb.append(", dataImportacao=");
		sb.append(dataImportacao);
		sb.append(", identificadorAtualizacao=");
		sb.append(identificadorAtualizacao);
		sb.append(", importado=");
		sb.append(importado);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public ItemImportacao toEntityModel() {
		ItemImportacaoImpl itemImportacaoImpl = new ItemImportacaoImpl();

		itemImportacaoImpl.setItemImportacaoId(itemImportacaoId);

		if (tipoItem == null) {
			itemImportacaoImpl.setTipoItem("");
		}
		else {
			itemImportacaoImpl.setTipoItem(tipoItem);
		}

		if (dataImportacao == Long.MIN_VALUE) {
			itemImportacaoImpl.setDataImportacao(null);
		}
		else {
			itemImportacaoImpl.setDataImportacao(new Date(dataImportacao));
		}

		if (identificadorAtualizacao == null) {
			itemImportacaoImpl.setIdentificadorAtualizacao("");
		}
		else {
			itemImportacaoImpl.setIdentificadorAtualizacao(identificadorAtualizacao);
		}

		itemImportacaoImpl.setImportado(importado);

		itemImportacaoImpl.resetOriginalValues();

		return itemImportacaoImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		itemImportacaoId = objectInput.readLong();
		tipoItem = objectInput.readUTF();
		dataImportacao = objectInput.readLong();
		identificadorAtualizacao = objectInput.readUTF();

		importado = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(itemImportacaoId);

		if (tipoItem == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(tipoItem);
		}

		objectOutput.writeLong(dataImportacao);

		if (identificadorAtualizacao == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(identificadorAtualizacao);
		}

		objectOutput.writeBoolean(importado);
	}

	public long itemImportacaoId;
	public String tipoItem;
	public long dataImportacao;
	public String identificadorAtualizacao;
	public boolean importado;
}
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
public class ItemImportacaoSoap implements Serializable {
	public static ItemImportacaoSoap toSoapModel(ItemImportacao model) {
		ItemImportacaoSoap soapModel = new ItemImportacaoSoap();

		soapModel.setItemImportacaoId(model.getItemImportacaoId());
		soapModel.setTipoItem(model.getTipoItem());
		soapModel.setDataImportacao(model.getDataImportacao());
		soapModel.setIdentificadorAtualizacao(model.getIdentificadorAtualizacao());
		soapModel.setImportado(model.isImportado());

		return soapModel;
	}

	public static ItemImportacaoSoap[] toSoapModels(ItemImportacao[] models) {
		ItemImportacaoSoap[] soapModels = new ItemImportacaoSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ItemImportacaoSoap[][] toSoapModels(ItemImportacao[][] models) {
		ItemImportacaoSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new ItemImportacaoSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ItemImportacaoSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ItemImportacaoSoap[] toSoapModels(List<ItemImportacao> models) {
		List<ItemImportacaoSoap> soapModels = new ArrayList<ItemImportacaoSoap>(models.size());

		for (ItemImportacao model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new ItemImportacaoSoap[soapModels.size()]);
	}

	public ItemImportacaoSoap() {
	}

	public long getPrimaryKey() {
		return _itemImportacaoId;
	}

	public void setPrimaryKey(long pk) {
		setItemImportacaoId(pk);
	}

	public long getItemImportacaoId() {
		return _itemImportacaoId;
	}

	public void setItemImportacaoId(long itemImportacaoId) {
		_itemImportacaoId = itemImportacaoId;
	}

	public String getTipoItem() {
		return _tipoItem;
	}

	public void setTipoItem(String tipoItem) {
		_tipoItem = tipoItem;
	}

	public Date getDataImportacao() {
		return _dataImportacao;
	}

	public void setDataImportacao(Date dataImportacao) {
		_dataImportacao = dataImportacao;
	}

	public String getIdentificadorAtualizacao() {
		return _identificadorAtualizacao;
	}

	public void setIdentificadorAtualizacao(String identificadorAtualizacao) {
		_identificadorAtualizacao = identificadorAtualizacao;
	}

	public boolean getImportado() {
		return _importado;
	}

	public boolean isImportado() {
		return _importado;
	}

	public void setImportado(boolean importado) {
		_importado = importado;
	}

	private long _itemImportacaoId;
	private String _tipoItem;
	private Date _dataImportacao;
	private String _identificadorAtualizacao;
	private boolean _importado;
}
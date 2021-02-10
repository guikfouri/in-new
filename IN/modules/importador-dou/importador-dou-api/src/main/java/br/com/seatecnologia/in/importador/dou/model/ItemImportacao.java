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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the ItemImportacao service. Represents a row in the &quot;IN_ItemImportacao&quot; database table, with each column mapped to a property of this class.
 *
 * @author SEA Tecnologia
 * @see ItemImportacaoModel
 * @see br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoImpl
 * @see br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoModelImpl
 * @generated
 */
@ImplementationClassName("br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoImpl")
@ProviderType
public interface ItemImportacao extends ItemImportacaoModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link br.com.seatecnologia.in.importador.dou.model.impl.ItemImportacaoImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ItemImportacao, Long> ITEM_IMPORTACAO_ID_ACCESSOR =
		new Accessor<ItemImportacao, Long>() {
			@Override
			public Long get(ItemImportacao itemImportacao) {
				return itemImportacao.getItemImportacaoId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ItemImportacao> getTypeClass() {
				return ItemImportacao.class;
			}
		};
}
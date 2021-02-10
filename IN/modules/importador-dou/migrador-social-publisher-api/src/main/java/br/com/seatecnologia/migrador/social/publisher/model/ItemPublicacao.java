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

import com.liferay.portal.kernel.annotation.ImplementationClassName;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.util.Accessor;

/**
 * The extended model interface for the ItemPublicacao service. Represents a row in the &quot;IN_ItemPublicacao&quot; database table, with each column mapped to a property of this class.
 *
 * @author SEA Tecnologia
 * @see ItemPublicacaoModel
 * @see br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoImpl
 * @see br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoModelImpl
 * @generated
 */
@ImplementationClassName("br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoImpl")
@ProviderType
public interface ItemPublicacao extends ItemPublicacaoModel, PersistedModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add methods to {@link br.com.seatecnologia.migrador.social.publisher.model.impl.ItemPublicacaoImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static final Accessor<ItemPublicacao, Long> ITEM_PUBLICACAO_ID_ACCESSOR =
		new Accessor<ItemPublicacao, Long>() {
			@Override
			public Long get(ItemPublicacao itemPublicacao) {
				return itemPublicacao.getItemPublicacaoId();
			}

			@Override
			public Class<Long> getAttributeClass() {
				return Long.class;
			}

			@Override
			public Class<ItemPublicacao> getTypeClass() {
				return ItemPublicacao.class;
			}
		};
}
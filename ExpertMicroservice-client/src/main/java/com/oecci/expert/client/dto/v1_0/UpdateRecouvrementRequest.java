/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.UpdateRecouvrementRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class UpdateRecouvrementRequest implements Cloneable, Serializable {

	public static UpdateRecouvrementRequest toDTO(String json) {
		return UpdateRecouvrementRequestSerDes.toDTO(json);
	}

	public DocumentRequest getDocument() {
		return document;
	}

	public void setDocument(DocumentRequest document) {
		this.document = document;
	}

	public void setDocument(
		UnsafeSupplier<DocumentRequest, Exception> documentUnsafeSupplier) {

		try {
			document = documentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected DocumentRequest document;

	@Override
	public UpdateRecouvrementRequest clone() throws CloneNotSupportedException {
		return (UpdateRecouvrementRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UpdateRecouvrementRequest)) {
			return false;
		}

		UpdateRecouvrementRequest updateRecouvrementRequest =
			(UpdateRecouvrementRequest)object;

		return Objects.equals(toString(), updateRecouvrementRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return UpdateRecouvrementRequestSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:2010734906
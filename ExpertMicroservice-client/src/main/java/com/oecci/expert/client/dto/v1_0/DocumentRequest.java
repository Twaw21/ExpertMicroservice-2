/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.DocumentRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class DocumentRequest implements Cloneable, Serializable {

	public static DocumentRequest toDTO(String json) {
		return DocumentRequestSerDes.toDTO(json);
	}

	public String getDocumentContent() {
		return documentContent;
	}

	public void setDocumentContent(String documentContent) {
		this.documentContent = documentContent;
	}

	public void setDocumentContent(
		UnsafeSupplier<String, Exception> documentContentUnsafeSupplier) {

		try {
			documentContent = documentContentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String documentContent;

	public String getDocumentName() {
		return documentName;
	}

	public void setDocumentName(String documentName) {
		this.documentName = documentName;
	}

	public void setDocumentName(
		UnsafeSupplier<String, Exception> documentNameUnsafeSupplier) {

		try {
			documentName = documentNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String documentName;

	@Override
	public DocumentRequest clone() throws CloneNotSupportedException {
		return (DocumentRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DocumentRequest)) {
			return false;
		}

		DocumentRequest documentRequest = (DocumentRequest)object;

		return Objects.equals(toString(), documentRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DocumentRequestSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-67387800
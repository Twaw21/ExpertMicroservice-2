/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.CreateDmdExtQuotVisaRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class CreateDmdExtQuotVisaRequest implements Cloneable, Serializable {

	public static CreateDmdExtQuotVisaRequest toDTO(String json) {
		return CreateDmdExtQuotVisaRequestSerDes.toDTO(json);
	}

	public Long getExpertID() {
		return expertID;
	}

	public void setExpertID(Long expertID) {
		this.expertID = expertID;
	}

	public void setExpertID(
		UnsafeSupplier<Long, Exception> expertIDUnsafeSupplier) {

		try {
			expertID = expertIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long expertID;

	public String getMotif() {
		return motif;
	}

	public void setMotif(String motif) {
		this.motif = motif;
	}

	public void setMotif(
		UnsafeSupplier<String, Exception> motifUnsafeSupplier) {

		try {
			motif = motifUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String motif;

	public Long getOrdreExpertID() {
		return ordreExpertID;
	}

	public void setOrdreExpertID(Long ordreExpertID) {
		this.ordreExpertID = ordreExpertID;
	}

	public void setOrdreExpertID(
		UnsafeSupplier<Long, Exception> ordreExpertIDUnsafeSupplier) {

		try {
			ordreExpertID = ordreExpertIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long ordreExpertID;

	@Override
	public CreateDmdExtQuotVisaRequest clone()
		throws CloneNotSupportedException {

		return (CreateDmdExtQuotVisaRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateDmdExtQuotVisaRequest)) {
			return false;
		}

		CreateDmdExtQuotVisaRequest createDmdExtQuotVisaRequest =
			(CreateDmdExtQuotVisaRequest)object;

		return Objects.equals(
			toString(), createDmdExtQuotVisaRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CreateDmdExtQuotVisaRequestSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-1908191740
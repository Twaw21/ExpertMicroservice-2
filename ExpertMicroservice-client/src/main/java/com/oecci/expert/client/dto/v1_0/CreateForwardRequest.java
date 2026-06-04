/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.CreateForwardRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class CreateForwardRequest implements Cloneable, Serializable {

	public static CreateForwardRequest toDTO(String json) {
		return CreateForwardRequestSerDes.toDTO(json);
	}

	public Long getClientID() {
		return clientID;
	}

	public void setClientID(Long clientID) {
		this.clientID = clientID;
	}

	public void setClientID(
		UnsafeSupplier<Long, Exception> clientIDUnsafeSupplier) {

		try {
			clientID = clientIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long clientID;

	public Long getExpertDestinataireID() {
		return expertDestinataireID;
	}

	public void setExpertDestinataireID(Long expertDestinataireID) {
		this.expertDestinataireID = expertDestinataireID;
	}

	public void setExpertDestinataireID(
		UnsafeSupplier<Long, Exception> expertDestinataireIDUnsafeSupplier) {

		try {
			expertDestinataireID = expertDestinataireIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long expertDestinataireID;

	public Long getExpertExpediteurID() {
		return expertExpediteurID;
	}

	public void setExpertExpediteurID(Long expertExpediteurID) {
		this.expertExpediteurID = expertExpediteurID;
	}

	public void setExpertExpediteurID(
		UnsafeSupplier<Long, Exception> expertExpediteurIDUnsafeSupplier) {

		try {
			expertExpediteurID = expertExpediteurIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long expertExpediteurID;

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

	@Override
	public CreateForwardRequest clone() throws CloneNotSupportedException {
		return (CreateForwardRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateForwardRequest)) {
			return false;
		}

		CreateForwardRequest createForwardRequest =
			(CreateForwardRequest)object;

		return Objects.equals(toString(), createForwardRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CreateForwardRequestSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-891840793
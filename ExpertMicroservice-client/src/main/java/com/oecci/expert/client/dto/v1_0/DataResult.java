/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.DataResultSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class DataResult implements Cloneable, Serializable {

	public static DataResult toDTO(String json) {
		return DataResultSerDes.toDTO(json);
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public void setDesignation(
		UnsafeSupplier<String, Exception> designationUnsafeSupplier) {

		try {
			designation = designationUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String designation;

	@Override
	public DataResult clone() throws CloneNotSupportedException {
		return (DataResult)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DataResult)) {
			return false;
		}

		DataResult dataResult = (DataResult)object;

		return Objects.equals(toString(), dataResult.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return DataResultSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:-623420937
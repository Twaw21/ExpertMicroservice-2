/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.LoadVisualRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class LoadVisualRequest implements Cloneable, Serializable {

	public static LoadVisualRequest toDTO(String json) {
		return LoadVisualRequestSerDes.toDTO(json);
	}

	public String getVisualContent() {
		return visualContent;
	}

	public void setVisualContent(String visualContent) {
		this.visualContent = visualContent;
	}

	public void setVisualContent(
		UnsafeSupplier<String, Exception> visualContentUnsafeSupplier) {

		try {
			visualContent = visualContentUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String visualContent;

	public String getVisualName() {
		return visualName;
	}

	public void setVisualName(String visualName) {
		this.visualName = visualName;
	}

	public void setVisualName(
		UnsafeSupplier<String, Exception> visualNameUnsafeSupplier) {

		try {
			visualName = visualNameUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String visualName;

	@Override
	public LoadVisualRequest clone() throws CloneNotSupportedException {
		return (LoadVisualRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LoadVisualRequest)) {
			return false;
		}

		LoadVisualRequest loadVisualRequest = (LoadVisualRequest)object;

		return Objects.equals(toString(), loadVisualRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return LoadVisualRequestSerDes.toJSON(this);
	}

}
// LIFERAY-REST-BUILDER-HASH:428642106
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.CreateCollaboRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class CreateCollaboRequest implements Cloneable, Serializable {

	public static CreateCollaboRequest toDTO(String json) {
		return CreateCollaboRequestSerDes.toDTO(json);
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public void setContact(
		UnsafeSupplier<String, Exception> contactUnsafeSupplier) {

		try {
			contact = contactUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String contact;

	public Long getCreatorID() {
		return creatorID;
	}

	public void setCreatorID(Long creatorID) {
		this.creatorID = creatorID;
	}

	public void setCreatorID(
		UnsafeSupplier<Long, Exception> creatorIDUnsafeSupplier) {

		try {
			creatorID = creatorIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long creatorID;

	public CreatorLevel getCreatorLevel() {
		return creatorLevel;
	}

	public String getCreatorLevelAsString() {
		if (creatorLevel == null) {
			return null;
		}

		return creatorLevel.toString();
	}

	public void setCreatorLevel(CreatorLevel creatorLevel) {
		this.creatorLevel = creatorLevel;
	}

	public void setCreatorLevel(
		UnsafeSupplier<CreatorLevel, Exception> creatorLevelUnsafeSupplier) {

		try {
			creatorLevel = creatorLevelUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected CreatorLevel creatorLevel;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEmail(
		UnsafeSupplier<String, Exception> emailUnsafeSupplier) {

		try {
			email = emailUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String email;

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNom(UnsafeSupplier<String, Exception> nomUnsafeSupplier) {
		try {
			nom = nomUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String nom;

	public String getPrenoms() {
		return prenoms;
	}

	public void setPrenoms(String prenoms) {
		this.prenoms = prenoms;
	}

	public void setPrenoms(
		UnsafeSupplier<String, Exception> prenomsUnsafeSupplier) {

		try {
			prenoms = prenomsUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String prenoms;

	public Role getRole() {
		return role;
	}

	public String getRoleAsString() {
		if (role == null) {
			return null;
		}

		return role.toString();
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setRole(UnsafeSupplier<Role, Exception> roleUnsafeSupplier) {
		try {
			role = roleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Role role;

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public void setStatut(
		UnsafeSupplier<Boolean, Exception> statutUnsafeSupplier) {

		try {
			statut = statutUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Boolean statut;

	@Override
	public CreateCollaboRequest clone() throws CloneNotSupportedException {
		return (CreateCollaboRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateCollaboRequest)) {
			return false;
		}

		CreateCollaboRequest createCollaboRequest =
			(CreateCollaboRequest)object;

		return Objects.equals(toString(), createCollaboRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CreateCollaboRequestSerDes.toJSON(this);
	}

	public static enum CreatorLevel {

		ADMIN("admin"), MODERATEUR("moderateur"), ASSISTANT("assistant");

		public static CreatorLevel create(String value) {
			for (CreatorLevel creatorLevel : values()) {
				if (Objects.equals(creatorLevel.getValue(), value) ||
					Objects.equals(creatorLevel.name(), value)) {

					return creatorLevel;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private CreatorLevel(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Role {

		ADMIN("admin"), MODERATEUR("moderateur"), ASSISTANT("assistant");

		public static Role create(String value) {
			for (Role role : values()) {
				if (Objects.equals(role.getValue(), value) ||
					Objects.equals(role.name(), value)) {

					return role;
				}
			}

			return null;
		}

		public String getValue() {
			return _value;
		}

		@Override
		public String toString() {
			return _value;
		}

		private Role(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:-270687597
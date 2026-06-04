/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.UpdateExpertRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class UpdateExpertRequest implements Cloneable, Serializable {

	public static UpdateExpertRequest toDTO(String json) {
		return UpdateExpertRequestSerDes.toDTO(json);
	}

	public String getAdressePostale() {
		return adressePostale;
	}

	public void setAdressePostale(String adressePostale) {
		this.adressePostale = adressePostale;
	}

	public void setAdressePostale(
		UnsafeSupplier<String, Exception> adressePostaleUnsafeSupplier) {

		try {
			adressePostale = adressePostaleUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String adressePostale;

	public String getAnnee_inscription() {
		return annee_inscription;
	}

	public void setAnnee_inscription(String annee_inscription) {
		this.annee_inscription = annee_inscription;
	}

	public void setAnnee_inscription(
		UnsafeSupplier<String, Exception> annee_inscriptionUnsafeSupplier) {

		try {
			annee_inscription = annee_inscriptionUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String annee_inscription;

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

	public String getMatricule() {
		return matricule;
	}

	public void setMatricule(String matricule) {
		this.matricule = matricule;
	}

	public void setMatricule(
		UnsafeSupplier<String, Exception> matriculeUnsafeSupplier) {

		try {
			matricule = matriculeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String matricule;

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

	public Update_by getUpdate_by() {
		return update_by;
	}

	public String getUpdate_byAsString() {
		if (update_by == null) {
			return null;
		}

		return update_by.toString();
	}

	public void setUpdate_by(Update_by update_by) {
		this.update_by = update_by;
	}

	public void setUpdate_by(
		UnsafeSupplier<Update_by, Exception> update_byUnsafeSupplier) {

		try {
			update_by = update_byUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Update_by update_by;

	@Override
	public UpdateExpertRequest clone() throws CloneNotSupportedException {
		return (UpdateExpertRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof UpdateExpertRequest)) {
			return false;
		}

		UpdateExpertRequest updateExpertRequest = (UpdateExpertRequest)object;

		return Objects.equals(toString(), updateExpertRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return UpdateExpertRequestSerDes.toJSON(this);
	}

	public static enum Update_by {

		BY_ADMIN("by_admin"), BY_MODERATEUR("by_moderateur"),
		BY_ASSISTANT("by_assistant");

		public static Update_by create(String value) {
			for (Update_by update_by : values()) {
				if (Objects.equals(update_by.getValue(), value) ||
					Objects.equals(update_by.name(), value)) {

					return update_by;
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

		private Update_by(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:2109882539
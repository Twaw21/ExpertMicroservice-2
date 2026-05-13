/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.CreateExpert_ComptableSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class CreateExpert_Comptable implements Cloneable, Serializable {

	public static CreateExpert_Comptable toDTO(String json) {
		return CreateExpert_ComptableSerDes.toDTO(json);
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

	public Long getExpertAssoID() {
		return expertAssoID;
	}

	public void setExpertAssoID(Long expertAssoID) {
		this.expertAssoID = expertAssoID;
	}

	public void setExpertAssoID(
		UnsafeSupplier<Long, Exception> expertAssoIDUnsafeSupplier) {

		try {
			expertAssoID = expertAssoIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long expertAssoID;

	public Inscription_by getInscription_by() {
		return inscription_by;
	}

	public String getInscription_byAsString() {
		if (inscription_by == null) {
			return null;
		}

		return inscription_by.toString();
	}

	public void setInscription_by(Inscription_by inscription_by) {
		this.inscription_by = inscription_by;
	}

	public void setInscription_by(
		UnsafeSupplier<Inscription_by, Exception>
			inscription_byUnsafeSupplier) {

		try {
			inscription_by = inscription_byUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Inscription_by inscription_by;

	public Inscription_mode getInscription_mode() {
		return inscription_mode;
	}

	public String getInscription_modeAsString() {
		if (inscription_mode == null) {
			return null;
		}

		return inscription_mode.toString();
	}

	public void setInscription_mode(Inscription_mode inscription_mode) {
		this.inscription_mode = inscription_mode;
	}

	public void setInscription_mode(
		UnsafeSupplier<Inscription_mode, Exception>
			inscription_modeUnsafeSupplier) {

		try {
			inscription_mode = inscription_modeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Inscription_mode inscription_mode;

	public Inscription_type getInscription_type() {
		return inscription_type;
	}

	public String getInscription_typeAsString() {
		if (inscription_type == null) {
			return null;
		}

		return inscription_type.toString();
	}

	public void setInscription_type(Inscription_type inscription_type) {
		this.inscription_type = inscription_type;
	}

	public void setInscription_type(
		UnsafeSupplier<Inscription_type, Exception>
			inscription_typeUnsafeSupplier) {

		try {
			inscription_type = inscription_typeUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Inscription_type inscription_type;

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

	public String getNomCabinet() {
		return nomCabinet;
	}

	public void setNomCabinet(String nomCabinet) {
		this.nomCabinet = nomCabinet;
	}

	public void setNomCabinet(
		UnsafeSupplier<String, Exception> nomCabinetUnsafeSupplier) {

		try {
			nomCabinet = nomCabinetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String nomCabinet;

	public String getNumeroCabinet() {
		return numeroCabinet;
	}

	public void setNumeroCabinet(String numeroCabinet) {
		this.numeroCabinet = numeroCabinet;
	}

	public void setNumeroCabinet(
		UnsafeSupplier<String, Exception> numeroCabinetUnsafeSupplier) {

		try {
			numeroCabinet = numeroCabinetUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected String numeroCabinet;

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

	@Override
	public CreateExpert_Comptable clone() throws CloneNotSupportedException {
		return (CreateExpert_Comptable)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CreateExpert_Comptable)) {
			return false;
		}

		CreateExpert_Comptable createExpert_Comptable =
			(CreateExpert_Comptable)object;

		return Objects.equals(toString(), createExpert_Comptable.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return CreateExpert_ComptableSerDes.toJSON(this);
	}

	public static enum Inscription_by {

		BY_ADMIN("by_admin"), BY_MODERATEUR("by_moderateur"),
		BY_ASSISTANT("by_assistant");

		public static Inscription_by create(String value) {
			for (Inscription_by inscription_by : values()) {
				if (Objects.equals(inscription_by.getValue(), value) ||
					Objects.equals(inscription_by.name(), value)) {

					return inscription_by;
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

		private Inscription_by(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Inscription_mode {

		FOR_ASSOC("for_assoc"), FOR_COORD("for_coord"), FOR_ADJ("for_adj"),
		FOR_NA("for_na");

		public static Inscription_mode create(String value) {
			for (Inscription_mode inscription_mode : values()) {
				if (Objects.equals(inscription_mode.getValue(), value) ||
					Objects.equals(inscription_mode.name(), value)) {

					return inscription_mode;
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

		private Inscription_mode(String value) {
			_value = value;
		}

		private final String _value;

	}

	public static enum Inscription_type {

		INDIVIDUEL("individuel"), CABINET("cabinet"),
		COLLABORATEUR("collaborateur");

		public static Inscription_type create(String value) {
			for (Inscription_type inscription_type : values()) {
				if (Objects.equals(inscription_type.getValue(), value) ||
					Objects.equals(inscription_type.name(), value)) {

					return inscription_type;
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

		private Inscription_type(String value) {
			_value = value;
		}

		private final String _value;

	}

}
// LIFERAY-REST-BUILDER-HASH:669008824
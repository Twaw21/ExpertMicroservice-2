package com.oecci.expert.client.dto.v1_0;

import com.oecci.expert.client.function.UnsafeSupplier;
import com.oecci.expert.client.serdes.v1_0.ReloadWalletRequestSerDes;

import java.io.Serializable;

import java.util.Objects;

import javax.annotation.Generated;

/**
 * @author Lenovo
 * @generated
 */
@Generated("")
public class ReloadWalletRequest implements Cloneable, Serializable {

	public static ReloadWalletRequest toDTO(String json) {
		return ReloadWalletRequestSerDes.toDTO(json);
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public void setAmount(
		UnsafeSupplier<Long, Exception> amountUnsafeSupplier) {

		try {
			amount = amountUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long amount;

	public Long getExpertComptableID() {
		return expertComptableID;
	}

	public void setExpertComptableID(Long expertComptableID) {
		this.expertComptableID = expertComptableID;
	}

	public void setExpertComptableID(
		UnsafeSupplier<Long, Exception> expertComptableIDUnsafeSupplier) {

		try {
			expertComptableID = expertComptableIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long expertComptableID;

	public Long getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(Long paymentID) {
		this.paymentID = paymentID;
	}

	public void setPaymentID(
		UnsafeSupplier<Long, Exception> paymentIDUnsafeSupplier) {

		try {
			paymentID = paymentIDUnsafeSupplier.get();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected Long paymentID;

	@Override
	public ReloadWalletRequest clone() throws CloneNotSupportedException {
		return (ReloadWalletRequest)super.clone();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ReloadWalletRequest)) {
			return false;
		}

		ReloadWalletRequest reloadWalletRequest = (ReloadWalletRequest)object;

		return Objects.equals(toString(), reloadWalletRequest.toString());
	}

	@Override
	public int hashCode() {
		String string = toString();

		return string.hashCode();
	}

	public String toString() {
		return ReloadWalletRequestSerDes.toJSON(this);
	}

}
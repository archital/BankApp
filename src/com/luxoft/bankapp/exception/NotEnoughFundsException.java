package com.luxoft.bankapp.exception;

/**
 * Created by SCJP on 15.01.2015.
 */
public class NotEnoughFundsException extends BankException {

	private float amount;

	public NotEnoughFundsException(float amount) {
		this.amount = amount;
	}

	@Override
	public String getMessage () {
		StringBuilder sb = new StringBuilder();
		sb.append("you need another ");
		sb.append(amount);
		return sb.toString();
	}
}

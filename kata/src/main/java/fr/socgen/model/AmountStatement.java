package fr.socgen.model;

import java.math.BigDecimal;
import java.util.Objects;

import fr.socgen.utils.OperationType;

public class AmountStatement {
	
	private BigDecimal amount;
	
	public AmountStatement() {
	}

	public AmountStatement(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public AmountStatement updateAmount(OperationType type, AmountStatement amount) {
		if(amount != null && !amount.getAmount().equals(new BigDecimal(0))) {
			if (type.equals(OperationType.DEPOSIT)) {
				if (getAmount() == null) {
					return new AmountStatement(amount.getAmount());
				}
				else {
					return new AmountStatement(getAmount().add(
							amount.getAmount()));
				}
			}		
			else {
				return new AmountStatement(getAmount().subtract(
							amount.getAmount()));
			}
		}
		else {
			throw new IllegalArgumentException("Illegal amount");
		}
	}

	@Override
	public String toString() {
		return "Amount [amount=" + amount + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(amount);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AmountStatement other = (AmountStatement) obj;
		return amount.equals(other.amount);
	}

}

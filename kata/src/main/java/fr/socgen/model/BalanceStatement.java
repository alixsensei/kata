package fr.socgen.model;

import java.math.BigDecimal;
import java.util.Objects;

import fr.socgen.utils.OperationType;

public class BalanceStatement {
	
	private BigDecimal balanceStatement;
	
	public BalanceStatement() {
	}

	public BalanceStatement(BigDecimal balanceStatement) {
		this.balanceStatement = balanceStatement;
	}

	public BigDecimal getBalanceStatement() {
		return balanceStatement;
	}

	public void setBalanceStatement(BigDecimal balanceStatement) {
		this.balanceStatement = balanceStatement;
	}

	public BalanceStatement updateBalanceStatement(OperationType type, AmountStatement amount) {
		if(amount != null && amount.getAmount() != null 
				&& !amount.getAmount().equals(new BigDecimal(0))) {
			if (type.equals(OperationType.DEPOSIT)) {
				if (getBalanceStatement() == null) {
					return new BalanceStatement(amount.getAmount());
				}
				else {
					return new BalanceStatement(getBalanceStatement().add(
							amount.getAmount()));
				}
			}		
			else {
				return new BalanceStatement(getBalanceStatement().subtract(
						amount.getAmount()));
			}
		}
		else {
			throw new IllegalArgumentException("Illegal balance");
		}
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(balanceStatement);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BalanceStatement other = (BalanceStatement) obj;
		return balanceStatement.equals(other.balanceStatement);
	}

	@Override
	public String toString() {
		return "BalanceStatement [balanceStatement=" + balanceStatement + "]";
	}
	
}

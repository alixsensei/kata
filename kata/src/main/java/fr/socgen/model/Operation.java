package fr.socgen.model;

import java.time.LocalDate;
import java.util.Objects;

import fr.socgen.utils.OperationType;


public class Operation {
	
	private OperationType type;
    private LocalDate date;
    private AmountStatement amount;
    private BalanceStatement balance;
    
    public Operation() {
	}

	public Operation(OperationType type, LocalDate date, AmountStatement amount, BalanceStatement balance) {
		this.type = type;
		this.date = date;
		this.amount = amount;
		this.balance = balance;
	}

	public OperationType getType() {
		return type;
	}

	public void setType(OperationType type) {
		this.type = type;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public AmountStatement getAmount() {
		return amount;
	}

	public void setAmount(AmountStatement amount) {
		this.amount = amount;
	}

	public BalanceStatement getBalance() {
		return balance;
	}

	public void setBalance(BalanceStatement balance) {
		this.balance = balance;
	}


	@Override
	public String toString() {
		return "Operation [type=" + type + ", date=" + date + ", amount=" + amount + ", balance=" + balance + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		return amount.equals(other.amount) && balance.equals(other.balance)
				&& date.equals(other.date) && type == other.type;
	}
	

	@Override
	public int hashCode() {
		return Objects.hash(amount, balance, date, type);
	}

	
}

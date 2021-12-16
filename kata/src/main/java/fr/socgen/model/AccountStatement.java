package fr.socgen.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.socgen.utils.OperationType;

public class AccountStatement {

	private AmountStatement amount;
	private BalanceStatement balanceStatement;
	List<Operation> operations = new ArrayList<Operation>();
	
	public AccountStatement() {
	}
	
	public AccountStatement(AmountStatement amount, BalanceStatement balanceStatement, List<Operation> operations) {
		this.amount = amount;
		this.balanceStatement = balanceStatement;
		this.operations = operations;
	}

	public AmountStatement getAmount() {
		return amount;
	}

	public void setAmount(AmountStatement amount) {
		this.amount = amount;
	}

	public BalanceStatement getBalanceStatement() {
		return balanceStatement;
	}

	public void setBalanceStatement(BalanceStatement balanceStatement) {
		this.balanceStatement = balanceStatement;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}
	
	@SuppressWarnings("static-access")
	public AccountStatement executeOperation(OperationType type, LocalDate date, AmountStatement amount) throws IllegalArgumentException {
		if(amount != null && amount.getAmount() != null) {
			// Deposit
			if(type.equals(OperationType.DEPOSIT)) {
				if(amount.getAmount().signum() <= 0) {
					throw new IllegalArgumentException("the amount must be greater than 0");
				}
				else {
					Operation op = new Operation();												
					op.setBalance(new BalanceStatement(amount.getAmount()));
					op.setType(type);
					op.setDate(date.now());
					op.setAmount(amount);				
					addOperation(op);
						
					// update Amount of account	
					if (getAmount() != null) {
						setAmount(getAmount().updateAmount(
							OperationType.DEPOSIT, amount));
					}
					else {
						setAmount(new AmountStatement().updateAmount(
							OperationType.DEPOSIT, amount));
					}						
				
					//update value of balance
					if (getBalanceStatement() != null) {
						setBalanceStatement(
							getBalanceStatement().updateBalanceStatement(
								OperationType.DEPOSIT, amount));
					}
					else {
						setBalanceStatement(
							new BalanceStatement().updateBalanceStatement(
								OperationType.DEPOSIT, amount));
					}
				}											
		    }
			
			// WithDrawal
			else if (type.equals(OperationType.WITHDRAWAL)) {
				if (getAmount() == null) {
					throw new IllegalArgumentException("You can not get money in an empty account");
				}
				else if (getAmount().getAmount().equals(new BigDecimal(0))) {
					throw new IllegalArgumentException("You can not get money in an empty account");
				}
				else if((getAmount().getAmount().subtract(
					amount.getAmount())).signum() < 0) {
					throw new IllegalArgumentException(
							"Amount chosen is greater than the amount available on the account");
				}
				else {
					Operation op = new Operation();
					op.setBalance(new BalanceStatement(amount.getAmount()));
					op.setType(type);
					op.setDate(date.now());
					op.setAmount(amount);										
					addOperation(op);
							
					// update Amount of account	
					if (getAmount() != null) {
						setAmount(getAmount().updateAmount(
							OperationType.WITHDRAWAL, amount));
					}
					else {
						setAmount(new AmountStatement().updateAmount(
							OperationType.WITHDRAWAL, amount));
					}						
					
					//update value of balance
					if (getBalanceStatement() != null) {
						setBalanceStatement(
							getBalanceStatement().updateBalanceStatement(
								OperationType.WITHDRAWAL, amount));
					}
					else {
						setBalanceStatement(
							new BalanceStatement().updateBalanceStatement(
								OperationType.WITHDRAWAL, amount));
					}
				}						
						
			}			
		}
		else {
			throw new IllegalArgumentException("Illegal operation");
		}
	
		return this;
	}
	
	public void addOperation(Operation operation) {
		if(operation != null) {
			operations.add(operation);
		}
		else {
			operations.add(new Operation());
		}
    }
	
	

}

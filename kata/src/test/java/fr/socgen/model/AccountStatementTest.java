package fr.socgen.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.DemoApplication;

import fr.socgen.utils.OperationType;


@SpringBootTest
@ContextConfiguration(classes = DemoApplication.class)
public class AccountStatementTest extends DemoApplication {
	
	//deposit in an empty amount account
	@Test
	public void executeOperationDepositTest() {
		//deposit in an empty amount account
		AccountStatement accountStatement = new AccountStatement();
		accountStatement.executeOperation(OperationType.DEPOSIT, 
				LocalDate.now(), new AmountStatement(new BigDecimal(10000)));
		assertEquals(new AmountStatement(new BigDecimal(10000)),
				accountStatement.getAmount());
	}
	
	//failed deposit
	@Test
	public void executeOperationsDepositFail() {
		// Negative Amount value
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			AccountStatement accountStatement = new AccountStatement();
			accountStatement.executeOperation(OperationType.DEPOSIT, 
					LocalDate.now(), new AmountStatement(new BigDecimal(-10000)));
	    });
		assertEquals("the amount must be greater than 0", exception.getMessage());
		
		// Zero Amount value
		exception = assertThrows(IllegalArgumentException.class, () -> {
			AccountStatement accountStatementEmpty = new AccountStatement();
			accountStatementEmpty.executeOperation(OperationType.DEPOSIT, 
					LocalDate.now(), new AmountStatement(new BigDecimal(0)));
	    });
		assertEquals("the amount must be greater than 0", exception.getMessage());
		
		// null value for amount
		exception = assertThrows(IllegalArgumentException.class, () -> {
			AccountStatement accountStatementEmpty = new AccountStatement();
			accountStatementEmpty.executeOperation(OperationType.DEPOSIT, 
					LocalDate.now(), null);
	    });
		assertEquals("Illegal operation", exception.getMessage());
		
	}
	
	//succeful Withdrawal
	@Test
	public void executeOperationWithdrawalTest() {
		//Withdrawal in an empty amount account
		AmountStatement amount = new AmountStatement(new BigDecimal(1000));
		BalanceStatement balance = new BalanceStatement(new BigDecimal(1000));
		AccountStatement accountStatement = 
				new AccountStatement(amount, balance, new ArrayList<Operation>());
		accountStatement.executeOperation(OperationType.WITHDRAWAL, 
				LocalDate.now(), new AmountStatement(new BigDecimal(200)));
		assertEquals(new AmountStatement(new BigDecimal(800)),
				accountStatement.getAmount());
	}
	
	//failed Withdrawal
	@Test
	public void executeOperationsWithdrawalFail() {

		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
			AccountStatement accountStatement = new AccountStatement();
			accountStatement.executeOperation(OperationType.WITHDRAWAL, 
					LocalDate.now(), new AmountStatement(new BigDecimal(400)));
	    });
		assertEquals("You can not get money in an empty account", exception.getMessage());
		

		exception = assertThrows(IllegalArgumentException.class, () -> {
			AccountStatement accountStatement = new AccountStatement(
				new AmountStatement(new BigDecimal(0)),
					new BalanceStatement(new BigDecimal(0)), 
						new ArrayList<Operation>());
			accountStatement.executeOperation(OperationType.WITHDRAWAL, 
					LocalDate.now(), new AmountStatement(new BigDecimal(400)));
	    });
		assertEquals("You can not get money in an empty account", exception.getMessage());
		

		exception = assertThrows(IllegalArgumentException.class, () -> {
			AccountStatement accountStatement = new AccountStatement(
				new AmountStatement(new BigDecimal(25)),
					new BalanceStatement(new BigDecimal(0)), 
						new ArrayList<Operation>());
			accountStatement.executeOperation(OperationType.WITHDRAWAL, 
					LocalDate.now(), new AmountStatement(new BigDecimal(790)));
	    });
		assertEquals("Amount chosen is greater than the amount available on the account", exception.getMessage());
	}
	
	// List of operations
	@Test
	public void executeOperationTest() {
		AccountStatement accountStatement = new AccountStatement();
		accountStatement.setBalanceStatement(
				new BalanceStatement(new BigDecimal(10)));
		
		//deposit
		accountStatement.executeOperation(OperationType.DEPOSIT, 
				LocalDate.now(), new AmountStatement(new BigDecimal(1500))); 
		//witdrawal
		accountStatement.executeOperation(OperationType.WITHDRAWAL, 
				LocalDate.now(), new AmountStatement(new BigDecimal(500)));
		// deposit
		accountStatement.executeOperation(OperationType.DEPOSIT, 
				LocalDate.now(), new AmountStatement(new BigDecimal(200)));
		//with
		accountStatement.executeOperation(OperationType.WITHDRAWAL, 
				LocalDate.now(), new AmountStatement(new BigDecimal(800)));
		
		// test
		Assert.assertEquals(accountStatement.getOperations().get(0), 
				new Operation(OperationType.DEPOSIT, 
						LocalDate.now(), new AmountStatement(new BigDecimal(1500)), 
						new BalanceStatement(new BigDecimal(1500))));
		
		Assert.assertEquals(accountStatement.getOperations().get(1), 
				new Operation(OperationType.WITHDRAWAL, 
				LocalDate.now(), new AmountStatement(new BigDecimal(500)), 
				new BalanceStatement(new BigDecimal(500))));
		
		Assert.assertEquals(accountStatement.getOperations().get(2), 
				new Operation(OperationType.DEPOSIT, 
				LocalDate.now(), new AmountStatement(new BigDecimal(200)), 
				new BalanceStatement(new BigDecimal(200))));
		
		Assert.assertEquals(accountStatement.getOperations().get(3), 
				new Operation(OperationType.WITHDRAWAL, 
				LocalDate.now(), new AmountStatement(new BigDecimal(800)), 
				new BalanceStatement(new BigDecimal(800))));			
	}

}

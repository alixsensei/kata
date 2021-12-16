package fr.socgen.model;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.DemoApplication;

import fr.socgen.utils.OperationType;

@SpringBootTest
@ContextConfiguration(classes = DemoApplication.class)
public class BalanceStatementTest extends DemoApplication {
	
	@Test
	public void updateBalanceStatementTest() {	
		
		BalanceStatement balance = new BalanceStatement();		
		BalanceStatement balanceTmp = balance.updateBalanceStatement(OperationType.DEPOSIT, 
				new AmountStatement(new BigDecimal(100)));			
		assertEquals(new BigDecimal(100), balanceTmp.getBalanceStatement());
		
		balance = new BalanceStatement(new BigDecimal(10));
		balanceTmp = balance.updateBalanceStatement(OperationType.DEPOSIT, 
				new AmountStatement(new BigDecimal(100)));
		assertEquals(new BigDecimal(110), balanceTmp.getBalanceStatement());
		
		BalanceStatement balanceWDrawal = balanceTmp.updateBalanceStatement(OperationType.WITHDRAWAL, 
				new AmountStatement(new BigDecimal(40)));			
		assertEquals(new BigDecimal(70), balanceWDrawal.getBalanceStatement());

	}
	
	//failed Balance
	@Test
	public void updateBalanceStatementFail() {	
		// Null Amount value
		Throwable exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			BalanceStatement balance = new BalanceStatement();		
			balance.updateBalanceStatement(OperationType.DEPOSIT, null);
		});
		assertEquals("Illegal balance", exception.getMessage());
		
		exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			BalanceStatement balance = new BalanceStatement();		
			balance.updateBalanceStatement(OperationType.WITHDRAWAL, 
				new AmountStatement());
		});
		assertEquals("Illegal balance", exception.getMessage());
				
		// Zero Amount value
		exception = Assertions.assertThrows(IllegalArgumentException.class, () -> {
			BalanceStatement balance = new BalanceStatement();		
			balance.updateBalanceStatement(OperationType.WITHDRAWAL, 
				new AmountStatement(new BigDecimal(0)));
		});
		assertEquals("Illegal balance", exception.getMessage());	
	}

}

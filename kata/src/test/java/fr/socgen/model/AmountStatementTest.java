package fr.socgen.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.example.demo.DemoApplication;

import fr.socgen.utils.OperationType;

@SpringBootTest
@ContextConfiguration(classes = DemoApplication.class)
public class AmountStatementTest extends DemoApplication {
	
	//failed Amount
	@Test
	public void updateAmountFail() {	
		// Null Amount value
		Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
				AmountStatement amount = new AmountStatement();		
				amount.updateAmount(OperationType.DEPOSIT, null);
		});
		assertEquals("Illegal amount", exception.getMessage());
			
		// Zero Amount value
		exception = assertThrows(IllegalArgumentException.class, () -> {
			AmountStatement amount = new AmountStatement();		
			amount.updateAmount(OperationType.DEPOSIT, 
				new AmountStatement(new BigDecimal(0)));
		});
		assertEquals("Illegal amount", exception.getMessage());
	}
	
	//succeful Amount
	@Test
	public void updateAmountTest() {
		
		AmountStatement amount = new AmountStatement();
		assertEquals(new AmountStatement(new BigDecimal(450)), 
				amount.updateAmount(OperationType.DEPOSIT, 
						new AmountStatement(new BigDecimal(450))));
		
		amount.setAmount(new BigDecimal(100));
		
		AmountStatement amountTmp = amount.updateAmount(
				OperationType.DEPOSIT, 
					new AmountStatement(new BigDecimal(60)));
		assertEquals(new AmountStatement(new BigDecimal(160)), amountTmp);
		
		assertEquals(new AmountStatement(new BigDecimal(50)), 
				amountTmp.updateAmount(OperationType.WITHDRAWAL, 
						new AmountStatement(new BigDecimal(110))));		
	}

}

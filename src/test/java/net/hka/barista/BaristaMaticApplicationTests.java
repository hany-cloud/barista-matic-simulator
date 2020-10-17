package net.hka.barista;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;

@DisplayName("When running The Test for BaristaMachine")
class BaristaMaticApplicationTests {

	private final String DRINK_MENU_TITLE = "Menu:";
	private final String INVENTORY_MENU_TITLE = "Inventory:";
	private final String[] inventoryMenuLinesAtInitArray = new String[] {INVENTORY_MENU_TITLE, "Cocoa,10", "Coffee,10", "Cream,10", "Decaf Coffee,10",
			"Espresso,10", "Foamed Milk,10", "Steamed Milk,10", "Sugar,10", "Whipped Cream,10"
	};
	private final String[] drinkMenuLinesAtInitArray = new String[] {DRINK_MENU_TITLE, "1,Caffe Americano,$3.30,true", "2,Caffe Latte,$2.55,true", 
			"3,Caffe Mocha,$3.35,true", "4,Cappuccino,$2.90,true",
			"5,Coffee,$2.75,true", "6,Decaf Coffee,$2.75,true"
	};
	
	BaristaMachine machine;
	
	@BeforeEach
	void init(TestInfo testInfo, TestReporter testReporter) {
		machine = new BaristaMachineImpl();
		
		testReporter.publishEntry("Running " + testInfo.getDisplayName() + " with Annotations " + Arrays.toString(testInfo.getTestMethod().get().getAnnotations()));
	}
	
	@AfterEach
	void cleanUp() {
		machine = null;
	}
	
	@Nested
	@DisplayName("When Testing Machine at startup")
	@Tag("MachineStartup")
	class MachineStartupTestCases {
		
		private String quitCommand = "Q";
		private String getCapturedOutput(String command) {
			String capturedOutput = null;
			
			InputStream stdin = System.in;
			PrintStream originalOut = System.out;
			
			try {
				ByteArrayOutputStream os = new ByteArrayOutputStream(100);
				PrintStream capture = new PrintStream(os);
				System.setOut(capture);
				
				System.setIn(new ByteArrayInputStream(command.getBytes()));	
				Scanner scanner = new Scanner(System.in);
				
				machine.startUp(scanner);	
				
				capture.flush();
				capturedOutput = os.toString();
			} finally {
				System.setIn(stdin);
				System.setOut(originalOut);
			}
			
			return capturedOutput;
		}
		
		@Test
		@DisplayName("When Testing Upon startup, the Barista-matic should display a list of its current inventory, followed by a drink menu")
		void testStartupMachineMenus() {
			String capturedOutput = getCapturedOutput(quitCommand);
			assertTrue(capturedOutput.contains(INVENTORY_MENU_TITLE));
			assertTrue(capturedOutput.contains(DRINK_MENU_TITLE));
			assertTrue(capturedOutput.indexOf(INVENTORY_MENU_TITLE) == 0);
			assertTrue(capturedOutput.indexOf(INVENTORY_MENU_TITLE) < capturedOutput.indexOf(DRINK_MENU_TITLE));

		}
		
		@Test
		@DisplayName("When Testing Upon startup, the Barista-matic dipslays all ingredients initially with 10 units")
		void testMachineAtStartupInitiallySetAllIngredientsUnitsTo10() {
			String capturedOutput = getCapturedOutput(quitCommand);
			
			for (String string : inventoryMenuLinesAtInitArray) {
				assertTrue(capturedOutput.contains(string));
			}
			
		}
	
		@Test
		@DisplayName("When Inventory menu is displayed in alphabetic order")
		void testInventoryMenuDisplayedInAlphabeticOrder() {
			String capturedOutput = getCapturedOutput(quitCommand);
			
			for (int i = 1; i < inventoryMenuLinesAtInitArray.length; i++) {
				assertTrue(capturedOutput.indexOf(inventoryMenuLinesAtInitArray[i - 1]) < capturedOutput.indexOf(inventoryMenuLinesAtInitArray[i]));
			}
			int len = inventoryMenuLinesAtInitArray.length;
			assertTrue(capturedOutput.indexOf(inventoryMenuLinesAtInitArray[len - 1]) < capturedOutput.indexOf(drinkMenuLinesAtInitArray[0]));
			for (int i = 1; i < drinkMenuLinesAtInitArray.length; i++) {
				assertTrue(capturedOutput.indexOf(drinkMenuLinesAtInitArray[i - 1]) < capturedOutput.indexOf(drinkMenuLinesAtInitArray[i]));
			}
				
		}
		
	}
	
	@Nested
	@DisplayName("When apply any of machine operations Test Cases")
	@Tag("MachineOperations")
	class MachineOperationsTestCases {
		
		private String selectDrinkNumber6Command = "6";	
		
		@Nested
		@DisplayName("When drinks are dispensed Test Cases")
		@Tag("MachineDispenseDrinkOperation")
		class DispenseDrinkOperationTestCases {
			
			@Test
			@DisplayName("When drink is dispensed and the machine has the required ingredients to make the drink, inventory should be updated")
			void testDrinkSuccessfullyDispensedAndInventoryUpdated() {
				StringBuilder response = machine.execute(selectDrinkNumber6Command);
				assertTrue(response.toString().contains("Decaf Coffee,7"));
			}
			
			@Test
			@DisplayName("When drink is dispensed and the machine has the required ingredients to make the drink, display a message with defined format with no trail white spaces")
			void testDrinkSuccessfullyDispensedMessageWithDefinedFormat() {
				StringBuilder response = machine.execute(selectDrinkNumber6Command);
				assertTrue(response.toString().contains("Dispensing: Decaf Coffee"));
				assertTrue(response.indexOf("Dispensing: Decaf Coffee") == 0);
				assertTrue(response.indexOf(INVENTORY_MENU_TITLE) == "Dispensing: Decaf Coffee".length() + 2);
			}
			
			@Test
			@DisplayName("When drink is dispensed, with no sufficient inventory, display a message with defined format with no trail white spaces")
			void testDrinkFaildToDispensedMessageWithDefinedFormat() {
				machine.execute(selectDrinkNumber6Command);
				machine.execute(selectDrinkNumber6Command);
				machine.execute(selectDrinkNumber6Command);
				
				StringBuilder response = machine.execute(selectDrinkNumber6Command);
				
				assertTrue(response.toString().contains("Out of stock: Decaf Coffee"));
				assertTrue(response.indexOf("Out of stock: Decaf Coffee") == 0);
				assertTrue(response.indexOf(INVENTORY_MENU_TITLE) == "Out of stock: Decaf Coffee".length() + 2);
				assertTrue(response.toString().contains("6,Decaf Coffee,$2.75,false"));
			}
		}
		
		@Test
		@DisplayName("When restore/restock the machine, each ingredient should restored to a maximum of 10 units")
		void testRestoreMachine() {
			String restockCommand = "R";	
			machine.execute(selectDrinkNumber6Command);
			StringBuilder response = machine.execute(restockCommand);
						
			for (String string : inventoryMenuLinesAtInitArray) {
				assertTrue(response.toString().contains(string));
			}
		}
	}
	
	
	@Test
	@DisplayName("When the user enters an invalid command, then the program should display a message with defined format with no trail white spaces")
	void testWhenUserEntersInvalidCommand() {
		String selectDrinkNumber7Command = "7";
		StringBuilder response = machine.execute(selectDrinkNumber7Command);
		assertTrue(response.toString().contains("Invalid selection: 7"));
		assertTrue(response.indexOf("Invalid selection: 7") == 0);
		assertTrue(response.indexOf(INVENTORY_MENU_TITLE) == "Invalid selection: 7".length() + 2);
		
		
		String selectDrinkNumberUCommand = "U";
		response = machine.execute(selectDrinkNumberUCommand);		
		assertTrue(response.toString().contains("Invalid selection: U"));
		assertTrue(response.indexOf("Invalid selection: U") == 0);
		assertTrue(response.indexOf(INVENTORY_MENU_TITLE) == "Invalid selection: U".length() + 2);
	}
	
}

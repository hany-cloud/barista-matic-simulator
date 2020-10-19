package net.hka.barista;

import java.text.NumberFormat;
import java.util.Scanner;

import net.hka.barista.exception.InvalidDrinkNumberException;
import net.hka.barista.exception.OutOfStockException;
import net.hka.barista.model.Drink;
import net.hka.barista.model.Ingredient;
import net.hka.barista.service.DrinkDataService;
import net.hka.barista.service.DrinkDataServiceImpl;
import net.hka.barista.service.IngredientDataService;
import net.hka.barista.service.IngredientDataServiceImpl;
import net.hka.barista.service.InventoryService;
import net.hka.barista.service.InventoryServiceImpl;

/**
 * Concrete class implementation of Barista-Matic machine.
 * 
 * @author Hany Kamal
 */
public class BaristaMachineImpl implements BaristaMachine {

	private final static String INVENTORY_MENU_TITLE = "Inventory:";

	private final static String DRINK_MENU_TITLE = "Menu:";

	private final static char LINE_SEPERATED_CHAR = ',';

	private final static String NEW_LINE = System.lineSeparator();

	private final InventoryService inventoryService;

	private final DrinkDataService drinkDataService;

	private final IngredientDataService ingredientDataService;

	public BaristaMachineImpl() {
		super();

		inventoryService = new InventoryServiceImpl();
		drinkDataService = new DrinkDataServiceImpl();
		ingredientDataService = new IngredientDataServiceImpl();

		restock();
	}

	@Override
	public void startUp(final Scanner scanner) {
		if(scanner == null) throw new IllegalArgumentException("The provided scanner is null!");
		System.out.println(this.buildView());

		String command = "";
		do {
			command = scanner.nextLine().trim();
			StringBuilder response = execute(command);
			renderView(response);
		} while (!command.equalsIgnoreCase("q"));

		scanner.close();
	}
	
	@Override
	public StringBuilder execute(final String menuOption) {
		if(menuOption.isEmpty()) throw new IllegalArgumentException("The provided menuOption is null!");
		StringBuilder response = new StringBuilder();
		switch (menuOption) {
		case "":
			break; // ignore white space
			
		case "r":
		case "R": // restore inventory
			this.restock();
			response = this.buildView();
			
		case "q":
		case "Q": // quit application
			break;
			
		default: // dispense the drink if available, and handle wrong commands
			response = this.dispense(menuOption);
			response.append(NEW_LINE);
			response.append(this.buildView());
		}

		return response;
	}

	// Restore the ingredients units in inventory at machine start up or upon user
	// request.
	private void restock() {
		inventoryService.restore();
	}

	// Dispense the drink if available, maintain ingredient amount in inventory,
	// and handle wrong commands.
	private StringBuilder dispense(final String drinkNumberOption) {
		StringBuilder response = new StringBuilder();
		String drinkName = "";
		try {
			Drink drink = drinkDataService.getDrinkByDrinkOption(drinkNumberOption); // throws
																						// InvalidDrinkNumberException
			drinkName = drink.getDrinkName();
			inventoryService.deduct(drink.getDrinkIngredients()); // throws OutOfStockException
			response.append(String.format("%s: %s", "Dispensing", drinkName));
		} catch (InvalidDrinkNumberException e) {
			response.append(String.format("%s: %s", "Invalid selection", drinkNumberOption));
		} catch (OutOfStockException e) {
			response.append(String.format("%s: %s", "Out of stock", drinkName));
		}

		return response;
	}

	// Build view for user interaction capability.
	private StringBuilder buildView() {
		StringBuilder response = new StringBuilder(INVENTORY_MENU_TITLE)
				.append(NEW_LINE)
				.append(buildInventoryMenu())
				.append(NEW_LINE)
				.append(DRINK_MENU_TITLE)
				.append(NEW_LINE)
				.append(buildDrinkMenu());
		return response;
	}

	// Build inventory menu to show ingredients availability in inventory
	private StringBuilder buildInventoryMenu() {
		StringBuilder response = new StringBuilder();
		String newline = "";
		for (Ingredient ingredient : ingredientDataService.listSortedIngredients()) {
			int unitsAvailable = inventoryService.getIngredientUnitBalance().get(ingredient);
			response.append(newline)
					.append(ingredient.getIngredientName())
					.append(LINE_SEPERATED_CHAR)
					.append(unitsAvailable);

			newline = NEW_LINE;
		}

		return response;
	}

	// Build drink menu to show available drinks options, price, and availability for each drink 
	private StringBuilder buildDrinkMenu() {
		StringBuilder response = new StringBuilder();
		int drinkNumber = 1;
		String newline = "";
		for (Drink drink : drinkDataService.listSortedDrinks()) {
			boolean isDrinkAvailable = inventoryService.canApplyDeduct(drink.getDrinkIngredients());

			response.append(newline).append(String.valueOf(drinkNumber++))
					.append(LINE_SEPERATED_CHAR)
					.append(drink.getDrinkName())
					.append(LINE_SEPERATED_CHAR)
					.append(NumberFormat.getCurrencyInstance().format(drink.getTotalPrice()))
					.append(LINE_SEPERATED_CHAR)
					.append(isDrinkAvailable);

			newline = NEW_LINE;
		}

		return response;
	}

	// Render the view for user
	private void renderView(StringBuilder response) {
		System.out.println(response);
	}
}

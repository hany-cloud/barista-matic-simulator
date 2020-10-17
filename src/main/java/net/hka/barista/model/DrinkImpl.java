package net.hka.barista.model;

import java.util.Map;

/**
 * Concrete class implementation for drinks that are provided by Barista-Matic
 * machine.
 * 
 * @author Hany Kamal
 *
 */
public class DrinkImpl implements Drink {

	private final String drinkName;
	private final Map<Ingredient, Integer> drinkIngredients;
	private double totalPrice;

	public DrinkImpl(String drinkName, Map<Ingredient, Integer> drinkIngredients) {
		super();
		this.drinkName = drinkName;
		this.drinkIngredients = drinkIngredients;
		this.calculateTotalPrice();
	}

	@Override
	public String getDrinkName() {
		return drinkName;
	}

	@Override
	public Map<Ingredient, Integer> getDrinkIngredients() {
		return drinkIngredients;
	}

	@Override
	public double getTotalPrice() {
		return totalPrice;
	}

	// calculate total price for the drink using the unit price of each ingredient
	// and ingredient's required units.
	private void calculateTotalPrice() {
		getDrinkIngredients().forEach((ingredient, unitsRequired) -> {
			totalPrice += ingredient.getUnitPrice() * unitsRequired;
		});
	}

}

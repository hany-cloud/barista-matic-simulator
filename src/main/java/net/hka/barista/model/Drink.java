package net.hka.barista.model;

import java.util.Map;

/**
 * Declare public API for drinks that are provided by Barista-Matic machine.
 * 
 * @author Hany Kamal
 */
public interface Drink {

	/**
	 * Return drink name.
	 * 
	 * @return drink name
	 */
	String getDrinkName();

	/**
	 * Return ingredients along with the units that are required to make the drink.
	 * 
	 * @return
	 */
	Map<Ingredient, Integer> getDrinkIngredients();

	/**
	 * Return the total cost price for the drink, that is calculated from the unit
	 * price of each ingredient of the drink.
	 * 
	 * @return total cost price for drink
	 */
	double getTotalPrice();
}

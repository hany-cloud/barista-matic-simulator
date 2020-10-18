package net.hka.barista.service;

import java.util.Collection;
import java.util.Map;

import net.hka.barista.model.Ingredient;

/**
 * Provide all necessary data structures that are required for preparing
 * ingredients that go into making drinks through the Ingredient model.
 * 
 * @author Hany Kamal
 *
 */
public interface IngredientDataService {

	/**
	 * List all ingredients sorted by name.
	 * 
	 * @return collection of drinks provided by machine
	 */
	Collection<Ingredient> listSortedIngredients();
	
	/**
	 * List all ingredients.
	 * 
	 * @return collection of drinks provided by machine
	 */
	Collection<Ingredient> listIngredients();

	/**
	 * Populate ingredients along with required units that are going into making
	 * each drink, using provided drink name.
	 * 
	 * @param drinkName provided drink name, that is used to populate its
	 *                  ingredients and required units
	 * @return ingredients along with required units that are going into making
	 *         drink
	 */
	Map<Ingredient, Integer> populateIngredientUnitsByDrink(final String drinkName);

}

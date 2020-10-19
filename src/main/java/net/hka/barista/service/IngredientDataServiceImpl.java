package net.hka.barista.service;

import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

import net.hka.barista.model.Ingredient;
import net.hka.barista.model.IngredientImpl;

/**
 * Concrete implementation for IngredientDataService interface.
 * 
 * @author Hany Kamal
 *
 */
public class IngredientDataServiceImpl implements IngredientDataService {

	private final static int COFFEE_UNITS = 3;
	private final static int LATTE_ESPRESSO_UNITS = 2;
	private final static int AMERICANO_ESPRESSO_UNITS = 3;
	private final static int CAPPUCCINO_ESPRESSO_UNITS = 2;
	private final static int ONE_UNIT = 1;

	// List of all ingredients.
	private static Map<IngredientRefName, Ingredient> ingredients;

	// Populate the ingredients list on loading time
	static {
		ingredients = populateAllIngredients();
	}

	// Populate all ingredients and ingredients' unit prices sorted by ingredient
	// name.
	private static Map<IngredientRefName, Ingredient> populateAllIngredients() {
		// Preferred EnumMap over TreeMap because it's faster and the order is
		// maintained in which the enumerator constants are declared.
		Map<IngredientRefName, Ingredient> ingredients = new EnumMap<>(IngredientRefName.class);

		for (IngredientRefName ingredientRefName : IngredientRefName.values()) {
			ingredients.put(ingredientRefName,
					new IngredientImpl(ingredientRefName.getRefName(), ingredientRefName.getUnitPrice()));
		}

		return ingredients;
	}

	// Return all available ingredients along with its price per unit.
	private Map<IngredientRefName, Ingredient> getIngredients() {
		return ingredients;
	}

	@Override
	public Collection<Ingredient> listSortedIngredients() {
		return ingredients.values();
	}
	
	@Override
	public Collection<Ingredient> listIngredients() {
		return listSortedIngredients();
	}

	// Preferred to use String drinkName as a parameter over DrinklRefName
	// enumerator, so this method can be easily updated or replaced  in case of
	// using a different data-source such as a database.
	@Override
	public Map<Ingredient, Integer> populateIngredientUnitsByDrink(final String drinkName) {
		if(drinkName.isEmpty()) throw new IllegalArgumentException("The provided drinkName is null or empty!");
		Map<Ingredient, Integer> ingredientUnits = new HashMap<>();
		DrinklRefName drinklRefName = DrinklRefName.get(drinkName);
		if(drinklRefName == null) throw new IllegalArgumentException("The provided drinkName is not legal!");
		switch (drinklRefName) {
		case CAFFE_AMERICANO:
			ingredientUnits.put(getIngredients().get(IngredientRefName.ESPRESSO), AMERICANO_ESPRESSO_UNITS);
			break;

		case CAFFE_LATTE:
			ingredientUnits.put(getIngredients().get(IngredientRefName.ESPRESSO), LATTE_ESPRESSO_UNITS);
			ingredientUnits.put(getIngredients().get(IngredientRefName.STEAMED_MILK), ONE_UNIT);
			break;

		case CAFFE_MOCHA:
			ingredientUnits.put(getIngredients().get(IngredientRefName.ESPRESSO), ONE_UNIT);
			ingredientUnits.put(getIngredients().get(IngredientRefName.COCOA), ONE_UNIT);
			ingredientUnits.put(getIngredients().get(IngredientRefName.STEAMED_MILK), ONE_UNIT);
			ingredientUnits.put(getIngredients().get(IngredientRefName.WHIPPED_CREAM), ONE_UNIT);
			break;

		case CAPPUCCINO:
			ingredientUnits.put(getIngredients().get(IngredientRefName.ESPRESSO), CAPPUCCINO_ESPRESSO_UNITS);
			ingredientUnits.put(getIngredients().get(IngredientRefName.STEAMED_MILK), ONE_UNIT);
			ingredientUnits.put(getIngredients().get(IngredientRefName.FOAMED_MILK), ONE_UNIT);
			break;

		case COFFEE:
			ingredientUnits.put(getIngredients().get(IngredientRefName.COFFE), COFFEE_UNITS);
			ingredientUnits.put(getIngredients().get(IngredientRefName.SUGAR), ONE_UNIT);
			ingredientUnits.put(getIngredients().get(IngredientRefName.CREAM), ONE_UNIT);
			break;

		case DECAFE_CAFFE:
			ingredientUnits.put(getIngredients().get(IngredientRefName.DECAF_COFFE), COFFEE_UNITS);
			ingredientUnits.put(getIngredients().get(IngredientRefName.SUGAR), ONE_UNIT);
			ingredientUnits.put(getIngredients().get(IngredientRefName.CREAM), ONE_UNIT);
			break;
		}

		return ingredientUnits;
	}

}

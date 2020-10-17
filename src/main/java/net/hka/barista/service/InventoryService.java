package net.hka.barista.service;

import java.util.Map;

import net.hka.barista.exception.OutOfStockException;
import net.hka.barista.model.Ingredient;

/**
 * Declare all operations that are need to be done on the inventory to keep
 * tracking the ingredients amounts and to determine its availability.
 * 
 * @author Hany Kamal
 *
 */
public interface InventoryService {
	int RESTORE_UNIT_VALUE = 10;

	/**
	 * Restore the ingredients units in inventory at startup of the machine or upon.
	 * user request.
	 */
	void restore();

	/**
	 * Apply all deduction required from inventory for ingredients that are included
	 * in drink.
	 * 
	 * @param ingredients list of ingredients along with its units that are need to
	 *                    be deducted from inventory
	 * @throws OutOfStockException throw this exception in case of the one or more
	 *                             of provided unit is larger for certain ingredient
	 *                             is larger than its balance in inventory
	 */
	void deduct(final Map<Ingredient, Integer> ingredients) throws OutOfStockException;

	/**
	 * Check if the inventory has a sufficient stock for the provided ingredients.
	 * 
	 * @param ingredients list of ingredients along with its units that are need to
	 *                    be deducted from inventory
	 * @return boolean indicating whether the inventory has sufficient balances
	 */
	boolean canApplyDeduct(final Map<Ingredient, Integer> ingredients);

	/**
	 * Return the balance for each ingredient in a map data structure.
	 * 
	 * @return balance for each ingredient in the inventory
	 */
	Map<Ingredient, Integer> getIngredientUnitBalance();

}

package net.hka.barista.service;

import java.util.Collection;

import net.hka.barista.exception.InvalidDrinkNumberException;
import net.hka.barista.model.Drink;

/**
 * Provide all necessary data structures that are required for creating
 * the drink through the Drink model.
 * 
 * @author Hany Kamal
 *
 */
public interface DrinkDataService {

	/**
	 * List all drinks sorted by name.
	 * 
	 * @return collection of drinks provided by machine
	 */
	Collection<Drink> listSortedDrinks();

	/**
	 * Return the drink for provided drink number.
	 * 
	 * @param drinkNumberOption string representing the entered drink option by user
	 * @return the corresponding drink for the provided drink number
	 * @throws InvalidDrinkNumberException throw an exception if the entered drink
	 *                                     option is out of available drinks range
	 *                                     or not presenting a number
	 */
	Drink getDrinkByDrinkOption(final String drinkNumberOption) throws InvalidDrinkNumberException;

}

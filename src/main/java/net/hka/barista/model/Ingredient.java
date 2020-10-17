package net.hka.barista.model;

/**
 * Declare public API for ingredients that go into making the drink.
 * 
 * @author Hany Kamal
 */
public interface Ingredient {

	/**
	 * Return ingredient name.
	 * 
	 * @return ingredient name
	 */
	String getIngredientName();

	/**
	 * Return ingredient unit price.
	 * 
	 * @return ingredient unit price
	 */
	double getUnitPrice();
}

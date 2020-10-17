package net.hka.barista.model;

/**
 * Concrete class implementation for ingredients that go into making the drink.
 * 
 * @author Hany Kamal
 *
 */
public class IngredientImpl implements Ingredient {

	private final String ingredientName;
	private final double unitPrice;

	public IngredientImpl(String ingredientName, double unitPrice) {
		super();
		this.ingredientName = ingredientName;
		this.unitPrice = unitPrice;
	}

	@Override
	public String getIngredientName() {
		return ingredientName;
	}

	@Override
	public double getUnitPrice() {
		return unitPrice;
	}

}

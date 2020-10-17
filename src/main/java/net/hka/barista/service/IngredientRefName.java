package net.hka.barista.service;

/**
 * Ingredients available that go into making the drink.
 * 
 * @author Hany Kamal
 */
enum IngredientRefName {

	COCOA("Cocoa", 0.90), 
	COFFE("Coffee", 0.75), 
	CREAM("Cream", 0.25), 
	DECAF_COFFE("Decaf Coffee", 0.75),
	ESPRESSO("Espresso", 1.10), 
	FOAMED_MILK("Foamed Milk", 0.35), 
	STEAMED_MILK("Steamed Milk", 0.35),
	SUGAR("Sugar", 0.25), 
	WHIPPED_CREAM("Whipped Cream", 1.00);

	private String name;

	private double unitPrice;

	private IngredientRefName(final String name, final double unitPrice) {
		this.name = name;
		this.unitPrice = unitPrice;
	}

	/**
	 * Get ingredient reference name.
	 * 
	 * @return ingredient reference name that is corresponding to each enumerator constant
	 */
	public String getRefName() {
		return name;
	}

	/**
	 * Get ingredient unit price.
	 * 
	 * @return ingredient unit price that is corresponding to each enumerator constant
	 */
	public double getUnitPrice() {
		return unitPrice;
	}
}

package net.hka.barista.service;

import java.util.HashMap;
import java.util.Map;

/**
 * Drink supported by Barista-Matic machine.
 * 
 * @author Hany Kamal
 * 
 */
enum DrinklRefName {

	CAFFE_AMERICANO("Caffe Americano"), 
	CAFFE_LATTE("Caffe Latte"), 
	CAFFE_MOCHA("Caffe Mocha"),
	CAPPUCCINO("Cappuccino"), 
	COFFEE("Coffee"), 
	DECAFE_CAFFE("Decaf Coffee");

	// Lookup table used to get Enum constant from drink String name
	private static final Map<String, DrinklRefName> lookup = new HashMap<>();

	// Populate the lookup table on loading time
	static {
		for (DrinklRefName drinkRefName : DrinklRefName.values()) {
			lookup.put(drinkRefName.getRefName(), drinkRefName);
		}
	}

	private String name;

	private DrinklRefName(final String name) {
		this.name = name;
	}

	/**
	 * Get drink reference name.
	 * 
	 * @return drink reference name that is corresponding to each enumerator constant
	 */
	public String getRefName() {
		return name;
	}

	/**
	 * This method can be used for reverse lookup purpose.
	 * 
	 * @param drinkName drink string name
	 * @return an Enum constant corresponding to drink string name
	 */
	public static DrinklRefName get(String drinkName) {
		return lookup.get(drinkName);
	}
}

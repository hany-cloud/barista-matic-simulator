package net.hka.barista.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import net.hka.barista.exception.InvalidDrinkNumberException;
import net.hka.barista.model.Drink;
import net.hka.barista.model.DrinkImpl;

/**
 * Concrete implementation for DrinkDataService interface.
 * 
 * @author Hany Kamal
 *
 */
public class DrinkDataServiceImpl implements DrinkDataService {

	private final IngredientDataService ingredientDataService;

	private final List<Drink> drinks;

	public DrinkDataServiceImpl() {
		super();
		ingredientDataService = new IngredientDataServiceImpl();
		drinks = populateAllDrinks();
	}

	// Populate list of all drinks sorted by drink name.
	private List<Drink> populateAllDrinks() {
		List<Drink> drinks = new ArrayList<>();
		for (DrinklRefName drinkRefName : DrinklRefName.values()) {
			String drinkName = drinkRefName.getRefName();
			Drink drink = new DrinkImpl(drinkName, ingredientDataService.populateIngredientUnitsByDrink(drinkName));
			drinks.add(drink);
		}

		Comparator<Drink> byName = (Drink o1, Drink o2) -> o1.getDrinkName().compareTo(o2.getDrinkName());
		Collections.sort(drinks, byName);

		return drinks;
	}

	@Override
	public List<Drink> listSortedDrinks() {
		return drinks;
	}

	@Override
	public Drink getDrinkByDrinkOption(final String drinkNumberOption) throws InvalidDrinkNumberException {
		Integer drinkNumber = Optional.ofNullable(drinkNumberOption)
				.filter(str -> str.matches("-?\\d+"))
				.map(Integer::parseInt)
				.filter(num -> num > 0 && num - 1 < listSortedDrinks().size())
				.orElseThrow(InvalidDrinkNumberException::new);

		return listSortedDrinks().get(drinkNumber - 1);
	}
	
	public static void main(String[] args) {
		try {
			new DrinkDataServiceImpl().getDrinkByDrinkOption(null);
		} catch (InvalidDrinkNumberException e) {
			System.out.println("error");
			e.printStackTrace();
		}
	}

}

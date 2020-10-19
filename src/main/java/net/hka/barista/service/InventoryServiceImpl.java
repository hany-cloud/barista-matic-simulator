package net.hka.barista.service;

import java.util.HashMap;
import java.util.Map;

import net.hka.barista.exception.OutOfStockException;
import net.hka.barista.model.Ingredient;

/**
 * Concrete implementation for InventoryService interface
 * 
 * @author Hany Kamal
 *
 */
public class InventoryServiceImpl implements InventoryService {

	private final IngredientDataService ingredientDataService;
	
	private final Map<Ingredient, Integer> ingredientUnitBalance;

	public InventoryServiceImpl() {
		super();
		ingredientDataService = new IngredientDataServiceImpl();
		this.ingredientUnitBalance = new HashMap<>();
	}

	@Override
	public void restore() {
		ingredientDataService.listIngredients()
				.forEach((ingredient) -> ingredientUnitBalance.put(ingredient, RESTORE_UNIT_VALUE));
	}

	@Override
	public void deduct(final Map<Ingredient, Integer> ingredients) throws OutOfStockException {
		if(ingredients == null || ingredients.isEmpty()) throw new IllegalArgumentException("The provided ingredients is null or empty!");
		if (!canApplyDeduct(ingredients))
			throw new OutOfStockException();
		ingredients.forEach((ingredient, deductingUnit) -> {
			int unitBalance = ingredientUnitBalance.get(ingredient);
			ingredientUnitBalance.put(ingredient, unitBalance - deductingUnit);
		});
	}

	@Override
	public boolean canApplyDeduct(final Map<Ingredient, Integer> ingredients) {
		if(ingredients == null || ingredients.isEmpty()) throw new IllegalArgumentException("The provided ingredients is null or empty!");
		for (Map.Entry<Ingredient, Integer> entry : ingredients.entrySet()) {
			Ingredient ingredient = entry.getKey();
			int deductingUnit = entry.getValue();
			int unitBalance = ingredientUnitBalance.get(ingredient);
			if (unitBalance < deductingUnit)
				return false;
		}
		return true;
	}

	@Override
	public Map<Ingredient, Integer> getIngredientUnitBalance() {
		return ingredientUnitBalance;
	}

}

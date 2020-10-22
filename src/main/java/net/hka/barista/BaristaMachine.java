package net.hka.barista;

import java.util.Scanner;

/**
 * Declare public API for Barista-Matic machine.
 * 
 * @author Hany Kamal
 */
public interface BaristaMachine {

	/**
	 * Start up machine, set the ingredient inventory stock amount, and print the
	 * response onto console.
	 * 
	 * @param scanner parsing user entry commands
	 */
	void startUp(final Scanner uiHandler);

	/**
	 * Execut the corresponding action for provided menu option and return the
	 * appropriate response after action performed.
	 * 
	 * @param menuOption the option that is entered by user, determining which
	 *                   action to perform
	 * @return the appropriate response after action performed
	 */
	StringBuilder execute(final String menuOption);

}

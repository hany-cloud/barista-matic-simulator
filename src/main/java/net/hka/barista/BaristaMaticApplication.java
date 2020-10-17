package net.hka.barista;

import java.util.Scanner;

/**
 * The entry point for Barista-Matic machine application.
 * 
 * @author Hany Kamal
 *
 */
public class BaristaMaticApplication {

	/**
	 * Entry point main method.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		BaristaMachine machine = new BaristaMachineImpl();
		Scanner scanner = new Scanner(System.in);
		machine.startUp(scanner);
	}

}

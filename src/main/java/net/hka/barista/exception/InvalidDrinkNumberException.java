package net.hka.barista.exception;

import java.io.IOException;

/**
 * This exception is thrown when user enters an invalid command.
 * 
 * @author Hany Kamal
 */
public class InvalidDrinkNumberException extends IOException {

	private static final long serialVersionUID = 1L;
	private String message;

	public InvalidDrinkNumberException() {
		this("");
	}

	public InvalidDrinkNumberException(String string) {
		this.message = string;
	}

	/**
	 * Return the equivalent message from the thrown exception.
	 */
	@Override
	public String getMessage() {
		return message;
	}
}

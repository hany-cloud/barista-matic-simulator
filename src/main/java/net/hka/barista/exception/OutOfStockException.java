package net.hka.barista.exception;

/**
 * This exception is thrown when machine doesn't have enough ingredients for
 * making the drink.
 * 
 * @author Hany Kamal
 *
 */
public class OutOfStockException extends Exception {

	private static final long serialVersionUID = 1L;
	private String message;

	public OutOfStockException() {
		this("");
	}

	public OutOfStockException(String string) {
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

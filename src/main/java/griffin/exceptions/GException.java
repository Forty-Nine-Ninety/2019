package griffin.exceptions;

/*

Note: this class shouldn't be extended unless the exception does not fall under one of Java's standard exceptions. A list of the most common exceptions has been provided below.

Unchecked Exception List
-ArrayIndexOutOfBoundsException
-ClassCastException
-IllegalArgumentException
-IllegalStateException
-NullPointerException
-NumberFormatException
-AssertionError
-ExceptionInInitializerError
-StackOverflowError
-NoClassDefFoundError

Checked Exception List
-Exception
-IOException
-FileNotFoundException
-ParseException
-ClassNotFoundException
-CloneNotSupportedException
-InstantiationException
-InterruptedException
-NoSuchMethodException
-NoSuchFieldException

*/

/**
 * Base class for exceptions
 * @author MajikalExplosions
 */
public class GException extends Exception {

	private static final long serialVersionUID = 4106337770811169205L;//Generated from the VS Code tooltip.
	
	public GException() {}
}

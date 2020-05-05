package comp3111.coursescraper;

public class ErrorHandling {
	static String ErrorMessage;
	
	/**
	* Set the error message to Invail URL when get error404
	*
	*/
	public static void NotFoundError404() {
		ErrorMessage = "Invail URL";
	}
	
	/**
	* Return the error message
	*
	* @return error message
	*/
	public static String getErrorMessage() {
		return ErrorMessage;
	}
	
}
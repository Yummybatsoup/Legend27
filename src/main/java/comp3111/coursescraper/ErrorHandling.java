package comp3111.coursescraper;

/**
* <h1>Error Handling</h1>
* This class is to do error handling by setting error message and get the message
* 
*/
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
	* Set the error message to Invail URL when get error404
	* @param s with Exception type
	*/
	public static void OtherError(Exception s) {
		ErrorMessage = s.toString();
	}
	
	/**
	* Return the error message
	* @return error message
	*/
	public static String getErrorMessage() {
		return ErrorMessage;
	}
	
}
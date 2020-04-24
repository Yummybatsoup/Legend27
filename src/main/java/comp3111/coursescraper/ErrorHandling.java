package comp3111.coursescraper;

public class ErrorHandling {
	static String ErrorMessage;
	
	public static void NotFoundError404() {
		ErrorMessage = "Invail URL";
	}
	
	public static String getErrorMessage() {
		return ErrorMessage;
	}
	
}
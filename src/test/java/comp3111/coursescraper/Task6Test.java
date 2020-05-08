package comp3111.coursescraper;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Task6Test extends ApplicationTest {
	
	private Scene s;
	
	@Override
	public void start(Stage stage) throws Exception {
    	FXMLLoader loader = new FXMLLoader();
    	loader.setLocation(getClass().getResource("/ui.fxml"));
   		VBox root = (VBox) loader.load();
   		Scene scene =  new Scene(root);
   		stage.setScene(scene);
   		stage.setTitle("Course Scraper");
   		stage.show();
   		s = scene;
	}
	
	@Test
	public void testbuttonSfqEnrollCourseEnabledBySearch() {
		clickOn("#tabMain");
		TextField url = (TextField) s.lookup("#textfieldURL");
		url.setText("http://w5.ab.ust.hk/wcq/cgi-bin/123");
		TextField term = (TextField) s.lookup("#textfieldTerm");
		term.setText("1910");
		TextField subject = (TextField) s.lookup("#textfieldSubject");
		subject.setText("COMP");
		clickOn("#buttonSearch");
		
		clickOn("#tabSfq");
		Button buttonSfqEnrollCourse =(Button) s.lookup("#buttonSfqEnrollCourse");
		assertTrue(buttonSfqEnrollCourse.isDisable() == false);
	}

	@Test
	public void testbuttonSfqEnrollCourseEnabledByAllSubjectSearch() {
		clickOn("#tabAllSubject");
		clickOn("#buttonAllSubjectSeacrch");
		clickOn("#tabSfq");
		Button buttonSfqEnrollCourse =(Button) s.lookup("#buttonSfqEnrollCourse");
		
		assertTrue(buttonSfqEnrollCourse.isDisable() == false);
	}
	
	
	@Test
	public void buttonInstructorSfqWithErrorUrl() {
		clickOn("#tabSfq");
		clickOn("#buttonInstructorSfq");
		TextArea console=(TextArea) s.lookup("#textAreaConsole");
		assertTrue(console.getText().equals("java.lang.IndexOutOfBoundsException: Index 0 out of bounds for length 0"));
	}
	
	@Test
	public void buttonInstructorSfq(){
		clickOn("#tabSfq");
		TextField url=(TextField) s.lookup("#textfieldSfqUrl");
		url.setText("http://thliuab.student.ust.hk/comp3111");
		clickOn("#buttonInstructorSfq");
		TextArea console=(TextArea) s.lookup("#textAreaConsole");
		assertTrue(console.getText().isBlank()==false);
	}
	
	/*
	@Test
	public void testSfqEnrollCourse() throws Exception {
		// search
		clickOn("#tabMain");
		TextField url = (TextField) s.lookup("#textfieldURL");
		url.setText("http://w5.ab.ust.hk/wcq/cgi-bin/123");
		TextField term = (TextField) s.lookup("#textfieldTerm");
		term.setText("1910");
		TextField subject = (TextField) s.lookup("#textfieldSubject");
		subject.setText("COMP");
		clickOn("#buttonSearch");
		
		// filter AM
//		CheckBox AMcheckbox = (CheckBox) s.lookup("#checkboxAM");
		
		clickOn("#tabFilter");
		clickOn("#checkboxAM");
		clickOn("#tabList");
		s.wait();
		
		//enrollcourse
		
//		assertTrue();
	}
	
	*/


}

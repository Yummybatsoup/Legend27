package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Tab;

public class Task4Test extends ApplicationTest{

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
	public void Testenroll() {
		//get number of children at default, used to compare when updated
		clickOn("#tabTimetable");
		AnchorPane ap = (AnchorPane) s.lookup("#timepane");
		int num = ap.getChildren().size();
		
		clickOn("#tabMain");
		TextField url = (TextField) s.lookup("#textfieldURL");
		url.setText("http://w5.ab.ust.hk/wcq/cgi-bin/");
		TextField term = (TextField) s.lookup("#textfieldTerm");
		term.setText("1910");
		TextField subject = (TextField) s.lookup("#textfieldSubject");
		subject.setText("COMP");
		clickOn("#buttonSearch");
		
		//select random filter
		clickOn("#tabFilter");
		clickOn("#checkboxSat");
		
		//enroll a random course
		clickOn("#tabList");
		TableView<Section> table = (TableView<Section>) s.lookup("#table");
		Node enrol = from(table).lookup(".table-row-cell").nth(0).lookup(".table-cell").nth(4).lookup(".check-box:determinate").query();
		clickOn(enrol);
		
		//check table updated or not
		clickOn("#tabTimetable");
		assert(ap.getChildren().size() != num);
		
	}

}
package comp3111.coursescraper;

import static org.junit.Assert.*;

import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class Task3Test extends ApplicationTest {
	
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
	public void test() {
		TextField url = (TextField) s.lookup("#textfieldURL");
		url.setText("http://w5.ab.ust.hk/wcq/cgi-bin/");
		TextField term = (TextField) s.lookup("#textfieldTerm");
		term.setText("1910");
		TextField subject = (TextField) s.lookup("#textfieldSubject");
		subject.setText("COMP");
		clickOn("#buttonSearch");
		
		clickOn("#tabFilter");
		
		clickOn("#checkboxMon");
		clickOn("#tabList");
		
		TableView<Section> table = (TableView<Section>) s.lookup("#table");
		ObservableList<Section> enrolled_section = table.getItems();
		
		assert(enrolled_section.size() != 0);
	}


}

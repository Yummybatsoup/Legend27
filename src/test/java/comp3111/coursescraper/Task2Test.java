package comp3111.coursescraper;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Task2Test extends ApplicationTest{

	private Scene s;
	private TextArea console;
	
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
   		
   		TextField url = (TextField) s.lookup("#textfieldURL");
		url.setText("http://w5.ab.ust.hk/wcq/cgi-bin/");
		TextField term = (TextField) s.lookup("#textfieldTerm");
		term.setText("1910");
		TextField subject = (TextField) s.lookup("#textfieldSubject");
		subject.setText("COMP");
		clickOn("#buttonSearch");
		clickOn("#tabFilter");
	}

	@Test
	public void TestSelectallButton() {
		Button select = (Button) s.lookup("#buttonselect");
		//reset button to default mode
		if(!select.getText().equals("Select All"))
			clickOn("#buttonselect");
		
		clickOn("#buttonselect");
		assertEquals(select.getText(), "De-select All");
	}
	
	@Test
	public void testAM() {
		//reset all button to non-select mode
		Button select = (Button) s.lookup("#buttonselect");
		//set button to non-default mode first if otherwise
		if(select.getText().equals("Select All"))
			clickOn("#buttonselect");
		
		clickOn("#buttonselect");
		//reset all button to non-select mode(end)
		
		clickOn("#checkboxAM");
		console=(TextArea) s.lookup("#textAreaConsole");
		
		//test for sth should appear in console(should be true at the last)
		boolean testexist = false;
		//test for sth should not appear in console(should not be true at the last)
		boolean testnotexist = false;
		
		String lines[] = console.getText().split("\\r?\\n");
		for(int i = 0;i < lines.length;i++)
			if(lines[i].equals("COMP 4651 - Cloud Computing and Big Data Systems (3 units)")) {
				for(int k = i;k<lines.length;k++) {
					if(lines[k].contains("LA2"))
						testexist = true;
					if(lines[k].contains("L1"))
						testnotexist = true;
					else if(lines[k].trim().isEmpty())
						break;
				}
				break;
			}
		assert(testexist && !testnotexist);
		/*
		for(String s: lines)
			if (s.trim().isEmpty())
				System.out.println(s);
		assert(true);
		*/
	}
	
	@Test
	public void testMon() {
		//reset all button to non-select mode
		Button select = (Button) s.lookup("#buttonselect");
		//set button to non-default mode first if otherwise
		if(select.getText().equals("Select All"))
			clickOn("#buttonselect");
		
		clickOn("#buttonselect");
		//reset all button to non-select mode(end)
		
		clickOn("#checkboxMon");
		console=(TextArea) s.lookup("#textAreaConsole");
		
		//test for sth should appear in console(should be true at the last)
		boolean testexist = false;
		//test for sth should not appear in console(should not be true at the last)
		boolean testnotexist = false;
		
		String lines[] = console.getText().split("\\r?\\n");
		for(int i = 0;i < lines.length;i++)
			if(lines[i].equals("COMP 3111 - Software Engineering (4 units)")) {
				for(int k = i;k<lines.length;k++) {
					if(lines[k].contains("L1"))
						testexist = true;
					if(lines[k].contains("L2"))
						testnotexist = true;
					else if(lines[k].trim().isEmpty())
						break;
				}
				break;
			}
		assert(testexist && !testnotexist);
		/*
		for(String s: lines)
			if (s.trim().isEmpty())
				System.out.println(s);
		assert(true);
		*/
	}

}

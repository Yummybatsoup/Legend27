package comp3111.coursescraper;

import java.time.LocalTime;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
public class Controller {

    @FXML
    private Tab tabMain;

    @FXML
    private TextField textfieldTerm;

    @FXML
    private TextField textfieldSubject;

    @FXML
    private Button buttonSearch;

    @FXML
    private TextField textfieldURL;

    @FXML
    private Tab tabStatistic;

    @FXML
    private ComboBox<?> comboboxTimeSlot;

    @FXML
    private Tab tabFilter;

    @FXML
    private Tab tabList;

    @FXML
    private Tab tabTimetable;

    @FXML
    private Tab tabAllSubject;

    @FXML
    private ProgressBar progressbar;

    @FXML
    private TextField textfieldSfqUrl;

    @FXML
    private Button buttonSfqEnrollCourse;

    @FXML
    private Button buttonInstructorSfq;

    @FXML
    private TextArea textAreaConsole;
    
    @FXML
    private Button buttonselect;
    
    @FXML
    private CheckBox checkboxAM;

    @FXML
    private CheckBox checkboxPM;

    @FXML
    private CheckBox checkboxMon;

    @FXML
    private CheckBox checkboxTue;

    @FXML
    private CheckBox checkboxWed;

    @FXML
    private CheckBox checkboxThu;

    @FXML
    private CheckBox checkboxFri;

    @FXML
    private CheckBox checkboxSat;
    
    @FXML
    private CheckBox checkboxCom;

    @FXML
    private CheckBox checkboxExc;

    @FXML
    private CheckBox checkboxLab;
    
    @FXML
    void buttonselectall() {
    	if (buttonselect.getText().equals("Select All"))
    	{
    		buttonselect.setText("De-select All");
    		checkboxAM.setSelected(true);
    		checkboxPM.setSelected(true);
    		checkboxMon.setSelected(true);
    		checkboxTue.setSelected(true);
    		checkboxWed.setSelected(true);
    		checkboxThu.setSelected(true);
    		checkboxFri.setSelected(true);
    		checkboxSat.setSelected(true);
    		checkboxCom.setSelected(true);
    		checkboxExc.setSelected(true);
    		checkboxLab.setSelected(true);
    		search();
    	}
    	else
    	{
    		buttonselect.setText("Select All");
    		checkboxAM.setSelected(false);
    		checkboxPM.setSelected(false);
    		checkboxMon.setSelected(false);
    		checkboxTue.setSelected(false);
    		checkboxWed.setSelected(false);
    		checkboxThu.setSelected(false);
    		checkboxFri.setSelected(false);
    		checkboxSat.setSelected(false);
    		checkboxCom.setSelected(false);
    		checkboxExc.setSelected(false);
    		checkboxLab.setSelected(false);
    		search();
    	}
    }
    
    @FXML
    void filter() {
    	search();
    }
    
    private Scraper scraper = new Scraper();
    
    int TOTAL_NUMBER_OF_COURSES;
    int ALL_SUBJECT_COUNT;
    
    @FXML
    void allSubjectSearch() throws Exception {
    	// WebClient client = new WebClient();
    	String baseurl = textfieldURL.getText();
    	String term = textfieldTerm.getText();
    	String title = "";
    	String instructor = "";
    	String home = "ACCT";
    	    	
    	//Create a list containing all the subjects HtmlElement
		List<String> subjects = scraper.searchSubject(baseurl, term, home);
		
		ALL_SUBJECT_COUNT = subjects.size();
		
		double subject_scraped = 0;
		double progress = 0;
		
		System.out.println(ALL_SUBJECT_COUNT);
		
		// progressbar.progressProperty().bind(progress);
		
		for (String sub:(List<String>)subjects) {
			List<Course> courses = scraper.scrape(baseurl, term, sub);
			System.out.println(sub);
			
			textAreaConsole.setText(textAreaConsole.getText() + sub + " is done \n");
			
			TOTAL_NUMBER_OF_COURSES += courses.size();
			subject_scraped += 1;
			
			progress = subject_scraped / ALL_SUBJECT_COUNT;
			
			// progressbar.setProgress(progress);
			System.out.println(progressbar.getProgress());
		}
    	
    }

    @FXML
    void findInstructorSfq() {
    	buttonInstructorSfq.setDisable(true);
    }

    @FXML
    void findSfqEnrollCourse() {

    }

    @FXML
    void search() {

    	List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(),textfieldSubject.getText());
    	if (v ==null) 
    		textAreaConsole.setText(ErrorHandling.getErrorMessage());
    	else {
	   		int TotalNumOfCourses =0;
	    	int TotalNumOfSlots =0;
	    	LocalTime time =  LocalTime.of(15, 10, 00, 0000);
	    	ArrayList<String> InstructorArr = new ArrayList<String>();
	    	ArrayList<String> UnavInstructorArr = new ArrayList<String>();
	    	for (Course c : v) {
	    		TotalNumOfSlots += c.getNumSlots();
	    		if (c.getNumSlots() != 0) {
//    				System.out.println(c.getNumSlots());
	    			TotalNumOfCourses++;
	    		}
	    		
	    		for (int i = 0; i < c.getNumSections(); i++) {
	    			Section s = c.getSection(i);
	    			for (int j = 0; j < s.getNumSlots(); j++) {
					Slot t = s.getSlot(j);
					String instr = t.getInstructor();
					if (!instr.isEmpty()){
						String[] insplit = instr.split("\\r?\\n");
						for (int k =0; k<insplit.length ; ++k) {
//							System.out.print(j+ insplit[j]+" ");
							InstructorArr.add(insplit[k].trim());
							if((t.getDay() == 3) && (t.getStart().isBefore(time)) && (t.getEnd().isAfter(time)))
								UnavInstructorArr.add(insplit[k]);
							}
//						System.out.println();
						}
	    			}
	    		}
	    	}
    	
	    	//Make array sorted and  distinct
	     	List<String> DistinctInstructorArr = InstructorArr.stream().sorted().distinct().collect(Collectors.toList());
	     	List<String> DistinctUnavInstructorArr = UnavInstructorArr.stream().sorted().distinct().collect(Collectors.toList());
	     
	     	//Remove unavailable
	     	DistinctInstructorArr.removeAll(DistinctUnavInstructorArr);
	     	//Remove TBA
	     	DistinctInstructorArr.remove("TBA");
	
	    	//TextAreaConsole controller
	    	textAreaConsole.setText("Total Number of courses: " + TotalNumOfCourses ); 		
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Total Number of slots: " + TotalNumOfSlots );
	
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + 
					"Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm :");
			for (String i : DistinctInstructorArr) {
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + i);
			}
			

			boolean all_false[] = {false, false, false, false, false, false};
	    	for (Course c : v) {
	    		if (checkboxExc.isSelected())
	    			if (c.getExclusion() != "null")
	    				continue;
	    		if (checkboxCom.isSelected())
	    			if (!c.getCC())
	    				continue;
	    		if (checkboxLab.isSelected())
	    			if (!c.gethaslabortut())
	    				continue;
	    		boolean days[] = {checkboxMon.isSelected(), checkboxTue.isSelected(), checkboxWed.isSelected(), 
	    				checkboxThu.isSelected(), checkboxFri.isSelected(), checkboxSat.isSelected()};
	    		for (int i = 0; i < c.getNumSections(); i++) {
	    			Section s = c.getSection(i);
	    			for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
	    				int day = s.getSlot(j).getDay();
	    				days[day] = false;
	    			}
	    		}
	    		if (!Arrays.equals(all_false, days))
	    			continue;
	    		
	    		boolean valid = false;
	    		if (checkboxAM.isSelected() && checkboxPM.isSelected()){
	    			for (int i = 0; i < c.getNumSections(); i++) {
		    			Section s = c.getSection(i);
		    			boolean haveAM = false;
		    			boolean havePM = false;
		    			for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
		    				if(s.getSlot(j).getStartHour() < 12 && s.getSlot(j).getEndHour() >= 12)
		    					valid = true;
		    				if(s.getSlot(j).getEndHour() < 12)
		    					haveAM = true;
		    				if(s.getSlot(j).getEndHour() >= 12)
		    					havePM = true;
		    			}
		    			if(haveAM == true && havePM == true)
		    				valid = true;
	    			}
	    		}
	    		else if (checkboxAM.isSelected()) {
	    			for (int i = 0; i < c.getNumSections(); i++) {
		    			Section s = c.getSection(i);
		    			for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
		    				if(s.getSlot(j).getEndHour() < 12)
		    					valid = true;
		    			}
	    			}
	    		}
	    		else if (checkboxPM.isSelected()) {
	    			for (int i = 0; i < c.getNumSections(); i++) {
		    			Section s = c.getSection(i);
		    			for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
		    				if(s.getSlot(j).getStartHour() >= 12)
		    					valid = true;
		    			}
	    			}
	    		}
	    		else
	    			valid = true;
	    		
	    		if(!valid)
	    			continue;
	    		
	    			
			textAreaConsole.setText(textAreaConsole.getText() + "\n");
			
//	    	for (Course c : v) {
//	    		System.out.println(c.getNumSections()+"  "+ c.getNumSlots());
//	    		if (c.getNumSlots() == 0)
//	    			continue;
	    		
	    		String newline = c.getTitle() + "\n";
	    		for (int i = 0; i < c.getNumSections(); i++) {
	    			Section s = c.getSection(i);
	    			for (int j = 0; j < s.getNumSlots(); j++) {
	    				Slot t = s.getSlot(j);
	    				newline += "Section:" + s.getTitle() + " Slot " + (j+1) + ":" + t + "\n";
	    			}
	    		}
	    		textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
	    		}
    	}
    }
    
	public void timetable(List<Course> courses) {
    	AnchorPane ap = (AnchorPane)tabTimetable.getContent();
    	for(Course c: courses) {
    	Slot s = c.getSection(0).getSlot(0);
    	Label randomLabel = new Label(c.getTitle());
    	double startY = (s.getStartHour()) * 20 + 40 + s.getStartMinute()*10/30;
    	double startX = s.getDay()*100;
    	randomLabel.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    	randomLabel.setLayoutX(startX);
    	randomLabel.setLayoutY(startY);
    	randomLabel.setMinWidth(100.0);
    	randomLabel.setMaxWidth(100.0);
    	randomLabel.setMinHeight(60);
    	randomLabel.setMaxHeight(60);
    
    	ap.getChildren().addAll(randomLabel);
    	}
    }
    
	public boolean equals(String str) {
		if (str == null) 
			return false;
		if (this.equals(str))
	        return true;
		else 
	        return false;
    }
    
    

}

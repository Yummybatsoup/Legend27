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
import javafx.util.Callback;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Arrays;
import java.util.Vector;

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
	private TableView<Section> table;

	@FXML
	private TableColumn<Section, String> courseCodeColumn;

	@FXML
	private TableColumn<Section, String> sectionColumn;

	@FXML
	private TableColumn<Section, String> nameColumn;

	@FXML
	private TableColumn<Section, String> instructorColumn;

	@FXML
	private TableColumn<Section, Boolean> enrolColumn;

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

	private Scraper scraper = new Scraper();

	// keep track of what courses we have scraped
	private List<Course> course_scraped = new Vector<Course>();

	// keep track of all the courses that filtered, will updated after every
	// filtering
	private List<Course> course_filter = new Vector<Course>();

	// keep track of all the sections that filtered, will updated after every
	// filtering
	private List<Section> section_filter = new Vector<Section>();

	// keep track of all the sections that are enrolled in
	private List<Section> section_enrolled = new Vector<Section>();

	int TOTAL_NUMBER_OF_COURSES = 0; // for task 5
	int ALL_SUBJECT_COUNT = 0; // for task 5
	
	boolean search_all_subject_clicked = false;

	/**
	 * Control anything needed to be initialized when the controller is constructed.
	 */
	@FXML
	public void initialize() {

		// For task 3 tableView initialization
		courseCodeColumn.setCellValueFactory(new PropertyValueFactory<>("CourseCode"));
		sectionColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
		nameColumn.setCellValueFactory(new PropertyValueFactory<>("CourseName"));
		instructorColumn.setCellValueFactory(new PropertyValueFactory<>("instructor"));

		// the check box column
		enrolColumn.setCellValueFactory(c -> {
			Section section = c.getValue();
			CheckBox checkBox = new CheckBox();
			checkBox.selectedProperty().setValue(section.isEnrolled());

			// add the listener: a function triggered whenever the check box is clicked
			checkBox.selectedProperty().addListener((ov, old_val, new_val) -> {
				section.setEnrolled(new_val);
				// System.out.println(section.getTitle() + " " + section.getCourseName() + " " +
				// section.isEnrolled());

				if (section.isEnrolled())
					section_enrolled.add(section);
				else
					section_enrolled.remove(section);

				// task4
				timetable(section_enrolled);

				textAreaConsole.setText("The following sections are enrolled: " + "\n");
				
				for (int i = 0; i < section_enrolled.size(); i++) {
					textAreaConsole.setText(textAreaConsole.getText() + "\n" + 
							section_enrolled.get(i).getCourseCode() + " " + 
							section_enrolled.get(i).getCourseName() + " " + 
							section_enrolled.get(i).getTitle()
							);
				}
			});
			return new SimpleObjectProperty(checkBox);
		});

		table.setItems(getEnrolSection(true));
		table.setEditable(true);

		// task 5 initialize
		progressbar.setProgress(0.0);

		// task 6 initialize
		buttonSfqEnrollCourse.setDisable(true);
	}

	/**
	 *  Create a list of sections that we want to display in the table
	 * @param filtered If yes: we construct our list according to the filter result. If no: we construct the list from the list of scraped course
	 * @return a list of sections to be displayed in the table
	 */
	public ObservableList<Section> getEnrolSection(boolean filtered) {
		ObservableList<Section> sections = FXCollections.observableArrayList();
		
		System.out.println("getEnrolSection called");
		
		if (filtered) {
			for (Section s : section_filter) {
				sections.add(s);
			}
		}else {
			for (Course course : course_scraped) {
				for (int i = 0; i < course.getNumSections(); i++) {
					
					boolean enrolled = false;
					
					// check if the section is already enrolled
					for (int j = 0; j < section_enrolled.size(); j ++) {
						
						if (course.getSection(i).getTitle().equals(section_enrolled.get(j).getTitle())
								&& course.getSection(i).getCourseName().equals(section_enrolled.get(j).getCourseName())) {
							sections.add(section_enrolled.get(j));
							System.out.println(section_enrolled.get(j).getTitle() + " " + section_enrolled.get(j).getCourseName());
							enrolled = true;
							break;
						}
					}
					
					if (!enrolled)
						sections.add(course.getSection(i));
				}
			}
			
		}
		return sections;
	}
	
	/**
	 *  trigger when select-all button click, check/uncheck all button
	 */
	@FXML
	void buttonselectall() {
		if (buttonselect.getText().equals("Select All")) {
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
			filter();
		} else {
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
			filter();
		}
	}

	// Print the course info, used in search function and filter function
	void printTextAreaConsole(List<Course> courses, boolean Skip0SectionCourse) {

		// textAreaConsole.setText("Filter");
		for (Course c : courses) {
			if(Skip0SectionCourse)
				if(c.getNumSections() == 0)
					continue;

			textAreaConsole.setText(textAreaConsole.getText() + "\n");

//    	for (Course c : v) {
//    		System.out.println(c.getNumSections()+"  "+ c.getNumSlots());
//    		if (c.getNumSlots() == 0)
//    			continue;

			String newline = c.getTitle() + "\n";
			for (int i = 0; i < c.getNumSections(); i++) {
				Section s = c.getSection(i);
				for (int j = 0; j < s.getNumSlots(); j++) {
					Slot t = s.getSlot(j);
					newline += "Section:" + s.getTitle() + " Slot " + (j + 1) + ":" + t + "\n";
				}
			}
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + newline);
		}
	}

	/**
	 *  Filter the course based on criteria set in tab filter, trigger when criteria changed
	 */
	@FXML
	void filter() {

		this.course_filter = new Vector<Course>();
		
		boolean all_false[] = { false, false, false, false, false, false };
		// textAreaConsole.setText("Filter");
		for (Course c : course_scraped) {
			if (checkboxExc.isSelected())
				if (c.getExclusion() != "null")
					continue;
			if (checkboxCom.isSelected())
				if (!c.getCC())
					continue;
			if (checkboxLab.isSelected())
				if (!c.gethaslabortut())
					continue;
			
			Course FilterC = new Course(c);
			for (int i = 0; i < c.getNumSections(); i++) {
				Section s = c.getSection(i);
				
				//Check days
				boolean days[] = { checkboxMon.isSelected(), checkboxTue.isSelected(), checkboxWed.isSelected(),
						checkboxThu.isSelected(), checkboxFri.isSelected(), checkboxSat.isSelected() };
				for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
					int day = s.getSlot(j).getDay();
					days[day] = false;
				}
				if (!Arrays.equals(all_false, days))
					continue;
				
				//Check AM/PM
				boolean valid = false; //used to check AM/PM filter, if criteria pass, it will turn true
				if (checkboxAM.isSelected() && checkboxPM.isSelected()) {
					boolean haveAM = false;
					boolean havePM = false;
					for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
						if (s.getSlot(j).getStartHour() < 12 && s.getSlot(j).getEndHour() >= 12)
							valid = true;
						if (s.getSlot(j).getStartHour() < 12)
							haveAM = true;
						if (s.getSlot(j).getStartHour() >= 12)
							havePM = true;
					}
					if (haveAM == true && havePM == true)
						valid = true;
				}
				else if (checkboxAM.isSelected()) {
					for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
						if (s.getSlot(j).getStartHour() < 12)
							valid = true;
					}
				}
				else if (checkboxPM.isSelected()) {
					for (int j = 0; j < c.getSection(i).getNumSlots(); j++) {
						if (s.getSlot(j).getEndHour() >= 12)
							valid = true;
					}
				}
				else
					valid = true;
				
				if (!valid)
					continue;
				
				FilterC.addSection(s);
			}
			
			this.course_filter.add(FilterC);
			textAreaConsole.setText("Filtered:");
		}

		section_filter = new Vector<Section>();
		for (Course c : course_filter) {
			for (int i = 0; i < c.getNumSections(); i++) {
				Section section = c.getSection(i);
				section_filter.add(section);
			}
		}
		table.setItems(getEnrolSection(true));

		this.printTextAreaConsole(course_filter, true);
	}

	/**
	 * Clicked once: Display the Total Number of Categories: ALL_SUBJECT_COUNT
	 * Clicked twice: scrape all the course according to the list of subjects, reset list of scraped course and list of filtered course
	 * @throws Exception
	 */
	@FXML
	void allSubjectSearch() throws Exception {
		//Task6  Find SFQ with my enrolled courses enabled
		buttonSfqEnrollCourse.setDisable(false);
		
		// WebClient client = new WebClient();
		
		String baseurl = textfieldURL.getText();
		String term = textfieldTerm.getText();
		String home = "ACCT";

		// Create a list containing all the subjects HtmlElement
		List<String> subjects = scraper.searchSubject(baseurl, term, home);

		ALL_SUBJECT_COUNT = subjects.size();
		
		if (search_all_subject_clicked == false) {
			search_all_subject_clicked = true;
			textAreaConsole.setText("Total Number of Categories/Code Prefix: " + ALL_SUBJECT_COUNT);
			return;
		}

		System.out.println(ALL_SUBJECT_COUNT);
		
		course_scraped.clear();
		course_filter.clear();
		section_filter.clear();
		
		/*
		double subject_scraped = 0;
		DoubleProperty progress = new SimpleDoubleProperty();
		progress.set(0);
		progressbar.progressProperty().bind(progress);
		
		for (String sub : (List<String>) subjects) {
			List<Course> courses = scraper.scrape(baseurl, term, sub);
			System.out.println(sub);
			
			for (int i = 0; i < courses.size(); i ++) {
				course_scraped.add(courses.get(i));
			}

			textAreaConsole.setText(textAreaConsole.getText() + sub + " is done \n");

			TOTAL_NUMBER_OF_COURSES += courses.size();
			subject_scraped += 1;

			progress.set(subject_scraped / ALL_SUBJECT_COUNT);
			Thread.sleep(100);

			//progressbar.setProgress(progress);
			System.out.println(progressbar.getProgress());
		}
		*/
		
		
		class bg_Thread implements Runnable {

			double subject_scraped = 0;
			double progress = 0;

			@Override
			public void run() {
				for (String sub : (List<String>) subjects) {
					List<Course> courses = scraper.scrape(baseurl, term, sub);
					// System.out.println(sub);
					
					for (int i = 0; i < courses.size(); i ++) {
						course_scraped.add(courses.get(i));
					}

					System.out.println(sub + " is done.");

					TOTAL_NUMBER_OF_COURSES += courses.size();
					subject_scraped += 1;

					progress = subject_scraped / ALL_SUBJECT_COUNT;

					progressbar.setProgress(progress);
					// System.out.println(progressbar.getProgress());
				}
				
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Total Number of Courses fetched: " + TOTAL_NUMBER_OF_COURSES);
				search_all_subject_clicked = false;
				
				System.out.println(section_filter.size());
				System.out.println(course_scraped.size());
				
				table.setItems(getEnrolSection(false));
				
				System.out.println("All subject search end");
			}
		}
		
		

		Thread th = new Thread(new bg_Thread());
		th.start();

	}

	@FXML
	void findInstructorSfq() {
		String url = textfieldSfqUrl.getText();
		List<InstructorSFQ> result = scraper.findInstructorSFQ(url);
		if (result == null) {
			textAreaConsole.setText(ErrorHandling.getErrorMessage());
		} else {
			textAreaConsole.setText("");
			for (InstructorSFQ r : result) {
				textAreaConsole.setText(textAreaConsole.getText() + r.toString() + "\n");
			}
		}
		;

	}

	@FXML
	void findSfqEnrollCourse() {

		String url = textfieldSfqUrl.getText();
		List<String> enrolled_code = new ArrayList<String>();

		for (Section section : section_enrolled) {
			enrolled_code.add(section.getCourseCode());
		}
		List<String> Distinct_enrolled_code = enrolled_code.stream().sorted().distinct().collect(Collectors.toList());
		/*
		 * for (String code : Distinct_enrolled_code) { System.out.println(code); }
		 */

		List<String> result = scraper.findCourseSFQ(url, Distinct_enrolled_code);
		if (result == null) {
			textAreaConsole.setText(ErrorHandling.getErrorMessage());
		} else {
			textAreaConsole.setText("");
			for (String r : result) {
				textAreaConsole.setText(textAreaConsole.getText() + r + "\n");
			}
		}
		;
	}

	@FXML
	void search() {
		//Task 6  Find SFQ with my enrolled courses enabled
		buttonSfqEnrollCourse.setDisable(false);
		
		List<Course> v = scraper.scrape(textfieldURL.getText(), textfieldTerm.getText(), textfieldSubject.getText());
		if (v == null)
			textAreaConsole.setText(ErrorHandling.getErrorMessage());
		else {
			int TotalNumOfCourses = 0;
			int TotalNumOfSlots = 0;
			LocalTime time = LocalTime.of(15, 10, 00, 0000);
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
						if (!instr.isEmpty()) {
							String[] insplit = instr.split("\\r?\\n");
							for (int k = 0; k < insplit.length; ++k) {
//							System.out.print(j+ insplit[j]+" ");
								InstructorArr.add(insplit[k].trim());
								if ((t.getDay() == 3) && (t.getStart().isBefore(time)) && (t.getEnd().isAfter(time)))
									UnavInstructorArr.add(insplit[k]);
							}
//						System.out.println();
						}
					}
				}
			}

			// Make array sorted and distinct
			List<String> DistinctInstructorArr = InstructorArr.stream().sorted().distinct()
					.collect(Collectors.toList());
			List<String> DistinctUnavInstructorArr = UnavInstructorArr.stream().sorted().distinct()
					.collect(Collectors.toList());

			// Remove unavailable
			DistinctInstructorArr.removeAll(DistinctUnavInstructorArr);
			// Remove TBA
			DistinctInstructorArr.remove("TBA");

			// TextAreaConsole controller
			textAreaConsole.setText("Total Number of courses: " + TotalNumOfCourses);
			textAreaConsole.setText(textAreaConsole.getText() + "\n" + "Total Number of slots: " + TotalNumOfSlots);

			textAreaConsole.setText(textAreaConsole.getText() + "\n"
					+ "Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm :");
			for (String i : DistinctInstructorArr) {
				textAreaConsole.setText(textAreaConsole.getText() + "\n" + i);
			}

			// textAreaConsole.setText(textAreaConsole.getText() + "\t Before all_false");

			// Record the course we have scraped
			this.course_scraped = v;
			this.printTextAreaConsole(course_scraped, false);
			
			table.setItems(getEnrolSection(false));
		}
	}

	private List<Label> EnrolledLabel = new Vector<Label>();
	private List<Label> EnrolledLabelBack = new Vector<Label>();
	
	/**
	 *  Create a timetable based on enrolled section
	 * @param sections List of sections that is enrolled by user
	 */
	public void timetable(List<Section> sections) {
		AnchorPane ap = (AnchorPane) tabTimetable.getContent();

		ap.getChildren().removeAll(EnrolledLabel);
		ap.getChildren().removeAll(EnrolledLabelBack);
		EnrolledLabel.clear();
		EnrolledLabelBack.clear();

		// System.out.println("Timetable");
		for (Section se : sections) {
			// random colors background generator
			double r1 = Math.random();
			double r2 = Math.random();
			double r3 = Math.random();
			if(r1 < 0.5)
				r1 = 0.5 + r1;
			if(r2 < 0.5)
				r2 = 0.5 + r2;
			if(r3 < 0.5)
				r3 = 0.5 + r3;
			for (int i = 0; i < se.getNumSlots(); i++) {
				// System.out.println(se.getCourseName());
				Slot s = se.getSlot(i);
				Label randomLabelBack = new Label();
				Label randomLabel = new Label(se.getCourseCode() + "\n" + se.getTitle());
				double startY = 40 + (s.getStartHour() - 9) * 20 + s.getStartMinute() * 10 / 30;
				double startX = (s.getDay() + 1) * 100;
				double Height = (s.getEndHour() - s.getStartHour()) * 20
						+ (s.getEndMinute() - s.getStartMinute()) * 10 / 30;
				double Width = 100;
				// System.out.println(s.getStartHour() + "\n" + startX + "\n" + startY + "\n" +
				// Height);
				randomLabelBack.setBackground(
						new Background(new BackgroundFill(Color.color(r1, r2, r3), CornerRadii.EMPTY, Insets.EMPTY)));
				randomLabelBack.setOpacity(0.4);
				randomLabelBack.setLayoutX(startX);
				randomLabelBack.setLayoutY(startY);
				randomLabelBack.setMinWidth(Width);
				randomLabelBack.setMaxWidth(Width);
				randomLabelBack.setMinHeight(Height);
				randomLabelBack.setMaxHeight(Height);
				
				randomLabel.setTextFill(Color.BLACK);
				randomLabel.setOpacity(0.5);
				randomLabel.setLayoutX(startX);
				randomLabel.setLayoutY(startY);
				randomLabel.setMinWidth(Width);
				randomLabel.setMaxWidth(Width);
				randomLabel.setMinHeight(30);
				randomLabel.setMaxHeight(30);

				EnrolledLabelBack.add(randomLabelBack);
				EnrolledLabel.add(randomLabel);
			}
		}
		
		ap.getChildren().addAll(EnrolledLabelBack);
		ap.getChildren().addAll(EnrolledLabel);
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

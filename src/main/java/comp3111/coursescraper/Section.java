package comp3111.coursescraper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.List;
import java.util.Vector;

/**
* <h1>Section class</h1>
* This class is to declare a section class with param, 
* 	private String title;
*	private String instructor;
*	private Slot [] slots;
*	private int numSlots;
*	private BooleanProperty enrolled = new SimpleBooleanProperty();
*/
public class Section {
	private static final int DEFAULT_MAX_SLOT = 3;
	
	//course attribute help to extract any info needed for the course
	Course course;
	
	private String title;
	private String instructor;
	private Slot [] slots;
	private int numSlots;
	
	// Similar to Boolean , BooleanProperty is needed to add a listener for JavaFX to know if this value is changed. (e.g. from enrolled to not enrolled)
	private BooleanProperty enrolled = new SimpleBooleanProperty();
	
	// Keep track of all the sections that are enrolled 
	// public static List<Section> ENROLLED_SECTIONS = new Vector<Section>();
	
	@Override
	public Section clone() {
		Section s = new Section();
		s.course = this.course;
		s.instructor = this.instructor;
		s.title = this.title;
		s.slots = this.slots;
		s.numSlots = this.numSlots;
		
		s.enrolled.set(this.isEnrolled());
		
		return s;
	}
	
	/**
	* default constructor  
	* set 3 empty slot to section, numSlots =0 and course is not enroll
	*/
	public Section() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) 
			slots[i] = null;
		numSlots = 0;
		this.enrolled.set(false);
		
		// added a listener function: this lambda function will be executed whenever the enrolled property is changed
		// in this case: whenever the check box is clicked to change from enrol or un-enrol to another 
		// lambda function: where (v, oldValue, newValue) is the parameter and the code inside {} will be execut

		/*
		this.enrolledProperty().addListener( (v, oldValue, newValue) -> {
			this.setEnrolled(newValue);
			
			if (this.isEnrolled())
				Section.ENROLLED_SECTIONS.add(this);
			else
				Section.ENROLLED_SECTIONS.remove(this);
			
			for (int i = 0; i < Section.ENROLLED_SECTIONS.size(); i++)
				System.out.print(Section.ENROLLED_SECTIONS.get(i).getTitle());
			
			System.out.println("");
			System.out.println(this.isEnrolled() + " : Listener worked");
			
		});
		*/
	}
	
	/**
	* get title
	* @return title
	*/
	public String getTitle() {
		return title;
	}
	
	/**
	* set title
	* @param title String 
	*/
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	* add slot
	* @param s slot
	*/
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	
	/**
	* get slot
	* @param i index
	* @return slots[i] 
	*/
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}
	
	/**
	* get num of slots
	* @return numSlots number of slots of a section 
	*/
	public int getNumSlots() {
		return numSlots;
	}
	
	/**
	* set number of slots
	* @param numSlots number of slots to be set
	*/
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	
	/**
	* get String of printing of input index 
	* @param i index
	* @return slots[i].toString() String value of slot 
	*/
	public String getSlotToString(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i].toString();
		return null;
	}
	
	/**
	* set Course
	* @param c course type
	*/
	public void setCourse(Course c) {
		this.course = c;
	}
	
	/**
	* get section's course code
	* @return course.getCourseCode 
	*/
	public String getCourseCode() {
		return this.course.getCourseCode();
	}
	
	/**
	* get section's course name
	* @return course.getCourseName 
	*/
	public String getCourseName() {
		return this.course.getTitle();
	}
	
	/*
	
	public void setCourseCode(String code) {
		this.courseCode = code;
	}
	
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	*/
	/**
	* set instructor name
	* @param i String
	*/
	public void setInstructor(String i) {
		this.instructor = i;
	}
	
	/**
	* get instructor name
	* @return instructor name
	*/
	public String getInstructor() {
		return this.instructor;
	}
	
	/**
	* get boolean of course is enrolled 
	* @return enrolled.get() boolean
	*/
	public boolean isEnrolled() {
		return enrolled.get();
	}
	
	/**
	* get enrolled property 
	* @return BooleanProperty 
	*/
	public BooleanProperty enrolledProperty() {
		return enrolled;
	}
	
	/**
	* set enrolled property 
	* @param enrol boolean
	*/
	public void setEnrolled(boolean enrol) {
		this.enrolled.set(enrol);
	}
}



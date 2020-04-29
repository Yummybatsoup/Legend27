package comp3111.coursescraper;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import java.util.List;
import java.util.Vector;

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
	public static List<Section> ENROLLED_SECTIONS = new Vector<Section>();
	
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
	
	public Section() {
		slots = new Slot[DEFAULT_MAX_SLOT];
		for (int i = 0; i < DEFAULT_MAX_SLOT; i++) 
			slots[i] = null;
		numSlots = 0;
		this.enrolled.set(false);
		
		// added a listener function: this lambda function will be executed whenever the enrolled property is changed
		// in this case: whenever the check box is clicked to change from enrol or un-enrol to another 
		// lambda function: where (v, oldValue, newValue) is the parameter and the code inside {} will be executed
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
		
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void addSlot(Slot s) {
		if (numSlots >= DEFAULT_MAX_SLOT)
			return;
		slots[numSlots++] = s.clone();
	}
	
	public Slot getSlot(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i];
		return null;
	}
	
	public int getNumSlots() {
		return numSlots;
	}
	
	public void setNumSlots(int numSlots) {
		this.numSlots = numSlots;
	}
	
	public String getSlotToString(int i) {
		if (i >= 0 && i < numSlots)
			return slots[i].toString();
		return null;
	}
	
	// Get the course
	public void setCourse(Course c) {
		this.course = c;
	}
	
	// Get the section's course code
	public String getCourseCode() {
		return this.course.getCourseCode();
	}
	
	// Get the section's course Name
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
	public void setInstructor(String i) {
		this.instructor = i;
	}
	
	public String getInstructor() {
		return this.instructor;
	}
	
	public boolean isEnrolled() {
		return enrolled.get();
	}
	
	public BooleanProperty enrolledProperty() {
		return enrolled;
	}
	
	public void setEnrolled(boolean enrol) {
		this.enrolled.set(enrol);
	}
}



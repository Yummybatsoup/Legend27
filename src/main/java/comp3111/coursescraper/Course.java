package comp3111.coursescraper;



public class Course {
	private static final int DEFAULT_MAX_Section = 50;
	
	// code refer to the course code
	private String code;
	
	private String title ; 
	private String description ;
	private String exclusion;
	private boolean hasCC;
	private Section [] sections;
	private int numSections;
	private boolean haslabortut;
	
	public Course() {
		code = "UST0000";
		sections = new Section[DEFAULT_MAX_Section];
		for (int i = 0; i < DEFAULT_MAX_Section; i++) 
			sections[i] = null;
		numSections = 0;
	}
	
	public void addSection(Section s) {
		if (numSections >= DEFAULT_MAX_Section)
			return;
		sections[numSections++] = s.clone();
	}
	
	public Section getSection(int i) {
		if (i >= 0 && i < numSections)
			return sections[i];
		return null;
	}

	/**
	 * return title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * set title
	 * @param title the course title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	
	/**
	 * set code
	 * @param code the course code
	 */
	public void setCourseCode(String code) {
		this.code = code;
	}


	/**
	 * return description
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the description
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * return exclusion
	 * @return the exclusion
	 */
	public String getExclusion() {
		return exclusion;
	}

	/**
	 * @param exclusion the exclusion to set
	 */
	public void setExclusion(String exclusion) {
		this.exclusion = exclusion;
	}
	
	
	public boolean getCC() {
		return hasCC;
	}

	
	public void setCC(boolean cc) {
		this.hasCC = cc;
	}
	
	public boolean gethaslabortut() {
		return haslabortut;
	}

	
	public void sethaslabortut(boolean haslabortut) {
		this.haslabortut = haslabortut;
	}

	/**
	 * @return numSections the numSections
	 */
	public int getNumSections() {
		return numSections;
	}

	/**
	 * @param numSections the numSlots to set
	 */
	public void setNumSections(int numSections) {
		this.numSections = numSections;
	}
	
	public int getNumSlots() {
		int num = 0;
		for(int i = 0; i < this.numSections;i++) {
			num += this.sections[i].getNumSlots();
		}
		return num;
	}
	
	public String getCourseCode() {
		return this.code;
	}

}

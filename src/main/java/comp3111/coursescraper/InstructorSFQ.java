package comp3111.coursescraper;

import java.util.List;
/**
* <h1>InstructorSFQ class for assisting calculate avg SFQ score</h1>
*
* Instructor SFQ including instructor name, numofSection and totalScore
* <br>
* Provided function to set name, numofSection and totalScore, as well as 
* return name, avg Score and the String for console print;
* 
*/

public class InstructorSFQ {
	private String name ; 
	private int numofSection ;
	private Double totalScore;

	 /**
	   * Default constructor 
	   * <br>
	   * TO set numofSction =0 and totalScore =0
	   */
	public InstructorSFQ() {
		this.numofSection = 0;
		this.totalScore =0.0;
	}
	
	 /**
	   * This method is set name
	   * @param n the name to be set
	   */
	public void setname(String n) {
		this.name =n;
	}
	
	/**
      * This method is to increase the numofSection
      */
	public void addnumofSection() {
		this.numofSection++;
	}
	
	/**
	   * This method is to add total score
	   * @param n the score to be added
	   */
	public void addtotalScore(Double n) {
		this.totalScore +=n;
	}
	
	/**
	   * This method is to get instructor name
	   * @return String type name
	   */
	public String getname() {
		return this.name;
	}

	/**
	   * This method is to get avg score of the instructor SFQ
	   * @return double type score
	   */
	public double getavgScore() {
		return this.totalScore/this.numofSection;
	}
	
	/**
	   * This method is to get print String for the console
	   * @return A string to be printed
	   */
	public String toString() {
		return "Name:"+this.name+" Avg Score:"+ this.getavgScore();
	}
	

}

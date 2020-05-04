package comp3111.coursescraper;

import java.util.List;

public class InstructorSFQ {
	private String name ; 
	private int numofSection ;
	private Double totalScore;
	
	public InstructorSFQ() {
		this.numofSection = 0;
		this.totalScore =0.0;
	}
	
	
	public void setname(String n) {
		this.name =n;
	}
	
	public void addnumofSection() {
		this.numofSection++;
	}
	
	public void addtotalScore(Double n) {
		this.totalScore +=n;
	}
	
	public String getname() {
		return this.name;
	}
	
	public double getavgScore() {
		return this.totalScore/this.numofSection;
	}
	
	public String toString() {
		return "Name:"+this.name+" Avg Score:"+ this.getavgScore();
	}
	

}

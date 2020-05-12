package comp3111.coursescraper;

import java.util.Map;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.Locale;
import java.time.format.DateTimeFormatter;

/**
* <h1>Slot class</h1>
* This class is to declare a slot class with param, 
* 	private int day;
*	private LocalTime start;
*	private LocalTime end;
*	private String venue;
*	private String instructor;
*	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
*	public static final Map with String and Integer DAYS_MAP
*/
public class Slot {
	private int day;
	private LocalTime start;
	private LocalTime end;
	private String venue;
	private String instructor;
	public static final String DAYS[] = {"Mo", "Tu", "We", "Th", "Fr", "Sa"};
	public static final Map<String, Integer> DAYS_MAP = new HashMap<String, Integer>();
	static {
		for (int i = 0; i < DAYS.length; i++)
			DAYS_MAP.put(DAYS[i], i);
	}

	@Override
	public Slot clone() {
		Slot s = new Slot();
		s.day = this.day;
		s.start = this.start;
		s.end = this.end;
		s.venue = this.venue;
		s.instructor = this.instructor;
		return s;
	}
	

	/**
	 * @return String DAYS[day] + start.toString() + "-" + end.toString() + " Venue :" + venue;
	 */
	public String toString() {
		return DAYS[day] + start.toString() + "-" + end.toString() + " Venue :" + venue;
	}
	

	/**
	 * @return start.getHour
	 */
	public int getStartHour() {
		return start.getHour();
	}
	

	/**
	 * @return start.getMinute
	 */
	public int getStartMinute() {
		return start.getMinute();
	}

	/**
	 * @return end.getHour
	 */
	public int getEndHour() {
		return end.getHour();
	}
	/**
	 * @return end.getMinute
	 */
	public int getEndMinute() {
		return end.getMinute();
	}
	/**
	 * @return the start
	 */
	public LocalTime getStart() {
		return start;
	}
	/**
	 * @param start the start to set
	 */
	public void setStart(String start) {
		this.start = LocalTime.parse(start, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * @return the end
	 */
	public LocalTime getEnd() {
		return end;
	}
	/**
	 * @param end the end to set
	 */
	public void setEnd(String end) {
		this.end = LocalTime.parse(end, DateTimeFormatter.ofPattern("hh:mma", Locale.US));
	}
	/**
	 * @return the venue
	 */
	public String getVenue() {
		return venue;
	}
	/**
	 * @param venue the venue to set
	 */
	public void setVenue(String venue) {
		this.venue = venue;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	/**
	 * @return the instructor
	 */
	public String getInstructor() {
		return instructor;
	}

	/**
	 * @param instructor the instructor to set
	 */
	public void setInstructor(String instructor) {
		this.instructor = instructor;
	}
}

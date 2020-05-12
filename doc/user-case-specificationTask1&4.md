# User-case: Search (Backend)

### Brief Description
Handling displaying data and error message in the console after search button is clicked.

### Basic Flow	
1.	The use case begins when the student actor chose the Main section
2.	{Enter Input}
2.	Input base URL, Terms, and Subject
3.	User press the search button, to search the course information.
	{Search Information}
4.	Result is displayed, with total number of different sections, the total number of courses as well as the Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm with ascendingly according to the alphabetical order of the instructor’s name

### Alternative Flows
#### S1. Incorrect URL

1.	{Search Information} If the system receives the URL, that have no data inside or have empty URL
2.	Console will display “invalid URL” message


# User-case: View Timetable

### Brief Description
Display the updated timetable

### Basic Flow	
1.	Click into Timetable table 
{Enter Timetable}
2.	Timetable shows every course block including the corresponding course code and section code in two line with different color in the correct time slot position. Overlapped area will displayed in different color.

### Alternative Flow
#### No course data
1.	{Enter Timetable} If there no course data 
2.	Display timetable with the first 5 sections of the scrapped data


User-case: Search (Backend)

Brief Description
Handling displaying data and error message in the console after search button is clicked.

Basic Flow	
1.	The use case begins when the student actor chose the Main section
2.	{Enter Input}
2.	Input base URL, Terms, and Subject
3.	User press the search button, to search the course information.
	{Search Information}
4.	Result is displayed, with total number of different sections, the total number of courses as well as the Instructors who has teaching assignment this term but does not need to teach at Tu 3:10pm with ascendingly according to the alphabetical order of the instructor’s name

Alternative Flows
S1. Incorrect base URL

1.	{Search Information} If the system receives the base URL, that have no data inside or have empty URL
2.	Console will display “input wrong URL” message
Ask the user to reinput the URL, to {Enter Input}

S2. Incorrect Terms 
1.	{Search Information} If the system receives the Terms that are not defined.
2.	Console will display “Undefined Terms” message, with the explainion of how to input the Terms, such as the term consist of 4 numbers, first two represent the academic year and the last two refer to the semester.
3.	Ask the user to reinput the Terms, to {Enter Input}

S3. Incorrect Subject 
1.	{Search Information} If the system receives the subject that are not defined.
2.	Console will display “Undefined Subject” message, with the list of all defined subject.
3.	Ask the user to reinput the Subject, to {Enter Input }

User-case: View Timetable

Brief Description
Display the updated timetable

Basic Flow	
1.	Click into Timetable table 
{Enter Timetable}
2.	Timetable shows every course block including the corresponding course code and section code in two line with different color in the correct time slot position. Overlapped area will displayed in different color.

Alternative Flow
1.	{Enter Timetable} If there no course data 
2.	Display timetable with the first 5 sections of the scrapped data


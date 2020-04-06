# TASK 3

The use case begin when the student want to view the list of the filtering result.

## Basic Flow

{Receiving Result}

The system receive a filter result list.

The system print a list of filter courses on the interface according to the filter, with all columns non-

editable except the enrol checkbox. 

{Select Activity}

while student has activity to perform

	{Enrolling Courses}
	if a checkbox is ticked
		the console is cleared and filter information is displayed
		the enrolment status of the course is changed to 'enrolled'
		display the list of enrolled section in the console 
		
	{Unenrolling Courses}
	if a checkbox is cleared
		the console is cleared and filter information is displayed
		the enrolment status of the course is changed to 'not enrolled'
		display the list of enrolled section in the console
		
The use case ends.

## Alternative Flow

At {Select Activity} if no status of the checkbox is changed, the system perform nothing other than just

 listing all the filtered information.

At {Receiving Result} if the filter cannot be correctly implemented, the system print all the result

 scrapped.

At {Enrolling Courses} if another section of the course is enrolled, reject this enrolment request and 

print "ERROR: another section has been enrolled " .

# TASK 5

## Basic Flow

The use case begin when the user want to search all subject.

{Enter URL and Term}

The user enter base_url and term that he/she want to search for.

{Clicking Search Button the First Time}

The system obtain the list of all subject from the base_url + term . 

The console print the total number of categories or code prefix as ALL_SUBJECT_COUNT in the 

console.

{Clicking Search Button the Second Time}

The system search all subjects from the base_url.

The system print 'SUBJECT is done' after each subject is scrapped in the console.

The progress bar is updated by the fraction of 1/(number of subjects scrapped) .

The system print "Number of Courses fetched: TOTAL_NUMBER_OF_COURSES "to the console 

after all subjects is searched.

The use case ends.

## Alternative Flow

At {Enter URL and Term} if any of the base_url or the term is INVALID, the system print "ERROR: INVALID base URL or Term" .

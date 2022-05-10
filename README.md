# CS611- Final Project: Grading System

## Name
---------------------------------------------------------------------------
--Jianqi Ma--
–-mjqpauli@bu.edu-- 
--U11094654--

--Baiqing Lyu--
–-baiqing@bu.edu--
--U03215838--

--Quang Nguyen--
–-nddq@bu.edu--
--U32906640--


## Files
---------------------------------------------------------------------------

src/main/java/entry/Main.java - Main entry point for the program <br />
src/main/java/gui/State.java - State to be shared among the GUI components <br />
src/main/java/gui/Assignment.java - Assignment detail and student grade table <br />
src/main/java/gui/Assignment.form - GUI template for java swing <br />
src/main/java/gui/AssignmentSelection.java - State to be shared among the GUI components <br />
src/main/java/gui/AssignmentSelection.form - GUI template for java swing <br />
src/main/java/gui/CourseSelection.java - Course selection GUI <br />
src/main/java/gui/CourseSelection.form - GUI template for java swing <br />
src/main/java/gui/CourseTitle.java - Shows the courses <br />
src/main/java/gui/CourseTitle.form - GUI template for java swing <br />
src/main/java/gui/LetterGrades.java - Shows the letter grades for a class <br />
src/main/java/gui/LetterGrades.form - GUI template for java swing <br />
src/main/java/gui/MainFrame.java - Chains together the other GUI components <br />
src/main/java/gui/MainFrame.form - GUI template for java swing <br />
src/main/java/gui/SemesterSelection.java - Semester selection GUI <br />
src/main/java/gui/SemesterSelection.form - GUI template for java swing <br />
src/main/java/gui/UserLogin.java - User login screen <br />
src/main/java/gui/UserLogin.form - GUI template for java swing <br />
src/main/java/utils/Assignment.java - Class design for the assignment object <br />
src/main/java/utils/ClassPathTuple.java - Create a tuple class to bind together a class name and their CSV path <br />
src/main/java/utils/CSVReader.java - Reads CSV files and outputs the corresponding objects <br />
src/main/java/utils/CSVWriter.java - Writes objects to CSV file at specified file paths <br />
src/main/java/utils/GradedClass.java - Class object that contains the students, assignments, and grades <br />
src/main/java/utils/Semester.java - Semester object that holds classes and information about the semester <br />
src/main/java/utils/Student.java - Student object that holds their respective grades and assignments in addition to their names and ID. <br />

## Notes
---------------------------------------------------------------------------
* Class management
	* Users can import classes from a CSV file
	* Stastics of classes are shown directly in a table
	* Class information that is changed can be saved to the same file
	* Delete classes that are no longer needed
	* Curves can be applied onto classes
	* Grades and their brackets can be applied and modified
* Student management
	* Users can view detailed information of students in each class
	* Students can be removed and added to classes
	* Student information can be manually edited and saved
* Assignment management
	* Assignments can be created and deleted
	* Assignments can have their weight changed
* Other features
	* User login
	* Material theme


## How to run
---------------------------------------------------------------------------
1. Navigate to the main Project Directory after downloading the files
2. Install Apache Maven and JDK 18
3. Run the following command in the main project directory: `mvn clean compile assembly:single`
4. The packaged jar will be created in the `target` directory
5. Click on the jar to open the software, note that the default java version must also be 18
6. If default version is not, then execute the jar on the command line via `java -jar file.jar`
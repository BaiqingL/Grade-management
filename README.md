### 611 Final Project Design Doc

Group 5 - Baiqing Lyu, Jianqi Ma, Quang Nguyen

---

#### Functionalities

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

#### Documentation
* entry
  * `Main`
    * Main entry point for the program
* utils
  * `Assignment`
    * Class design for the assignment object
  * `ClassPathTuple`
    * Create a tuple class to bind together a class name and their CSV path
  * `CSVReader`
    * Reads CSV files and outputs the corresponding objects
  * `CSVWriter`
    * Writes objects to CSV file at specified file paths
  * `GradedClass`
    * Class object that contains the students, assignments, and grades
  * `Semester`
    * Semester object that holdes classes and information about the semester
  * `Student`
    * Student object that holds their respective grades and assignments in addition to their names and ID.
* gui
  * `State`
    * State to be shared among the GUI components
  * `Assignment`
    * Assignment detail and student grade table
  * `AssignmentSelection`
    * Select certain assignments to be displayed
  * `CourseSelection`
    * Course selection GUI
  * `CourseTitle`
    * Shows the courses
  * `LetterGrades`
    * Shows the letter grades for a class
  * `MainFrame`
    * Chains together the other GUI components
  * `SemesterSelection`'
    * Semester selection GUI
  * `UserLogin`
    * User login screen
#### Work distribution

* Baiqing Lyu
  * Backend CSV writing and reading
  * `Util` object design
  * Frontend theme installation
  * Chaining together frontend buttons and backend saving
  * User input prompts
  * Dynamic updates to the frontend after user modification
  * Maven project setup and jar packaging
* Jianqi Ma
* Quang Nguyen
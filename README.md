<img src="https://raw.githubusercontent.com/rusevradoslav/DevUni/master/src/main/resources/static/images/5198robot.ico" align="right"/>

# DevUni

**Contributor:**

*  Violeta Kastreva

**Description:**

Quizzical is a web app used for tracking the progress of students via quizzes. 
Teacher - users have the opportunity to create network of students, place them in different groups and assign quiz events to the groups (exam quiz for example). Last but not least teacher receives students' results right after the quiz is taken.
Student - users also can keep track of their own results and all information about the ended, active or pending events that are assigned to them.

**Technologies:**

**Front-End:**

*   HTML
*   CSS
*   Bootstrap
*   JavaScript
*   Thymeleaf 

 
**Back-End:**
*   Java
*   Spring Boot 
*   Spring MVC 
*   Spring Security
*   MySQL Database 
*   Hibernate
*   JPA

## Running

**Quizzical run requirements:**

**Java (JRE) 14**

* <b>Windows</b> and <b>MacOS X</b> installers include JRE so just use them and don't think about internals.
* On <b>Linux</b> you may need to install Java manually (usually by running `sudo apt-get install openjdk-14-jdk` or something similar).
* If you don't use installer (on Windows or Mac OS X) you may need to download Java (JDK) from <a href="https://www.oracle.com/java/technologies/javase/jdk14-archive-downloads.html">Oracle website</a>.

**MySQL Workbench 8.0.21**

* You can download MySQL Workbench from official <a href="https://dev.mysql.com/downloads/workbench/"> MySQL Workbench website.

## Building

#### Prerequisites:

 1. Java (JDK) 14 
 2. Apache Maven 3+
 3. MySQL Dialect 8
 4. Internet access
 5. Git client

#### Build
**Before build the project you need follow a few steps:**

 1. Clone GitHub Repository using the following command:
 
   ```
    $ git clone https://github.com/violeta-kastreva/quizzical.git
   ```
 2. In application properties file you need to enter your personal database username and password:
   ```
   spring.datasource.username=[DB USERNAME]
   spring.datasource.password=[DB PASSWORD]
   ```

  Application properties file is in `src/main/resources/application.yml`

 **Valid Profile Accounts:**
 
    Teacher : email -> teacher@teacher.com ; password -> teacher 
    Student : email -> student@student.bg ; password -> student 

### Routes

URLs | Description
---------|---------
 */* | Index page - page where guest user can see a description of the app.
 */homestudent* | Student home page - page where authorized student can see all quizzes and teachers in Quizzical.
 */hometeacher* | Teacher home page - page where authorized teacher can see all groups, quizzes and students in Quizzical.
 */users/register* | Register page - page where user can register.
 */creategroup* | Create Group page - page where teacher can create groups of students.
 */createquiz* | Create Group page - page where teacher can create quiz and assign it to a group.
 */createquestion* | Create Group page - page where teacher can create questions and answers for the quiz. 
 */createdquizzes* | All quizzes - page where the teacher can see all quizzes which they created
 */createdgroups* | All groups - page where the teacher can see all groups which they created
 */allstudents* | All students - page where the teacher can see all the students in the database
 */allresults* | All quiz results - page where the teacher can see all the students' results from taking quizzes
 */allteachers* | All teachers - page where the student can see all the teachers in the database
 */myquizzes* | Page where a student can view all the assigned quizzes 
 */myresults* | Page where a student can view all of their results from taking quizzes
 */takequiz* | Page where a student takes an assigned quiz they picked
 */groups* | Page where a student can view all of the groups they participate in
 */teacherprofile* | User Details page - page where a teacher can see user profile details.
 */studentprofile* | User Details page - page where a student can see user profile details.
 


  - Admin All Teachers Page
   ![adminallteacherspage](/src/main/resources/static/screenshots/admin_all_teachers.jpg) 
   
  - Admin All Teacher Requests aPage
   ![adminallteacherrequestsspage](/src/main/resources/static/screenshots/admin_all_teacher_requests.jpg) 
  
  - Admin All Students Page
   ![adminallstudentspage](/src/main/resources/static/screenshots/admin_all_students.jpg) 

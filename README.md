<img src="https://github.com/violeta-kastreva/quizzical/blob/master/quizzical/src/main/resources/static/img/logo.png?raw=true" align="right" width="50" height="50"/>

# Quizzical

**Contributor:**

*  Violeta Kastreva

**Description:**

Quizzical is a web app used for tracking the progress of students via quizzes. 

Teacher-users have the opportunity to create network of students, place them in different groups and assign quiz events to the groups (exam quiz for example). Last but not least teacher receives students' results right after the quiz is taken.

Student-users also can keep track of their own results and all information about the ended, active or pending quizzes that are assigned to them.

**Technologies:**

**Front-End:**

*   HTML
*   CSS
*   Bootstrap
*   JavaScript
*   Thymeleaf 
* Using Lazy Kit Bootstrap template : https://demo.themewagon.com/preview/lazy-kit-is-a-free-bootstrap-4-ui-kit-lazy-kits

 
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
 

Database Diagram Screenshot
 ---
 
 ![database](/quizzical/src/main/resources/static/screenshots/database.jpg)
 
 
 Website Screenshots
 ---
 
 - Index Page 
 ![indexpage](/quizzical/src/main/resources/static/screenshots/indexpage.jpg) 
 
 - Register Page 
 ![registerpage](/quizzical/src/main/resources/static/screenshots/register.jpg) 
 
 - Login Page 
  ![login](/quizzical/src/main/resources/static/screenshots/login.jpg)    
 
 - Teacher Home Page 
 ![homepageteacher](/quizzical/src/main/resources/static/screenshots/teacherhome.jpg)
 
  - Student Home Page 
 ![homepagestudent](/quizzical/src/main/resources/static/screenshots/studenthome.jpg) 
 
  - View Profile Page
 ![profilepage](/quizzical/src/main/resources/static/screenshots/teacherprofile.jpg)
 
 - Teacher Create Group Page
 ![creategroup](/quizzical/src/main/resources/static/screenshots/creategroup.jpg)
 
  - Teacher Create Quiz Page
 ![createquiz](/quizzical/src/main/resources/static/screenshots/createquiz.jpg)

 - Teacher Create Questions for Quiz Page
 ![createquestions](/quizzical/src/main/resources/static/screenshots/createquestions.jpg)
  
 - View Teacher Groups Page
 ![teachergroups](/quizzical/src/main/resources/static/screenshots/teachergroups.jpg)
 
 - View Teacher Quizzes Page
 ![teacherquizzes](/quizzical/src/main/resources/static/screenshots/teacherquizzes.jpg)
 
  - View Teacher - Students Quiz Results Page
 ![teacherresults](/quizzical/src/main/resources/static/screenshots/teacherresults.jpg)
 
  - View All Students Page
 ![allstudents](/quizzical/src/main/resources/static/screenshots/allstudents.jpg)
 
 - View Student's Quizzes Page 
 ![studenttakequiz](/quizzical/src/main/resources/static/screenshots/studenttakequiz.jpg)
 
 - View Student taking Quiz Page 
 ![studenttakingquiz](/quizzical/src/main/resources/static/screenshots/takingquiz.jpg)
 
  - View Student's Quiz Results Page
 ![quizresultsstudent](/quizzical/src/main/resources/static/screenshots/quizresultsstudent.jpg)
 
  - View All Teachers Page
 ![allteachers](/quizzical/src/main/resources/static/screenshots/allteachers.jpg)
 
 
  **Using Lazy Kit Bootstrap template** : https://demo.themewagon.com/preview/lazy-kit-is-a-free-bootstrap-4-ui-kit-lazy-kits
 

The main feature of the application is Class Timetable for students and teachers.
Students or teachers can get their timetable for a day or for a month.

<hr>
<h1 align="center"> Installation of the working environment:</h1>
1. Install JDK11 from <a href=https://www.oracle.com/java/technologies/javase-jdk11-downloads.html>here</a><br>
2. Install latest version Eclipse IDE for EE from <a href=https://www.eclipse.org/downloads/>here</a><br>
3. Install latest version of Apache Maven for UNIX from <a href=https://maven.apache.org/download.cgi>here</a><br>
4. Install PostgreSQL 13 from <a href=https://www.postgresql.org/download>here</a><br>
5. Install latest version Git from <a href=https://git-scm.com/downloads>here</a><br>

<h1 align="center"> Preparing for work: </h1>
1. Clone application from Github to local repository<br>
2. Import cloned project to Eclipse (Import/General/Projects from Folder or Archive)<br>
3. Run Sql-scripts from src/main/resources/db/sql in the next order:<br>
3-1. Create working database: <br>
For Unix-like: $ sudo -u postgres psql -d university -f db_creation.sql<br>
For Windows psql -u postgres -f db_creation.sql<br>
3-2. Create database user: <br>
For Unix-like: $ sudo -u postgres psql -d university -f user_creation.sql<br>
For Windows psql -u postgres -f user_creation.sql<br>
3-3. mvn flyway:migrate -Dflyway.url=jdbc:postgresql://localhost:5432/university -Dflyway.user=tester -Dflyway.password=test<br>
3-4.(Optional) If you want to add test database, reply steps 3.1-3.3 with /src/test/resouces/db/sql folder 
To insert example database entries:<br>
Run Sql-scripts from src/test/resources/db/sql
For Unix-like: $ sudo -u postgres psql -d university -f test_data.sql<br>
For Windows psql -u postgres -d university -f test_data.sql<br>
UI acesses:
Admin: admin@admin.com admin
User: user@user.com user
4. Insert username and password in application.yml for mail sending. <br>
Config prepared to using yandex.ru, don't forget to allow access to mail by 3's party client in mail properties.
If you want to use another mail service, you need to update application mail configuration.
5. Run in Console mvn spring-boot:run<br>
6. Enter <span>http://127.0.0.1:8080/university</span> in browser<br>
<hr>
Enjoy!!!

<h1 align="center"> Main steps changelog: </h1>
2020-03-04: Start project<br>
2020-04-07: Add logging system<br>
2020-04-13: Start to adding Spring MVC/Thymeleaf/Bootstrap<br>
2020-04-29: Add jndi, put config files for Tomcat into resorces<br>
2020-04-30: Start to implement Hibernate instead of Spring JDBC<br>
2020-05-06: Hibernate implemented<br>
2020-05-07: Start project migration to Spring Boot<br>
2020-06-04: Flyway implementation<br>
2020-06-12: Start validation implementtation<br>
2020-06-18: Start REST implementation<br>
2020-06-24: Start Swagger implementation<br>
2020-10-25: Next step planning<br>
2020-10-27: JaCoCo implemented<br>
2020-12-26: Start SpringSecurity implementation<br>
2021-01-29: Release candidate


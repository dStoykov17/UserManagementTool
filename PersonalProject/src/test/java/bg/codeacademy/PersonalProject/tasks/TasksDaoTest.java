package bg.codeacademy.PersonalProject.tasks;

import bg.codeacademy.PersonalProject.User;
import bg.codeacademy.PersonalProject.user.UserService;
import io.restassured.RestAssured;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TasksDaoTest extends AbstractTestNGSpringContextTests
{
  @LocalServerPort
  int port;

  @BeforeClass(alwaysRun = true, dependsOnMethods = "springTestContextPrepareTestInstance")
  protected void setupRestAssured()
  {
    RestAssured.port = port;
  }

  @Autowired
  private UserService userService;
  private TasksService taskService;
  @BeforeMethod
  private void createData(){
    userService.addUser("testUser","123456");
  }
  @AfterMethod
  private void deleteData(){
    User toDeletes = userService.findByUsername("testUser");
    userService.deleteUser(toDeletes.getId());
  }


  @Test
  public void testGetTasks()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("taskId", "481")
        .when()
        .get("/project/tasks")
        .then()
        .assertThat()
        .statusCode(200);
  }
  @Test
  public void testGetTasksInvalidTaskId()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("taskId", "sfda")
        .when()
        .get("/project/tasks")
        .then()
        .assertThat()
        .statusCode(400);
  }
  @Test
  public void testShowAllTasksByUserId()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("assigneId", "521")
        .when()
        .get("/project/tasks/taskId")
        .then()
        .assertThat()
        .statusCode(200);
  }
  @Test
  public void testShowAllTasksByUserIdInvalidUserId()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("assigneId", "fasf")
        .when()
        .get("/project/tasks/taskId")
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
  public void testShowAllTasksByDesc1()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("desc1", "A task")
        .when()
        .get("/project/tasks/taskName")
        .then()
        .assertThat()
        .statusCode(200);
  }
  @Test
  public void testShowAllTasksByDesc1InvalidDesc()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("desc1", "12441515")
        .when()
        .get("/project/tasks/taskName")
        .then()
        .assertThat()
        .statusCode(404);
  }

  @Test
  public void testShowAtaskByIdAndDesc()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("desc1", "A task")
        .param("taskId","684")
        .when()
        .get("/project/tasks/taskIdDesc")
        .then()
        .assertThat()
        .statusCode(200);
  }
  @Test
  public void testShowAtaskByIdAndDescWrongDescRightId()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("desc1", "1248210")
        .param("taskId", "682")
        .when()
        .get("/project/tasks/taskIdDesc")
        .then()
        .assertThat()
        .statusCode(404);
  }
  @Test
  public void testShowAtaskByIdAndDescWrongIdRightDesc()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("desc1", "New Status")
        .param("taskId", "asfsar")
        .when()
        .get("/project/tasks/taskIdDesc")
        .then()
        .assertThat()
        .statusCode(400);
  }
  @Test
  public void testShowAtaskByIdAndDescWrongIdWrongDesc()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("desc1", "214156")
        .param("assigneId", "asfsar")
        .when()
        .get("/project/tasks/taskIdDesc")
        .then()
        .assertThat()
        .statusCode(404);
  }

  @Test
  public void testUpdateTaskDesc()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("desc1", "New Status")
        .param("taskId", "682")
        .when()
        .put("/project/tasks/change")
        .then()
        .assertThat()
        .statusCode(200);
  }
}
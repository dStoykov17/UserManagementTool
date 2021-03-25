package bg.codeacademy.PersonalProject.user;

import bg.codeacademy.PersonalProject.User;
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
 public class UserDaoTest extends AbstractTestNGSpringContextTests
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
  public void testGetUser()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("userId", "401")
        .when()
        .get("/project/user/get")
        .then()
        .assertThat()
        .statusCode(200);
  }
  @Test
  public void testGetUserInvalidUserId(){
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("userId", "satfsa")
        .when()
        .get("/project/user/get")
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
  public void testFindUserByUsername()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("username", "person")
        .when()
        .get("/project/user/getUsername")
        .then()
        .assertThat()
        .statusCode(200);
  }
  @Test
  public void testFindUserByUsernameInvalidParam()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("username", "fsafasa")
        .when()
        .get("/project/user/getUsername")
        .then()
        .assertThat()
        .statusCode(404);
  }

  @Test
  public void testFindUserByUsernameAndId()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("username", "person")
        .param("userId", "541")
        .when()
        .get("/project/user/getIdName")
        .then()
        .assertThat()
        .statusCode(200);
  }

  @Test
  public void testFindUserByUsernameAndIdInvalidUsername()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("username", "asfasf")
        .param("userId", "541")
        .when()
        .get("/project/user/getIdName")
        .then()
        .assertThat()
        .statusCode(404);
  }

  @Test
  public void testFindUserByUsernameAndIdInvalidUserId()
  {
    RestAssured
        .given()
        .auth().basic("testUser","123456")
        .param("username", "person")
        .param("userId", "afga")
        .when()
        .get("/project/user/getIdName")
        .then()
        .assertThat()
        .statusCode(400);
  }
}
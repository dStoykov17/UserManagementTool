package bg.codeacademy.PersonalProject.Groups;

import bg.codeacademy.PersonalProject.User;
import bg.codeacademy.PersonalProject.tasks.TasksService;
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
public class GroupDaoTest extends AbstractTestNGSpringContextTests
{
  @LocalServerPort
  int port;

  @BeforeClass(alwaysRun = true, dependsOnMethods = "springTestContextPrepareTestInstance")
  protected void setupRestAssured()
  {
    RestAssured.port = port;
  }

  @Autowired
  private UserService  userService;
  private TasksService taskService;

  @BeforeMethod
  private void createData()
  {
    userService.addUser("testUser", "123456");
  }

  @AfterMethod
  private void deleteData()
  {
    User toDeletes = userService.findByUsername("testUser");
    userService.deleteUser(toDeletes.getId());
  }


  @Test
  public void testGetAllGroups()
  {
    RestAssured
        .given()
        .auth().basic("testUser", "123456")
        .param("userId", "521")
        .when()
        .get("/project/group/getAll")
        .then()
        .assertThat()
        .statusCode(200);
  }

  @Test
  public void testGetAllGroupsInvalidUserId()
  {
    RestAssured
        .given()
        .auth().basic("testUser", "123456")
        .param("userId", "sstar")
        .when()
        .get("/project/group/getAll")
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
public void testGetUserGroupByGroupId()
{

  RestAssured
      .given()
      .auth().basic("testUser", "123456")
      .param("groupId", "837")
      .when()
      .get("/project/group/getGroup")
      .then()
      .assertThat()
      .statusCode(200);

}

  @Test
  public void testGetUserGroupByGroupIdInvalidGroupId()
  {

    RestAssured
        .given()
        .auth().basic("testUser", "123456")
        .param("groupId", "skutsd")
        .when()
        .get("/project/group/getGroup")
        .then()
        .assertThat()
        .statusCode(400);

  }

  @Test
  public void testGetUserGroupByGroupNameAndUserId()
  {
    RestAssured
        .given()
        .auth().basic("testUser", "123456")
        .param("group_name", "users")
        .param("userId", "521")
        .when()
        .get("/project/group/getGroup2")
        .then()
        .assertThat()
        .statusCode(200);
  }

  @Test
  public void testGetUserGroupByGroupNameAndUserIdInvalidUserId()
  {
    RestAssured
        .given()
        .auth().basic("testUser", "123456")
        .param("group_name", "users")
        .param("userId", "afs")
        .when()
        .get("/project/group/getGroup2")
        .then()
        .assertThat()
        .statusCode(400);
  }

  @Test
  public void testGetUserGroupByGroupNameAndUserIdInvalidGroupName()
  {
    RestAssured
        .given()
        .auth().basic("testUser", "123456")
        .param("group_name", "1f9a24gd")
        .param("userId", "521")
        .when()
        .get("/project/group/getGroup2")
        .then()
        .assertThat()
        .statusCode(404);
  }

}

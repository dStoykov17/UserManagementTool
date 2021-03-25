package bg.codeacademy.PersonalProject.user;

import bg.codeacademy.PersonalProject.Models.BasicUserInfoGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/user")
public class UserResource
{
  private final UserService userService;

  @Autowired
  public UserResource(UserService personService)
  {
    this.userService = personService;
  }

  @GetMapping("/get")
  public ResponseEntity<?> getUserById(Integer userId)
  {
    List<BasicUserInfoGet> id = userService.getUserById(userId);
    if(id.isEmpty()){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(id);
  }

  @PostMapping("/add")
  public void addUser(String username, String password)
  {
    userService.addUser(username, new BCryptPasswordEncoder().encode(password));
  }

  @GetMapping("/getUsername")
  public ResponseEntity<?> findUserByUsername(String username)
  {

    BasicUserInfoGet name = userService.findUserByUsername(username);
    if(name == null){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(name);
  }
  @GetMapping("/getIdName")
  public ResponseEntity<?> findUserByUsernameAndId(Integer userId, String username)
  {
    List<BasicUserInfoGet> all = userService.findUserByUsernameAndId(userId,username);
    if(all.isEmpty()){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(all);
  }
}

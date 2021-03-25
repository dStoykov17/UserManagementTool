package bg.codeacademy.PersonalProject.user;

import bg.codeacademy.PersonalProject.Models.BasicUserInfoGet;
import bg.codeacademy.PersonalProject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService
{
  private final UserDao userDao;

  private final PasswordEncoder passwordEncoder;
  @Autowired
  public UserService(UserDao userDao, PasswordEncoder passwordEncoder)
  {
    this.userDao = userDao;
    this.passwordEncoder = passwordEncoder;
  }

  public List<BasicUserInfoGet> getUserById(Integer userId)
  {
    return userDao.getUser(userId);
  }

  public BasicUserInfoGet findUserByUsername(String username)
  {

    return userDao.findUserByUsername(username);
  }

  public void addUser(String username, String password)
  {
    userDao.addUser(username, passwordEncoder.encode(password));
  }

  //login purposes
  public User findByUsername(String username)
  {
    return userDao.findByUsername(username);
  }

  public List<BasicUserInfoGet> findUserByUsernameAndId(Integer userId, String username)
  {

    return userDao.findUserByUsernameAndId(userId, username);
  }
//tests
  public void deleteUser(Integer userId)
  {
     userDao.deleteUser(userId);
  }

}


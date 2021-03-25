package bg.codeacademy.PersonalProject.config;

import bg.codeacademy.PersonalProject.Groups.GroupService;
import bg.codeacademy.PersonalProject.Models.GroupInfoGet;
import bg.codeacademy.PersonalProject.User;
import bg.codeacademy.PersonalProject.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
  private final UserService userService;
  private final GroupService groupService;
  @Autowired
  public UserDetailsServiceImpl(UserService userService, GroupService groupService)
  {
    this.userService = userService;
    this.groupService = groupService;
  }
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
  {
    User user = userService.findByUsername(username);
    List<String> roles= groupService.getAllGroups(user.getId()).stream()
        .map(GroupInfoGet::getGroupName).collect(Collectors.toList());
    {
      org.springframework.security.core.userdetails.User.UserBuilder builder =
          org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
              .password(user.getPassword())
          .roles(roles.toArray(new String[roles.size()]));

      return builder.build();

    }
  }
}


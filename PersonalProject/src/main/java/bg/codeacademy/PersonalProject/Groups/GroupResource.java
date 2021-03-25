package bg.codeacademy.PersonalProject.Groups;

import bg.codeacademy.PersonalProject.Models.GroupInfoGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/group")
public class GroupResource
{
  private final GroupService groupService;

  @Autowired
  public GroupResource(GroupService groupService)
  {
    this.groupService = groupService;
  }

  @GetMapping("/getAll")
  public ResponseEntity<?> getAllGroups(Integer userId)
  {
    List<GroupInfoGet> all = groupService.getAllGroups(userId);
        if(all.isEmpty()){
          return ResponseEntity.notFound().build();
        }
          return ResponseEntity.ok(all);
  }

  @GetMapping("/getGroup")
  public ResponseEntity<?> getUserGroupByGroupId(Integer groupId)
  {
    List<GroupInfoGet> all = groupService.getUserGroupByGroupId(groupId);
    if (all.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(all);
  }

  @PutMapping("/change")
  public void changeUserGroup(Integer userId, String group_name)
  {
    groupService.changeUserGroup(userId, group_name);
  }

  @GetMapping("/getGroup2")
  public ResponseEntity<?> getUserGroupByGroupNameAndUserId(Integer userId, String group_name)
  {
    List<GroupInfoGet> all = groupService.getUserGroupByGroupNameAndUserId(userId, group_name);
    if (all.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(all);
  }

  @PostMapping("/add")
  public void addNewGroup(String group_name, Integer userId)
  {
    groupService.addNewGroup(group_name, userId);
  }
}
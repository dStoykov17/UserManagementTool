package bg.codeacademy.PersonalProject.Groups;

import bg.codeacademy.PersonalProject.Models.GroupInfoGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService
{
  private final GroupDao groupDao;

  @Autowired
  public GroupService(GroupDao groupDao)
  {
    this.groupDao = groupDao;
  }

  public List<GroupInfoGet> getAllGroups(Integer userId)
  {
    return groupDao.getAllGroups(userId);
  }

  public List<GroupInfoGet> getUserGroupByGroupId(Integer groupId){
    return groupDao.getUserGroupByGroupId(groupId);
  }

  public void changeUserGroup(Integer userId, String group_name){
    groupDao.changeUserGroup(userId,group_name);
  }

  public void addNewGroup(String group_name, Integer userId)
  {
    groupDao.addNewGroup(group_name,userId);
  }

  public List<GroupInfoGet> getUserGroupByGroupNameAndUserId(Integer userId, String group_name){
   return groupDao.getUserGroupByGroupNameAndUserId(userId, group_name);
  }
  //tests only
  public void deleteGroup(Integer groupId){
    groupDao.deleteGroup(groupId);
  }
}

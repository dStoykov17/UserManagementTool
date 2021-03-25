package bg.codeacademy.PersonalProject.Models;

public class GroupInfoGet
{
  private String groupName;
  private Integer groupId;
  private Integer id;

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public Integer getGroupId()
  {
    return groupId;
  }

  public void setGroupId(Integer groupId)
  {
    this.groupId = groupId;
  }

  public String getGroupName()
  {
    return groupName;
  }

  public void setGroupName(String groupName)
  {
    this.groupName = groupName;
  }
}

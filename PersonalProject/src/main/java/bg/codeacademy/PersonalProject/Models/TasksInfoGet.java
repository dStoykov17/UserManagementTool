package bg.codeacademy.PersonalProject.Models;

public class TasksInfoGet
{
  private String desc;

  private  String time;

  private Integer id;

  private Integer taskId;
  private Integer groupId;

  public Integer getGroupId()
  {
    return groupId;
  }

  public void setGroupId(Integer groudId)
  {
    this.groupId = groudId;
  }

  public Integer getTaskId()
  {
    return taskId;
  }

  public void setTaskId(Integer taskId)
  {
    this.taskId = taskId;
  }

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getTime()
  {
    return time;
  }

  public void setTime(String time)
  {
    this.time = time;
  }

  public String getDesc()
  {
    return desc;
  }

  public void setDesc(String desc)
  {
    this.desc = desc;
  }
}

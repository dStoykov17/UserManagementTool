package bg.codeacademy.PersonalProject.tasks;

import bg.codeacademy.PersonalProject.Models.TasksInfoGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TasksService
{
  private final TasksDao tasksDao;

  @Autowired
  public TasksService(TasksDao paymentDao)
  {
    this.tasksDao = paymentDao;
  }

  public List<TasksInfoGet> getTasks(Integer taskId)
  {
    return tasksDao.getTasks(taskId);
  }

  public void addTask(String desc1, Integer assigneId)
  {
    tasksDao.addTask(desc1, assigneId);
  }

  public List<TasksInfoGet> showAllTasksByUserId(Integer userId)
  {
    return tasksDao.showAllTasksByUserId(userId);
  }

  public List<TasksInfoGet> showAllTasksByDesc1(String desc1)
  {
    return tasksDao.showAllTasksByDesc1(desc1);
  }
  public List<TasksInfoGet> showAtaskByIdAndDesc(String desc1, Integer taskId){
    return tasksDao.showAtaskByIdAndDesc(desc1, taskId);
  }
//tests only
  public void deleteTask(Integer taskId){
    tasksDao.deleteTask(taskId);
  }

  public void updateTaskDesc(String desc1, Integer taskId){
    tasksDao.updateTaskDesc(desc1, taskId);
  }
  public List<TasksInfoGet> showOtherUsersTasksByGroupId(Integer groupId, Integer userId){
    return tasksDao.showOtherUsersTasksByGroupId(groupId,userId);
  }
}


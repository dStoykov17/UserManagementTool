package bg.codeacademy.PersonalProject.tasks;

import bg.codeacademy.PersonalProject.Models.TasksInfoGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project/tasks")
public class TasksResource
{
  private final TasksService tasksService;

  @Autowired
  public TasksResource(TasksService tasksService)
  {
    this.tasksService = tasksService;
  }

  // get tasks by taskId
  @GetMapping
  public ResponseEntity<?> getTasks(Integer taskId)
  {
    List<TasksInfoGet> all = tasksService.getTasks(taskId);
    if (all.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(all);
  }

  @PostMapping("/add")
  public void addTask(String desc1, Integer assigneId)
  {
    tasksService.addTask(desc1, assigneId);
  }

  //get tasks by userId
  @GetMapping("/taskId")
  public ResponseEntity<?> showAllTasksByUserId(Integer assigneId)
  {
    List<TasksInfoGet> all = tasksService.showAllTasksByUserId(assigneId);
    if (all.isEmpty()) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(all);
  }

  @GetMapping("/taskName")
  public ResponseEntity<?> showAllTasksByDesc1(String desc1)
  {
    List<TasksInfoGet> all = tasksService.showAllTasksByDesc1(desc1);
    if (all.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(all);
  }

  @GetMapping("/taskIdDesc")
  public ResponseEntity<?> showAtaskByIdAndDesc(String desc1, Integer taskId)
  {
    List<TasksInfoGet> all = tasksService.showAtaskByIdAndDesc(desc1,taskId);
    if (all.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(all);
  }
  @PutMapping ("/change")
  public void updateTaskDesc(String desc1, Integer taskId){

    tasksService.updateTaskDesc(desc1,taskId);
  }
  @GetMapping("/userTasks")
  public ResponseEntity<?> showOtherUsersTasksByGroupId(Integer groupId, Integer userId){

    List<TasksInfoGet> all = tasksService.showOtherUsersTasksByGroupId(groupId, userId);
    if(all.isEmpty()){
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(all);
  }

}


package bg.codeacademy.PersonalProject.tasks;

import bg.codeacademy.PersonalProject.Models.TasksInfoGet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TasksDao
{
  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  @Autowired
  public TasksDao(NamedParameterJdbcOperations namedParameterJdbcOperations)
  {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }


  public List<TasksInfoGet> getTasks(Integer taskId)
  {
    List<TasksInfoGet> results = null;
    try {
      results = namedParameterJdbcOperations.query("SELECT taskId,assigneId,desc1,to_char(assgnment_dt) FROM tasks where taskId=:taskId",
          new MapSqlParameterSource("taskId", taskId), new RowMapper<TasksInfoGet>()
          {
            @Override
            public TasksInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              TasksInfoGet rs = new TasksInfoGet();
              rs.setId(resultSet.getInt("taskId"));
              rs.setDesc(resultSet.getString("desc1"));
              rs.setTime(resultSet.getString("to_char(assgnment_dt)"));
              return rs;
            }
          });
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return results;
  }


  public void addTask(String desc1, Integer assigneId)
  {
    try {
      namedParameterJdbcOperations.update("INSERT INTO tasks(desc1,assgnment_dt,assigneId) " +
          "VALUES(:desc1,SYSDATE,:assigneId)", new MapSqlParameterSource("desc1", desc1).addValue("assigneId", assigneId));
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
  }


  public List<TasksInfoGet> showAllTasksByUserId(Integer assigneId)
  {
    List<TasksInfoGet> results = null;
    try {
      results = namedParameterJdbcOperations.query("SELECT * FROM tasks WHERE assigneId=:assigneId",
          new MapSqlParameterSource("assigneId", assigneId), new RowMapper<TasksInfoGet>()
          {
            @Override
            public TasksInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              TasksInfoGet rs = new TasksInfoGet();
              rs.setId(resultSet.getInt("assigneId"));
              rs.setDesc(resultSet.getString("desc1"));
              return rs;
            }
          });
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return results;
  }


  public List<TasksInfoGet> showAllTasksByDesc1(String desc1)
  {

    List<TasksInfoGet> results = null;
    try {
      results = namedParameterJdbcOperations.query("Select assigneId,desc1,assgnment_dt FROM tasks WHERE desc1 =:desc1",
          new MapSqlParameterSource("desc1", desc1), new RowMapper<TasksInfoGet>()
          {
            @Override
            public TasksInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              TasksInfoGet rs = new TasksInfoGet();
              rs.setId(resultSet.getInt("assigneId"));
              rs.setDesc(resultSet.getString("desc1"));
              rs.setTime(resultSet.getString("assgnment_dt"));
              return rs;
            }
          });
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return results;
  }

  public List<TasksInfoGet> showAtaskByIdAndDesc(String desc1, Integer taskId)
  {

    List<TasksInfoGet> results = null;
    try {
      results = namedParameterJdbcOperations.query("Select assigneId,desc1,assgnment_dt,taskId FROM tasks WHERE desc1 =:desc1 AND taskId = :taskId",
          new MapSqlParameterSource("desc1", desc1).addValue("taskId", taskId), new RowMapper<TasksInfoGet>()
          {
            @Override
            public TasksInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              TasksInfoGet rs = new TasksInfoGet();
              rs.setId(resultSet.getInt("assigneId"));
              rs.setDesc(resultSet.getString("desc1"));
              rs.setTime(resultSet.getString("assgnment_dt"));
              rs.setTaskId(resultSet.getInt("taskId"));
              return rs;
            }
          });
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return results;
  }

  public void deleteTask(Integer taskId)
  {
    try {
      final String sql = "Delete  FROM tasks WHERE taskId = :taskId";

      final SqlParameterSource sp = new MapSqlParameterSource("taskId", taskId);

      namedParameterJdbcOperations.update(sql, sp);
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
  }


  public void updateTaskDesc(String desc1, Integer taskId)
  {
     namedParameterJdbcOperations.update("UPDATE tasks SET desc1 = :desc1 WHERE taskId= :taskId",
        new MapSqlParameterSource("desc1", desc1).addValue("taskId", taskId));
  }

  public List<TasksInfoGet> showOtherUsersTasksByGroupId(Integer groupId, Integer userId){

    List<TasksInfoGet> results = null;
    try {
      results = namedParameterJdbcOperations.query("SELECT assigneId, taskId, desc1,assgnment_dt, user_groups.groupId " +
                                             "FROM tasks  " +
                                             "INNER JOIN user_groups ON tasks.assigneId = user_groups.userId " +
                                             "WHERE user_groups.groupId = :groupId",
          new MapSqlParameterSource("userId", userId).addValue("groupId",groupId), new RowMapper<TasksInfoGet>()
          {
            @Override
            public TasksInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              TasksInfoGet rs = new TasksInfoGet();
              rs.setId(resultSet.getInt("assigneId"));
              rs.setTaskId(resultSet.getInt("taskId"));
              rs.setTime(resultSet.getString("assgnment_dt"));
              rs.setDesc(resultSet.getString("desc1"));
              rs.setGroupId(resultSet.getInt("groupId"));

              return rs;
            }
          });
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return results;
  }
}

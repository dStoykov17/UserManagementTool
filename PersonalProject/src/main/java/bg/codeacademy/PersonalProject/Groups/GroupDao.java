package bg.codeacademy.PersonalProject.Groups;

import bg.codeacademy.PersonalProject.Models.GroupInfoGet;
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
public class GroupDao
{
  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  @Autowired
  public GroupDao(NamedParameterJdbcOperations namedParameterJdbcOperations)
  {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }


  public List<GroupInfoGet> getAllGroups(Integer userId)
  {

    List<GroupInfoGet> results = null;
    try {

      results = namedParameterJdbcOperations.query("SELECT userId,groupId,group_name FROM user_groups WHERE userId = :userId",
          new MapSqlParameterSource("userId",userId), new RowMapper<GroupInfoGet>()
          {
            @Override
            public GroupInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              GroupInfoGet re = new GroupInfoGet();
              re.setId(resultSet.getInt("groupId"));
              re.setGroupId(resultSet.getInt("userId"));
              re.setGroupName(resultSet.getString("group_name"));
              return re;
            }
          });
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return results;
  }

  public List<GroupInfoGet> getUserGroupByGroupId(Integer groupId)
  {

    List<GroupInfoGet> results = null;
    try {
      results = namedParameterJdbcOperations.query("SELECT * FROM user_groups WHERE groupId=:groupId",
          new MapSqlParameterSource("groupId", groupId), new RowMapper<GroupInfoGet>()
          {
            @Override
            public GroupInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              GroupInfoGet rs = new GroupInfoGet();
              rs.setId(resultSet.getInt("groupId"));
              rs.setGroupId(resultSet.getInt("userId"));
              rs.setGroupName(resultSet.getString("group_name"));
              return rs;
            }
          });                 
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return results;
  }

  public void changeUserGroup(Integer userId, String group_name)
  {
    try {
      namedParameterJdbcOperations.update("UPDATE user_groups SET group_name = :group_name WHERE userId= :userId",
          new MapSqlParameterSource("group_name", group_name).addValue("userId", userId));
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  public List<GroupInfoGet> getUserGroupByGroupNameAndUserId(Integer userId, String group_name)
  {
    List<GroupInfoGet> results = null;
    try {
      results = namedParameterJdbcOperations.query("SELECT * FROM user_groups WHERE userId = :userId AND group_name = :group_name",
          new MapSqlParameterSource("userId", userId).addValue("group_name", group_name), new RowMapper<GroupInfoGet>()
          {
            @Override
            public GroupInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              GroupInfoGet rs = new GroupInfoGet();
              rs.setId(resultSet.getInt("groupId"));
              rs.setGroupId(resultSet.getInt("userId"));
              rs.setGroupName(resultSet.getString("group_name"));
              return rs;
            }
          });
    }   catch (DataAccessException e) {
      e.printStackTrace();
    }
    return results;
  }
  public void addNewGroup(String group_name, Integer userId)
  {
    try {
      namedParameterJdbcOperations.update("INSERT INTO user_groups(group_name,userId) VALUES(:group_name,:userId)",
          new MapSqlParameterSource("group_name", group_name).addValue("userId", userId));
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
  }
  public void deleteGroup(Integer groupId)
  {
    try {
      final String sql = "Delete  FROM user_groups WHERE groupId = :groupId";

      final SqlParameterSource sp = new MapSqlParameterSource("groupId", groupId);

      namedParameterJdbcOperations.update(sql, sp);
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
  }
}

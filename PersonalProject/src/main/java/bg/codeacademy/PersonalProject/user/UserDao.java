package bg.codeacademy.PersonalProject.user;

import bg.codeacademy.PersonalProject.Models.BasicUserInfoGet;
import bg.codeacademy.PersonalProject.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


@Repository
public class UserDao
{
  private final NamedParameterJdbcOperations namedParameterJdbcOperations;

  @Autowired
  public UserDao(NamedParameterJdbcOperations namedParameterJdbcOperations)
  {
    this.namedParameterJdbcOperations = namedParameterJdbcOperations;
  }

  public List<BasicUserInfoGet> getUser(Integer userId)
  {
    List<BasicUserInfoGet> basicUserInfoGets = null;
    try {
      basicUserInfoGets = namedParameterJdbcOperations.query("Select username,userId FROM users1 WHERE userId =:userId",
          new MapSqlParameterSource("userId", userId), new RowMapper<BasicUserInfoGet>()
          {
            @Override
            public BasicUserInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              BasicUserInfoGet rs = new BasicUserInfoGet();
              rs.setId((resultSet.getInt("userId")));
              rs.setUsername(resultSet.getString("username"));
              return rs;
            }
          });
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return basicUserInfoGets;
  }

  public void addUser(String username, String password)
  {
    try {
      namedParameterJdbcOperations.update("INSERT INTO users1(username,password) VALUES(:username,:password)",
          new MapSqlParameterSource("username", username).addValue("password", password));
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
  }

  public BasicUserInfoGet findUserByUsername(String username)
  {
    BasicUserInfoGet results = null;
    try {
      results = namedParameterJdbcOperations.queryForObject("Select username,userId FROM users1 WHERE username =:username",
          new MapSqlParameterSource("username", username), new RowMapper<BasicUserInfoGet>()
          {
            @Override
            public BasicUserInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              BasicUserInfoGet rs = new BasicUserInfoGet();
              rs.setId(resultSet.getInt("userId"));
              rs.setUsername(resultSet.getString("username"));
              return rs;
            }
          });
    }
    catch (EmptyResultDataAccessException e) {
      System.out.println("Error!");
    }
    return results;
  }

  public List<BasicUserInfoGet> findUserByUsernameAndId(Integer userId, String username)
  {
    List<BasicUserInfoGet> basicUserInfoGets = null;
    try {
      basicUserInfoGets = namedParameterJdbcOperations.query("Select username,userId FROM users1 WHERE userId =:userId AND username = :username",
          new MapSqlParameterSource("userId", userId).addValue("username", username), new RowMapper<BasicUserInfoGet>()
          {
            @Override
            public BasicUserInfoGet mapRow(ResultSet resultSet, int i) throws SQLException
            {
              BasicUserInfoGet rs = new BasicUserInfoGet();
              rs.setId((resultSet.getInt("userId")));
              rs.setUsername(resultSet.getString("username"));
              return rs;
            }
          });
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
    return basicUserInfoGets;
  }

  //method for authorisation only
  public User findByUsername(String username)
  {
    User user = null;
    try {
      user = namedParameterJdbcOperations.queryForObject("Select username,userId,password FROM users1 WHERE username =:username",
          new MapSqlParameterSource("username", username), new RowMapper<User>()
          {
            @Override
            public User mapRow(ResultSet resultSet, int i) throws SQLException
            {
              User rs = new User();
              rs.setId(resultSet.getInt("userId"));
              rs.setUsername(resultSet.getString("username"));
              rs.setPassword(resultSet.getString("password"));
              return rs;
            }
          });
    }
    catch (EmptyResultDataAccessException e) {
      System.out.println("Error!");
    }
    return user;
  }

  public void deleteUser(Integer userId)
  {
    try {
      final String sql = "Delete  FROM users1 WHERE userId = :userId";

      final SqlParameterSource sp = new MapSqlParameterSource("userId", userId);

      namedParameterJdbcOperations.update(sql, sp);
    }
    catch (DataAccessException e) {
      e.printStackTrace();
    }
  }
}


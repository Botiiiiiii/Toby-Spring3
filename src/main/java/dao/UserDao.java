package dao;


import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class UserDao {
    private DataSource dataSource;

    private JdbcTemplate jdbcTemplate;

    public UserDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public void add(final User user) {
        this.jdbcTemplate.update("insert into users(id,name,password) values (?, ?, ?);",
                user.getId(),user.getName(),user.getPassword());
    }

    public User findById(String id) throws ClassNotFoundException {
        String sql = "select * from users where id = ?";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                User user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.queryForObject(sql, rowMapper, id);

    }

    public int getCount() throws SQLException, ClassNotFoundException {
        return this.jdbcTemplate.queryForObject("select count(*) from users;", Integer.class);
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        this.jdbcTemplate.update("delete from users");
    }

    public List<User> getAll(){
        String sql = "select * from users order by id;";
        RowMapper<User> rowMapper = new RowMapper<User>() {
            public User mapRow(ResultSet rs, int rowNum) throws SQLException{
                User user = new User(rs.getString("id"),rs.getString("name"),rs.getString("password"));
                return user;
            }
        };
        return this.jdbcTemplate.query(sql,rowMapper);
    }
}

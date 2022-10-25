package dao;


import domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.*;
import java.util.Map;

public class UserDao {
    private DataSource dataSource;

    public UserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void jdbcContextWithStatemnetStrategy(StatementStrategy stmt) throws SQLException{
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = dataSource.getConnection();
            StatementStrategy strategy = new DeleteAllStatment();
            ps = stmt.makePreparedStatement(c);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (ps != null) {
                try{
                    ps.close();
                } catch (SQLException e){
                }
            }
            if (c != null){
                try {
                    c.close();
                }catch (SQLException e){
                }
            }
        }
    }
    public void deleteAll() throws SQLException {
        StatementStrategy st = new DeleteAllStatment();
        jdbcContextWithStatemnetStrategy(st);
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int count = 0;
        try {
            c = dataSource.getConnection();
            ps = c.prepareStatement("select count(*) from users");
            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (rs != null) {
                try{
                    rs.close();
                } catch (SQLException e){
                }
            }
            if (ps != null) {
                try{
                    ps.close();
                } catch (SQLException e){
                }
            }
            if (c != null){
                try {
                    c.close();
                }catch (SQLException e){
                }
            }
        }
    }

    public void add(User user) throws SQLException {
        StatementStrategy st = new AddStatement(user);
        jdbcContextWithStatemnetStrategy(st);
    }

    public User findById(String id) {
        try {
            Connection c = dataSource.getConnection();

            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            ResultSet rs = pstmt.executeQuery();
            User user = null;
            if(rs.next()){
                user = new User(rs.getString("id"), rs.getString("name"),
                        rs.getString("password"));
            }

            rs.close();
            pstmt.close();
            c.close();
            if(user == null) throw new EmptyResultDataAccessException(1);

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

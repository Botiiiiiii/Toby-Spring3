package dao;

import domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    @Test
    void addAndSelect() {
        UserDao userDao = new UserDao();
        String id = "10";
        userDao.add(new User(id,"test","1234"));
        User user = userDao.findById(id);
    }
}
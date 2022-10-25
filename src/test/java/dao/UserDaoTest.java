package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserDaoTest {
    private User user1;
    private User user2;
    private User user3;
    @BeforeEach
    void setup(){
        user1 = new User("1","test1","1234");
        user2 = new User("2","test2","2222");
        user3 = new User("3","test3","3333");
    }

    @Test
    void addAndGet() {
        UserDao userDao = new DaoFactory().userDao();
        userDao.add(user1);
        User test = userDao.findById(user1.getId());
        Assertions.assertEquals(user1.getId(), test.getId());
    }
}
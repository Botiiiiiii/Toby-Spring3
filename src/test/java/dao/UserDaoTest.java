package dao;

import domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = DaoFactory.class)
class UserDaoTest {
    @Autowired
    ApplicationContext context;
    UserDao userDao;
    private User user1;
    private User user2;
    private User user3;

    @BeforeEach
    void setup(){
        this.user1 = new User("1","test1","1234");
        this.user2 = new User("2","test2","2222");
        this.user3 = new User("3","test3","3333");

        this.userDao = context.getBean("userDao",UserDao.class);
    }

    @Test
    void addAndGet() throws SQLException, ClassNotFoundException {
        this.userDao.deleteAll();
        Assertions.assertEquals(this.userDao.getCount(),0);
        this.userDao.add(this.user1);
        User test = this.userDao.findById(this.user1.getId());
        Assertions.assertEquals(this.user1.getId(), test.getId());

        List<User> users0 = userDao.getAll();
        Assertions.assertEquals(users0.size(),0);
    }
}
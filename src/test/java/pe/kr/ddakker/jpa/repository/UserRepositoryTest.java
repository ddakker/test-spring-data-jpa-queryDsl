package pe.kr.ddakker.jpa.repository;

import com.mysema.query.jpa.impl.JPAQuery;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import pe.kr.ddakker.jpa.AppConfig;
import pe.kr.ddakker.jpa.domain.QUser;
import pe.kr.ddakker.jpa.domain.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by ddakker on 2015-04-09.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@Transactional
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UserRepositoryTest {
    @Autowired private UserRepository userRepository;

    @PersistenceContext private EntityManager entityManager;

    /**
     * Spring JPA CRUD 기능을 이용한다면
     */
    @Before
    public void create() {
        User inputUser = new User();
        inputUser.setId(System.currentTimeMillis());
        inputUser.setName("ddakker");
        inputUser.setAge(33);
        inputUser.setRegDt(new Date());
        userRepository.save(inputUser);

        inputUser = new User();
        inputUser.setId(System.currentTimeMillis());
        inputUser.setName("petitjjang");
        inputUser.setAge(33);
        inputUser.setRegDt(new Date());
        userRepository.save(inputUser);

        inputUser = new User();
        inputUser.setId(System.currentTimeMillis());
        inputUser.setName("sisigi");
        inputUser.setAge(1);
        inputUser.setRegDt(new Date());
        userRepository.save(inputUser);

        assertEquals("전체 사이즈", 3, userRepository.count());
    }

    /**
     * Method Name Query를 사용한다면
     */
    @Test
    public void testRepository_methodNameQuery() {
        List<User> userList = userRepository.findByName("ddakker");
        assertEquals("갯수는", 1, userList.size());
    }
    /**
     * Spring Data JPA & QueryDSL Predicate 사용한다면
     */
    @Test
    public void testRepository_Predicate() {
        String name = "dda%";
        int age = 33;

        QUser user = QUser.user;
        Page<User> page =  userRepository.findAll(user.name.like(name).and(user.age.eq(age)), new PageRequest(0,10));
        assertEquals("검색 결과", 1, page.getNumberOfElements());

        Iterable<User> users = userRepository.findAll(user.age.eq(age));
        for (User u : users) {
            System.out.println("iterable user: " + u.getId() + ", " + u.getName() + ", " + u.getAge() + "," + u.getRegDt());
        }
    }


    /**
     * Spring Data JPA & QueryDSL 확장 기능을 이용한다면
     * @throws Exception
     */
    @Test
    public void testRepository_support() throws Exception {
        UserRepositorySupport userRepositorySupport = new UserRepositorySupport(User.class);
        userRepositorySupport.setEntityManager(entityManager);
        assertEquals("큰 나이", 33, userRepositorySupport.getMaxAge());
        assertEquals("작은 나이", 1, userRepositorySupport.getMinAge());

    }

    /**
     * 직접 QueryDSL을 써본다면..
     *      - 이것을 어느 영역에 둬야 할까...
     */
    @Test
    public void test_Dsl() {
        JPAQuery query = new JPAQuery(entityManager);
        QUser qUser = QUser.user;
        List<User> userList = query.from(qUser).where(qUser.name.eq("ddakker")).list(qUser);

        for (User u : userList) {
            System.out.println("list user: " + u.getId() + ", " + u.getName() + ", " + u.getAge() + "," + u.getRegDt());
        }
    }
}

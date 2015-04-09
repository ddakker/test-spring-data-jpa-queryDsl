package pe.kr.ddakker.jpa.repository;

import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import pe.kr.ddakker.jpa.domain.QUser;
import pe.kr.ddakker.jpa.domain.User;

/**
 * Created by ddakker on 2015-04-09.
 */
public class UserRepositorySupport extends QueryDslRepositorySupport {
    public UserRepositorySupport(Class<User> user) {
        super(user);
    }

    public int getMinAge() {
        QUser qUser = QUser.user;
        return from(qUser).uniqueResult(qUser.age.min());
    }

    public int getMaxAge() {
        QUser qUser = QUser.user;
        return from(qUser).uniqueResult(qUser.age.max());
    }
}

package pe.kr.ddakker.jpa.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import pe.kr.ddakker.jpa.domain.User;

import java.util.List;

/**
 * Created by ddakker on 2015-04-09.
 */
public interface UserRepository extends CrudRepository<User, Long>, QueryDslPredicateExecutor<User> {

    public List<User> findByName(String name);
}
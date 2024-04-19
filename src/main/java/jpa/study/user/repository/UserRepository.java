package jpa.study.user.repository;

import jpa.study.user.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthId(Long oauthId);

    @Override
    @Query("select u from User u where u.isDelete = false")
    List<User> findAll();
}

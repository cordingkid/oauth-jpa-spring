package jpa.study.user.repository;

import jpa.study.common.exception.CustomException;
import jpa.study.common.exception.ErrorCode;
import jpa.study.user.application.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

import static jpa.study.common.exception.ErrorCode.*;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByOauthId(Long oauthId);

    @Override
    @Query("select u from User u where u.isDelete = false")
    List<User> findAll();


    default User getById(Long id) {
        User user = findById(id)
                .orElseThrow(() -> new CustomException(RESOURCE_NOT_FOUND));

        if (user.isDelete()) {
            throw new CustomException(DELETED_USER_EXCEPTION);
        }

        return user;
    }
}

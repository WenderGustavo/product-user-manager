package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.repositories.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);
    boolean existsByLogin(String login);
}

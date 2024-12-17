package com.wendergustavo.SpringBootCleanarchApplication.infrastructure.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByLogin(String login);
    boolean existsByLogin(String login);
}

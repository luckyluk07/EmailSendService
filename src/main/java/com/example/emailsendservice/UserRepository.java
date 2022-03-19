package com.example.emailsendservice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Modifying
    @Query("update user u set u.email = :email, u.username = :username where u.id = :id")
    void updateUser(@Param("id") Long id, @Param("email") String email, @Param("username") String username);
}

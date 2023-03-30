package com.security.demo.repo;

import com.security.demo.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<Users,Integer> {
    @Query(name = "findUserByUserName", value = "select u from Users u where u.username = :username")
    public Users findUserByUsername(@Param("username") String username);
}

package com.example.webappcrud.dao;

import com.example.webappcrud.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select distinct user from User user join fetch user.roles roles")
    List<User> findAll();

    @Query("select distinct user from User user join fetch user.roles roles where user.id=:id")
    Optional<User> findById(@Param("id") Integer id);

    @Query("select distinct user from User user join fetch user.roles roles where user.login=:login")
    User findUserByLogin(@Param("login") String login);
}
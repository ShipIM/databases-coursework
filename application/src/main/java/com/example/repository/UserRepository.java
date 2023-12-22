package com.example.repository;

import com.example.model.entity.User;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.RepositoryDefinition;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

@RepositoryDefinition(domainClass = User.class, idClass = String.class)
public interface UserRepository {
    @Query("select _user.login as email, p.password as password from _user " +
            "join password p on _user.login = p.user_login " +
            "where login = :email")
    Optional<User> findByEmail(@Param("email") String email);

    @Modifying
    @Query("call create_user(:login, :birth_date, :registration_date, :password)")
    void createUser(@Param("login") String login,
                    @Param("birth_date") Date birthDate,
                    @Param("registration_date") Date registrationDate,
                    @Param("password") String password);

    @Query("select exists(select * from _user where login = :login)")
    boolean isUserExists(@Param("login") String login);
}

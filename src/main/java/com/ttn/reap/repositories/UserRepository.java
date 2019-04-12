package com.ttn.reap.repositories;

import com.ttn.reap.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    List<User> findAll();

    User findByFullName(String fullName);

    Optional<User> findByEmailAndPassword(String email, String password);

    List<User> findByFullNameLike(String fullNamePattern);

    Optional<User> findByEmail(String email);

    Optional<User> findByResetToken(String resetToken);

    @Query("SELECT email FROM User")
    List<String> findAllEmail();
}

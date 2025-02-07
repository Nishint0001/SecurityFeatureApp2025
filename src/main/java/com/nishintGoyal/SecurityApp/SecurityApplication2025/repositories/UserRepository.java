package com.nishintGoyal.SecurityApp.SecurityApplication2025.repositories;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long>
{
    Optional<UserEntity> findByEmail(String email);
}

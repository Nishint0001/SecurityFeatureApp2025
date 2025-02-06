package com.nishintGoyal.SecurityApp.SecurityApplication2025.repositories;

import com.nishintGoyal.SecurityApp.SecurityApplication2025.entities.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity,Long>
{

}

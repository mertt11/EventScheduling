package com.example.demo.repository;

import com.example.demo.entity.ModelUser;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelUserRepository extends JpaRepository<ModelUser,Long> {
    ModelUser findByEmail(String email);
    boolean existsByNameAndLastNameIgnoreCase(String name,String lastName);
}

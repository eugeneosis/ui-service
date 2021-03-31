package com.ru.microservice.repository;

import com.ru.microservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMappingRepository extends JpaRepository<User, Long> {
}

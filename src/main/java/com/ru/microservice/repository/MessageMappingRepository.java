package com.ru.microservice.repository;

import com.ru.microservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageMappingRepository extends JpaRepository<Message, Long> {
}

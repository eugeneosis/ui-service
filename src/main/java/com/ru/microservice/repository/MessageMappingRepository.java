package com.ru.microservice.repository;

import com.ru.microservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageMappingRepository extends JpaRepository<Message, Long> {

    List<Message> findMessageByTextIgnoreCase(String search);
}
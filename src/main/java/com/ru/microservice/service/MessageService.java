package com.ru.microservice.service;

import com.ru.microservice.model.Message;
import com.ru.microservice.repository.MessageMappingRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageMappingRepository messageRepository;

    public List<Message> findAll(String search) {
        if (search == null || search.isEmpty()) {
            return messageRepository.findAll();
        } else {
            return messageRepository.findMessageByTextIgnoreCase(search);
        }
    }

    public Long count() { return messageRepository.count(); }

    public List<Message> findAll() {
        return messageRepository.findAll();
    }

    public Map<String, Integer> getMessages() {
        HashMap<String, Integer> messages = new HashMap<>();
        findAll().forEach(message ->
                messages.put(message.getText(), Math.toIntExact(message.getId())));
        return messages;
    }
}
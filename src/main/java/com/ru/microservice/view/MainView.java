package com.ru.microservice.view;

import com.ru.microservice.model.Message;
import com.ru.microservice.repository.MessageMappingRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Component;

@Component
@Route
public class MainView extends VerticalLayout {

    private final MessageMappingRepository messageMappingRepository;
    final Grid<Message> grid;


    public MainView(MessageMappingRepository messageMappingRepository) {
        this.messageMappingRepository = messageMappingRepository;
        this.grid = new Grid<>(Message.class);
        add(grid);
        listMessages();
    }

    private void listMessages() {
        grid.setItems(messageMappingRepository.findAll());
    }
}

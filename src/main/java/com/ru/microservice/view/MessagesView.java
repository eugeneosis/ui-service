package com.ru.microservice.view;

import com.ru.microservice.model.Message;
import com.ru.microservice.service.MessageService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@Route(value = "messages", layout = Layout.class)
@PageTitle("Сообщения")
public class MessagesView extends VerticalLayout {

    private final MessageService messageService;

    Grid<Message> grid = new Grid<>(Message.class);
    TextField search = new TextField();

    public MessagesView(MessageService messageService) {
        this.messageService = messageService;
        setSizeFull();
        configureGrid();
        configureFilter();
        add(search, grid);
        listMessages();
    }

    @SneakyThrows
    private String apply(Message message) {
        if ((message.getDate() == null)) {
            return " ";
        } else {
            return new SimpleDateFormat("HH:mm:ss").format(new Date(message.getDate() * 1000L));
        }
    }

    private void configureGrid() {
        grid.setSizeFull();
        //grid.removeColumnByKey("user");
        grid.removeColumnByKey("date");
        grid.setColumns("id", "text", "languageCode");
        /*grid.addColumn(message -> {
            Long user = message.getUser().getId();
            return user == null ? "-" : user;
        }).setHeader("Chat Id").setSortable(true);*/
        grid.addColumn(this::apply).setHeader("Date").setSortable(true);
    }

    private void configureFilter() {
        search.setPlaceholder("Поиск по сообщениям");
        search.setClearButtonVisible(true);
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.addValueChangeListener(x -> listMessages());
    }

    private void listMessages() {
        grid.setItems(messageService.findAll(search.getValue()));
    }
}
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
import org.springframework.beans.factory.ObjectFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static java.time.format.DateTimeFormatter.ofPattern;


@PageTitle("Сообщения")
@Route(value = "messages", layout = Layout.class)
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

    private LocalDateTime getLocalDateTime(Message message) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getDate() * 1000),
                TimeZone.getDefault().toZoneId());
    }

    @SneakyThrows
    private String getDate(Message message, ObjectFactory<DateTimeFormatter> objectFactory) {
        return getTime(message, objectFactory);
    }

    @SneakyThrows
    private String getTime(Message message, ObjectFactory<DateTimeFormatter> objectFactory) {
        return getString(message, objectFactory);
    }

    private String getString(Message message, ObjectFactory<DateTimeFormatter> objectFactory) {
        if (message.getDate() == null) return " ";
        else {
            LocalDateTime time = getLocalDateTime(message);
            DateTimeFormatter formatter = objectFactory.getObject();
            return time.format(formatter);
        }
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeAllColumns();
        grid.addColumn(Message::getId).setHeader("Id").setSortable(true);
        grid.addColumn(Message::getText).setHeader("Сообщение").setSortable(true);
        grid.addColumn(Message::getLanguageCode).setHeader("Язык").setSortable(true);
        grid.addColumn(message -> getDate(message, () -> ofPattern("yyyy-MM-dd"))).setHeader("Дата").setSortable(true);
        grid.addColumn(message -> getTime(message, () -> ofPattern("HH:mm:ss"))).setHeader("Время").setSortable(true);
    }

    private void configureFilter() {
        search.setPlaceholder("Поиск сообщений");
        search.setClearButtonVisible(true);
        search.setValueChangeMode(ValueChangeMode.LAZY);
        search.addValueChangeListener(x -> listMessages());
    }

    private void listMessages() {
        grid.setItems(messageService.findAll(search.getValue()));
    }
}
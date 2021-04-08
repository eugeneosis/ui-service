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

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

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
    private String getDateTimeStr(Message message) {
        if ((message.getDate() == null)) {
            return " ";
        } else {
            LocalDateTime time =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getDate() * 1000),
                            TimeZone.getDefault().toZoneId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return time.format(formatter);
        }
    }

    @SneakyThrows
    private String copyPasteMethodGetDateTimeStrForDummyChangeDateTimePattern(Message message) {
        if ((message.getDate() == null)) {
            return " ";
        } else {
            LocalDateTime time =
                    LocalDateTime.ofInstant(Instant.ofEpochMilli(message.getDate() * 1000),
                            TimeZone.getDefault().toZoneId());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            return time.format(formatter);
        }
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.removeColumnByKey("date");
        grid.setColumns("id", "text", "languageCode");
        grid.addColumn(this::getDateTimeStr).setHeader("Date").setSortable(true);
        grid.addColumn(this::copyPasteMethodGetDateTimeStrForDummyChangeDateTimePattern).setHeader("Time").setSortable(true);

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
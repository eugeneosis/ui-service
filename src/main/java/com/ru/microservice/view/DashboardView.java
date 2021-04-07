package com.ru.microservice.view;


import com.ru.microservice.service.MessageService;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.charts.Chart;
import com.vaadin.flow.component.charts.model.ChartType;
import com.vaadin.flow.component.charts.model.DataSeries;
import com.vaadin.flow.component.charts.model.DataSeriesItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.util.Map;


@PageTitle("Статистика")
@Route(value = "dashboard", layout = Layout.class)
public class DashboardView extends VerticalLayout {

    private final MessageService messageService;

    public DashboardView(MessageService messageService) {
        this.messageService = messageService;

        addClassName("dashboard");
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(getMessagesCount(), getMessagesChart());
    }

    private Component getMessagesChart() {
        Chart chart = new Chart(ChartType.PIE);
        DataSeries dataSeries = new DataSeries();
        Map<String, Integer> messages = messageService.getMessages();
        messages.forEach((text, integer) ->
                dataSeries.add(new DataSeriesItem(text, integer)));
        chart.getConfiguration().setSeries(dataSeries);
        return chart;
    }

    private Span getMessagesCount() {
        Span span = new Span(messageService.count() + " messages");
        span.addClassName("span");
        return span;
    }
}
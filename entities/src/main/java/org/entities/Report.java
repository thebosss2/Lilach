package org.entities;

import javafx.scene.chart.Chart;

import java.util.Date;

public class Report {
    private Date date;
    private Chart[] charts;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Chart[] getChart() {
        return charts;
    }

    public void setChart(Chart[] chart) {
        this.charts = chart;
    }
}

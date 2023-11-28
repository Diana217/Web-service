package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Row {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("TableId")
    private int tableId;
    @JsonProperty("RowValues")
    private String rowValues;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public String getRowValues() {
        return rowValues;
    }

    public void setRowValues(String rowValues) {
        this.rowValues = rowValues;
    }
}

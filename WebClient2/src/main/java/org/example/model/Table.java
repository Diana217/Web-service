package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Table {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("TableName")
    private String tableName;
    @JsonProperty("DatabaseId")
    private int databaseId;
    @JsonProperty("Columns")
    private List<Column> columns;
    @JsonProperty("Rows")
    private List<Row> rows;

    public Table() {
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    public Table(String name) {
        this.tableName = name;
        this.columns = new ArrayList<>();
        this.rows = new ArrayList<>();
    }

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public int getDatabaseId() {
        return databaseId;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<Row> getRows() {
        return rows;
    }

    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
}

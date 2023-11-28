package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Column {
    @JsonProperty("Id")
    private int id;
    @JsonProperty("TableId")
    private int tableId;
    @JsonProperty("ColName")
    private String colName;
    @JsonProperty("TypeId")
    private int typeId;
    @JsonProperty("ColType")
    private Type colType;

    public Column() {}

    public Column(String name, String type) {
        this.colName = name;
        switch (type) {
            case "Integer":
                this.colType = new TypeInteger();
                break;
            // ... other cases
            default:
                this.colType = new TypeString();
                break;
        }
    }

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

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public Type getColType() {
        return colType;
    }

    public void setColType(Type colType) {
        this.colType = colType;
    }
}

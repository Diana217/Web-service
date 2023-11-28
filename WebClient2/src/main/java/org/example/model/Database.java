package org.example.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Database {
    @JsonProperty("Id")
    private int Id;
    @JsonProperty("DBName")
    private String DBName;
    @JsonProperty("DBPath")
    private String DBPath;
    @JsonProperty("Tables")
    private List<Table> Tables;

    public Database() {
        this.Tables = new ArrayList<>();
    }

    public Database(String name) {
        this.DBName = name;
        this.Tables = new ArrayList<>();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public String getDBName() {
        return DBName;
    }

    public void setDBName(String DBName) {
        this.DBName = DBName;
    }

    public String getDBPath() {
        return DBPath;
    }

    public void setDBPath(String DBPath) {
        this.DBPath = DBPath;
    }

    public List<Table> getTables() {
        return Tables;
    }

    public void setTables(List<Table> tables) {
        this.Tables = tables;
    }

    // Getters and Setters
}

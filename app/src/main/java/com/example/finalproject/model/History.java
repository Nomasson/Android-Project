package com.example.finalproject.model;

import java.util.List;

public class History {
    private String history_id;
    private String history_search;
    private String history_map;

    public History(String history_id, String history_search, String history_map) {
        this.history_id = history_id;
        this.history_search = history_search;
        this.history_map = history_map;
    }

    public String getHistory_id() {
        return history_id;
    }

    public void setHistory_id(String history_id) {
        this.history_id = history_id;
    }

    public String getHistory_search() {
        return history_search;
    }

    public void setHistory_search(String history_search) {
        this.history_search = history_search;
    }

    public String getHistory_map() {
        return history_map;
    }

    public void setHistory_map(String history_map) {
        this.history_map = history_map;
    }
}

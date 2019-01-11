package com.rasel.recyclerviewexpandable;

import java.util.List;

public class ApiResponse {
    private String message;
    private Boolean error;
    private List<Clients> client;

    public String getMessage() {
        return message;
    }

    public Boolean getError() {
        return error;
    }

    public List<Clients> getClient() {
        return client;
    }
}

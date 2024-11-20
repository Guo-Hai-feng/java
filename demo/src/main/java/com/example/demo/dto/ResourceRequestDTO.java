package com.example.demo.dto;

import java.util.List;

public class ResourceRequestDTO {
    private int process;
    private List<Integer> request;

    // Getters and setters
    public int getProcess() {
        return process;
    }

    public void setProcess(int process) {
        this.process = process;
    }

    public List<Integer> getRequest() {
        return request;
    }

    public void setRequest(List<Integer> request) {
        this.request = request;
    }
}

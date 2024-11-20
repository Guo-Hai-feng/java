package com.example.demo.dto;

import java.util.List;

import java.util.List;

public class InitRequestDTO {

    private int processCount; // 进程数量
    private int resourceCount; // 资源种类数量
    private List<Integer> available; // 可用资源列表
    private List<List<Integer>> max; // 每个进程的最大资源需求

    // Getter 和 Setter 方法

    public int getProcessCount() {
        return processCount;
    }

    public void setProcessCount(int processCount) {
        this.processCount = processCount;
    }

    public int getResourceCount() {
        return resourceCount;
    }

    public void setResourceCount(int resourceCount) {
        this.resourceCount = resourceCount;
    }

    public List<Integer> getAvailable() {
        return available;
    }

    public void setAvailable(List<Integer> available) {
        this.available = available;
    }

    public List<List<Integer>> getMax() {
        return max;
    }

    public void setMax(List<List<Integer>> max) {
        this.max = max;
    }

}


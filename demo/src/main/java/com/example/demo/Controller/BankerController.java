package com.example.demo.controller;
import com.example.demo.dto.InitRequestDTO;
import com.example.demo.dto.ResourceRequestDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.*;
import org.springframework.web.bind.annotation.*;

@Controller
public class BankerController {

    private int processCount;      // 进程数量
    private int resourceCount;     // 资源种类
    private int[] available;       // 可用资源
    private int[][] max;           // 最大需求矩阵
    private int[][] allocation;    // 分配矩阵
    private int[][] need;          // 需求矩阵

    @PostMapping("/init")
    @ResponseBody
    public Map<String, Object> initialize(@RequestBody InitRequestDTO payload) {
            processCount = payload.getProcessCount();
            resourceCount = payload.getResourceCount();
            List<List<Integer>> maxList = payload.getMax();
            List<Integer> availableList = payload.getAvailable();

            // 初始化资源状态
            available = availableList.stream().mapToInt(i -> i).toArray();
            max = new int[processCount][resourceCount];
            allocation = new int[processCount][resourceCount];
            need = new int[processCount][resourceCount];

            for (int i = 0; i < processCount; i++) {
                for (int j = 0; j < resourceCount; j++) {
                    max[i][j] = maxList.get(i).get(j);
                    need[i][j] = max[i][j];
                    allocation[i][j] = 0;
                }
            }

            // 构造成功响应
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "资源初始化成功！");
            response.put("updatedState", Map.of(
                    "available", available,  // 输出一维数组
                    "allocation", allocation,                // 多维数组
                    "need", need                              // 多维数组
            ));
            return response;


    }


    @PostMapping("/request")
    @ResponseBody
    public Map<String, Object> handleRequest(@RequestBody ResourceRequestDTO payload) {
        int process = (int) payload.getProcess();//进程id
        List<Integer> requestList = (List<Integer>) payload.getRequest();//请求分配资源数序列
        int[] request = requestList.stream().mapToInt(i -> i).toArray();//转化为数组

        Map<String, Object> response = new HashMap<>();


        for (int i = 0; i < resourceCount; i++) {
            // Step 1: 如果申请资源数大于需要的，视为不合理

            if (request[i] > need[process][i]) {
                response.put("success", false);
                response.put("message", "资源申请不合理");
                return response;
            }
            //如果超过availabe，资源不够分配
            if (request[i] > available[i]) {
                response.put("success", false);
                response.put("message", "资源申请超过最大可用资源数，资源不够分配");
                return response;
            }
        }

        // Step 2: 临时分配资源
        //试探性分配
        int[] availableTemp = available.clone();
        int[][] allocationTemp = deepCopy(allocation);
        int[][] needTemp = deepCopy(need);

        for (int i = 0; i < resourceCount; i++) {
            availableTemp[i] -= request[i];
            allocationTemp[process][i] += request[i];
            needTemp[process][i] -= request[i];
        }

        // Step 3: 执行银行家算法，检查安全性
        List<Integer> safeSequence = checkSafety(availableTemp, allocationTemp, needTemp);

        if (safeSequence == null) {
            response.put("success", false);
            response.put("message", "找不到安全序列，进程资源申请不予满足");
            return response;
        }

        // Step 4: 更新资源状态
        available = availableTemp;
        allocation = allocationTemp;
        need = needTemp;

        response.put("success", true);
        response.put("safeSequence", safeSequence);
        response.put("updatedState", Map.of(
                "available", available,
                "allocation", allocation,
                "need", need
        ));
        return response;
    }

    // 深拷贝二维数组
    private int[][] deepCopy(int[][] original) {
        return Arrays.stream(original).map(int[]::clone).toArray(int[][]::new);
    }

    // 银行家算法检查安全性
    private List<Integer> checkSafety(int[] availableTemp, int[][] allocationTemp, int[][] needTemp) {
        boolean[] finish = new boolean[processCount];
        int[] work = availableTemp.clone();
        List<Integer> safeSequence = new ArrayList<>();

        while (safeSequence.size() < processCount) {
            boolean found = false;
            for (int i = 0; i < processCount; i++) {
                if (!finish[i] && canFinish(work, needTemp[i])) {
                    for (int j = 0; j < resourceCount; j++) {
                        work[j] += allocationTemp[i][j];
                    }
                    finish[i] = true;
                    safeSequence.add(i);
                    found = true;
                    break;
                }
            }
            if (!found) return null; // 找不到安全序列
        }
        return safeSequence;
    }

    // 检查某进程是否可以完成
    private boolean canFinish(int[] work, int[] need) {
        for (int i = 0; i < resourceCount; i++) {
            if (work[i] < need[i]) return false;
        }
        return true;
    }
}

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

    /*

外层循环：通过 while (safeSequence.size() < processCount) 保证最终找到一个包含所有进程的安全序列（即，长度等于进程数）。
如果在一个完整的循环中无法找到可执行的进程（即所有进程的需求都大于可用资源），返回 null，表示无法找到安全序列，系统处于不安全状态。
内层循环：遍历每个进程，检查该进程是否可以完成（即检查 canFinish 方法）。
canFinish ：它接收当前可用资源 (work 数组) 和某进程的需求资源 (need 数组)，如果 work[i] 小于 need[i] 的任何元素，表示该进程无法完成；
如果所有的 work[i] 都大于等于 need[i]，则返回 true，表示该进程能够完成。
代码的执行流程：
从 availableTemp 数组开始，记录当前可用资源。
通过 finish 数组记录每个进程是否完成。
遍历每个进程，判断是否可以完成。如果某个进程可以完成，则将其分配的资源释放回 work 中（即更新 work）。
如果成功完成一个进程，则将其加入到 safeSequence 中。
如果能够遍历所有进程并找到一个可以完成的顺序，则返回该安全序列；否则返回 null，表示没有找到安全序列，系统处于不安全状态。
    * */

    // 银行家算法检查安全性
    private List<Integer> checkSafety(int[] availableTemp, int[][] allocationTemp, int[][] needTemp) {
        boolean[] finish = new boolean[processCount];   //记录每个进程是否已经完成。
        int[] work = availableTemp.clone();  //记录当前可用的资源，初始时为 availableTemp 的副本。
        List<Integer> safeSequence = new ArrayList<>();  //保存找到的安全序列，最终返回该序列。

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

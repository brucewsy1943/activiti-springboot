package com.ourdream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排他网关
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestExclusiveGateWay {
    /**
     * 部署
     */
    @Test
    public void deploy(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("processes/choise.bpmn20.xml")
                .deploy();
        //.addClasspathResource("processes/iot_order.png")

        System.out.println(deployment.getId()+"          " + deployment.getName() + "          " + deployment.getKey());//12505          null
    }

    /**
     * 启动
     */
    @Test
    public void start(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("myProcess1");
        System.out.println("pid:"+pi.getId() + ",activitiId:" + pi.getActivityId());
    }

    /**
     * 查找个人任务
     */
    @Test
    public void findMyTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String userId = "p1";
        List<Task> list =  processEngine.getTaskService().createTaskQuery().taskAssignee(userId).list();

        for (Task task:list) {
            System.out.println("id: " + task.getId());
            System.out.println("name: " + task.getName());
            System.out.println("assignee: " + task.getAssignee());
            System.out.println("createTime: " + task.getCreateTime());
            System.out.println("executionId" + task.getExecutionId());
        }

    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        TaskService taskService = processEngine.getTaskService();


        String taskId = "";
        Map<String,Object> variables = new HashMap<>();
        variables.put("money",200);
        processEngine.getTaskService().complete(taskId,variables);

        System.out.println("完成任务");
    }


}
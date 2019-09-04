package com.ourdream;

import com.ourdream.entity.Person;
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

import java.io.InputStream;
import java.util.Date;

/**
 *带流程变量
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestVariable {

    @Test
    public void deploy(){
        InputStream inputStream = this.getClass().getResourceAsStream("/processes/variable.bpmn");

        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        Deployment deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("流程审批（流程变量）")
                .addInputStream("variable1.bpmn",inputStream)
                .deploy();
        //.addClasspathResource("processes/iot_order.png")

        System.out.println(deployment.getId()+"          " + deployment.getName() + "          " + deployment.getKey());//12505          null
    }

    @Test
    public void startProcess(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        String processDefinitionKey = "myVariable";

        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey(processDefinitionKey);
        System.out.println("pid:" + pi.getId());

    }

    @Test
    public void setVariables(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
        //指定办理人
        String assignee = "张三";//注意 这个地方 是在流程中指定的人

        String processInstanceId = "42501";

        Task task = taskService.createTaskQuery()
                      .taskAssignee(assignee)
                      .processInstanceId(processInstanceId)
                      .singleResult();

        taskService.setVariable(task.getId(),"请假人","张无忌1236456");
        taskService.setVariableLocal(task.getId(),"请假天数",6);
        taskService.setVariable(task.getId(),"请假日期",new Date());

        Person p = new Person(1,"翠湖");

        taskService.setVariable(task.getId(),"人员信息",p);
    }

    @Test
    public void getVariables(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        TaskService taskService = processEngine.getTaskService();
    }
}
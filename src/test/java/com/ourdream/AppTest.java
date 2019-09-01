package com.ourdream;

import static org.junit.Assert.assertTrue;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Unit test for simple App.
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppTest 
{

    /**
     * 启动
     */
    @Test
    public void start(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        ProcessInstance pi = processEngine.getRuntimeService().startProcessInstanceByKey("myProcess");
        System.out.println("pid:"+pi.getId() + ",activitiId:" + pi.getActivityId());
    }



    /**
     * 部署
     */
    @Test
    public void deploy(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        Deployment deployment = processEngine.getRepositoryService()
                                            .createDeployment()
                                            .addClasspathResource("processes/leave.bpmn")
                                            .deploy();
                                            //.addClasspathResource("processes/iot_order.png")

        System.out.println(deployment.getId()+"          " + deployment.getName() + "          " + deployment.getKey());//12505          null
    }

    /**
     * 查看个人任务 act_ru_task---实时性的
     * 查询
     */
    @Test
    public void queryMyTask(){
        String assignee = "张三";//查找张三对应的任务
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee).list();

        for (Task task:tasks) {
            System.out.println("taskId:" + task.getId());
        }
    }

    /**
     * 流程的某个节点(任务)结束
     * 执行了这个方法之后会进行到流程的下一步
     */
    @Test
    public void completeTask(){
        System.out.println("fuck you ");
    }

    /**
     *就是查询act_re_procdef（流程定义）表这张表
     */
    @Test
    public void findProcessDefinition() {
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<ProcessDefinition> list = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
                .createProcessDefinitionQuery()// 创建一个流程定义的查询
                /** 指定查询条件,where条件 */
                // .deploymentId(deploymentId)//使用部署对象ID查询
                // .processDefinitionId(processDefinitionId)//使用流程定义ID查询
                // .processDefinitionKey(processDefinitionKey)//使用流程定义的key查询
                // .processDefinitionNameLike(processDefinitionNameLike)//使用流程定义的名称模糊查询

                /** 排序 */
                .orderByProcessDefinitionVersion().asc()// 按照版本的升序排列
                // .orderByProcessDefinitionName().desc()//按照流程定义的名称降序排列

                /** 返回的结果集 */
                .list();// 返回一个集合列表，封装流程定义
        // .singleResult();//返回惟一结果集
        // .count();//返回结果集数量
        // .listPage(firstResult, maxResults);//分页查询
        if (list != null && list.size() > 0) {
            for (ProcessDefinition pd : list) {
                System.out.println("流程定义ID:" + pd.getId());// 流程定义的key+版本+随机生成数
                System.out.println("流程定义的名称:" + pd.getName());// 对应helloworld.bpmn文件中的name属性值
                System.out.println("流程定义的key:" + pd.getKey());// 对应helloworld.bpmn文件中的id属性值
                System.out.println("流程定义的版本:" + pd.getVersion());// 当流程定义的key值相同的相同下，版本升级，默认1
                System.out.println("资源名称bpmn文件:" + pd.getResourceName());
                System.out.println("资源名称png文件:" + pd.getDiagramResourceName());
                System.out.println("部署对象ID：" + pd.getDeploymentId());
                System.out
                        .println("#########################################################");
            }
        }
    }


}

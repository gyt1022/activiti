import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.io.*;
import java.util.List;

public class ActivitiTest {
    @Test
    public void createActivitiTask(){
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("ActiveDemo.bpmn")
                .addClasspathResource("ActiveDemo.png")
                .deploy();
    }
    @Test
    public void testStartProcessInstance(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
//       查看数据库中的act_re_procdef表
        processEngine.getRuntimeService()
                .startProcessInstanceById("myProcess_1:1:4");
    }

    @Test
    public void testQingjia(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
//        查看数据库中的act_ru_task表
        processEngine.getTaskService()
                .complete("2504");
    }

    @Test
    public void testQueryTask(){
        ProcessEngine processEngine =ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks=processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("小明")
                .list();
        for(Task task :tasks){
            System.out.println(task.getName());
        }
    }
    @Test
    public void testFinishTask_manager(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
 //        查看数据库中的act_ru_task表
        processEngine.getTaskService()
                .complete("5002");
    }

    @Test
    public void testFinishTask_Boss(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("7502");
    }


//    流程删除

    @Test
    public void testDelete(){
//       得到流程引擎
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
//        查看数据库中的act_ru_task表
//        第一个参数是部署的流程的ID，第二个true是代表级联删除
        processEngine.getRepositoryService()
                .deleteDeployment("12501",true);
    }
//    查询所有部署流程
    @Test
    public void queryAllDeplyoment(){
//     得到流程引擎
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        List<org.activiti.engine.repository.Deployment> lists=processEngine.getRepositoryService()
                .createDeploymentQuery()
                .orderByDeploymenTime()
                .desc()
                .list();
        for(Deployment deployment:lists){
            System.out.println(deployment);
        }
    }


//    查看流程图
    @Test
    public void testShowImage() throws IOException {
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
//        查看数据库 deploymentID
        InputStream inputStream=processEngine.getRepositoryService()
                .getResourceAsStream("1","ActiveDemo.png");
        OutputStream outputStream=new FileOutputStream("D:/process.png");
        int b=-1;
        while((b=inputStream.read())!=-1){
            outputStream.write(b);
        }
        inputStream.close();
        outputStream.close();


    }
}

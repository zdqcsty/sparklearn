package com.example.sparklearn.spark;

import org.apache.spark.launcher.SparkAppHandle;
import org.apache.spark.launcher.SparkLauncher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class SubmitPython {


    public static void main(String[] args) {
        try {
            submitJob();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String submitJob() throws Exception {

        //线程暂时阻塞，（main线程和startApplication是异步执行）
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        //基础参数
        HashMap<String, String> map = new HashMap<>();
        map.put("HADOOP_CONF_DIR", "/opt/beh/core/hadoop/etc/hadoop");
        map.put("JAVA_HOME", "/opt/beh/core/jdk");
        map.put("SPARK_HOME", "/opt/beh/core/spark");
        SparkLauncher launcher = new SparkLauncher(map);

        //这行很重要，相当于提交java时指定的jar
        launcher.setAppResource("hdfs:///user/zgh/ceshi.py");
        launcher.addPyFile("hdfs:///user/zgh/ceshi.py");
        //提交py参数传递
        List<String> args = new ArrayList<String>();
        args.add("--sql=" + "select * from ceshi.test limit 10" );
        args.add("--output=" + "/user/zgh/cdahcdbjba");
        args.add("--headers=" + "a,b,c,v,e,n");
        for (String arg: args) {
            launcher.addAppArgs(arg);
        }
        //local模式可行
        launcher.setMaster("yarn");
        launcher.setDeployMode("client");
        launcher.setConf(SparkLauncher.EXECUTOR_MEMORY, "4g");
        launcher.setConf(SparkLauncher.EXECUTOR_CORES, "2");
        SparkAppHandle sparkAppHandle = launcher.startApplication(new SparkAppHandle.Listener() {
            @Override
            public void stateChanged(SparkAppHandle handle) {
                if (handle.getState().isFinal()) {
                    countDownLatch.countDown();
                }
                System.out.println("state:" + handle.getState().toString());
            }

            @Override
            public void infoChanged(SparkAppHandle handle) {
            }
        });

        System.out.println("The task is executing, please wait ....");
        //线程等待任务结束
        countDownLatch.await();
        System.out.println("===================================");
        System.out.println(sparkAppHandle.getAppId() + "---" + sparkAppHandle.getState());
        System.out.println("===================================");
        return "aaa";
    }
}

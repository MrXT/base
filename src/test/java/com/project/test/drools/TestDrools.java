package com.project.test.drools;

import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

public class TestDrools {

    @Test
    public void testBean() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();//kmodule.xml
        KieSession kieSession = kieContainer.newKieSession("session");
        //加入数据
        kieSession.insert(new String());
        //执行规则
        int i = kieSession.fireAllRules();//fire:火
        kieSession.dispose();
    }

    @Test
    public void test() {
        KieServices kieServices = KieServices.Factory.get();
        KieContainer kieContainer = kieServices.getKieClasspathContainer();//kmodule.xml
        KieSession kieSession = kieContainer.newKieSession("sessionHello");
        //加入数据
        Result result = new Result();
        result.setRes("hello world");
        kieSession.insert(result);
        kieSession.insert(new String());
        //执行规则
        int i = kieSession.fireAllRules();//fire:火
        kieSession.dispose();//关闭session
    }
}

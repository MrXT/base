package com.project.test;

import com.project.common.util.HttpClientUtil;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class TestBean extends SuperBena {
    private String userId;

    private String age;

    public static void main(String[] args) throws InterruptedException {
        //System.out.println(TestBean.builder().userId("").id("123").age("213").name("xxxxx").build().getName());
        /*int i = 1;
        while (true) {
            Thread.sleep((long) (Math.random() * 5 + 1) * 1000);
            HttpClientUtil.get("http://localhost?msg=" + i);
            i++;
        }*/
    }

}

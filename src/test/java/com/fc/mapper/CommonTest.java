package com.fc.mapper;

import org.junit.Test;

/**
 * Created by lidp on 2019/5/30.
 */
public class CommonTest {
    @Test
    public void fff1(){
        try {
            System.out.println(1);
            fff2();
        }catch (Exception e){
            System.out.println("error1");
        }finally {
            System.out.println("final");
        }
    }
    public void fff2(){
        try {
            throw new Exception();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(2);
        }
    }
}

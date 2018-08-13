package com.recklessMo.registry.config;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class test {

    @BeforeClass
    public static void firstInit(){
        System.out.println("first init");
    }

    @Before
    public void init(){
        System.out.println("init");
    }

    @Test
    public void testTest(){

        String str = "xyz";
        Assert.assertEquals("xyz", str);

    }

}

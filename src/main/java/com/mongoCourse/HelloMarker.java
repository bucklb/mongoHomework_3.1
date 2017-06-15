package com.mongoCourse;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

// Just a simple

/**
 * Created by buckl on 05/06/2017.
 */
public class HelloMarker {
    public static void main(String[] args) {
        Configuration config =new Configuration();
        config.setClassForTemplateLoading(HelloMarker.class, "/");

        try {
            Template temp=config.getTemplate("hello.ftl");
            StringWriter writer = new StringWriter();
            Map<String,Object> helloMap=new HashMap<String,Object>();
            helloMap.put("name","Your Name here");
            temp.process(helloMap,writer);

            System.out.println(writer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

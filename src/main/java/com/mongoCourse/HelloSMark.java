package com.mongoCourse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.bson.Document;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import java.io.StringWriter;

import static spark.Spark.get;
import static spark.Spark.halt;

/**
 * Created by buckl on 05/06/2017.
 */
public class HelloSMark {
    public static void main(String[] args) {

        final Configuration config =new Configuration();
        config.setClassForTemplateLoading(HelloSMark.class, "/");

        MongoClient client = new MongoClient();
        MongoDatabase db= client.getDatabase("course");
        final MongoCollection<Document> coll =db.getCollection("hello");

        coll.drop();
        coll.insertOne(new Document("name","MongoDB").append("plus","Appended"));
//        coll.insertOne(new Document("plus","Addition"));



        if (false) {

            // new way
            get("/", (req, res) -> "gfsdfgfdsf New Sparks are flying!");

        } else {

            // course way
            Spark.get("/",new Route() {
                public Object handle(Request request, Response response) throws Exception {
                    StringWriter writer = new StringWriter();

                    try {
                        Template temp=config.getTemplate("hello.ftl");
//                        Map<String,Object> helloMap=new HashMap<String,Object>();
//                        helloMap.put("name","Your Spark here");
//                        temp.process(helloMap,writer);

                        Document doc=coll.find().first();
                        temp.process(doc,writer);

                        System.out.println(writer);

                    } catch (Exception e) {
                        halt(500);
                        e.printStackTrace();
                    }


                    return writer;
                }
            });
        }
    }
}

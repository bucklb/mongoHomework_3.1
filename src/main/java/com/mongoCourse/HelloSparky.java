package com.mongoCourse;

import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

import static spark.Spark.get;

/**
 * Created by buckl on 05/06/2017.
 */
public class HelloSparky {
    public static void main(String[] args) {

        if (false) {

            // new way
            get("/", (req, res) -> "gfsdfgfdsf New Sparks are flying!");

        } else {

            // course way
            Spark.get("/",new Route() {
                public Object handle(Request request, Response response) throws Exception {
                    return "Course Sparks are flying!";
                }
            });
        }
    }
}

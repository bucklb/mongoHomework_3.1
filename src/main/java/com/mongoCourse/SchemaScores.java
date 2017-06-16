package com.mongoCourse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import freemarker.template.Configuration;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.*;

// Homework 3.1 - pull the lowest homework score from each student

/**
 * Created by buckl on 05/06/2017.
 */
public class SchemaScores {
    public static void main(String[] args) {

        // Get the client, to get the database, to get the collection of all students. The usual
        MongoClient client = new MongoClient();
        MongoDatabase db= client.getDatabase("school");
        MongoCollection<Document> coll =db.getCollection("students");

        // Get an iterator for the collection we just got, so that we can step through the collection
        MongoCursor<Document> cursor=coll.find().iterator();
        try{

            // Step through the collection
            while (cursor.hasNext()){

                // Get the next document and locate the lowest homework score
                Document c=cursor.next();

                // Get the scores in a way we can handle.  We want to know the lowest HOMEWORK score.
                // One approach is to just know the corresponding type/score to create as a filter
                // MIGHT be possible to identify the individual element and remove it explicitly
//                List<Document> l = (List<Document>)c.get("scores");
                List<Document> l = c.get("scores", List.class);
                double m=9999999.9;
                int i=0,p=0;
                for (Document d:l){

                    i++;
                    // isolate homework record with lowest score (assume every student has a homework :)
                    if( d.get("type").equals("homework")) {
                        double t=(double)d.get("score");
                        System.out.println(t);
                        if (t<m) {
                            m=t;
                            p=i-1;
                        }
                    }
                }

                // Output the offending item
                System.out.println(">>"+ l.get(p));

                // Specify what it is we are wanting to remove (effectively as a filter) and then specify that it's to be removed
                    // Specify (by example) the thing(s) we want to remove - could be one or many
                    // e.g. might want to remove the "homework" scores, as it shouldn't really count towards the exam based results
                Document victim=new Document("scores",new Document("type","homework").append("score",m));
                    // $pull will remove any full matches - $pop removes last elt, regardless of filter, $pullAll removes all matches ??
                Document update=new Document("$pull", victim);
                System.out.println(update);

                // Now specify what we want done to the document.  We want to update it by PULLing out the identified score
                // - obviously a limit to how often we can do this before all the scores are gone
////                coll.updateOne(c , update);

            }

        }finally{
            // let it go, let it go ...
            cursor.close();
        }

    }
}

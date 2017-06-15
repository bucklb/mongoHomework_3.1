package com.mongoCourse;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;


// Homeowrk for week 2 (can't recall the exact number)

/**
 * Created by buckl on 05/06/2017.
 */
public class Scores {
    public static void main(String[] args) {

        // Standard way to get students.grades collection
        MongoClient client = new MongoClient();
        MongoDatabase db= client.getDatabase("students");
        final MongoCollection<Document> coll =db.getCollection("grades");

        // Only interested in the homework scores.  Use a sort so we can be sure of the lowest score (appears each time the student changes)
        Bson f=Filters.eq("type","homework");
        Bson s= Sorts.ascending("student_id","score");
        int i,j=-1;

        // Get a means to step through the collection
        MongoCursor<Document> cursor=coll.find(f).sort(s).iterator();
        try{
            while (cursor.hasNext()){
                Document c=cursor.next();

                // if student has changed then we have hit the lowest score for the newly encountered student
                i=(int)c.get("student_id");
                if(i!=j){
                    j=i;
                    // drop this document from the collection, assuming we're doing this for real of course
//                    coll.deleteOne(c);

                }

                System.out.println(c);
            }

        }finally{
            // let it go
            cursor.close();
        }
    }
}

package com.mmi.controller;


import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.mmi.output.Result;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;



import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;


@Component
@RestController

public class MainController {
	
	@RequestMapping(value= "/HW_Wise", method=RequestMethod.GET)
	public HashMap<Integer, Result> getlist() throws UnknownHostException {		
		
            
            MongoClient mongoClient = new MongoClient("localhost", 27017);
            DB db= mongoClient.getDB("mmi_intouch_dev");
            DBCollection devices = db.getCollection("mmi_device");
            
            HashMap<Integer, Result> result = new HashMap<Integer, Result>();
    		ArrayList<Integer> deviceNumbers =  (ArrayList<Integer>) devices.distinct("deviceType");
    		for(Integer type: deviceNumbers) {
    			Result create = new Result(type);
    			result.put(type,create);
    		}
            
           
           for(Integer i: deviceNumbers) {
            DBObject query = new BasicDBObject("deviceType", i);
            DBCursor cursor = devices.find(query);
            query.put("active", false);
            Result update = result.get(i);
            while (cursor.hasNext()) {
        		
        		update.setDeactive_count(update.getDeactive_count()+1);
        		cursor.next();
            }
            
           }
          
           
           
           
         
           Timestamp timestamp = new Timestamp(System.currentTimeMillis()-(long)(86400000*360));
           long t= timestamp.getTime();
           t = t/1000;

           for(Integer i: deviceNumbers) {
               DBObject query2 = new BasicDBObject("deviceType", i);
               DBCursor cursor2 = devices.find(query2);
               Result update = result.get(i);
               while (cursor2.hasNext()) {
            	   DBObject a = cursor2.next();
            	   Timestamp fromDB = new Timestamp((long)Long.parseLong(a.get("updationDate").toString())) ;
            	   long t1 = fromDB.getTime();
            	   if(a.containsField("installDate"))
            		   if(t1>=t) {
            			   update.setWorking_count(update.getWorking_count()+1);
           		}
            	   
           }
           }
           
           
           
           for(Integer i: deviceNumbers) {
               DBObject query3 = new BasicDBObject("deviceType", i);
               DBCursor cursor3 = devices.find(query3);
               Result update = result.get(i);
               while (cursor3.hasNext()) {
            	   DBObject a = cursor3.next();
            	   Timestamp fromDB = new Timestamp((long)Long.parseLong(a.get("updationDate").toString())) ;
            	   long t1 = fromDB.getTime();
            		  // if(timestamp.compareTo(fromDB) < 0) {
            	   if(t1<t) {
        			   update.setNonworking_count(update.getNonworking_count()+1);           			
           		   }
            	   
               }
           }
           
           for(Integer i: deviceNumbers) {
        	   Result update = result.get(i);
               update.caldevicemodel();
           }
           return result;
           
 
	}
	

	
}

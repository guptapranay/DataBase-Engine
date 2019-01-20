/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.engine;

import static database.engine.QueryParameter.file;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;
import org.json.simple.*;
import java.util.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
/**
 *
 * @author Suyash
 */
public class CSVtoJSON {
    
    public void writeWithoutRestriction() throws FileNotFoundException, IOException
    {
        FileReader fileReader=new FileReader(QueryParameter.path+QueryParameter.file); 
        
        FileReader fileReader2=new FileReader(QueryParameter.path+QueryParameter.file);
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);       
        String[] header=bufferedReader2.readLine().split(",");
        
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.EXCEL.withHeader());  
        
        JSONArray jsonarray=new JSONArray();       
        ArrayList<JSONObject> jsobject=new ArrayList<>();
        int j=0;        
        for (CSVRecord csvRecord: csvParser)
        {
            // 1.Create JSON Object and add values to it.
            jsobject.add(new JSONObject());
            
            if(QueryParameter.fields.contains("*"))
            {
                for(int i=0;i<header.length;i++)
                {
                    jsobject.get(j).put(header[i], csvRecord.get(header[i]));                    
                }
            }
            else
            {
                for(int i=0;i<QueryParameter.fields.size();i++)
                {
                    jsobject.get(j).put(QueryParameter.fields.get(i), csvRecord.get(QueryParameter.fields.get(i)));
                } 
            }

            // 2.Create JSON Array add this created Object to this array
            jsonarray.add(jsobject.get(j));   
            j++;
        }                
        
        // 3.Write to JSON file
        new File("C:\\output").mkdir();
        try (FileWriter file = new FileWriter("C:\\output\\ipl.json")) {
 
            file.write(jsonarray.toJSONString());
            file.flush();
            System.out.println("Output file is saved at : C:\\output\\ipl.json");
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void writeWithRestriction() throws FileNotFoundException, IOException
    {
        FileReader fileReader=new FileReader(QueryParameter.path+QueryParameter.file); 
        
        FileReader fileReader2=new FileReader(QueryParameter.path+QueryParameter.file);
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);       
        String[] header=bufferedReader2.readLine().split(",");
        
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.EXCEL.withHeader());  
        
        JSONArray jsonarray=new JSONArray();       
        ArrayList<JSONObject> jsobject=new ArrayList<>();
        int j=0;        
        for (CSVRecord csvRecord: csvParser)
        {
            // 1.Create JSON Object and add values to it.
            jsobject.add(new JSONObject());
            
            if(QueryParameter.fields.contains("*"))
            {
                for(int i=0;i<header.length;i++)
                {
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("<"))
                        if((Integer.parseInt(csvRecord.get(QueryParameter.restrictions.get(0).propertyName)))<(Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale)))
                        {
                            jsobject.get(j).put(header[i], csvRecord.get(header[i])); 
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">"))
                        if((Integer.parseInt(csvRecord.get(QueryParameter.restrictions.get(0).propertyName)))>(Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale)))
                        {
                            jsobject.get(j).put(header[i], csvRecord.get(header[i])); 
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && csvRecord.get(QueryParameter.restrictions.get(0).propertyName).matches("[0-9]+"))
                        if((Integer.parseInt(csvRecord.get(QueryParameter.restrictions.get(0).propertyName)))==(Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale)))
                        {
                            jsobject.get(j).put(header[i], csvRecord.get(header[i])); 
                        }    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(csvRecord.get(QueryParameter.restrictions.get(0).propertyName).matches("[0-9]+")))
                    {
                        if(csvRecord.get(QueryParameter.restrictions.get(0).propertyName).equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            jsobject.get(j).put(QueryParameter.fields.get(i), csvRecord.get(QueryParameter.fields.get(i)));
                        }
                    }
                                       
                }
            }
            else
            {
                for(int i=0;i<QueryParameter.fields.size();i++)
                {
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("<"))
                        if((Integer.parseInt(csvRecord.get(QueryParameter.restrictions.get(0).propertyName)))<(Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale)))
                        {
                            jsobject.get(j).put(QueryParameter.fields.get(i), csvRecord.get(QueryParameter.fields.get(i)));
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">"))
                        if((Integer.parseInt(csvRecord.get(QueryParameter.restrictions.get(0).propertyName)))>(Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale)))
                        {
                            jsobject.get(j).put(QueryParameter.fields.get(i), csvRecord.get(QueryParameter.fields.get(i)));
                        } 
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && csvRecord.get(QueryParameter.restrictions.get(0).propertyName).matches("[0-9]+"))
                        if((Integer.parseInt(csvRecord.get(QueryParameter.restrictions.get(0).propertyName)))==(Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale)))
                        {
                            jsobject.get(j).put(QueryParameter.fields.get(i), csvRecord.get(QueryParameter.fields.get(i)));
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(csvRecord.get(QueryParameter.restrictions.get(0).propertyName).matches("[0-9]+")))
                    {
                        if(csvRecord.get(QueryParameter.restrictions.get(0).propertyName).equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            jsobject.get(j).put(QueryParameter.fields.get(i), csvRecord.get(QueryParameter.fields.get(i)));
                        }
                    }                    
                } 
            }

            // 2.Create JSON Array add this created Object to this array
            jsonarray.add(jsobject.get(j));   
            j++;
        }                
        
        // 3.Write to JSON file
        new File("C:\\output").mkdir();
        try (FileWriter file = new FileWriter("C:\\output\\ipl.json")) {
 
            file.write(jsonarray.toJSONString());
            file.flush();
            System.out.println("Output file is saved at : C:\\output\\ipl.json");
 
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    
    }
    
    void wtiteall() throws FileNotFoundException, IOException
    {
         FileReader fileReader=new FileReader(QueryParameter.path+QueryParameter.file); 
        
        FileReader fileReader2=new FileReader(QueryParameter.path+QueryParameter.file);
        BufferedReader bufferedReader2 = new BufferedReader(fileReader2);       
        String[] header=bufferedReader2.readLine().split(",");
        
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        
        CSVParser csvParser = new CSVParser(bufferedReader, CSVFormat.EXCEL.withHeader());  
        
        JSONArray jsonarray=new JSONArray();       
        ArrayList<JSONObject> jsobject=new ArrayList<>();
        int j=0;        
        for (CSVRecord csvRecord: csvParser)
        {
            // 1.Create JSON Object and add values to it.
            jsobject.add(new JSONObject());
            
            
                for(int i=0;i<header.length;i++)
                {
                    jsobject.get(j).put(header[i], csvRecord.get(header[i]));                    
                }
            
            

            // 2.Create JSON Array add this created Object to this array
            jsonarray.add(jsobject.get(j));   
            j++;
        }                
        
        // 3.Write to JSON file
        new File("C:\\output").mkdir();
        try (FileWriter file = new FileWriter("C:\\output\\ipl.json")) {
 
            file.write(jsonarray.toJSONString());
            file.flush();
            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    void cond1() throws FileNotFoundException, IOException, ParseException
    {
        //JSON parser object to parse read file
        JSONParser jsonParserr = new JSONParser();
        FileReader readerr = new FileReader("C:\\output\\ipl.json");
        //Read JSON file
        Object obj = jsonParserr.parse(readerr);
        
        JSONArray jsonarray=new JSONArray();       
        ArrayList<JSONObject> jsobject=new ArrayList<>();
        
        JSONArray jarrayr = (JSONArray) obj;
        Iterator itr2 = jarrayr.iterator(); 
        Iterator<Map.Entry> itr1; 
        int j=0;
        JSONObject jobjectr;
        int n=0;
        
        while (itr2.hasNext())  
        {   jsobject.add(new JSONObject());
            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            jobjectr=(JSONObject) jarrayr.get(j);            
        
            if(QueryParameter.fields.contains("*"))
            {                    
                if(QueryParameter.restrictions.get(0).condition.equals("<"))
                    if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale))
                    {
                        while (itr1.hasNext()) 
                        {                 
                            Map.Entry pair = itr1.next(); 
                            jsobject.get(j).put(pair.getKey(), pair.getValue());                
                        } 
                        jsonarray.add(jsobject.get(j));                        
                    }
                if(QueryParameter.restrictions.get(0).condition.equals(">"))
                    if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale))
                    {
                        while (itr1.hasNext()) 
                        {                 
                            Map.Entry pair = itr1.next(); 
                            jsobject.get(j).put(pair.getKey(), pair.getValue());                
                        } 
                        jsonarray.add(jsobject.get(j));                        
                    }
                String z=(String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName);
                if(QueryParameter.restrictions.get(0).condition.equals("=") && z.matches("[0-9]+"))
                    if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale))
                    {
                        while (itr1.hasNext()) 
                        {                 
                            Map.Entry pair = itr1.next(); 
                            jsobject.get(j).put(pair.getKey(), pair.getValue());                
                        } 
                        jsonarray.add(jsobject.get(j));
                        
                    }    
                if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")))
                {   String g=(String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName);
                    if(g.equals(QueryParameter.restrictions.get(0).propertyVale))
                    {
                        while (itr1.hasNext()) 
                        {                 
                            Map.Entry pair = itr1.next(); 
                            jsobject.get(j).put(pair.getKey(), pair.getValue());                
                        }
                        jsonarray.add(jsobject.get(j));                        
                    }
                }                
            }
            else
            {
                if(((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName)).matches("[0-9]+"))
                n=Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName));
                    
                if(QueryParameter.restrictions.get(0).condition.equals("<"))
                
                    
                    if(n<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale))
                    {
                        while (itr1.hasNext()) 
                        {                 
                            Map.Entry pair = itr1.next(); 
                            if(QueryParameter.fields.contains((String)pair.getKey()))
                            {
                            jsobject.get(j).put(pair.getKey(), pair.getValue());
                            }
                        } 
                        jsonarray.add(jsobject.get(j));
                        
                    }
                
                if(QueryParameter.restrictions.get(0).condition.equals(">"))
                    if(n>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale))
                    {
                        while (itr1.hasNext()) 
                        {                 
                            Map.Entry pair = itr1.next(); 
                            if(QueryParameter.fields.contains((String)pair.getKey()))
                            jsobject.get(j).put(pair.getKey(), pair.getValue());                
                        }
                        jsonarray.add(jsobject.get(j));
                        
                    }
                String z=(String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName);
                if(QueryParameter.restrictions.get(0).condition.equals("=") && z.matches("[0-9]+"))
                    if(n<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale))
                    {
                        while (itr1.hasNext()) 
                        {                 
                            Map.Entry pair = itr1.next();
                            if(QueryParameter.fields.contains((String)pair.getKey()))
                            jsobject.get(j).put(pair.getKey(), pair.getValue());                
                        }
                        jsonarray.add(jsobject.get(j));
                        
                    }    
                if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")))
                {   String g=(String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName);
                    if(g.equals(QueryParameter.restrictions.get(0).propertyVale))
                    {
                        while (itr1.hasNext()) 
                        {                 
                            Map.Entry pair = itr1.next(); 
                            if(QueryParameter.fields.contains((String)pair.getKey()))
                            jsobject.get(j).put(pair.getKey(), pair.getValue());                
                        }
                        jsonarray.add(jsobject.get(j));
                        
                    }
                } 
            } j++;
        }    
         
        new File("C:\\output").mkdir();
        try (FileWriter file = new FileWriter("C:\\output\\ipl2.json")) {
 
            file.write(jsonarray.toJSONString());
            file.flush();            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
                    
    }
    
    void cond2() throws FileNotFoundException, IOException, ParseException
    {
        //JSON parser object to parse read file
        JSONParser jsonParserr = new JSONParser();
        FileReader readerr = new FileReader("C:\\output\\ipl.json");
        //Read JSON file
        Object obj = jsonParserr.parse(readerr);
        
        JSONArray jsonarray=new JSONArray();       
        ArrayList<JSONObject> jsobject=new ArrayList<>();
        
        JSONArray jarrayr = (JSONArray) obj;
        Iterator itr2 = jarrayr.iterator(); 
        Iterator<Map.Entry> itr1; 
        int j=0;
        JSONObject jobjectr;
        int n=0;
        
        while (itr2.hasNext())  
        {   jsobject.add(new JSONObject());
            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            jobjectr=(JSONObject) jarrayr.get(j); 
            String z=(String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName);
            String x=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                
            if(QueryParameter.logical_operators.contains("and"))                 
            {
                if(QueryParameter.fields.contains("*"))
                {                    
                    if(QueryParameter.restrictions.get(0).condition.equals("<") && QueryParameter.restrictions.get(1).condition.equals("<"))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") && QueryParameter.restrictions.get(1).condition.equals(">"))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+"))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+")))
                    {   
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && g.equals(QueryParameter.restrictions.get(1).propertyVale))
                        {   
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals(">") && QueryParameter.restrictions.get(1).condition.equals("<"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") && QueryParameter.restrictions.get(1).condition.equals(">"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+")))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && QueryParameter.restrictions.get(1).condition.equals("<"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && QueryParameter.restrictions.get(1).condition.equals(">"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        } 
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+")))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("<"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals(">"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        } 
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+")))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    
                    
                }
                else
                {
                    if(((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName)).matches("[0-9]+"))
                    n=Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName));
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("<") && QueryParameter.restrictions.get(1).condition.equals("<"))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") && QueryParameter.restrictions.get(1).condition.equals(">"))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+"))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+")))
                    {   
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && g.equals(QueryParameter.restrictions.get(1).propertyVale))
                        {   
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next();
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals(">") && QueryParameter.restrictions.get(1).condition.equals("<"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next();
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") && QueryParameter.restrictions.get(1).condition.equals(">"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+")))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && QueryParameter.restrictions.get(1).condition.equals("<"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && QueryParameter.restrictions.get(1).condition.equals(">"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        } 
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+")))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("<"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals(">"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        } 
                    if(QueryParameter.restrictions.get(0).condition.equals("=") && !(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+")))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) && g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                if(QueryParameter.fields.contains((String)pair.getKey()))
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    } 
                } 
                j++;
            }
            else if(QueryParameter.logical_operators.contains("or"))                 
            {
                if(QueryParameter.fields.contains("*"))
                {                    
                    if(QueryParameter.restrictions.get(0).condition.equals("<") || QueryParameter.restrictions.get(1).condition.equals("<"))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next();
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") || QueryParameter.restrictions.get(1).condition.equals(">"))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") || (QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+")))
                    {    
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || (Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale)))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("<") || (QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+"))))
                    {   
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || g.equals(QueryParameter.restrictions.get(1).propertyVale))
                        {   
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next();
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals(">") || QueryParameter.restrictions.get(1).condition.equals("<"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next();
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") || QueryParameter.restrictions.get(1).condition.equals(">"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") || (QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+")))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals(">") || (QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+"))))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    if(QueryParameter.restrictions.get(0).condition.equals("=") || QueryParameter.restrictions.get(1).condition.equals("<"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    if(QueryParameter.restrictions.get(0).condition.equals("=") || QueryParameter.restrictions.get(1).condition.equals(">"))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") || (QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+")))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        } 
                    if(QueryParameter.restrictions.get(0).condition.equals("=") || (QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+"))))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") || (!(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("<")))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))<Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") || (!(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals(">")))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))>Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    
                    if(QueryParameter.restrictions.get(0).condition.equals("=") || (!(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("=") && x.matches("[0-9]+")))
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(1).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        } 
                    if(QueryParameter.restrictions.get(0).condition.equals("=") || (!(z.matches("[0-9]+")) && QueryParameter.restrictions.get(1).condition.equals("=") && !(x.matches("[0-9]+"))))
                    {    
                        String g=(String)jobjectr.get(QueryParameter.restrictions.get(1).propertyName);
                        if(Integer.parseInt((String)jobjectr.get(QueryParameter.restrictions.get(0).propertyName))==Integer.parseInt(QueryParameter.restrictions.get(0).propertyVale) || g.equals(QueryParameter.restrictions.get(0).propertyVale))
                        {
                            while (itr1.hasNext()) 
                            {                 
                                Map.Entry pair = itr1.next(); 
                                jsobject.get(j).put(pair.getKey(), pair.getValue());                
                            } 
                            jsonarray.add(jsobject.get(j));                        
                        }
                    }
                    
                    
                }
                else
                {
                    
                }
                j++;
            }
            
        }    
         
        new File("C:\\output").mkdir();
        try (FileWriter file = new FileWriter("C:\\output\\ipl2.json")) {
 
            file.write(jsonarray.toJSONString());
            file.flush();            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    void count(int i) throws FileNotFoundException, IOException, ParseException
    {
        //JSON parser object to parse read file
        JSONParser jsonParserr = new JSONParser();
        FileReader readerr = new FileReader("C:\\output\\ipl.json");
        //Read JSON file
        Object obj = jsonParserr.parse(readerr);
        
        JSONArray jsonarray=new JSONArray();       
        JSONObject jsobject=new JSONObject();
        
        JSONArray jarrayr = (JSONArray) obj;
        Iterator itr2 = jarrayr.iterator(); 
        Iterator<Map.Entry> itr1;
        
        
        int j=0;
        int counter=0;
        while (itr2.hasNext())  
        {   
            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            
            while (itr1.hasNext()) 
            {                 
                Map.Entry pair = itr1.next(); 
                
                if((pair.getKey().equals(QueryParameter.aggregateFunctions.get(i).field)))
                counter++;                
            } 
            
        }  
        QueryParameter.aggregateFunctions.get(i).result=counter;
        JSONParser jsonParserrw = new JSONParser();
        FileReader readerw = new FileReader("C:\\output\\ipl2.json");
        //Read JSON file
        Object objw = jsonParserrw.parse(readerw);
        
        JSONArray jsonarrayw=new JSONArray();       
        JSONObject jsobjectw=new JSONObject();
        JSONArray jarrayw = (JSONArray) objw;
        String s=QueryParameter.aggregateFunctions.get(i).function+"("+QueryParameter.aggregateFunctions.get(i).field+")";
        jsobject.put(s, counter);
        jarrayw.add(jsobject);
        
       PrintWriter writer = new PrintWriter("C:\\output\\ipl2.json");
        writer.print("");
        writer.close();
        try (FileWriter file = new FileWriter("C:\\output\\ipl2.json",true)) {
 
            file.write(jarrayw.toJSONString());
            file.flush();            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sum(int i) throws FileNotFoundException, IOException, ParseException
    {
        //JSON parser object to parse read file
        JSONParser jsonParserr = new JSONParser();
        FileReader readerr = new FileReader("C:\\output\\ipl.json");
        //Read JSON file
        Object obj = jsonParserr.parse(readerr);
        
        JSONArray jsonarray=new JSONArray();       
        JSONObject jsobject=new JSONObject();
        
        JSONArray jarrayr = (JSONArray) obj;
        Iterator itr2 = jarrayr.iterator(); 
        Iterator<Map.Entry> itr1;
        
        
        int j=0;
        int sum=0;
        while (itr2.hasNext())  
        {   
            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            
            while (itr1.hasNext()) 
            {                 
                Map.Entry pair = itr1.next(); 
                
                if((pair.getKey().equals(QueryParameter.aggregateFunctions.get(i).field)))
                 sum=sum+Integer.parseInt((String) pair.getValue());
            }            
            
        } 
        QueryParameter.aggregateFunctions.get(i).result=sum;
        JSONParser jsonParserrw = new JSONParser();
        FileReader readerw = new FileReader("C:\\output\\ipl2.json");
        //Read JSON file
        Object objw = jsonParserrw.parse(readerw);
        
        JSONArray jsonarrayw=new JSONArray();       
        JSONObject jsobjectw=new JSONObject();
        JSONArray jarrayw = (JSONArray) objw;
        String s=QueryParameter.aggregateFunctions.get(i).function+"("+QueryParameter.aggregateFunctions.get(i).field+")";
        jsobject.put(s, sum);
        jarrayw.add(jsobject);
        
       File f=new File("C:\\output\\ipl2.json");
       PrintWriter writer = new PrintWriter("C:\\output\\ipl2.json");
        writer.print("");
        writer.close();
        try (FileWriter file = new FileWriter(f)) {
                
            file.write(jarrayw.toJSONString());
            file.flush();            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void min(int i) throws FileNotFoundException, IOException, ParseException
    {
        JSONParser jsonParserr = new JSONParser();
        FileReader readerr = new FileReader("C:\\output\\ipl.json");
        //Read JSON file
        Object obj = jsonParserr.parse(readerr);
        
        JSONArray jsonarray=new JSONArray();       
        JSONObject jsobject=new JSONObject();
        
        JSONArray jarrayr = (JSONArray) obj;
        Iterator itr2 = jarrayr.iterator(); 
        Iterator<Map.Entry> itr1;
        
        
        int j=0;
        int min=Integer.MAX_VALUE;
        while (itr2.hasNext())  
        {   
            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            
            while (itr1.hasNext()) 
            {                 
                Map.Entry pair = itr1.next(); 
                
                if((pair.getKey().equals(QueryParameter.aggregateFunctions.get(i).field)))
                 if(Integer.parseInt((String)pair.getValue())<min)
                     min=Integer.parseInt((String)pair.getValue());
            }            
            
        } 
        QueryParameter.aggregateFunctions.get(i).result=min;
        JSONParser jsonParserrw = new JSONParser();
        FileReader readerw = new FileReader("C:\\output\\ipl2.json");
        //Read JSON file
        Object objw = jsonParserrw.parse(readerw);
        
        JSONArray jsonarrayw=new JSONArray();       
        JSONObject jsobjectw=new JSONObject();
        JSONArray jarrayw = (JSONArray) objw;
        String s=QueryParameter.aggregateFunctions.get(i).function+"("+QueryParameter.aggregateFunctions.get(i).field+")";
        jsobject.put(s, min);
        jarrayw.add(jsobject);
        
       File f=new File("C:\\output\\ipl2.json");
       PrintWriter writer = new PrintWriter("C:\\output\\ipl2.json");
        writer.print("");
        writer.close();
        try (FileWriter file = new FileWriter(f)) {
                
            file.write(jarrayw.toJSONString());
            file.flush();            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void max(int i) throws FileNotFoundException, IOException, ParseException
    {
        JSONParser jsonParserr = new JSONParser();
        FileReader readerr = new FileReader("C:\\output\\ipl.json");
        //Read JSON file
        Object obj = jsonParserr.parse(readerr);
        
        JSONArray jsonarray=new JSONArray();       
        JSONObject jsobject=new JSONObject();
        
        JSONArray jarrayr = (JSONArray) obj;
        Iterator itr2 = jarrayr.iterator(); 
        Iterator<Map.Entry> itr1;
        
        
        int j=0;
        int max=-1;
        while (itr2.hasNext())  
        {   
            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            
            while (itr1.hasNext()) 
            {                 
                Map.Entry pair = itr1.next(); 
                
                if((pair.getKey().equals(QueryParameter.aggregateFunctions.get(i).field)))
                 if(Integer.parseInt((String)pair.getValue())>max)
                     max=Integer.parseInt((String)pair.getValue());
            }            
            
        } 
        QueryParameter.aggregateFunctions.get(i).result=max;
        JSONParser jsonParserrw = new JSONParser();
        FileReader readerw = new FileReader("C:\\output\\ipl2.json");
        //Read JSON file
        Object objw = jsonParserrw.parse(readerw);
        
        JSONArray jsonarrayw=new JSONArray();       
        JSONObject jsobjectw=new JSONObject();
        JSONArray jarrayw = (JSONArray) objw;
        String s=QueryParameter.aggregateFunctions.get(i).function+"("+QueryParameter.aggregateFunctions.get(i).field+")";
        jsobject.put(s, max);
        jarrayw.add(jsobject);
        
       File f=new File("C:\\output\\ipl2.json");
       PrintWriter writer = new PrintWriter("C:\\output\\ipl2.json");
        writer.print("");
        writer.close();
        try (FileWriter file = new FileWriter(f)) {
                
            file.write(jarrayw.toJSONString());
            file.flush();            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void avg(int i) throws FileNotFoundException, IOException, ParseException
    {
        JSONParser jsonParserr = new JSONParser();
        FileReader readerr = new FileReader("C:\\output\\ipl.json");
        //Read JSON file
        Object obj = jsonParserr.parse(readerr);
        
        JSONArray jsonarray=new JSONArray();       
        JSONObject jsobject=new JSONObject();
        
        JSONArray jarrayr = (JSONArray) obj;
        Iterator itr2 = jarrayr.iterator(); 
        Iterator<Map.Entry> itr1;
        
        
        int j=0;
        int sum=0;
        int avg=0;
        while (itr2.hasNext())  
        {   
            itr1 = ((Map) itr2.next()).entrySet().iterator(); 
            
            while (itr1.hasNext()) 
            {                 
                Map.Entry pair = itr1.next(); 
                
                if((pair.getKey().equals(QueryParameter.aggregateFunctions.get(i).field)))
                {
                    sum=sum+Integer.parseInt((String)pair.getValue());
                    j++;
                }
            }            
            
        } 
        avg=sum/j;
        QueryParameter.aggregateFunctions.get(i).result=avg;
        JSONParser jsonParserrw = new JSONParser();
        FileReader readerw = new FileReader("C:\\output\\ipl2.json");
        //Read JSON file
        Object objw = jsonParserrw.parse(readerw);
        
        JSONArray jsonarrayw=new JSONArray();       
        JSONObject jsobjectw=new JSONObject();
        JSONArray jarrayw = (JSONArray) objw;
        String s=QueryParameter.aggregateFunctions.get(i).function+"("+QueryParameter.aggregateFunctions.get(i).field+")";
        jsobject.put(s, avg);
        jarrayw.add(jsobject);
        
       File f=new File("C:\\output\\ipl2.json");
       PrintWriter writer = new PrintWriter("C:\\output\\ipl2.json");
        writer.print("");
        writer.close();
        try (FileWriter file = new FileWriter(f)) {
                
            file.write(jarrayw.toJSONString());
            file.flush();            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    void orderby() throws FileNotFoundException, IOException, ParseException
    {
        //JSON parser object to parse read file
        JSONParser jsonParserr = new JSONParser();
        FileReader readerr = new FileReader("C:\\output\\ipl2.json");
        //Read JSON file
        Object obj = jsonParserr.parse(readerr);
        
        
        
        List<JSONObject> myJsonArrayAsList = new ArrayList<JSONObject>();
        JSONObject jobject;
        JSONArray jarray = (JSONArray) obj;
        for(int i=0;i<jarray.size()-1;i++)
        {
            jobject=(JSONObject) jarray.get(i);
            myJsonArrayAsList.add(jobject);
        }
        
        Collections.sort(myJsonArrayAsList, new Comparator<JSONObject>() {
        @Override
        public int compare(JSONObject jsonObjectA, JSONObject jsonObjectB) {
            int compare = 0;
            int keyA=0, keyB=0;
            int k=0;
            
            if(((String)jsonObjectA.get(QueryParameter.order_by_field)).matches("[0-9]+"))
            {
                keyA = (int)jsonObjectA.get(QueryParameter.order_by_field);
                keyB = (int)jsonObjectB.get(QueryParameter.order_by_field);
                compare = Integer.compare(keyA, keyB);
            }
            else
            {
                String str1=(String)jsonObjectA.get(QueryParameter.order_by_field);
                String str2=(String)jsonObjectB.get(QueryParameter.order_by_field);
                compare=str1.compareTo(str2);
            }
            

            return compare;
        }
        });
        
        JSONParser jsonParserrw = new JSONParser();
        FileReader readerw = new FileReader("C:\\output\\ipl2.json");
        //Read JSON file
        Object objw = jsonParserrw.parse(readerw);
        JSONArray jarrayw = (JSONArray) objw;
        jarrayw.clear();
        
        for(int i=0;i<QueryParameter.aggregateFunctions.size();i++)
        {
            JSONObject o = new JSONObject();
            String s=QueryParameter.aggregateFunctions.get(i).function+"("+QueryParameter.aggregateFunctions.get(i).field+")";
            o.put(s, QueryParameter.aggregateFunctions.get(i).result);
            
            myJsonArrayAsList.add(o);

        }
        
        jarrayw.add(myJsonArrayAsList);
        
        
          File f=new File("C:\\output\\ipl2.json");
       PrintWriter writer = new PrintWriter("C:\\output\\ipl2.json");
        writer.print("");
        writer.close();
        try (FileWriter file = new FileWriter(f)) {
                
            file.write(jarrayw.toJSONString());
            file.flush();            
 
        } catch (IOException e) {
            e.printStackTrace();
        }
       
        
    }
  
    
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException  {
        QueryParser queryParser=new QueryParser();
        //queryParser.parseQuery("select * from ipl.csv where id<10 and city=Delhi");  
        
        System.out.println("enter query");
        Scanner sc=new Scanner(System.in);
        String s=sc.nextLine();
        queryParser.parseQuery(s);
        CSVtoJSON ob=new CSVtoJSON();
        
        ob.wtiteall();      
        if(QueryParameter.restrictions.size()<1)
        ob.writeWithoutRestriction();
        else if(QueryParameter.restrictions.size()==1)
            ob.cond1();
        else
            ob.cond2();
        
        for(int i=0;i<QueryParameter.aggregateFunctions.size();i++)
        {
            if(QueryParameter.aggregateFunctions.get(i).function.equalsIgnoreCase("sum"))
                ob.sum(i);
            else if(QueryParameter.aggregateFunctions.get(i).function.equalsIgnoreCase("avg"))
                ob.avg(i);
            else if(QueryParameter.aggregateFunctions.get(i).function.equalsIgnoreCase("max"))
                ob.max(i);
            else if(QueryParameter.aggregateFunctions.get(i).function.equalsIgnoreCase("min"))
                ob.min(i);
            else if(QueryParameter.aggregateFunctions.get(i).function.equalsIgnoreCase("count"))
                ob.count(i);
        }
        
        if(!(QueryParameter.order_by_field.isEmpty()))        
            ob.orderby();
          
        System.out.println("Output file saved to : C:\\output\\ipl2.json");    
        
        
        
        /*for(int i=0;i<row.length;i++)
        {
            if (row[i].matches("[0-9]+") && !(row[i].contains(".")))
            {
                System.out.println(row[i]+" Integer");
            }
            else
            if(row[i].matches("[0-9.]*"))
            {
                System.out.println(row[i]+" Float");
            }
            else
                System.out.println(row[i]+" String");
        }*/     
        
        
    }
    
}

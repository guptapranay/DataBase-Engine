/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.engine;

import java.util.ArrayList;

/**
 *
 * @author Suyash
 */
public class QueryParser {

    /**
     * @param args the command line arguments
     */
    public String filename=new String();
    public String filepath="";
    
    public void parseQuery(String queryString)
    {
        QueryParameter queryParameter=new QueryParameter();
        Restriction restriction=new Restriction();
        AggregateFunction aggregateFunction=new AggregateFunction();
        
        String query=queryString;
        String[] query_parse=query.split(" ");
        
        String base_part=new String();
        String[] base_part_parse;
        String filter_part=new String(); 
        String[] filter_part_parse;
                
        ArrayList<String> conditions = new ArrayList<String>();
        ArrayList<String> logical_operators=new ArrayList<String>();
        ArrayList<String> fields=new ArrayList<String>();
        
        String order_by_field=new String();
        String group_by_field=new String();
        
        ArrayList<String> functions=new ArrayList<String>();
        
        for(int i=0;i<query_parse.length;i++)
        {
            if(query_parse[i].contains("csv") && !(query_parse[i].contains("\\")))
            {
                filename=query_parse[i];
                filepath="";
            }
            if(query_parse[i].contains("csv") && query_parse[i].contains("\\"))
            {
                
                String[] temp=query_parse[i].split("\\\\");                
                for(int j=0;j<temp.length;j++)
                {
                    if(!(temp[j].contains("csv")))
                        filepath=filepath+temp[j]+"\\";
                    else
                        filename=temp[j];
                }
            }
            
        }
        
        
        boolean wh=false; //variable to track wheather where is found or not
        for(int i=0;i<query_parse.length;i++)
        {
            if(query_parse[i].equalsIgnoreCase("where"))
                wh=true;
            if(wh==false)
                base_part=base_part+query_parse[i]+" ";
            else
                filter_part=filter_part+query_parse[i]+" ";
        }
        base_part_parse=base_part.split(" ");
        if(!(filter_part.isEmpty()))
            filter_part=filter_part.substring(6);
        filter_part_parse=filter_part.split(" ");
        
        
        
        for(int i=0;i<filter_part_parse.length;i++)
        {
            if(filter_part_parse[i].equalsIgnoreCase("<") || filter_part_parse[i].equalsIgnoreCase(">") || filter_part_parse[i].equalsIgnoreCase("="))
            {
                conditions.add(filter_part_parse[i-1]+filter_part_parse[i]+filter_part_parse[i+1]);
            }
            else if(filter_part_parse[i].contains("<") || filter_part_parse[i].contains(">") || filter_part_parse[i].contains("="))
            {
                conditions.add(filter_part_parse[i]);
            }
            if(filter_part_parse[i].equalsIgnoreCase("and") || filter_part_parse[i].equalsIgnoreCase("or") || filter_part_parse[i].equalsIgnoreCase("not"))
            {
                logical_operators.add(filter_part_parse[i]);
            }
        }
        
        String res="";
        String[] spl=null;
        for(int i=1;!(base_part_parse[i].equalsIgnoreCase("from"));i++)
        {
            res=res+base_part_parse[i];            
            spl=res.split(",");            
        } 
        int x=0;
        ;
        for(int i=0;i<spl.length;i++)
        {   
            if((spl[i].toLowerCase().contains("count")) || (spl[i].toLowerCase().contains("max")) || (spl[i].toLowerCase().contains("avg")) || (spl[i].toLowerCase().contains("min")) || (spl[i].toLowerCase().contains("sum")))
            {
                continue;
            }
            else
                fields.add(spl[i]);                           
        }
        for(int i=0;i<spl.length;i++)
        {
            if((spl[i].toLowerCase().contains("count")))
            {
                
                QueryParameter.aggregateFunctions.add(new AggregateFunction());
                QueryParameter.aggregateFunctions.get(x).function="count";
                QueryParameter.aggregateFunctions.get(x).field=spl[i].substring(6, spl[i].length()-1);
                x++;
            }
            if((spl[i].toLowerCase().contains("max")))
            {
                QueryParameter.aggregateFunctions.add(new AggregateFunction());
                QueryParameter.aggregateFunctions.get(x).function="max";
                QueryParameter.aggregateFunctions.get(x).field=spl[i].substring(4, spl[i].length()-1);
                x++;
            }
            if((spl[i].toLowerCase().contains("avg")))
            {
                QueryParameter.aggregateFunctions.add(new AggregateFunction());
                QueryParameter.aggregateFunctions.get(x).function="avg";
                QueryParameter.aggregateFunctions.get(x).field=spl[i].substring(4, spl[i].length()-1);
                x++;
            }
            if((spl[i].toLowerCase().contains("min")))
            {
                QueryParameter.aggregateFunctions.add(new AggregateFunction());
                QueryParameter.aggregateFunctions.get(x).function="min";
                QueryParameter.aggregateFunctions.get(x).field=spl[i].substring(4, spl[i].length()-1);
                x++;
            }
            if((spl[i].toLowerCase().contains("sum")))
            {
                QueryParameter.aggregateFunctions.add(new AggregateFunction());
                QueryParameter.aggregateFunctions.get(x).function="sum";
                QueryParameter.aggregateFunctions.get(x).field=spl[i].substring(4, spl[i].length()-1);
                x++;
            }
        }
        
        
        for(int i=0;i<filter_part_parse.length;i++)
        {
            if(filter_part_parse[i].equalsIgnoreCase("order") && filter_part_parse[i+1].equalsIgnoreCase("by"))
                order_by_field=filter_part_parse[i+2];
            if(filter_part_parse[i].equalsIgnoreCase("group") && filter_part_parse[i+1].equalsIgnoreCase("by"))
                group_by_field=filter_part_parse[i+2];
            if(filter_part_parse[i].equalsIgnoreCase("orderby"))
                order_by_field=filter_part_parse[i+1];
            if(filter_part_parse[i].equalsIgnoreCase("groupby"))
                group_by_field=filter_part_parse[i+1];
        }
        
        
        res="";
        spl=null;
        for(int i=1;!(base_part_parse[i].equalsIgnoreCase("from"));i++)
        {
            res=res+base_part_parse[i]; 
            spl=res.split(",");   
            
        }
        for(int i=1;i<spl.length;i++)
        {
            if(spl[i].toLowerCase().contains("min") || spl[i].toLowerCase().contains("max") || spl[i].toLowerCase().contains("avg") || spl[i].toLowerCase().contains("count") || spl[i].toLowerCase().contains("sum"))
                functions.add(spl[i]);
            
            
        }
        
        
        for(int i=0;i<conditions.size();i++)
        {
            queryParameter.restrictions.add(new Restriction());
            if(conditions.get(i).contains("<"))
            {
                String[] s=conditions.get(i).split("<");
                queryParameter.restrictions.get(i).propertyName=s[0];
                queryParameter.restrictions.get(i).condition="<";
                queryParameter.restrictions.get(i).propertyVale=s[1];
                if(s[1].charAt(0)=='\'')
                queryParameter.restrictions.get(i).propertyVale=s[1].substring(1, s[1].length()-1);
                
                
                
            }
            else if(conditions.get(i).contains(">"))
            {
                String[] s=conditions.get(i).split(">");
                queryParameter.restrictions.get(i).propertyName=s[0];
                queryParameter.restrictions.get(i).condition=">";
                queryParameter.restrictions.get(i).propertyVale=s[1];
                if(s[1].charAt(0)=='\'')
                queryParameter.restrictions.get(i).propertyVale=s[1].substring(1, s[1].length()-1);
                
                
            }
            else if(conditions.get(i).contains("="))
            {
                String[] s=conditions.get(i).split("=");
                queryParameter.restrictions.get(i).propertyName=s[0];
                queryParameter.restrictions.get(i).condition="=";
                queryParameter.restrictions.get(i).propertyVale=s[1];
                if(s[1].charAt(0)=='\'')
                queryParameter.restrictions.get(i).propertyVale=s[1].substring(1, s[1].length()-1);
                
            }
                    
        }
        
        queryParameter.file=filename;
        queryParameter.queryString=query;
        queryParameter.baseQuery=base_part;
        queryParameter.fields=fields;
        queryParameter.path=filepath;
        queryParameter.logical_operators=logical_operators;
        queryParameter.order_by_field=order_by_field;
        queryParameter.group_by_field=group_by_field;
        
        
        
    }
    
    
}

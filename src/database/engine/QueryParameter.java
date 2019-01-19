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

public class QueryParameter {
    String queryString;
    public static String file;
    public static String path;
    String baseQuery;
    String QUERY_TYPE;
    public static ArrayList<Restriction> restrictions=new ArrayList<Restriction>();
    public static ArrayList<String> fields=new ArrayList<String>();
    public static ArrayList<String> logical_operators=new ArrayList<String>();
    public static String order_by_field=new String();
    public static String group_by_field=new String();
    public static ArrayList<AggregateFunction> aggregateFunctions=new ArrayList<AggregateFunction>();

    

    

    
    
    
    
}

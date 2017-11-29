/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wacoal.dockeeper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wacoal.dockeeper.wsdl.EmpClass;
import com.wacoal.dockeeper.wsdl.GetEmpByFilterResponse;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Administrator
 */
@RestController
@RequestMapping("/main")
public class MainController {
    
    @Autowired
    EmpClient empClient;
    
    @Autowired
    DataSource datasource;
    
    //@RequestMapping(method= RequestMethod.GET, value= "/main?{name}")
    @RequestMapping(method = RequestMethod.GET, value = "")
    public ModelAndView index(Model model) {
        //ModelAndView a = new ModelAndView();
        //return "Hello Worldscd"+name;
        GetEmpByFilterResponse resp = empClient.getEmpByFilter();
        List<EmpClass> emps = resp.getGetEmpByFilterResult().getEmpClass();
        EmpClass emp = emps.get(0);
        model.addAttribute("emp", emp);
  
        return new ModelAndView("main");
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/{name}")
    public String getName(@PathVariable String name) throws Exception {
        
        StringBuilder restUrl = new StringBuilder("http://twcapi.wacoalsampan.com/api/emp/search/v1?empname=");
        restUrl.append(name).append("&api_token=BI8YXJFZmvYGhhx9MjjoLcC8mLMza85oUN9uJtH7uMUEORlqhTNBQ6fMEZXn");
        
        RestTemplate rest = new RestTemplate();
        String resp = rest.getForObject(restUrl.toString(), String.class);
        
        ObjectMapper mapper = new ObjectMapper();
        
        Map<String, String>[] map = mapper.readValue(resp, new TypeReference<Map<String, String>[]>(){});

        
        return resp;
    }
    
    @RequestMapping(method = RequestMethod.GET, value = "/some")
    public String getSomeParam(
            @RequestParam(value="name", required=false, defaultValue="World") String name
    ) throws Exception {
        Connection con = this.datasource.getConnection();
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery("select * from ms_type");
//        while(res.next()){
//            //System.out.println(res.getString("type_id")+"|"+res.getString("type_name"));
//            //System.out.println("Hellow\n");
//        }
        
        JSONArray arr = convertResultSetIntoJSON(res);
        
        List<String> list = new ArrayList<String>();
        for(int i = 0; i < arr.length(); i++) {
            //list.add(arr.getJSONObject(i).getString("name"));
        }
        
        //System.out.println(arr.toString());
        
        res.close();
        stmt.close();
        
        CallableStatement func = con.prepareCall("");
        
        con.close();
        return arr.toString();
    }
    
    public static JSONArray convertResultSetIntoJSON(ResultSet resultSet) throws Exception {
        JSONArray jsonArray = new JSONArray();
        while (resultSet.next()) {
            int total_rows = resultSet.getMetaData().getColumnCount();
            JSONObject obj = new JSONObject();
            for (int i = 0; i < total_rows; i++) {
                String columnName = resultSet.getMetaData().getColumnLabel(i + 1).toLowerCase();
                Object columnValue = resultSet.getObject(i + 1);
                // if value in DB is null, then we set it to default value
                if (columnValue == null) {
                    columnValue = "null";
                }
 
                if (obj.has(columnName)) {
                    columnName += "1";
                }
                obj.put(columnName, columnValue);
            }
            jsonArray.put(obj);
        }
        return jsonArray;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author ASUS
 */
public class findId {
    public static void main(String[] args) {
        try{
            String id = "PC00128";
            String url = "https://serverstudent.herokuapp.com/findStudent"+id;
//            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
            int responCode = con.getResponseCode();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputline;
            StringBuffer response = new StringBuffer();
            while ((inputline = in.readLine()) != null) {
                response.append(inputline);
            }
            in.close();
            System.out.println(response.toString());
            JSONObject myresponse = new JSONObject(response.toString());
//            for(int i = 0; i<myresponse.length();i++){
            String massv = myresponse.getString("massv");
            String name = myresponse.getString("name");
            int age = myresponse.getInt("age");
            JSONObject json = new JSONObject(myresponse.get("clazz").toString());
//                String idClazz = json.getString("id");
                String nameClazz = json.getString("name");
//
//            txtMasv.setText(massv);
//            txtName.setText(name);
//            txtAge.setText(String.valueOf(age));
                System.out.println("name : "+name);
                System.out.println("age : "+age);
                System.out.println("massv: "+massv);
                System.out.println("clazz: "+nameClazz);
//            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}

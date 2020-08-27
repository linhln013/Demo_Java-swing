/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package http;

import static com.sun.org.apache.xml.internal.serialize.OutputFormat.Defaults.Encoding;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.crypto.NoSuchPaddingException;
import static jdk.nashorn.tools.ShellFunctions.input;
import org.json.JSONException;

/**
 *
 * @author ASUS
 */
public class requestStudent {
    public static void main(String[] args) throws MalformedURLException, IOException, JSONException, NoSuchAlgorithmException, NoSuchPaddingException {
        URL url = new URL("https://serverstudent.herokuapp.com/");
        Map<String,Object> params = new LinkedHashMap<>();
        params.put("massv", "PC00128");
        params.put("name", "Le Nhat Linh");
        params.put("age", 20);
        
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String,Object> param : params.entrySet()){
            if(postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append("=");
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
            
        }
        System.out.println("postData : "+ postData);
//        String a = new String(postData.toString().getBytes("UTF-8"));
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");
        System.out.println("postDataBytes : "+ postDataBytes);
        
        HttpURLConnection con = (HttpURLConnection) url.openConnection();//Open a Connection
        con.setRequestMethod("POST");//Set the Request Method
        con.setRequestProperty("Content-Type", "application/www-form-urlencoded");//Set the Request Content-Type Header Parameter
        con.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));//Set Response Format Type
        con.setDoOutput(true);//Ensure the Connection Will Be Used to Send Content
        con.getOutputStream().write(postDataBytes);
//        String jsonInputString = "details={\"massv\":\"pc00128\",\"name\":\"LeNhatLinh\",\"age\":\"20\"}";
//        try (OutputStream os = con.getOutputStream()) {
//            byte[] input = jsonInputString.getBytes("utf-8");
//            os.write(input, 0, input.length);
//            System.out.println("Successful");
//        }
    }
}

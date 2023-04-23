package demo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URI;
import java.net.URL;

public class App {
    public static void main(String[] args) throws Exception {
       
        RestProxy proxy = new RestProxy(new URI("http://localhost:8080/api"));
        
        System.out.println(proxy.get(String.class).length());
        System.out.println(proxy.post(String.class));

        
        URL url = new URL("http://localhost:8080/api/categories/");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

      //  con.setRequestProperty("Content-Type", "application/json");

      // ale lepší nastavit
//        con.setConnectTimeout(5000);
  //      con.setReadTimeout(5000);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
            System.out.println(content.toString());
        }
        in.close();
        con.disconnect();

        System.out.println(content.toString());
    }
}

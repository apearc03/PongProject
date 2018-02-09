package pongproject.game.webservice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;


//Use this soley to retrieve database credentials
public class webserviceConnector {
	
	private URL url;
	private URLConnection con;
	private JSONParser jsonParse;
	private JSONArray jsonArr;
	private byte[] decoded;

	private String inputLine;
	private String reconstitutedString;
	private Object obj;
	
	public webserviceConnector() {
		
		jsonParse = new JSONParser();
		jsonArr = new JSONArray();
	}
	
	
	//When game starts connection can be checked by sending a "" parameter call to this method. If in the PHP script GET['operation'] is not set, return a JSON {connection: false}
	
	public JSONArray webServiceQuery(String param) throws Exception {
	
		jsonArr.clear();
    	
        url = new URL("http://titan.dcs.bbk.ac.uk/~apearc03/projectphp/projectPHP.php?"+param);
        con = url.openConnection();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        
       

        
        while ((inputLine = in.readLine()) != null) { 
        	
        	decoded = Base64.getDecoder().decode(inputLine);
        	reconstitutedString = new String(decoded);
        	//System.out.println(reconstitutedString);
            obj  = jsonParse.parse(reconstitutedString);  
            jsonArr.add(obj);

            
  
        }
        
        
      
        in.close();
        
        return jsonArr;
	}
	

	
	
	
	

	
	
}

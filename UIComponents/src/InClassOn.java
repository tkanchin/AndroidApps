import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

public class InClassOn {
	 
	public void readFileAtPath(String filename) {
        // Lets make sure the file path is not empty or null
        if (filename == null || filename.isEmpty()) {
               System.out.println("Invalid File Path");
               	return; 
               	}
        String filePath = System.getProperty("user.dir") + "/" + filename;
        BufferedReader inputStream = null;
        // We need a try catch block so we can handle any potential IO errors
        try {
               try {
                      inputStream = new BufferedReader(new FileReader(filePath));
                      String lineContent = null;
                      
                      ArrayList<String> myArrayList = new ArrayList<String>(); 
                      
                      HashMap<String, String> myHashMap = new HashMap<String, String>();
                   
                      String[] resultingTokens;
                      
                     
                      // Loop will iterate over each line within the file.
                      // It will stop when no new lines are found.
                      while ((lineContent = inputStream.readLine()) != null) {
                         // System.out.println("Found the line: " + lineContent);
                    	  
                    	  //lineContent += lineContent;
                    	  
                    	  resultingTokens = lineContent.split(",");
                    	  
                    	  myHashMap.put(resultingTokens[1], resultingTokens[2]);
                    	  
                    		for (int i = 0; i < resultingTokens.length; i++) {
                          		
                          		//System.out.println(resultingTokens[i].trim());
                    			
                    			//if (resultingTokens[i].matches("Year") || resultingTokens[i].matches("Movie") || resultingTokens[i].matches("$")) {
                    				
                    				//continue;
                    				
                    			//}
          
                    			
                    			myArrayList.add(resultingTokens[i].trim());
                
                    			
                    		
                    			
                    			
                          	}
       
                      }
                      
                    
                      
                  
              		System.out.println(myArrayList);
                      
                	
              
              
              		
              	
              	HashMap<String, String> reversedHashMap = new HashMap<String, String>();
              	for (String key : myHashMap.keySet()){
              	    reversedHashMap.put(myHashMap.get(key), key);
              	}  	  
              	      
            	System.out.println(reversedHashMap);
            	
            	
              	
              		
                     }
                      
               // Make sure we close the buffered reader.
               finally {
                      if (inputStream != null)
                            inputStream.close();
               }
        } catch (IOException e) {
               e.printStackTrace();
  }// end of method

		
		}
	
	public static void main(String args[]) {
		
		InClassOn obj = new InClassOn();
		
		obj.readFileAtPath("topmovies.csv");
		
		
		
		
	}
	
	
}




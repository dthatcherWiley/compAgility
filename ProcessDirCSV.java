import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;
public class ProcessDirCSV {    public static void main(String args[]) throws IOException {
      
      File directoryPath = new File("./csv");
      //List of all files and directories
      String contents[] = directoryPath.list();
      //System.out.println("List =:" + contents.length);

      Vector system = new Vector();
      Vector category = new Vector();
      Vector score = new Vector();
      int sysCntr = 0;
      int catCntr = 0;
      int scoreCntr = 0;

      HashMap<String, String> progTBL = new HashMap<String, String>();
      HashMap<String, String> scoreTBL = new HashMap<String, String>();

      Scanner programTable = new Scanner(new File("./csv/ProgramTable.csv")); 
      programTable.useDelimiter(","); 
      int tblCntr = 0;
      String key = "";
      String value = "";
      while (programTable.hasNext())  //returns a boolean value  
        {  
            tblCntr++;
            if (tblCntr == 1){
                key = programTable.next().trim();
            }
            if (tblCntr == 2){
                value = programTable.next().trim();
                progTBL.put(key, value);
                value = "";
                key = "";
                tblCntr = 0;
            }
        }
        
        Scanner scoreTable = new Scanner(new File("./csv/ScoreTable.csv")); 
        scoreTable.useDelimiter(","); 
        tblCntr = 0;
        key = "";
        value = "";
         while (scoreTable.hasNext())  //returns a boolean value  
        {  
            tblCntr++;
            if (tblCntr == 1){
                key = scoreTable.next().trim();
            }
            if (tblCntr == 2){
                value = scoreTable.next().trim();
                scoreTBL.put(key, value);
                value = "";
                key = "";
                tblCntr = 0;
            }
        }
      //System.out.println("HashMap = " +scoreTBL.toString());
      System.out.println("Program,System,Category,Maturity Model Rate,Maturity Score");
      for(int i=0; i<contents.length; i++) {
        
         String filename = contents[i];
         
         if (filename.endsWith(".csv")) {
           
            Scanner sc = new Scanner(new File("./csv/" + filename));    
            sc.useDelimiter(",");   //sets the delimiter pattern 
            int firstOccurence=0;
            String scat=filename; 
            
            system.add(sysCntr, filename);
            sysCntr++;

            while (sc.hasNext())  //returns a boolean value  
                    {  
                      String s = sc.next();
                      
                      if (s.startsWith("People -") || s.startsWith("Process -" ) || s.startsWith("Tech -" ) ){
                          firstOccurence++;
                         if (firstOccurence <= 21) { 
                             int idx=s.indexOf("Your");
                             if (idx>0) {
                                 s = s.substring(0, idx);
                             }
                             //scat = scat +"," + s+"("+firstOccurence+") ";
                             category.add(catCntr, s.trim()); 
                             catCntr++;                                                        
                            // System.out.println("FILENAME: " + s);  //find and returns the next complete token from this scanner  
                          }
                       }

                       if (firstOccurence >= 21 && firstOccurence < 45){
                           firstOccurence++;
                           if (firstOccurence > 23 && firstOccurence< 45){
                               int idx = s.indexOf("Comparative");
                               if (idx>0) {
                                 s = s.substring(0, idx);
                               }
                              // scat = scat +"," + s+"("+firstOccurence+") ";
                               score.add(scoreCntr, s.trim());
                               scoreCntr++;                               
                           }
                       }
                    }
                    
                   Iterator isystem = system.iterator();  
                   
                   String printSystem = "";
                    while (isystem.hasNext()) { 
                        printSystem=isystem.next().toString();
                        int idx = printSystem.indexOf(".csv");
                        printSystem = printSystem.substring(0, idx);
                       
                    } 

                    Iterator icategory = category.iterator();
                    Iterator iscore = score.iterator();   
                    
                    while (icategory.hasNext()) { 
                        System.out.print(progTBL.get(printSystem)+",");
                        System.out.print(printSystem + ",");
                        System.out.print(icategory.next());
                        if (iscore.hasNext()) { 
                            System.out.print(",");
                            String S = iscore.next().toString();
                            if (S.isBlank()) {
                                S="0";
                            }
                            float f=Float.parseFloat(S);
                            int cat = Math.round(f);
                            String s=String.valueOf(cat).trim();
                            System.out.print(scoreTBL.get(s) + ",");
                            System.out.println(s);
                            // System.out.println(iscore.next());
                        }
                       
                    } 
                   
                    system.clear();
                    category.clear();
                    score.clear();
                    sysCntr = 0;
                    catCntr = 0;
                    scoreCntr = 0;
                    sc.close();  
         }
          
      }
   }
}

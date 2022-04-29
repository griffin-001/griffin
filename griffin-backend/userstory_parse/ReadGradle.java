package userstory_parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ReadGradle {

    private static final HashMap<String,String> dependencies = new HashMap<>();

    public static void main(String[] args) {

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File("griffin-backend/userstory_parse/build_test.gradle"))));
            String line;
            while((line=br.readLine())!=null){
                line = line.trim();
                if(line.length() != 0){
                    //Exclude lengths less than 5 { or }
                    if(line.length()>5){
                        //Excluding comments
                        if(line.charAt(0)!='/' && line.charAt(0)!='*'){
                            //Excluding those ending in) or {
                            if((line.charAt(line.length()-1)!=')') && (line.charAt(line.length()-1)!='{')){
                                //Exclude those that do not end with ' "
                                if((line.charAt(line.length()-1)=='\'') || (line.charAt(line.length()-1)=='\"')){
                                    //style 1
                                    if(line.split(" ")[1].charAt(0)=='\'' ||line.split(" ")[1].charAt(0)=='\"'){
                                        line = line.split(" ")[1];
                                        if(line.contains(":")){
                                            String[] split = line.replace("'","").replace("\"","").split(":");
                                            if(split.length>2){
                                                dependencies.put(split[1],split[2]);
                                            }
                                        }
                                    //style 2
                                    }else if(line.split(" ")[1].equals("group:")){
                                        String[] split = line.split("'");
                                        dependencies.put(split[3],split[5]);
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }catch (Exception e) {
            e.printStackTrace();
        }

        // Print dependencies HashMap
        System.out.println("\nDependencies: ");
        dependencies.forEach((key, value) -> System.out.println("Name: " + key + ", Version: " + value));

    }
}

package com.griffin.transformer;

import java.io.*;
import java.util.*;

public class ReadGradle {
    public static String parseProjectName(File file) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = br.readLine()) != null) {
                line=line.trim();
                if(line.contains("=")){
                    return line.split("=")[1].replace("'","").trim();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static List<String> parseDependencies(File file) {
        List<String> dependencies = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while((line=br.readLine())!=null){
                line = line.trim();
                if(line.length() != 0){
                    //Exclude lengths less than 3 { or }
                    if(line.length()>3){
                        //Excluding comments
                        if(line.charAt(0)!='/' && line.charAt(0)!='*'){
                            //style 3
                            if(line.startsWith("id")){
                                if(line.contains("'")){
                                    String[] split = line.split("'");
                                    if(split.length>=3){
                                        dependencies.add("NoGroup"+";"+split[1]+";"+split[3]);
                                    }else if(split.length<3){
                                        dependencies.add("NoGroup"+";"+split[1]+";"+"latest");
                                    }
                                }else if(line.contains("'")){
                                    String[] split = line.split("\"");
                                    if(split.length>=3){
                                        dependencies.add("NoGroup"+";"+split[1]+";"+split[3]);
                                    }else if(split.length<3){
                                        dependencies.add("NoGroup"+";"+split[1]+";"+"latest");
                                    }
                                }
                            }
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
                                                dependencies.add(split[0]+";"+split[1]+";"+split[2]);
                                            }else if(split.length==2){
                                                dependencies.add(split[0]+";"+split[1]+";"+"latest");
                                            }
                                        }
                                        //style 2
                                    }else if(line.split(" ")[1].equals("group:")){
                                        String[] split = line.split("'");
                                        dependencies.add(split[1]+";"+split[3]+";"+split[5]);
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
        return dependencies;
    }
}

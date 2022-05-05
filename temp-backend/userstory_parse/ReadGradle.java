package userstory_parse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ReadGradle {

    private static final List<String> dependency = new LinkedList<>();

    public static void main(String[] args) {
        //file package
        File fileList = new File("temp-backend/userstory_parse/gradle_files");
        //file set
        File[] files = fileList.listFiles();
        for (File file : files) {
            String filePath = file.toString();
            if(filePath.endsWith("gradle")){
                File realFile = new File(filePath);
                readGradle(realFile);
            }
        }

        System.out.println("\nDependencies: ");
        for (String s : dependency) {
            System.out.println(s);
        }
    }

    private static void readGradle(File file) {
        try{
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
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
                                                dependency.add(split[0]+";"+split[1]+";"+split[2]);
                                            }
                                        }
                                        //style 2
                                    }else if(line.split(" ")[1].equals("group:")){
                                        String[] split = line.split("'");
                                        dependency.add(split[1]+";"+split[3]+";"+split[5]);
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
    }
}

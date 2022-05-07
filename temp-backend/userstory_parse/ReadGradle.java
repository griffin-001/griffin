import java.io.*;
import java.util.*;

public class ReadGradle {

    private static String projectName = "";
//    private static final List<String> dependency = new LinkedList<>();
    private static final HashSet<String> dependencies = new HashSet<>();

    public static void main(String[] args) {
        //file package
        File fileList = new File("temp-backend/userstory_parse/gradle_files");
        //file set
        File[] files = fileList.listFiles();
        for (File file : files) {
            String filePath = file.toString();
            File realFile = new File(filePath);
            if(filePath.endsWith("settings.gradle")){
                readProjectName(realFile);
            }
            if(filePath.endsWith("gradle")){
                readGradle(realFile);
            }
        }

        System.out.println("\nProjectName: ");
        System.out.println(projectName);
        System.out.println("\nDependencies: ");
        for (String s : dependencies) {
            System.out.println(s);
        }
    }

    private static void readProjectName(File file) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line;
                while ((line = br.readLine()) != null) {
                    line=line.trim();
                    if(line.contains("=")){
                        projectName = line.split("=")[1].replace("'","").trim();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    private static void readGradle(File file) {
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
//                                        dependency.add("NoGroup"+";"+split[1]+";"+split[3]);
                                        dependencies.add("NoGroup"+";"+split[1]+";"+split[3]);
                                    }else if(split.length<3){
//                                        dependency.add("NoGroup"+";"+split[1]+";"+"latest");
                                        dependencies.add("NoGroup"+";"+split[1]+";"+"latest");
                                    }
                                }else if(line.contains("'")){
                                    String[] split = line.split("\"");
                                    if(split.length>=3){
//                                        dependency.add("NoGroup"+";"+split[1]+";"+split[3]);
                                        dependencies.add("NoGroup"+";"+split[1]+";"+split[3]);
                                    }else if(split.length<3){
//                                        dependency.add("NoGroup"+";"+split[1]+";"+"latest");
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
//                                                dependency.add(split[0]+";"+split[1]+";"+split[2]);
                                                dependencies.add(split[0]+";"+split[1]+";"+split[2]);
                                            }else if(split.length==2){
//                                                dependency.add(split[0]+";"+split[1]+";"+"latest");
                                                dependencies.add(split[0]+";"+split[1]+";"+"latest");
                                            }
                                        }
                                        //style 2
                                    }else if(line.split(" ")[1].equals("group:")){
                                        String[] split = line.split("'");
//                                        dependency.add(split[1]+";"+split[3]+";"+split[5]);
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
    }
}

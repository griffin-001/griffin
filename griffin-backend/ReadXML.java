import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.HashMap;

public class ReadXML {
    private static String projectName = "";
    private static final HashMap<String,String> plugins = new HashMap<>();
    private static final HashMap<String,String> dependencies = new HashMap<>();
    private static final HashMap<String,String> properties = new HashMap<>();

    public static void main(String[] argv) {
        try {
            File xmlFile = new File("griffin-backend/pom.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            getName(doc);
            getPlugins(doc);
            getDependencies(doc);
            getProperties(doc);

            // Print document name
            System.out.println("Project Name: \n" + projectName);

            // Print plugins HashMap
            System.out.println("\nPlugins: ");
            plugins.forEach((key, value) -> System.out.println("Name: " + key + ", Version: " + value));

            // Print dependencies HashMap
            System.out.println("\nDependencies: ");
            dependencies.forEach((key, value) -> System.out.println("Name: " + key + ", Version: " + value));

            // Print properties HashMap
            System.out.println("\nProperties: ");
            properties.forEach((key, value) -> System.out.println(key + " " + value));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getName(Document doc) {
        // Gets the first element called artifactId
        // NOTE: Unsure if the title is always the first artifactId element
        NodeList nameList = doc.getElementsByTagName("artifactId");
        Element element = (Element) nameList.item(0);
        projectName = element.getTextContent();
    }

    public static void getPlugins(Document doc) {
        // Finds all <plugin> elements in the XML file
        NodeList pluginList = doc.getElementsByTagName("plugin");

        // Get the name and version of each plugin
        xmlToHashMap(pluginList, plugins);
    }

    public static void getDependencies(Document doc) {
        // Finds all <dependency> elements in the XML file
        NodeList dependencyList = doc.getElementsByTagName("dependency");

        // Get the name and version of each plugin
        xmlToHashMap(dependencyList, dependencies);
    }

    public static void getProperties(Document doc) {
        // Finds all <property> elements in the XML file
        NodeList dependencyList = doc.getElementsByTagName("property");

        // Get the name and version of each plugin
        xmlToHashMap(dependencyList, properties);
    }

    private static void xmlToHashMap(NodeList dependencyList, HashMap<String, String> dependencies) {
        for(int i = 0; i < dependencyList.getLength(); i++) {
            Element element = (Element) dependencyList.item(i);

            NodeList nameNode = element.getElementsByTagName("artifactId");
            NodeList versionNode = element.getElementsByTagName("version");

            Element nameElement = (Element) nameNode.item(0);
            Element versionElement = (Element) versionNode.item(0);

            dependencies.put(nameElement.getTextContent(), versionElement.getTextContent());
        }
    }
}
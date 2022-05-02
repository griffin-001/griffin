package userstory_parse;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReadXML {
    private static String projectName = "";
    private static final List<String> plugins = new ArrayList<>();
    private static final List<String> dependencies = new ArrayList<>();
    private static final List<String> properties = new ArrayList<>();

    public static void main(String[] argv) {
        try {
            File xmlFile = new File("temp-backend/userstory_parse/pom.xml");
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
            plugins.forEach(System.out::println);

            // Print dependencies HashMap
            System.out.println("\nDependencies: ");
            dependencies.forEach(System.out::println);

            // Print properties HashMap
            System.out.println("\nProperties: ");
            properties.forEach(System.out::println);

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
        xmlToStrings(pluginList, plugins);
    }

    public static void getDependencies(Document doc) {
        // Finds all <dependency> elements in the XML file
        NodeList dependencyList = doc.getElementsByTagName("dependency");

        // Get the name and version of each plugin
        xmlToStrings(dependencyList, dependencies);
    }

    public static void getProperties(Document doc) {
        // Finds all <property> elements in the XML file
        NodeList dependencyList = doc.getElementsByTagName("property");

        // Get the name and version of each plugin
        xmlToStrings(dependencyList, properties);
    }

    private static void xmlToStrings(NodeList dependencyList, List<String> stringList) {
        for(int i = 0; i < dependencyList.getLength(); i++) {
            Element element = (Element) dependencyList.item(i);

            NodeList groupIdNode = element.getElementsByTagName("groupId");
            NodeList artifactIdNode = element.getElementsByTagName("artifactId");
            NodeList versionNode = element.getElementsByTagName("version");

            Element groupIdElement = (Element) groupIdNode.item(0);
            Element artifactIdElement = (Element) artifactIdNode.item(0);
            Element versionElement = (Element) versionNode.item(0);

            stringList.add(groupIdElement.getTextContent() + ":" +
                          artifactIdElement.getTextContent() + ":" +
                             versionElement.getTextContent());
        }
    }
}
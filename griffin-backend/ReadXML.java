import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import org.junit.Assert;

public class ReadXML {
    private static String projectName = "";
    private static HashMap<String,String> plugins = new HashMap<>();
    private static HashMap<String,String> dependencies = new HashMap<>();
    private static HashMap<String,String> properties = new HashMap<>();

    public static void main(String[] argv) {
        try {
            File xmlFile = new File("griffin-backend/pom.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xmlFile);

            getPlugins(doc);



            // Print plugins HashMap
            plugins.forEach((key, value) -> System.out.println(key + " " + value));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getPlugins(Document doc) {

        // Finds all <plugin> elements in the XML file
        NodeList pluginList = doc.getElementsByTagName("plugin");

        // Get the name and version of each plugin
        for(int i = 0; i < pluginList.getLength(); i++) {
            Element element = (Element) pluginList.item(i);

            NodeList nameNode = element.getElementsByTagName("artifactId");
            NodeList versionNode = element.getElementsByTagName("version");

            Element nameElement = (Element) nameNode.item(0);
            Element versionElement = (Element) versionNode.item(0);

            plugins.put(nameElement.getTextContent(), versionElement.getTextContent());
        }
    }
}
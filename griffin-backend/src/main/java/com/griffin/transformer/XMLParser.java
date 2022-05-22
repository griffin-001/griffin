package com.griffin.transformer;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Component
public class XMLParser {
    private static final Logger log = LoggerFactory.getLogger(XMLParser.class);
    public String parseProjectName(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // Gets the first element called artifactId
            NodeList nameList = doc.getElementsByTagName("artifactId");
            Element element = (Element) nameList.item(0);
            return element.getTextContent();

        } catch (Exception e) {
            log.warn("Couldn't parse XML project name");
            return null;
        }
    }

    public List<String> parsePlugins(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // Finds all <plugin> elements in the XML file
            NodeList pluginList = doc.getElementsByTagName("plugin");

            // Get the name and version of each plugin
            return xmlToStrings(pluginList);

        } catch (Exception e) {
            log.warn("Couldn't parse XML plugins");
            return null;
        }
    }

    public List<String> parseDependencies(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // Finds all <dependency> elements in the XML file
            NodeList dependencyList = doc.getElementsByTagName("dependency");

            // Get the name and version of each plugin
            return xmlToStrings(dependencyList);

        } catch (Exception e) {
            log.warn("Couldn't parse XML dependencies");
            return null;
        }
    }

    public List<String> parseProperties(File file) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);

            // Finds all <property> elements in the XML file
            NodeList dependencyList = doc.getElementsByTagName("property");

            // Get the name and version of each plugin
            return xmlToStrings(dependencyList);

        } catch (Exception e) {
            log.warn("Couldn't parse XML properties");
            return null;
        }
    }

    private List<String> xmlToStrings(NodeList dependencyList) {
        List<String> stringList = new ArrayList<>();

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
        return stringList;
    }
}

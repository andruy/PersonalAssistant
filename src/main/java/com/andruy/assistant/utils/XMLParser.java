package com.andruy.assistant.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLParser {
    public String getXML(String urlString) {
        try {
            // Download XML content from the URL
            StringBuilder xmlContent = new StringBuilder();
            URL url = new URL(urlString);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                xmlContent.append(line);
            }
            reader.close();

            // Parse the XML content
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(urlString);

            // Convert the document to a string
            String xmlString = parseNode(doc.getDocumentElement());

            return xmlString;
        } catch (Exception e) {
            e.printStackTrace();

            return "No content found.";
        }
    }

    private String parseNode(Node node) {
        StringBuilder xmlStringBuilder = new StringBuilder();
        xmlStringBuilder.append("<").append(node.getNodeName()).append(">");

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node childNode = nodeList.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                xmlStringBuilder.append(parseNode(childNode));
            } else if (childNode.getNodeType() == Node.TEXT_NODE) {
                xmlStringBuilder.append(childNode.getTextContent());
            }
        }

        xmlStringBuilder.append("</").append(node.getNodeName()).append(">");
        return xmlStringBuilder.toString();
    }
}

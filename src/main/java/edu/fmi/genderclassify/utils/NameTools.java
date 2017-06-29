package edu.fmi.genderclassify.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Miroslav Kramolinski
 */
public class NameTools {
    private static Map<String, String> namesCache; // attempt at reducing HTTP requests
    /**
     *
     * @param name should be a first name, which the gender should be determined for
     * @return one of { null, "male", "female", "unisex" } where null means the input parameter name
     *         is not found as a valid first name and "unisex" is when the name is classified as both male and female
     */
    public static String checkGenderFromRestnames(String name) {
        if(namesCache == null)
            namesCache = new HashMap<>();

        if(namesCache.containsKey(name))
            return namesCache.get(name);

        InputStream input = null;
        try {
            input = new URL("http://www.thomas-bayer.com/restnames/name.groovy?name=" + name).openStream();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            NodeList restnames = document.getElementsByTagName("restnames");

            if(restnames.item(0).equals("error"))
                return null;

            boolean male = false;
            boolean female = false;
            for(int i = 0; i < restnames.item(0).getChildNodes().getLength(); i ++) {
                Node outerNode = restnames.item(0).getChildNodes().item(i);

                if(!outerNode.getNodeName().equalsIgnoreCase("nameinfo"))
                    continue;

                for (int j = 0; j < outerNode.getChildNodes().getLength(); j++) {
                    Node innerNode = outerNode.getChildNodes().item(j);
                    if (innerNode.getNodeName().equalsIgnoreCase("male")
                            && innerNode.getTextContent().equalsIgnoreCase("true"))
                        male = true;
                    else if (innerNode.getNodeName().equalsIgnoreCase("female")
                            && innerNode.getTextContent().equalsIgnoreCase("true"))
                        female = true;
                }
            }

            return (male && female? "unisex": (male? "male": "female"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            if(input != null)
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }


        return null;
    }
}

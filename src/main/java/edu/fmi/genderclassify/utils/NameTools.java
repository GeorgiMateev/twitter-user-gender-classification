package edu.fmi.genderclassify.utils;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class NameTools {
    private static Map<String, String> namesToGendersCache; // attempt at reducing HTTP requests
    private static Map<String, String> usernamesToFirstNamesCache; // cache the username=firstname file data
    private static String FIRST_NAMES_GENDER_CACHE = "src\\main\\resources\\firstnamesgender.txt";
    /**
     *
     * @param name should be a first name, which the gender should be determined for
     * @return one of { null, "male", "female", "unisex" } where null means the input parameter name
     *         is not found as a valid first name and "unisex" is when the name is classified as both male and female
     */
    public static String checkGenderFromRestnames(String name) {
        if(namesToGendersCache == null) {
            namesToGendersCache = new HashMap<>();

            if(new File(FIRST_NAMES_GENDER_CACHE).exists()) {
                try {
                    for(String line: Files.lines(Paths.get(FIRST_NAMES_GENDER_CACHE)).collect(Collectors.toList())) {
                        namesToGendersCache.put(line.split("=")[0], line.split("=")[1]);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    Files.createFile(Paths.get(FIRST_NAMES_GENDER_CACHE));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(namesToGendersCache.containsKey(name))
            return namesToGendersCache.get(name);

        if(name == null || name.length() < 2)
            return null;

        for(char c: name.toCharArray())
            if(!((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')))
                return null;

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

            namesToGendersCache.put(name, male && female? "unisex": (male? "male": "female"));
            try {
                String line = name + "=" + namesToGendersCache.get(name) + System.lineSeparator();
                Files.write(Paths.get(FIRST_NAMES_GENDER_CACHE), line.getBytes(), StandardOpenOption.APPEND);
            }catch (IOException e) {
                //exception handling left as an exercise for the reader
            }

            return namesToGendersCache.get(name);
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

    public static String getFirstNameFromUsername(String username) {
        if(usernamesToFirstNamesCache == null) {
            usernamesToFirstNamesCache = new HashMap<>();

            try {
                for(String line: Files.lines(Paths.get("src\\main\\resources\\firstnames.txt"), StandardCharsets.ISO_8859_1).collect(Collectors.toList())) {
                    String name = line.split("=")[0];
                    String userId = line.split("=")[1];

                    if(name != null && !name.equals(""))
                        usernamesToFirstNamesCache.put(userId, name.split(" ")[0]);
                }
//                Files.lines(Paths.get("src\\main\\resources\\firstnames.txt"))
//                        .skip(1)
//                        .forEach(line -> {
//                            String name = line.split("=")[0];
//                            String userId = line.split("=")[1];
//
//                            if(name != null && !name.equals(""))
//                                usernamesToFirstNamesCache.put(userId, name.split(" ")[0]);
//                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return usernamesToFirstNamesCache.get(username);
    }
}

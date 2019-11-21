package edu.cscc;

import java.io.IOException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;

/**
 * Process webserver configuration
 * @author prakash parasuram, Jemar Bush, Luis Espinal
 */
public class Config {
    public static final String PORT = "port";
    public static final String DEFAULTPAGE = "defaultPage";
    public static final String DEFAULTFOLDER = "defaultFolder";

    private static final String CONFIG_FILE = "./TinyWS.xml";
    private static Properties properties = new Properties();
    /**
     * Constructor Config()
     * @author prakash parasuram, Jemar Bush, Luis Espinal
     * this reads Property file in CONFIG_FILE and populates XML properties in properties
     * as key value pairs
     */
    public Config() {
        // properties = new Properties();
        try {
            this.readProperties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            TinyWS.fatalError(e);
        } catch (IOException e) {
            e.printStackTrace();
            TinyWS.fatalError(e);
        }
    }
    /**
     * readProperties() this reads Property file in CONFIG_FILE and populates XML properties in properties as key value pairs
     * @author prakash parasuram, Jemar Bush, Luis Espinal
     */
    public void readProperties() throws IOException {
                File file = new File(CONFIG_FILE);
                FileInputStream fileInput = new FileInputStream(file);
                // Properties properties = new Properties();
                properties.loadFromXML(fileInput);
                fileInput.close();

    }
    /**
     * getProperty(String key) this returns value of the key given in xml
     * @author prakash parasuram, Jemar Bush, Luis Espinal
     * @param key
     * @return keyValue as a string
     * as key value pairs
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    /**
     * this just dumps all properties to standard out
     * dumpProperties()
     * @author prakash parasuram, Jemar Bush, Luis Espinal
     */
    public void dumpProperties() {
            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                System.out.println(key + ": " + value);
        }
    }
}

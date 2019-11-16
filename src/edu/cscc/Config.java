package edu.cscc;

import java.io.IOException;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;

/**
 * Process webserver configuration
 * @author student name
 */
public class Config {
    public static final String PORT = "port";
    public static final String DEFAULTPAGE = "defaultPage";
    public static final String DEFAULTFOLDER = "defaultFolder";

    private static final String CONFIG_FILE = "./TinyWS.xml";
    private static Properties properties;
    public Config() {
        // TODO code here
        properties = new Properties();
        try {
            this.readProperties();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readProperties() throws IOException {
        // TODO code here

                File file = new File(CONFIG_FILE);
                FileInputStream fileInput = new FileInputStream(file);
                // Properties properties = new Properties();
                properties.loadFromXML(fileInput);
                fileInput.close();

    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void dumpProperties() {
        // TODO code here
            Enumeration enuKeys = properties.keys();
            while (enuKeys.hasMoreElements()) {
                String key = (String) enuKeys.nextElement();
                String value = properties.getProperty(key);
                System.out.println(key + ": " + value);
        }
    }
}

package edu.cscc;

import static java.lang.Integer.parseInt;

/**
 * TinyWS a simplistic Tiny Web Server
 * @author student name here
 */
public class TinyWS {

    private static int port;
    private static String defaultFolder;
    private static String defaultPage;

    public static void main(String[] args) {
        TinyWS tiny = new TinyWS();
        tiny.listen();
    }

    public TinyWS() {
       Config config = new Config();
       port = parseInt(config.getProperty(Config.PORT));
       defaultFolder = config.getProperty(Config.DEFAULTFOLDER);
       defaultPage = config.getProperty(Config.DEFAULTPAGE);
       config.dumpProperties();
        System.out.println("-- TinyWS properties are --");
        System.out.println(Config.PORT + ":" + port);
        System.out.println(Config.DEFAULTFOLDER + ":" + defaultFolder);
        System.out.println(Config.DEFAULTPAGE + ":" + defaultPage);
    }

    public void listen() {
       // TODO add code here
    }

    /**
     * Log web server requests
     * @param s - message to log
     */
    public static void log(String s) {
        System.out.println(s);
    }

    /**
     * Handle fatal error - print info and die
     */
    public static void fatalError(String s) {
        handleError(s, null, true);
    }

    /**
     * Handle fatal error - print info and die
     */
    public static void fatalError(Exception e) {
        handleError(null, e, true);
    }

    /**
     * Handle fatal / non-fatal errors
     */
    public static void handleError(String s, Exception e, boolean isFatal) {
        if (s != null) {
            System.out.println(s);
        }
        if (e != null) {
            e.printStackTrace();
        }
        if (isFatal) System.exit(-1);
    }

    /**
     * Get port configuration value
     * @return port number
     */
    public static int getPort() {
        return port;
    }

    /**
     * Get default HTML folder
     * @return folder
     */
    public static String getDefaultFolder() {
        return defaultFolder;
    }

    /**
     * Get name of default web page
     * @return default page
     */
    public static String getDefaultPage() {
        return defaultPage;
    }
}

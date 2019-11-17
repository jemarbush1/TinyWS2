package edu.cscc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import static java.lang.Integer.parseInt;

/**
 * TinyWS a simplistic Tiny Web Server
 * @author student name here
 */
public class TinyWS {

    private static int port;
    private static String defaultFolder;
    private static String defaultPage;
    ServerSocket serverSocket;
    Socket clientSocket;
    String inputLine, outputLine;
    Config config;
    public static void main(String[] args) {
        TinyWS tiny = new TinyWS();
        tiny.listen();
    }

    public TinyWS() {
       config = new Config();
       port = parseInt(config.getProperty(Config.PORT));
       defaultFolder = config.getProperty(Config.DEFAULTFOLDER);
       defaultPage = config.getProperty(Config.DEFAULTPAGE);
       config.dumpProperties();
       /*
        System.out.println("-- TinyWS properties are --");
        System.out.println(Config.PORT + ":" + port);
        System.out.println(Config.DEFAULTFOLDER + ":" + defaultFolder);
        System.out.println(Config.DEFAULTPAGE + ":" + defaultPage);

        */
    }

    public void listen() {
       // TODO add code here
        /**
         * listen() method that does the following. Add exception handling where required.
         * a.	Instantiate a ServerSocket on the web server’s port. See https://docs.oracle.com/javase/9/docs/api/java/net/ServerSocket.html Also, see the Schildt book (10th ed.) pages 763-764 for information of ServerSockets.
         * b.	Use the setSoTimeOut() method with an argument of zero to allow an indefinite time-out while listening on the port.
         * c.	Create an infinite loop that does the following:
         * i.	Call accept() on server socket – which waits for a request and then returns a Socket connection object. See https://docs.oracle.com/javase/9/docs/api/java/net/Socket.html
         * ii.	Log connection.getInetAddress().getCanonicalHostName() – which outputs the source of the HTTP request.
         * iii.	Instantiate a RequestHandler object that takes the Socket connection as a parameter. (This is a class you will create momentarily)
         * iv.	Call the processRequest() method of the RequestHandler object you just instantiated.
         * v.	Close the Socket connection.
         */
        try {
            serverSocket = new ServerSocket(parseInt(config.getProperty(Config.PORT)));
            serverSocket.setSoTimeout(0);
            while (true){
                clientSocket = serverSocket.accept();
                TinyWS.log(clientSocket.getInetAddress().getCanonicalHostName());
                RequestHandler requestHandler = new RequestHandler(clientSocket);
                requestHandler.processRequest();
                clientSocket.close();
            }// end of infinite while
        } catch (IOException e) {
            e.printStackTrace();
            TinyWS.fatalError(e);
        }


    }
    public void socket (int portNumber){
        try {
            serverSocket = new ServerSocket(portNumber);
            clientSocket = serverSocket.accept();
            PrintWriter out =
                    new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            TinyWS.log(clientSocket.getInetAddress().getCanonicalHostName());
            // Initiate conversation with client
            KnockKnockProtocol kkp = new KnockKnockProtocol();
            outputLine = kkp.processInput(null);
            // System.out.println(outputLine);
            out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                // System.out.println(inputLine);
                outputLine = kkp.processInput(inputLine);
                // System.out.println(outputLine);
                out.println(outputLine);
                if (outputLine.equals("Bye."))
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
            TinyWS.fatalError(e);
        }
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

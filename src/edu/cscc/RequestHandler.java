package edu.cscc;

import java.io.*;
import java.net.Socket;

public class RequestHandler {
    private Socket connection;


    public RequestHandler(Socket connection) {
        this.connection = connection;
    }

    public void processRequest() {
        String inString;
        try {
            inString = readRequest();
            System.out.println(inString);
            HTTPRequest httpRequest = new HTTPRequest(inString);
            if (!(httpRequest.isValidRequest()))
                throw new IOException();
            else
                System.out.println("good request: " + inString);
            ResponseHandler responseHandler = new ResponseHandler(httpRequest);
            responseHandler.sendResponse(connection);
        } catch (IOException e) {
            e.printStackTrace();
            TinyWS.fatalError(e);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
                TinyWS.fatalError(e);
            }
        }
    }
    /**
     * . Implement code to read the request using a BufferedReader and use a StringBuilder to build the request string.
     */
    private String readRequest() throws IOException {
        String inputLine;
        // Set socket timeout to 500 milliseconds
        // connection.setSoTimeout(500);
        int recbufsize = connection.getReceiveBufferSize();
        InputStream in = connection.getInputStream();
        BufferedReader inBuf = new BufferedReader(new InputStreamReader(in));
        //
        inputLine = inBuf.readLine();
        return(inputLine);
    }
}

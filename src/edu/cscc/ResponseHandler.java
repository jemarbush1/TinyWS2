package edu.cscc;

import java.io.*;
import java.net.*;

public class ResponseHandler {
    private static final String NOT_FOUND_RESPONSE =
            "HTTP/1.0 404 NotFound\n" +
                    "Server: TinyWS\n" +
                    "Content-type: text/plain\n\n" +
                    "Requested file not found.";

    private static final String FORBIDDEN_RESPONSE =
            "HTTP/1.0 403 Forbidden\n" +
                    "Server: TinyWS\n" +
                    "Content-type: text/plain\n\n" +
                    "Requested action is forbidden.  So there!";

    private static final String HTTP_OK_HEADER =
            "HTTP/1.0 200 OK\n" +
                    "Server: TinyWS\n" +
                    "Content-type: ";
    private static final int HTTP_NOT_FOUND = 404;
    private static final int HTTP_FORBIDDEN = 403;
    private static final int HTTP_OK = 200;

    private HTTPRequest request;

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    private int responseCode;

    public ResponseHandler(HTTPRequest request) {
        this.request = request;
        responseCode = 404;
    }
    /**
     * Send HTTP Response
     */
    public void sendResponse(Socket connection) throws IOException {
        byte[] response = null;
        int sendbufsize = connection.getSendBufferSize();
        BufferedOutputStream out = new BufferedOutputStream(
                connection.getOutputStream(), sendbufsize);
        // TODO code here
        TinyWS.log(request.getPath());
        response = getFile(request.getPath()); // raw file data
        // dress it up now
        // System.arraycopy()
        byte[] header ={};
        byte[] mime = {};
        byte[] twoNewLines ={};
        header= HTTP_OK_HEADER.getBytes();
        if (request.getPath().equals("/"))
            mime = "text/html".getBytes();
        else
            mime = getMimeType(request.getPath()).getBytes();
        twoNewLines = "\n\n".getBytes();
        // we stitch together header, mime, 2 new lines, and the file
        byte[] dest = new byte[header.length + mime.length + twoNewLines.length + response.length];
        System.arraycopy(header, 0, dest, 0, header.length);
        System.arraycopy(mime, 0, dest, header.length, mime.length);
        System.arraycopy(twoNewLines, 0, dest, header.length+mime.length, twoNewLines.length);
        System.arraycopy(response, 0, dest, header.length+mime.length+twoNewLines.length, response.length);
        response = dest;
        out.write(response,0,response.length);
        out.flush();
    }

    // Find requested file, assume Document Root is in html folder in project directory
    private byte[] getFile(String path) {
        // TODO code here
        // document root = .\html
        File file;
        byte[] fileData = {};
        String docRoot = "./html";
        if (path == null) {
            setResponseCode(HTTP_NOT_FOUND);
            return null;
        }
        if (path.contains("..")) {
            setResponseCode(HTTP_FORBIDDEN);
            return null;
        }
        if (path.startsWith("/")) {
            path = docRoot + path;
        } else {
            path = docRoot + "/" + path;
        }
        file = new File(path);
        if (file.isDirectory()) {
            path = path + TinyWS.getDefaultPage();
            file = new File(path);
        } else if (!file.isFile() || !file.canRead()) {
            setResponseCode(HTTP_FORBIDDEN);
            return null;
        }
        fileData = readFile(file);
        if (fileData == null)
            setResponseCode(HTTP_NOT_FOUND);
        else
            setResponseCode(HTTP_OK);
        return fileData;
    }
    // Read file, return byte array (null if error)
    private byte[] readFile(File f) {
        byte[] getBytes = {};
        try {
            getBytes = new byte[(int) f.length()];
            InputStream is = new FileInputStream(f);
            is.read(getBytes);
            is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            TinyWS.fatalError(e);
        } catch (IOException e) {
            e.printStackTrace();
            TinyWS.fatalError(e);
        }
        return getBytes;
    }

    // Return mimetype based on file suffix (or null if error)
    private String getMimeType(String path) {
        String mimeType = null;
        String[] pathArray;
        pathArray = path.split("\\.");
        // pathArray.length = n, then n-1 = last item
        switch (pathArray[pathArray.length - 1].toLowerCase()) {
            case "html":
                mimeType = "text/html";
                break;
            case "txt":
                mimeType = "text/plain";
                break;
            case "gif":
                mimeType = "image/gif";
                break;
            case "jpg":
                mimeType = "image/jpeg";
                break;
        }
        return mimeType;
    }
}

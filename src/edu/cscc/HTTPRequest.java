package edu.cscc;

public class HTTPRequest {
    private String request;         // request string
    private String path;            // path to file
    private boolean validRequest;   // is request valid?

    public HTTPRequest(String r) {
        // TODO Constructor
        this.request = r;
        this.validRequest = parse(r);
    }

    public boolean isValidRequest() {
        return (validRequest);
    }

    public String getPath() {
        return (path);
    }

    // TODO uncomment
    private boolean parse(String r) {
        //  TODO code here
        String parsedArray [] ;
        parsedArray = r.split("[ \t\n?]");
        if (parsedArray[0].equals("GET") ||
            !(parsedArray[1].contains("/")))
            return false;
        else {
            this.path = parsedArray[1];
            return true;
        }
    }
}

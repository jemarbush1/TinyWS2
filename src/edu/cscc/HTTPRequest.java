package edu.cscc;
/**
 * This class is constructed with a request string which is parsed into path and validRequest boolean
 * @author prakash parasuram, Jemar Bush, Luis Espinal
 */
public class HTTPRequest {
    public String getRequest() {
        return request;
    }

    private String request;         // request string
    private String path;            // path to file
    private boolean validRequest;   // is request valid?
    /**
     * constructor with a request string which is parsed into path and validRequest boolean
     * @param r string passed by web browser to http server
     */
    public HTTPRequest(String r) {
        this.request = r;
        if (!(this.validRequest = parse(r))) {
            TinyWS.log(r);
            TinyWS.fatalError(r);
        }
    }
    /**
     * return boolean whether the string this object was created with is good
     * @return boolean whether the string this object was created with is good
     */
    public boolean isValidRequest() {
        return (validRequest);
    }
    /**
     * return path of this object
     * @return path of this object
     */
    public String getPath() {
        return (path);
    }

    /**
     * constructor with a request string which is parsed into path and validRequest boolean, populates object property path
     * @param r string sent from browser
     * @return boolean whether the string this object was created with is good
     */
    private boolean parse(String r) {
        System.out.println("printing r "+r);
        String parsedArray [] ;
        if (r == null)
            return false; // for some reason firefox refresh ctl shift  r gives a null string
        parsedArray = r.split("[ \t\n?]");
        System.out.println(parsedArray.toString());
        if (!(parsedArray[0].equals("GET")) ||
            !(parsedArray[1].contains("/")))
            return false;
        else {
            this.path = parsedArray[1];
            return true;
        }
    }
}

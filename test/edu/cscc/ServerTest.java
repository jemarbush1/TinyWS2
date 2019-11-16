package edu.cscc;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ServerTest implements Runnable {
    @Override
    public void run() {
        Server server = new Server(8089);
    }
    @Test
    public void justTestClientServer(){
        Thread t1 = new Thread(new ServerTest());
        t1.start();
        Client client = new Client(8089);
    }
}

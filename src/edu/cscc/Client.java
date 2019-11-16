package edu.cscc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;  // Import the Scanner class
public class Client {
    String hostName = "localhost";
    int portNumber = 8081;

    public Client(int portNumber) {
        this.portNumber = portNumber;
        try (
                Socket kkSocket = new Socket(hostName, portNumber);
                PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(kkSocket.getInputStream()));
        ) {
            String fromServer, fromUser;
            Scanner myObj = new Scanner(System.in);
            while ((fromServer = in.readLine()) != null) {
                System.out.println("Server: " + fromServer);
                if (fromServer.equals("Bye."))
                    break;

                // fromUser = stdIn.readLine();
                fromUser = myObj.nextLine();
                if (fromUser != null) {
                    System.out.println("Client: " + fromUser);
                    out.println(fromUser);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            TinyWS.fatalError(e);
        }
    }
}
package edu.cscc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    int portNumber = 8081;
    Socket test;

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    public void doSocket() {
        try (
                ServerSocket serverSocket = new ServerSocket(portNumber);
                // Socket clientSocket = serverSocket.accept();
                Socket clientSocket = serverSocket.accept();
                PrintWriter out =
                        new PrintWriter(clientSocket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
        ) {
            String inputLine, outputLine;
            serverSocket.setSoTimeout(0);
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
    }  // end of doSocket
}

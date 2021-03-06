package server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.security.SecureRandom;
import java.util.Scanner;

public class ClientManager implements Runnable {
    private final Socket socket;
    private String name;
    private final int AuthToken;
    private int botNumber;
    private final PrintWriter out;
    private final Scanner in;

    public ClientManager(Socket socket) throws IOException {
        this.socket = socket;
        out = new PrintWriter(socket.getOutputStream());
        this.AuthToken = setAuthToken();
        in = new Scanner(socket.getInputStream());
    }

    @Override
    public void run() {
        while (true) {

        }
    }


    private void getNameAndBotNo() {

    }

    private int setAuthToken() {
        SecureRandom random = new SecureRandom();
        int token = random.nextInt();
        return token;
    }

}

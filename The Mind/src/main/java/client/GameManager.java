package client;

import client.UI.ConsoleManager;

import java.net.Socket;

public class GameManager {
    private final Client client;
    private final MessageTransmitter transmitter;
    private final ConsoleManager consoleManager;
    private Thread messageGetter;
    private Thread messageSender;
    private String message = "";

    public GameManager(Socket socket, Client client) {
        this.client = client;
        transmitter = new MessageTransmitter(socket);
        consoleManager = new ConsoleManager();
    }

    public void startGame() {
        introduceGame();
        getNameAndBotNo();
        getAuthToken();
        start();
    }

    private void introduceGame() {
        consoleManager.sendMessage("The Mind\n" +
                "The human mind is amazingly powerful. " +
                "It can do things for real that sometimes feel like magic.\n");
    }

    private void getNameAndBotNo() {
        //send message duo to a better way
        consoleManager.sendMessage("Enter your name:");
        client.setName(consoleManager.getMessage());
        transmitter.sendMessage("Name: " + client.getName());
        consoleManager.sendMessage("Enter the number of bots:");
        client.setBotNumber(Integer.parseInt(consoleManager.getMessage()));
        transmitter.sendMessage("botNumber: " + client.getBotNumber());
    }

    //TODO
    private void getAuthToken() {
        //check if message contains auth token
        client.setAuthToken(Integer.parseInt(transmitter.getMessage()));
        transmitter.sendMessage("AuthToken: " + client.getAuthToken());
    }

    private void start() {
       // final String[] message = {""};
        do {
            messageGetter = new Thread(() -> {
                message = transmitter.getMessage();
                messageSender.interrupt();
                //deserialize message
                consoleManager.sendMessage(message);
            });
            messageSender = new Thread(() -> {
                message = consoleManager.getMessage();
                messageGetter.interrupt();
                transmitter.sendMessage(message);
            });
            messageGetter.start();
            messageSender.start();

        } while (message.equals("Game finished"));
    }


}

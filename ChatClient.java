package Weekend2.Hometask2.chatserver;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: Richie Blackmore
 * Date: 12.02.13
 * Time: 0:14
 * To change this template use File | Settings | File Templates.
 */
public class ChatClient implements Observer {

    private static int counter;
    private Subject server;
    public int number;
    private Socket socket;

    private String senderName = "noname"; //name, or nick of a chat user

    OutputStream outputStream;
    InputStream inputStream;

    ClientThread clientThread;

    public void close() {
        try {
            System.out.println("Removing observer #" + number);
            server.removeObserver(this);
            System.out.println("Closing client #" + number);
            clientThread.interrupt();
            inputStream.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void update(ChatMessage message) throws IOException {


        System.out.println("Inside update() " + number + " received message " + message.getMessage());
        System.out.println("sending message...");

        //ChatClient client = (ChatClient) message.sender;

        //char chars[] = (message.getSender().getSenderName() + ": " + message.getMessage()).toCharArray();
        char chars[] = getMessageForSend(message);
        //TODO convert message to function
        //char chars[] = message.getMessage().toCharArray();
            for (char c:chars) {
            //outputStream.write((int)c);
            //System.out.println("Sending " + c);
            outputStream.write((int) c);
        }
            outputStream.flush();
            System.out.println("Sent: " + message.getMessage());
    }

    /** Composes messages for send (using message body, sender name etc.)
     *
     * @param message
     * @return
     */
    private char[] getMessageForSend(ChatMessage message) {

        char chars[] = (message.getSender().getSenderName() + ": " + message.getMessage()).toCharArray();
        return chars;

    }

    @Override
    public String getSenderName() {
        return senderName;
    }





    public Socket getSocket() {
        return socket;
    }

    public ChatClient(Subject server, Socket socket) throws IOException {
        this.server=server;
        counter++;
        number=counter;
        this.socket=socket;
        //run new thread for listening server
        System.out.println("New ChatClient #" + number + " created");

        inputStream = socket.getInputStream();
        outputStream = socket.getOutputStream();

        //TODO получение имени клиента вставить здесь

        //authorize();

        clientThread =  new ClientThread();
        clientThread.start();



    }




    public void display() {
        System.out.println("display() called by Observer#" + number);
    }

    class ClientThread extends Thread {

        /**
         *  makes all necessary for client authorization - receiving name, password if needed
         */
        private void authorize() throws IOException {

            final String NAME_REQUEST = new String("What's your name or nick, " + socket.getInetAddress() + "?\n");

            OutputStreamWriter writer = new OutputStreamWriter(outputStream);
            Scanner scanner = new Scanner(inputStream);

            writer.write(NAME_REQUEST);
            writer.flush();
            System.out.println("Request to user sent");

            //read user name
            ChatClient.this.senderName = scanner.nextLine();
            writer.write("Welcome to chat, " + ChatClient.this.senderName + "!\n");
            writer.flush();

            //update(new ChatMessage(ChatClient.this, new String("What's your name or nick, " + socket.getInetAddress() + "?\n")));

        }

        public void run() {
        //listen client and notify subject after receiving new messages
            try {

                authorize();

                System.out.println(number + " at  " + socket.getInetAddress() + " started to listen");

                int i=0;
                while (i>=0) {
                    StringBuffer buffer = new StringBuffer();
                    System.out.println("New buffer for listening created");
                    i=0;
                    //read single line
                    while ((char)i!='\n') {
                        //когда socket закрывается - тут срабатывает exception
                        i = inputStream.read();
                        buffer.append((char)i);
                     }
                    //buffer = new StringBuffer();
                    //inputStream.close();
                    System.out.println("Before notify buffer is " + buffer);
                    server.notifyObservers(new ChatMessage(ChatClient.this, buffer.toString()));
                    System.out.println(number + " notified observers about message: " + buffer.toString());
                }
                close();

            } catch (IOException e) {
                e.printStackTrace();
                //close socket
                // if IO exception occured
                close();
            }
        }

    }
}



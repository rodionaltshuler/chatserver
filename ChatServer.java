package Weekend2.Hometask2.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Richie Blackmore
 * Date: 12.02.13
 * Time: 0:13
 * To change this template use File | Settings | File Templates.
 */
public class ChatServer implements Subject {
    //TODO: 1) users list; 2) disconnect observer if error occured; 3) field in client for username

    ArrayList<Observer> observers;
    ArrayBlockingQueue<ChatMessage> messages;

    static final int MESSAGES_BUFFER_SIZE = 100;

    static final int PORT = 30000;

    public ChatServer() {
     observers = new ArrayList<Observer>();
     messages = new ArrayBlockingQueue<ChatMessage>(MESSAGES_BUFFER_SIZE);
    }

    @Override
    public void registerObserver(Observer observer) {
        //To change body of implemented methods use File | Settings | File Templates.
        observers.add(observer);
        System.out.println("Observer added to ArrayList");
    }

    @Override
    public void removeObserver(Observer observer) {
        //To change body of implemented methods use File | Settings | File Templates.
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ChatMessage message) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
        System.out.println("Start notify " + observers.size() + " observers");
        for (Observer observer:observers) {
            //передать в качестве аргумента новое сообщение для отображения
            //if (observer!=message.sender)
            System.out.println("Update call from ChatServer");
            observer.update(message);
            ChatClient obs = (ChatClient) observer;
            System.out.println("Notified observer #" + obs.number);
        }

    }


    public void startReceive() throws IOException {

        //open socket...
        ServerSocket socket = new ServerSocket(PORT);
        //and wait for connection
        do {
            Socket newClientSocket = socket.accept();
            System.out.println("Connection established!");
            ChatClient client = new ChatClient(this, newClientSocket);
            registerObserver(client);
            //connection established - save new observer
        } while(true);


    }

}

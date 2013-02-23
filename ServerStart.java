package Weekend2.Hometask2.chatserver;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Richie Blackmore
 * Date: 12.02.13
 * Time: 1:11
 * To change this template use File | Settings | File Templates.
 */
public class ServerStart{

    public static void main(String args[]) {

        ChatServer server = new ChatServer();
        try {
            server.startReceive();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }
}

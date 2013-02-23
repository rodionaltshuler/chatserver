package Weekend2.Hometask2.chatserver;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Richie Blackmore
 * Date: 12.02.13
 * Time: 0:13
 * To change this template use File | Settings | File Templates.
 */
public interface Observer {

    public void update(ChatMessage message) throws IOException;

    public String getSenderName();

}

package Weekend2.Hometask2.chatserver;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: Richie Blackmore
 * Date: 12.02.13
 * Time: 0:12
 * To change this template use File | Settings | File Templates.
 */
public interface Subject {
    public void registerObserver(Observer observer);
    public void removeObserver(Observer observer);
    public void notifyObservers(ChatMessage message) throws IOException;
}

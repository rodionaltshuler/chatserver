package Weekend2.Hometask2.chatserver;

/**
 * Created with IntelliJ IDEA.
 * User: Richie Blackmore
 * Date: 12.02.13
 * Time: 0:21
 * To change this template use File | Settings | File Templates.
 */
public class ChatMessage {

    String message;
    Observer sender;
    int time; //time when message was sent

    public ChatMessage (Observer sender, String message) {
        this.message=message;
        this.sender=sender;


    }
    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public Observer getSender() {
        return sender;
    }

    public void setSender(Observer sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }



}


package Message;

/**
 *
 * @author talha
 */
public class Message implements java.io.Serializable {

    //message types enum
    public static enum Message_Type {
        None, Name, RivalConnected,Color,Turn, Text, Movement, Score, Result
    }
    //message type
    public Message_Type type;
    //message content
    public Object content;

    public Message(Message_Type t) {
        this.type = t;
    }
}

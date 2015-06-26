package fingerBand.Databean;
import java.util.Date;

import fingerBand.User;

public class Message {
    private String speaker;
    private String message;
    private Date timestamp;

    public Message(String speaker , String message )
    {
        this.speaker = speaker;
        this.message = message;
    }

    public String getSpeaker() {
        return speaker;
    }

    public String getMessage() {
        return message;
    }


}

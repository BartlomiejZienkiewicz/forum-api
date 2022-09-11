package bartlomiejzienkiewicz.forumapi.Dao.Message;

import bartlomiejzienkiewicz.forumapi.Dao.Dao;
import bartlomiejzienkiewicz.forumapi.Entity.Message.Message;
import lombok.Getter;

import java.util.Date;

@Getter
public class MessageDao implements Dao<Message>, Comparable<MessageDao> {

    private String author;
    private String howLongAgo;
    private String contents;
    private Long howLongAgoInMillis;
    @Override
    public void convertToDao(Message message){
        author = message.getUser().getUsername();
        Date currentDate = new Date(System.currentTimeMillis());
        Long value = currentDate.getTime() - message.getUtilDate().getTime();
        this.howLongAgoInMillis = value;

        this.contents = message.getContents();



        //years
        if(value/31557600000L >= 1L){
            if(value/31557600000L < 2L){
                this.howLongAgo = "1 year ago";
            }
            else {
                this.howLongAgo = value/31557600000L + " years ago";
            }
        }
        //months
        else if(value/2629800000L >= 1L){
            if(value/2629800000L < 2L){
                this.howLongAgo = "1 month ago";
            }
            else {
                this.howLongAgo = value/2629800000L + " months ago";
            }
        }
        //weeks
        else if(value/604800016L >= 1L){
            if(value/604800016L < 2L){
                this.howLongAgo = "1 week ago";
            }
            else {
                this.howLongAgo = value/604800016L + " weeks ago";
            }
        }
        //days
        else if(value/86400000L >= 1L){
            if(value/86400000L < 2L){
                this.howLongAgo = "1 day ago";
            }
            else {
                this.howLongAgo = value/86400000L + " days ago";
            }
        }
        //hours
        else if(value/3600000L >= 1L){
            if(value/3600000L < 2L){
                this.howLongAgo = "1 hour ago";
            }
            else {
                this.howLongAgo = value/3600000L + " hours ago";
            }
        }
        //minutes
        else if(value/60000L >= 1L){
            if(value/60000L < 2L){
                this.howLongAgo = "1 minute ago";
            }
            else {
                this.howLongAgo = value/60000L + " minutes ago";
            }
        }
        else{
            this.howLongAgo = "Less than one minute ago";
        }

    }


    @Override
    public int compareTo(MessageDao o) {
        return this.getHowLongAgoInMillis().compareTo(o.getHowLongAgoInMillis());

    }
}

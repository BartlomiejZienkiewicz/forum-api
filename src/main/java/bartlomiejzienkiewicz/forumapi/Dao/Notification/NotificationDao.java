package bartlomiejzienkiewicz.forumapi.Dao.Notification;

import bartlomiejzienkiewicz.forumapi.Dao.Dao;
import bartlomiejzienkiewicz.forumapi.Entity.Notification.Notification;
import bartlomiejzienkiewicz.forumapi.Entity.Notification.NotificationType;
import lombok.*;

import java.util.Date;

@Getter
@Setter

@NoArgsConstructor
@AllArgsConstructor
public class NotificationDao implements Dao<Notification> , Comparable<NotificationDao>{

    Long id;
    Long idOfPost;
    String contents;
    String username;
    Long howLongAgoInMillis;
    String howLongAgo;
    String userTag;
    String type;
    String fromWho;


    @Override
    public void convertToDao(Notification entity) {
        this.id = entity.getId();
        this.contents = entity.getContents();
        this.username = entity.getUser().getUsername();
        this.userTag = entity.getUser().getTag();
        this.idOfPost = entity.getIdOfPost();
        this.fromWho = entity.getFromWho();

        if(entity.getNotificationType() == NotificationType.COMMENT){
            type = "comment";
        }
        else if(entity.getNotificationType() == NotificationType.MESSAGE){
            type = "message";
        }

        Date currentDate = new Date(System.currentTimeMillis());

        Long value = currentDate.getTime() - entity.getTimeOfCreation().getTime();

        this.howLongAgoInMillis = value;

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
        else {
            this.howLongAgo = "Less than 1 minute ago";
        }

    }

    @Override
    public int compareTo(NotificationDao o) {
        return this.getHowLongAgoInMillis().compareTo(o.getHowLongAgoInMillis());

    }
}

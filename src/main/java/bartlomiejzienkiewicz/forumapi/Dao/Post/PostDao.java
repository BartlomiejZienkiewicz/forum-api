package bartlomiejzienkiewicz.forumapi.Dao.Post;

import bartlomiejzienkiewicz.forumapi.Dao.Dao;
import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import lombok.Getter;
import lombok.ToString;


import java.util.Date;

@ToString
@Getter
public class PostDao implements Dao<Post>, Comparable<PostDao> {
    Long id;
    String contents;
    Integer numberOfLikes;
    Integer idOfAuthor;
    String nameOfAuthor;
    String howLongAgo;
    Integer numberOfComments = 0;
    Long howLongAgoInMillis;


    @Override
    public void convertToDao(Post entity) {
        this.id = entity.getId();
        this.contents = entity.getContents();
        this.numberOfLikes = entity.getNumberOfLikes();
        this.nameOfAuthor = entity.getUsername();
        this.numberOfComments = entity.getNumberOfComments();

        Date currentDate = new Date(System.currentTimeMillis());

        Long value = currentDate.getTime() - entity.getUtilDate().getTime();

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
    public int compareTo(PostDao o) {
        return this.getHowLongAgoInMillis().compareTo(o.getHowLongAgoInMillis());
    }
}

package bartlomiejzienkiewicz.forumapi.Dao.Comment;

import bartlomiejzienkiewicz.forumapi.Dao.Dao;
import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CommentDao implements Dao<Comment>, Comparable<CommentDao> {

    Long id;
    String contents;
    Integer numberOfLikes;
    Integer idOfAuthor;
    String nameOfAuthor;
    String howLongAgo;
    Long howLongAgoInMillis;
    Integer numberOfAnswers;
    boolean isItAnswerToAnotherComment;
    Long idOfCommentItAnswers ;




    @Override
    public void convertToDao(Comment entity) {
        this.id = entity.getId();
        this.contents = entity.getContents();
        this.numberOfLikes = entity.getNumberOfLikes();
        this.nameOfAuthor = entity.getUsername();
        this.numberOfAnswers = entity.getNumberOfAnswers();
        if(entity.getIsItAnswer() == null){
            this.isItAnswerToAnotherComment = false;
        }
        else {
            this.isItAnswerToAnotherComment = true;
        }

        if(entity.getIdOfCommentItAnswers() == null){
            this.idOfCommentItAnswers = -1L;
        }

        else{
            this.idOfCommentItAnswers = entity.getIdOfCommentItAnswers();
        }



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
    public int compareTo(CommentDao o) {
        return this.getHowLongAgoInMillis().compareTo(o.getHowLongAgoInMillis());

    }
}

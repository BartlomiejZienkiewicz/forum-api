package bartlomiejzienkiewicz.forumapi.Entity.User;

import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import bartlomiejzienkiewicz.forumapi.Entity.Message.Message;
import bartlomiejzienkiewicz.forumapi.Entity.Notification.Notification;
import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "username")
    private String username;

    @NotNull
    @Column(name = "password")
    private String password;

    @NotNull
    @Column(name  = "email")
    private String email;


    @Column(name = "role")
    private Role role;

    @Column(name = "tag")
    private String tag;

    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            mappedBy = "user"
    )
    private List<Post> postList = new ArrayList<Post>();



    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            mappedBy = "user"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Comment> commentList = new ArrayList<>();

    @OneToMany(
            cascade = CascadeType.REMOVE,
            mappedBy = "user"

    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Message> messageList = new ArrayList<>();


    @OneToMany(
            cascade = CascadeType.REMOVE,
            mappedBy = "user"
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Notification> notificationList = new ArrayList<>();

    @Column(name = "numberOfNotifications")
    private Integer numberOfNotifications = 0;







    @PrePersist
    private void prePersist(){
        tag ='@' + username;
        numberOfNotifications = 0;
    }
    public void addMessage(Message message){
        if(messageList == null){
            messageList = new ArrayList<Message>();
        }
        messageList.add(message);
        message.setUser(this);
    }


    public void addPost(Post post){
        if(postList == null){
            postList = new ArrayList<Post>();
        }
        postList.add(post);
        post.setUser(this);
    }
    public void addComment(Comment comment){
        if(commentList == null){
            commentList = new ArrayList<Comment>();
        }
        commentList.add(comment);
        comment.setUser(this);
    }
    public void addNotification(Notification notification){
        if(notificationList == null){
            notificationList = new ArrayList<Notification>();
        }
        if(numberOfNotifications == null){
            numberOfNotifications = 0;
        }
        numberOfNotifications++;
        notificationList.add(notification);
        notification.setUser(this);
    }
}

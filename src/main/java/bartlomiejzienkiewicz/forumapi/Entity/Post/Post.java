package bartlomiejzienkiewicz.forumapi.Entity.Post;

import bartlomiejzienkiewicz.forumapi.Entity.Comment.Comment;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import lombok.*;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contents")
    @NotNull
    private String contents;


    @Column(name="numberOfLikes")
    private Integer numberOfLikes;

    @Column(name = "dateOfCreation")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date utilDate;

    @Column(name = "nameOfAuthor")
    @NotNull
    private String username;



    @OneToMany(
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            mappedBy = "post"
    )
    private List<Comment> commentList = new ArrayList<Comment>();

    @Column(name = "number_of_comments")
    private Integer numberOfComments = 0;

    @ManyToOne
    @JoinColumn(name = "idOfUser")
    @NotNull
    private User user;



    @PrePersist
    public void prePersist() {

        if(numberOfLikes == null)
            numberOfLikes = 0;
        if(numberOfComments == null){
            numberOfComments = 0;
        }

    }

    public void addComment(Comment comment){
        if(commentList == null){
            commentList = new ArrayList<Comment>();
        }
        if(numberOfComments == null){
            numberOfComments = 0;
        }
        commentList.add(comment);
        this.numberOfComments += 1;
    }


}

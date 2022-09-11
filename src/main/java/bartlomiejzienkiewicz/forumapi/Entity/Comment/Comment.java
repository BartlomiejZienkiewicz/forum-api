package bartlomiejzienkiewicz.forumapi.Entity.Comment;

import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import bartlomiejzienkiewicz.forumapi.Entity.User.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contents")
    @NotNull
    @NonNull
    private String contents;

    @Column(name = "numberOfLikes")
    @NotNull
    private Integer numberOfLikes = 0;

    @ManyToOne
    @JoinColumn(
            name = "idOfPost"
    )
    @NotNull
    private Post post;

    @ManyToOne
    @JoinColumn(name = "idOfUser")
    @NotNull
    private User user;

    @NotNull
    @Column(name = "nameOfAuthor")
    private String username;

    @Column(name = "dateOfCreation")
    @Temporal(TemporalType.TIMESTAMP)
    @NotNull
    private Date utilDate;

    @Column(name = "numberOfAnswers")
    private Integer numberOfAnswers = 0;

    @Column(name = "isItAnswerToAnotherComment", columnDefinition = "boolean default false")
    private Boolean isItAnswer = false;

    @Column(name = "idOfCommentItAnswers", columnDefinition = "bigint default -1")
    private Long idOfCommentItAnswers = -1L;

    @PrePersist
    private void prePersist(){
        if(numberOfAnswers == null){
            numberOfAnswers = 0;
        }

        if(numberOfLikes == null){
            numberOfLikes = 0;
        }
    }





}
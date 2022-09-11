package bartlomiejzienkiewicz.forumapi.Repository.PostRepository;

import bartlomiejzienkiewicz.forumapi.Entity.Post.Post;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import bartlomiejzienkiewicz.forumapi.Repository.EntityService;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements EntityService<Post> {




    PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {

        this.postRepository = postRepository;
    }

    @Override
    public void save(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deleteAll() {
        postRepository.deleteAll();
    }

    @Override
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }


    public List<Post> findByNameOfAuthor(String name) {
        List<Post> postList = new ArrayList<>();
        postList = postRepository.findAll().stream().filter(post -> post.getUsername().equals(name)).collect(Collectors.toList());
        return postList;
    }
    public List<Post> findAll(){
        return postRepository.findAll();
    }



}

package kookmin.kuham.post.repository;

import kookmin.kuham.post.schema.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
    Collection<Post> findAllByUserId(String userId);
}

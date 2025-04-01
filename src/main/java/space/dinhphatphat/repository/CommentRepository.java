package space.dinhphatphat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import space.dinhphatphat.model.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByStoryIdOrderByCreatedAtDesc(int story_id);
}

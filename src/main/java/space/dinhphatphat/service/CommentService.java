package space.dinhphatphat.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.dinhphatphat.model.Comment;
import space.dinhphatphat.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Comment create(Comment comment) {
        try {
            return commentRepository.save(comment);
        }
        catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public void deleteById(int id) {
        commentRepository.deleteById(id);
    }

    public List<Comment> findByStoryId(int storyId) {
        return commentRepository.findByStoryIdOrderByCreatedAtDesc(storyId);
    }

    public Comment findById(int id) {
        return commentRepository.findById(id).orElse(null);
    }
}

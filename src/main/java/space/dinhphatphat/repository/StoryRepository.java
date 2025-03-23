package space.dinhphatphat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.dinhphatphat.model.Story;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

    List<Story> findAllByUser_Id(int user_id);

    List<Story> findTop3ByOrderByCreatedAtDesc();
}

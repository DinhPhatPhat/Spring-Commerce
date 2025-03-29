package space.dinhphatphat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.dinhphatphat.model.Story;

import java.util.List;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer> {

    List<Story> findAllByUser_Id(int user_id);

    List<Story> findTop3ByOrderByCreatedAtDesc();

    List<Story> findAllByOrderByUpdatedAtDesc();

    Page<Story> findByTitleContainingIgnoreCase(String search, Pageable pageable);

    Story findByMeta(String meta);

}

package space.dinhphatphat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import space.dinhphatphat.model.CartItem;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    CartItem findByProduct_IdAndUser_Id(int productId, int userId);

    Optional<CartItem> findByUser_IdAndProduct_Id(int userId, int productId);

    List<CartItem> findByUser_Id(int userId);
}

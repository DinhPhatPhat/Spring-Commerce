package space.dinhphatphat.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import space.dinhphatphat.model.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory_IdAndActive(int categoryId, boolean active);

    @Query("SELECT p FROM Product p WHERE " +
            "p.active = true AND " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:name IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND " +
            "(:brand IS NULL OR LOWER(p.brand) LIKE LOWER(CONCAT('%', :brand, '%'))) AND " +
            "(:color IS NULL OR LOWER(p.color) LIKE LOWER(CONCAT('%', :color, '%')))")
    Page<Product> findByFilters(
            @Param("categoryId") Long categoryId,
            @Param("name") String name,
            @Param("brand") String brand,
            @Param("color") String color,
            Pageable pageable
    );

    List<Product> findByCategory_Id(int categoryId);
}

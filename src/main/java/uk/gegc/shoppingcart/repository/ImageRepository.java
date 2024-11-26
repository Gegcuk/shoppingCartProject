package uk.gegc.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gegc.shoppingcart.model.Image;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}

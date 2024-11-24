package uk.gegc.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uk.gegc.shoppingcart.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}

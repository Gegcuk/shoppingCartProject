package uk.gegc.shoppingcart.service.image;

import org.springframework.web.multipart.MultipartFile;
import uk.gegc.shoppingcart.dto.ImageDTO;
import uk.gegc.shoppingcart.model.Image;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    public List<ImageDTO> saveImages(Long productId, List<MultipartFile> files);
    void updateImage(MultipartFile file, Long imageId);
}

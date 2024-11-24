package uk.gegc.shoppingcart.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uk.gegc.shoppingcart.dto.ImageDTO;
import uk.gegc.shoppingcart.exception.ResourceNotFoundException;
import uk.gegc.shoppingcart.model.Image;
import uk.gegc.shoppingcart.model.Product;
import uk.gegc.shoppingcart.repository.ImageRepository;
import uk.gegc.shoppingcart.service.product.IProductService;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService{
    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Image with id: " + id + " not found!"));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.findById(id).ifPresentOrElse(imageRepository :: delete, () -> {
            throw new ResourceNotFoundException("Image with id: " + id + " not found!");
        });
    }

    @Override
    public List<ImageDTO> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDTO> savedImageDTOs = new ArrayList<>();
        for(MultipartFile file : files){
            try{
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                String downloadUrlBase = "/api/v1/images/image/download";
                String downloadUrl = downloadUrlBase + image.getId();
                image.setDownloadUrl(downloadUrl);
                Image savedImage = imageRepository.save(image);
                savedImage.setDownloadUrl(downloadUrlBase + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImageId(savedImage.getId());
                imageDTO.setImageName(savedImage.getFileName());
                imageDTO.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDTOs.add(imageDTO);

            } catch (SQLException | IOException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return savedImageDTOs;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }
}

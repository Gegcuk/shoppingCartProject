package uk.gegc.shoppingcart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.gegc.shoppingcart.dto.ImageDTO;
import uk.gegc.shoppingcart.exception.ResourceNotFoundException;
import uk.gegc.shoppingcart.model.Image;
import uk.gegc.shoppingcart.response.APIResponse;
import uk.gegc.shoppingcart.service.image.IImageService;

import java.sql.SQLException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long productId){
        try {
            List<ImageDTO> imageDTOs = imageService.saveImages(files, productId);
            return ResponseEntity.ok(new APIResponse("Upload success!", imageDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Upload failed.", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{imageId}")
    public ResponseEntity<Resource> downloadImage(@PathVariable(name = "imageId") Long id) throws SQLException {
        Image image = imageService.getImageById(id);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/image/{imageId}/update")
    public ResponseEntity<APIResponse> updateImage(@PathVariable (name = "imageId") Long imageId, @RequestBody MultipartFile file){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.updateImage(file, imageId);
                return ResponseEntity.ok(new APIResponse("Update success", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Update failed.", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{imageId}/delete")
    public ResponseEntity<APIResponse> deleteImage(@PathVariable (name = "imageId") Long imageId){
        try {
            Image image = imageService.getImageById(imageId);
            if(image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new APIResponse("Delete success", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Delete failed.", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}

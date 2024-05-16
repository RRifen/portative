package mirea.pracs.productcrud.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import mirea.pracs.productcrud.exceptions.InternalServerErrorException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageService {

  @Value("${images.path}")
  private String imagesPath;

  public String saveImage(MultipartFile image) {
    String[] fileParts = Objects.requireNonNull(image.getOriginalFilename()).split("\\.");
    String fileName = String.format("%s/%s.%s", imagesPath, UUID.randomUUID(), fileParts[fileParts.length - 1]);
    try {
      FileOutputStream fos = new FileOutputStream(fileName);
      fos.write(image.getBytes());
      fos.close();
    } catch (IOException e) {
      log.error(e.getMessage());
      throw new InternalServerErrorException(e.getMessage());
    }
    return fileName;
  }

  public void deleteImage(String imagePath) {
    var image = new File(imagePath);
    try {
      Files.delete(image.toPath());
      log.info("Image {} was deleted", image.getAbsolutePath());
    } catch (IOException e) {
      log.warn("Image {} was not deleted", image.getAbsolutePath());
    }
  }

}

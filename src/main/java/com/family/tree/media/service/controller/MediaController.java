package com.family.tree.media.service.controller;

import com.family.tree.media.service.entity.PersonDetail;
import com.family.tree.media.service.model.MediaDto;
import com.family.tree.media.service.repository.MediaRepository;
import com.family.tree.media.service.service.S3Service;
import com.family.tree.media.service.util.ThumbnailUtil;
import org.apache.tika.Tika;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.NoSuchKeyException;
import java.awt.image.BufferedImage;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import com.family.tree.media.service.entity.Media;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.imageio.ImageIO;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private static final Logger logger = LoggerFactory.getLogger(MediaController.class);

    private final S3Service s3Service;
    private final MediaRepository mediaRepository;
    private final ThumbnailUtil thumbnailUtil;

    public MediaController(S3Service s3Service, MediaRepository mediaRepository, ThumbnailUtil thumbnailUtil) {
        this.s3Service = s3Service;
        this.mediaRepository = mediaRepository;
        this.thumbnailUtil = thumbnailUtil;
    }
    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file,
                                         @RequestParam("personId") Long personId,
                                         @RequestParam(value = "description", required = false) String desc) throws IOException {
        try {
            long start = System.currentTimeMillis();
            String methodPrefix="MediaController#upload() ";
            logger.info("{} personId={}, description={}",methodPrefix,personId,desc);

            String url = s3Service.uploadFile(file);
            logger.info("{} File upload completed, key={}",methodPrefix,url);
            String thumbnailUrl=uploadThumbnail(file);
            logger.info("{} Thumbnail upload completed, key={}",methodPrefix,thumbnailUrl);


            Media media = new Media();
            PersonDetail personDetail=new PersonDetail();
            personDetail.setId(personId);
            media.setPerson(personDetail);
            media.setFileName(file.getOriginalFilename());
            media.setFileType(file.getContentType());
            media.setFileSize(file.getSize());
            media.setFileUrl(url);
            media.setMediaType(detectType(file.getContentType()));
            media.setUploadedAt(LocalDateTime.now());
            media.setDescription(desc);
            media.setFileThumbnailUrl(thumbnailUrl);
            mediaRepository.save(media);
            logger.info("{} Saved, media={}",methodPrefix,media);
            MediaDto mediaDto=new MediaDto();
            mediaDto.setImgUrl(url);
            mediaDto.setThumbnailUrl(thumbnailUrl);
            logger.info("{} executed in {} ms",methodPrefix,System.currentTimeMillis() - start);

            return ResponseEntity.ok(mediaDto);
        } catch (IOException e) {
            logger.error("IOException occurred {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        } catch (Exception e) {
            logger.error("Exception occurred {} ",e.getMessage());
            throw new RuntimeException(e);
        }
    }
    @PostMapping("/upload/profile")
    public ResponseEntity<Object> uploadProfilePic(@RequestParam("file") MultipartFile file,
                                         @RequestParam("personId") Long personId,
                                         @RequestParam(value = "description", required = false) String desc) throws IOException {
        try {
            long start = System.currentTimeMillis();
            String methodPrefix="MediaController#uploadProfilePic() ";
            logger.info("{} personId={}, description={}",methodPrefix,personId,desc);

            String url = s3Service.uploadFile(file);
            logger.info("{} File upload completed, key={}",methodPrefix,url);
            String thumbnailUrl=uploadThumbnail(file);
            MediaDto mediaDto=new MediaDto();
            mediaDto.setImgUrl(url);
            mediaDto.setThumbnailUrl(thumbnailUrl);
            logger.info("{} Thumbnail upload completed, key={}",methodPrefix,thumbnailUrl);
            logger.info("{} executed in {} ms",methodPrefix,System.currentTimeMillis() - start);

            return ResponseEntity.ok(mediaDto);
        } catch (IOException e) {
            logger.error("IOException occurred {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed");
        } catch (Exception e) {
            logger.error("Exception occurred {} ",e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/download")
    public ResponseEntity<byte[]> download(@RequestParam String fileName) {
        try {
            long start = System.currentTimeMillis();
            String methodPrefix="MediaController#download() ";
            logger.info("{} fileName={}",methodPrefix,fileName);

            byte[] fileContent = s3Service.downloadFile(fileName);
            // Detect MIME type using Apache Tika
            Tika tika = new Tika();
            String contentType = tika.detect(fileContent, fileName);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(contentType));
            headers.setContentDisposition(ContentDisposition.attachment().filename(fileName).build());
            logger.info("{} executed in {} ms",methodPrefix,System.currentTimeMillis() - start);
            return ResponseEntity.ok()
                    .headers(headers)
                    .body(fileContent);

        } catch (NoSuchKeyException e) {
            logger.error("NoSuchKeyException occurred {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            logger.error("Exception occurred {} ",e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/by-users")
    public ResponseEntity<List<Media>> getMediaByUsers(@RequestBody List<Long> personIds) {
        List<Media> mediaList = mediaRepository.findByPersonIdIn(personIds);
        return ResponseEntity.ok(mediaList);
    }

    private String detectType(String contentType) {
        if (contentType == null) return "document";
        if (contentType.startsWith("image/")) return "photo";
        if (contentType.startsWith("video/")) return "video";
        return "document";
    }

    public String uploadThumbnail(MultipartFile multipartFile) throws Exception {

        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) originalFilename = "unknown";

        // Create thumbnail BufferedImage based on file type
        File tempFile = File.createTempFile("upload-"+multipartFile.getOriginalFilename(), null);
        multipartFile.transferTo(tempFile);


        BufferedImage thumbnailImage = thumbnailUtil.createThumbnail(tempFile, 256, 256);

        // Convert BufferedImage to InputStream (JPEG)
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(thumbnailImage, "jpg", baos);
        ByteArrayInputStream thumbInputStream = new ByteArrayInputStream(baos.toByteArray());

        // Upload thumbnail to S3
        String thumbKey = "thumb_" + originalFilename + ".jpg";
        String url=s3Service.uploadFile(thumbInputStream, thumbKey, "image/jpeg");
        // Clean up temp file
        tempFile.delete();
        return url;
    }
}

package com.family.tree.media.service.util;

import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class ThumbnailUtil {

    public BufferedImage createThumbnail(BufferedImage originalImage, int width, int height) {
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = thumbnail.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return thumbnail;
    }

    public BufferedImage createThumbnail(File file, int width, int height) throws Exception {
        String mimeType = Files.probeContentType(file.toPath());
        if (mimeType == null) {
            mimeType = guessMimeTypeByExtension(file.getName());
        }

        System.out.println("mimeType:"+mimeType);

        if (mimeType.startsWith("image/")) {
            return createImageThumbnail(file, width, height);
        } else if (mimeType.startsWith("video/")) {
            File frameFile = extractVideoFrameWithFFmpeg(file);
            return createImageThumbnail(frameFile, width, height);
        } else {
            return createImageThumbnail(file, width, height);
        }
    }

    private BufferedImage createImageThumbnail(File file, int width, int height) throws IOException {
        BufferedImage originalImage = ImageIO.read(file);
        if (originalImage == null) {
            throw new IOException("Cannot read image file");
        }
        Image scaledImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = thumbnail.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        return thumbnail;
    }

    private File extractVideoFrameWithFFmpeg(File videoFile) throws IOException, InterruptedException {
        File tempFrame = File.createTempFile("frame-", ".jpg");
        ProcessBuilder pb = new ProcessBuilder("ffmpeg",
                "-ss", "00:00:02",  // 2 seconds into the video
                "-i", videoFile.getAbsolutePath(),
                "-frames:v", "1",
                "-q:v", "2",
                tempFrame.getAbsolutePath());
        Process process = pb.start();
        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new IOException("FFmpeg failed to extract frame");
        }
        return tempFrame;
    }

    private BufferedImage loadDefaultThumbnail(int width, int height) throws IOException {
        InputStream is = getClass().getResourceAsStream("/default-thumb.png");
        if (is == null) {
            throw new IOException("Default thumbnail resource not found");
        }
        BufferedImage defaultImage = ImageIO.read(is);
        Image scaled = defaultImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage thumbnail = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = thumbnail.createGraphics();
        g2d.drawImage(scaled, 0, 0, null);
        g2d.dispose();
        return thumbnail;
    }

    private String guessMimeTypeByExtension(String filename) {
        if (filename == null) return "application/octet-stream";
        String ext = "";

        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex >= 0 && dotIndex < filename.length() - 1) {
            ext = filename.substring(dotIndex + 1).toLowerCase();
        }

        switch (ext) {
            case "jpg":
            case "jpeg": return "image/jpeg";
            case "png": return "image/png";
            case "gif": return "image/gif";
            case "bmp": return "image/bmp";
            case "mp4": return "video/mp4";
            case "mov": return "video/quicktime";
            case "avi": return "video/x-msvideo";
            case "mkv": return "video/x-matroska";
            default: return "application/octet-stream";
        }
    }


}

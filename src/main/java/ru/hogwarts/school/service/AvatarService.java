package ru.hogwarts.school.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.StudentRepository;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Service
public class AvatarService {

    @Value("${school.students.avatar.dir.path}")
    private String avatarsDir;

    private AvatarRepository avatarRepository;
    private StudentRepository studentRepository;

    private Logger logger = LoggerFactory.getLogger(AvatarService.class);

    public AvatarService(AvatarRepository avatarRepository, StudentRepository studentService) {
        logger.debug("Constructor AvatarService was called");
        this.avatarRepository = avatarRepository;
        this.studentRepository = studentService;
    }

    public void editAvatar(Long studentId, MultipartFile file) throws IOException {
        logger.debug("Method editAvatar was called with studentId = {}", studentId);
        Student student = studentRepository.findById(studentId).get();

        Path path = Path.of(avatarsDir, student + "." + getExtension(file.getOriginalFilename()));
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);

        try (
                InputStream is = file.getInputStream();
                OutputStream os = Files.newOutputStream(path, StandardOpenOption.CREATE_NEW);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                BufferedOutputStream bos = new BufferedOutputStream(os, 1024);
        ) {
            bis.transferTo(bos);
        }

        Avatar avatar = findAvatar(studentId);
        avatar.setStudent(student);
        avatar.setFilePath(path.toString());
        avatar.setFileSize(file.getSize());
        avatar.setMediaType(file.getContentType());
        avatar.setData(generateSmallAvatar(path));

        avatarRepository.save(avatar);
    }

    @Transactional
    public Avatar findAvatar(Long studentId){
        logger.debug("Method findAvatar was called with studentId = {} ", studentId);
        return avatarRepository.findByStudentId(studentId).orElse(new Avatar());
    }

    private String getExtension(String originalFilename) {
        logger.debug("Method getExtension was called with originalFilename = {} ", originalFilename);
        String result = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        return result;
    }

    private byte[] generateSmallAvatar(Path path) throws IOException {
        logger.debug("Method generateSmallAvatar was called with path = {} ", path);
        try (
                InputStream is = Files.newInputStream(path);
                BufferedInputStream bis = new BufferedInputStream(is, 1024);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();) {
            BufferedImage image = ImageIO.read(bis);

            int height = image.getHeight() / (image.getWidth() / 100);
            BufferedImage avatarImage = new BufferedImage(100, height, image.getType());
            Graphics2D graphics = avatarImage.createGraphics();
            graphics.drawImage(image, 0, 0, 100, height, null);
            graphics.dispose();

            ImageIO.write(avatarImage, getExtension(getExtension(path.getFileName().toString())), baos);
            return baos.toByteArray();
        }
    }

    public List<Avatar> findAvatars() {
        logger.debug("Method findAvatars was called");
        return avatarRepository.findAll();
    }

    public List<Avatar> findAvatars(Integer pageNumber, Integer pageSize) {
        logger.debug("Method findAvatars was called with pageNumber = {}, pageSize = {}", pageNumber, pageSize);
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        return avatarRepository.findAll(pageRequest).getContent();
    }
}

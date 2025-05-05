package ru.hogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.AvatarDTO;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.utils.AvatarMappingUtil;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@RestController
@RequestMapping("avatar")
public class AvatarController {

    private final AvatarService avatarService;
    private final AvatarMappingUtil avatarMappingUtil;

    public AvatarController(AvatarService avatarService, AvatarMappingUtil avatarMappingUtil) {
        this.avatarService = avatarService;
        this.avatarMappingUtil = avatarMappingUtil;
    }

    @PostMapping(value = "{studentId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> editAvatar(@PathVariable Long studentId, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.editAvatar(studentId, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "{studentId}/from-db")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long studentId) {
        Avatar avatar = avatarService.findAvatar(studentId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getData().length);

        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }

    @GetMapping(value = "get-all")
    public List<AvatarDTO> getAvatar(@RequestParam(value = "page", required = false) Integer pageNumber, @RequestParam(value = "count", required = false) Integer pageSize) {
        List<Avatar> avatars;

        if (pageNumber != null && pageSize != null){
            avatars = avatarService.findAvatars(pageNumber, pageSize);
        } else {
            avatars = avatarService.findAvatars();
        }

        return avatars.stream()
                .map(avatar -> avatarMappingUtil.avatarToAvatarDto(avatar))
                .toList();
    }

    @GetMapping(value = "{studentId}/from-driver")
    public void getAvatar(@PathVariable Long studentId, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(studentId);

        Path path = Path.of(avatar.getFilePath());

        try (
                InputStream is = Files.newInputStream(path);
                OutputStream os = response.getOutputStream();
        ) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }
}

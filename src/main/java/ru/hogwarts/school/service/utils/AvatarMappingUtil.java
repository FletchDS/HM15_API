package ru.hogwarts.school.service.utils;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.AvatarDTO;

import java.util.List;

@Service
public class AvatarMappingUtil {

    public AvatarDTO avatarToAvatarDto(Avatar avatar){
        AvatarDTO avatarDTO = new AvatarDTO();
        avatarDTO.setId(avatar.getId());
        avatarDTO.setFilePath(avatar.getFilePath());
        avatarDTO.setMediaType(avatar.getMediaType());
        avatarDTO.setFileSize(avatar.getFileSize());
        avatarDTO.setUrl(getAvatarUri(avatar));
        return avatarDTO;
    }

    public Avatar avatarDtoToAvatar(AvatarDTO avatarDTO){
        Avatar avatar = new Avatar();
        avatar.setId(avatarDTO.getId());
        avatar.setFilePath(avatarDTO.getFilePath());
        avatar.setMediaType(avatarDTO.getMediaType());
        avatar.setFileSize(avatarDTO.getFileSize());
        return avatar;
    }

    public List<AvatarDTO> avatarListToAvatarDtoList(List<Avatar> avatars){
        return avatars.stream()
                .map(this::avatarToAvatarDto)
                .toList();
    }

    public String getAvatarUri(Avatar avatar){
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString() + "/avatar/" + avatar.getStudent().getId() + "/from-db";
    }
}

package com.islandcollaborative.creativeexchange.services;

import com.islandcollaborative.creativeexchange.models.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class AppUserService {
    @Autowired
    FileUploadService fileUploadService;

    private final String profileImageRoot = "profile/";

    public void updateProfilePicture(AppUser userPrincipal,
                                            MultipartFile multipartFile) throws IOException {
        if (multipartFile == null) return;

        String path = profileImageRoot + userPrincipal.getId() + "/" + multipartFile.getOriginalFilename();
        InputStream stream = new BufferedInputStream(multipartFile.getInputStream());
        fileUploadService.upload(path, stream);
        userPrincipal.setImageFilename(multipartFile.getOriginalFilename());
    }

    public String getProfilePicturePath(AppUser userPrincipal) {
        return fileUploadService.getURL(profileImageRoot + userPrincipal.getId() + "/" + userPrincipal.getImageFilename());
    }
}

package com.islandcollaborative.creativeexchange.services;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.repositories.AppUserRepository;
import org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Service
public class AppUserService {
    @Autowired
    FileUploadService fileUploadService;

    @Autowired
    AppUserRepository appUserRepository;

    private final String profileImageRoot = "profile/";
    private final String defaultProfilePicturePath = "/default-profile.jpg";
    private final String defaultProfilePicturePathSmall = "/default-profile-small.jpg";

    private Map<String, String> contentTypesExtensions = Map.ofEntries(
            Map.entry("image/jpg", ".jpg"),
            Map.entry("image/jpeg", ".jpg"),
            Map.entry("image/png", ".png")
    );

    public void updateProfilePicture(AppUser userPrincipal,
                                            MultipartFile image) throws IOException {
        if (image == null || image.getContentType() == "application/octet-stream") return;

        String ext = contentTypesExtensions.get(image.getContentType());
        if (ext == null) throw new InvalidContentTypeException("File type must be a jpg or png");

        String path = profileImageRoot + userPrincipal.getId() + ext;
        InputStream stream = new BufferedInputStream(image.getInputStream());

        try {
            fileUploadService.upload(path, stream, image.getContentType(), image.getSize());
        } catch (Exception e) {
            throw new IOException("File upload service did not work");
        }

        userPrincipal.setImageExtension(ext);
        userPrincipal.setHasProfilePicture(true);

        appUserRepository.save(userPrincipal);
    }

    public String getProfilePicturePath(AppUser userPrincipal) {
        return userPrincipal.hasProfilePicture() ?
                fileUploadService.getURL(
                profileImageRoot + userPrincipal.getId() +
                        userPrincipal.getImageExtension()
                ) :
                defaultProfilePicturePath;
    }

    public String getProfilePictureSmallURL(AppUser userPrincipal) {
        return userPrincipal.hasProfilePicture() ?
                fileUploadService.getURL(
                    profileImageRoot + userPrincipal.getId() + "-small"
                            + userPrincipal.getImageExtension()
                ) :
                defaultProfilePicturePathSmall;
    }
}

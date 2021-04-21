package com.islandcollaborative.creativeexchange.services;

import com.islandcollaborative.creativeexchange.models.Post;
import com.islandcollaborative.creativeexchange.models.PostImage;
import com.islandcollaborative.creativeexchange.repositories.PostImageRepository;
import com.islandcollaborative.creativeexchange.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Service
public class PostService {
    @Autowired
    PostRepository postRepository;

    @Autowired
    PostImageRepository postImageRepository;

    @Autowired
    FileUploadService fileUploadService;

    private List<String> allowedContentTypes = Arrays.asList(
            "image/jpg",
            "image/jpeg",
            "image/png"
    );

    private final String postImageRoot = "posts/";

    public void addImages(Post post, MultipartFile[] images) throws IOException {
        if (images == null || images.length == 0) return;

        for (MultipartFile image : images) {
            if (!allowedContentTypes.contains(image.getContentType())) continue;

            System.out.println("===============================");
            System.out.println(images[0].getOriginalFilename());
            System.out.println(images[0].getName());
            System.out.println(images[0].getBytes());
            System.out.println(images[0].getSize());
            System.out.println(images[0].getResource());
            System.out.println(images[0].getContentType());
            System.out.println("===============================");
            String path = postImageRoot + post.getId() + "/" + image.getOriginalFilename();
            InputStream stream = new BufferedInputStream(image.getInputStream());
            fileUploadService.upload(path, stream);

            PostImage postImage = new PostImage(image.getOriginalFilename(), post, null);
            postImageRepository.save(postImage);
        }
    }

    public String getImageUrl(PostImage image) throws IOException {
        return fileUploadService.getURL(postImageRoot + image.getPost().getId()
                + "/" + image.getFilename());
    }

    public void removeImage(Post post, PostImage image) {
        // TODO
    }
}

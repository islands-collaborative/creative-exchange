package com.islandcollaborative.creativeexchange.services;

import com.islandcollaborative.creativeexchange.models.AppUser;
import com.islandcollaborative.creativeexchange.models.Post;
import com.islandcollaborative.creativeexchange.models.PostImage;
import com.islandcollaborative.creativeexchange.repositories.PostImageRepository;
import com.islandcollaborative.creativeexchange.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired PostRepository postRepository;

    @Autowired PostImageRepository postImageRepository;

    @Autowired FileUploadService fileUploadService;

    private Map<String, String> contentTypesExtensions = Map.ofEntries(
            Map.entry("image/jpg", ".jpg"),
            Map.entry("image/jpeg", ".jpg"),
            Map.entry("image/png", ".png")
    );

    private final String postImageRoot = "posts/";

    public void addImages(Post post, MultipartFile[] images) throws IOException {
        if (images == null || images.length == 0) return;

        for (MultipartFile image : images) {

            String ext = contentTypesExtensions.get(image.getContentType());
            if (ext == null) continue;

//            System.out.println("===============================");
//            System.out.println(images[0].getOriginalFilename());
//            System.out.println(images[0].getName());
//            System.out.println(images[0].getBytes());
//            System.out.println(images[0].getSize());
//            System.out.println(images[0].getResource());
//            System.out.println(images[0].getContentType());
//            System.out.println("===============================");

            PostImage postImage = new PostImage(ext, post, null);
            postImageRepository.save(postImage);
            String path = postImageRoot + post.getId() + "/" + postImage.getId() + ext;
            InputStream stream = new BufferedInputStream(image.getInputStream());

            try {
                fileUploadService.upload(path, stream, image.getContentType(), image.getSize());
            } catch (Exception e) {
                postImageRepository.delete(postImage);
            }
        }
    }

    public String getImageUrl(PostImage image) throws IOException {
        return fileUploadService.getURL(postImageRoot + image.getPost().getId()
                + "/" + image.getFilename());
    }

    public void removeImage(PostImage image) {
        String path = postImageRoot + image.getPost().getId() + "/" + image.getId() + image.getExtension();
        fileUploadService.delete(path);
        postImageRepository.delete(image);
    }

    public List<Post> getUserFeed(AppUser userPrincipal) {
        return postRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .filter(u -> userPrincipal.getFollowed().contains(userPrincipal))
                .collect(Collectors.toCollection(ArrayList::new));
    }
}

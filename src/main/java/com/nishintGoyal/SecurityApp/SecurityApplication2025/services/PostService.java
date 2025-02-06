package com.nishintGoyal.SecurityApp.SecurityApplication2025.services;


import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.PostDTO;

import java.util.List;

public interface PostService
{
    PostDTO createNewPost(PostDTO inputPost);

    PostDTO getPostById(Long id);

    List<PostDTO> getAllPosts();

    PostDTO updatePostById(Long id,PostDTO input);

}

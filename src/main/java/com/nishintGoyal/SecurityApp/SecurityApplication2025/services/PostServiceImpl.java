package com.nishintGoyal.SecurityApp.SecurityApplication2025.services;


import com.nishintGoyal.SecurityApp.SecurityApplication2025.dtos.PostDTO;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.entities.PostEntity;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.exceptions.ResourceNotFoundException;
import com.nishintGoyal.SecurityApp.SecurityApplication2025.repositories.PostRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService
{
    private final ModelMapper modelMapper;
    private final PostRepository postRepository;

    public PostServiceImpl(ModelMapper modelMapper, PostRepository postRepository)
    {
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    //create

    @Override
    public PostDTO createNewPost(PostDTO inputPost)
    {
        PostEntity covertToEntity=modelMapper.map(inputPost, PostEntity.class);
        PostEntity savedEntity=postRepository.save(covertToEntity);
        PostDTO convertDto=modelMapper.map(savedEntity, PostDTO.class);

        return convertDto;
    }

    //getById

    @Override
    public PostDTO getPostById(Long id)
    {
        PostEntity getById=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post not id with "+id));

        PostDTO convertBackToDto=modelMapper.map(getById, PostDTO.class);

        return convertBackToDto;

    }

    //getAll

    @Override
    public List<PostDTO> getAllPosts()
    {
      List<PostEntity> getAll=postRepository.findAll();

      List<PostDTO> convertToDtoList=getAll.stream().map(record->modelMapper.map(record, PostDTO.class)).toList();

      return convertToDtoList;
    }

    //update

    @Override
    public PostDTO updatePostById(Long id,PostDTO input)
    {
        PostEntity getFromDb=postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Not present in Db "+id));
        input.setId(id);
        modelMapper.map(input, getFromDb);

        PostEntity savedEntity=postRepository.save(getFromDb);

        PostDTO convertBackToDto=modelMapper.map(savedEntity, PostDTO.class);

        return convertBackToDto;

    }


}

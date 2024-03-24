package com.YusufFakhreddin.ICDTicketingSystem.mapper;

import com.YusufFakhreddin.ICDTicketingSystem.comment.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.comment.CommentDTO;
import com.YusufFakhreddin.ICDTicketingSystem.user.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    private final ModelMapper modelMapper;
    private final UserService userService;

    @Autowired
    public CommentMapper(ModelMapper modelMapper, UserService userService) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        configureCommentMapping();
    }

    private void configureCommentMapping() {
        modelMapper.addMappings(new PropertyMap<CommentDTO, Comment>() {
            @Override
            protected void configure() {
                using(ctx -> userService.findUserByUsername(((CommentDTO) ctx.getSource()).getAuthor_username()))
                        .map(source, destination.getAuthor());
            }
        });

        modelMapper.addMappings(new PropertyMap<Comment, CommentDTO>() {
            @Override
            protected void configure() {
                map().setAuthor_username(source.getAuthor().getUsername());
            }
        });
    }
}
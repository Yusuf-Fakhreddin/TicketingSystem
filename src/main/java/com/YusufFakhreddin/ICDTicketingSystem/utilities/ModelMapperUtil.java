package com.YusufFakhreddin.ICDTicketingSystem.utilities;

import com.YusufFakhreddin.ICDTicketingSystem.attachment.AttachmentDTO;
import com.YusufFakhreddin.ICDTicketingSystem.comment.CommentDTO;
import com.YusufFakhreddin.ICDTicketingSystem.ticket.Ticket;
import com.YusufFakhreddin.ICDTicketingSystem.comment.Comment;
import com.YusufFakhreddin.ICDTicketingSystem.ticket.dto.TicketDTO;
import com.YusufFakhreddin.ICDTicketingSystem.user.UserDTO;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ModelMapperUtil {
    private final ModelMapper modelMapper;

    @Autowired
    public ModelMapperUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);

        this.modelMapper.addMappings(new PropertyMap<Comment, CommentDTO>() {
            @Override
            protected void configure() {
                map().setAuthor_username(source.getAuthor().getUsername());
            }
        });


        // Define custom mapping
        this.modelMapper.addMappings(new PropertyMap<Ticket, TicketDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setTitle(source.getTitle());
                map().setDescription(source.getDescription());
                map().setStatus(source.getStatus());
                map().setPriority(source.getPriority());
                map().setType(source.getType());
                map().setDate(source.getDate());
                map().setTime(source.getTime());
                map().setOwner(source.getOwner().getUsername());
                map().setOwnerTeam(source.getOwnerTeam().getName());
                map().setAssignedUser(source.getAssignedUser().getUsername());
                map().setAssignedTeam(source.getAssignedTeam().getName());
                // Add more mappings as needed
                using(ctx -> {
                    Ticket ticket = (Ticket) ctx.getSource();
                    return ticket.getAttachments() != null ? ticket.getAttachments().stream()
                            .map(attachment -> modelMapper.map(attachment, AttachmentDTO.class))
                            .collect(Collectors.toList()) : new ArrayList<>();
                }).map(source, destination.getAttachments());
            }
        });
    }



    public <S, T> T mapObject(S source, Class<T> targetClass) {
        try {
            return modelMapper.map(source, targetClass);
        } catch (MappingException ex) {
            System.out.println("Error mapping object: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public String[] getNullPropertyNames(UserDTO userDTO) {
        BeanWrapper src = new BeanWrapperImpl(userDTO);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        return emptyNames.toArray(new String[0]);
    }
}

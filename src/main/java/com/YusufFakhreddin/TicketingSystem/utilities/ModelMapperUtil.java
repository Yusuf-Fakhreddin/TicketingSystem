package com.YusufFakhreddin.TicketingSystem.utilities;


import com.YusufFakhreddin.TicketingSystem.comment.CommentMapper;
import com.YusufFakhreddin.TicketingSystem.ticket.TicketMapper;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ModelMapperUtil {
    private final ModelMapper modelMapper;

    @Autowired
    public ModelMapperUtil(ModelMapper modelMapper, TicketMapper ticketMapper, CommentMapper commentMapper) {
        this.modelMapper = modelMapper;
        this.modelMapper.getConfiguration()
                .setAmbiguityIgnored(true)
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(Configuration.AccessLevel.PRIVATE);
    }

    public <S, T> T mapObject(S source, Class<T> targetClass) {
        try {
            return modelMapper.map(source, targetClass);
        } catch (Exception ex) {
            System.out.println("Error mapping object: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }
}
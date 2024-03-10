package com.YusufFakhreddin.ICDTicketingSystem.dto;

import com.YusufFakhreddin.ICDTicketingSystem.ErrorHandling.CustomException;
import com.YusufFakhreddin.ICDTicketingSystem.entity.Ticket;
import lombok.RequiredArgsConstructor;
import org.modelmapper.MappingException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.config.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

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
                map().setOwnerTeam(source.getOwner().getTeam().getName());
                map().setAssigned_user(source.getAssigned_user());
                map().setAssigned_team(source.getAssigned_team());
                // Add more mappings as needed
            }
        });
    }
    public <S, T> T mapObject(S source, Class<T> targetClass) {
        try {
            return modelMapper.map(source, targetClass);
        } catch (MappingException ex) {
            throw new CustomException(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Error mapping object");
        }
    }
}

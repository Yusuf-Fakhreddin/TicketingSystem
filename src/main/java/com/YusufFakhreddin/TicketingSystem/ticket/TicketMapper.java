package com.YusufFakhreddin.TicketingSystem.ticket;

import com.YusufFakhreddin.TicketingSystem.attachment.AttachmentDTO;
import com.YusufFakhreddin.TicketingSystem.team.TeamService;
import com.YusufFakhreddin.TicketingSystem.ticket.Ticket;
import com.YusufFakhreddin.TicketingSystem.ticket.dto.TicketDTO;
import com.YusufFakhreddin.TicketingSystem.user.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class TicketMapper {

    private final ModelMapper modelMapper;
    private final TeamService teamService;
    private final UserService userService;

    @Autowired
    public TicketMapper(ModelMapper modelMapper, TeamService teamService, UserService userService) {
        this.modelMapper = modelMapper;
        this.teamService = teamService;
        this.userService = userService;
        configureTicketMapping();
    }


    private void configureTicketMapping() {
        modelMapper.addMappings(new PropertyMap<TicketDTO, Ticket>() {
            @Override
            protected void configure() {
                using(ctx -> {
                    TicketDTO ticketDTO = (TicketDTO) ctx.getSource();
                    return userService.findUserByUsername(ticketDTO.getAssignedUser());
                }).map(source, destination.getAssignedUser());

                using(ctx -> {
                    TicketDTO ticketDTO = (TicketDTO) ctx.getSource();
                    return teamService.findTeamByName(ticketDTO.getAssignedTeam());
                }).map(source, destination.getAssignedTeam());

                using(ctx -> {
                    TicketDTO ticketDTO = (TicketDTO) ctx.getSource();
                    return teamService.findTeamByName(ticketDTO.getOwnerTeam());
                }).map(source, destination.getOwnerTeam());

            }
        });

        modelMapper.addMappings(new PropertyMap<Ticket, TicketDTO>() {
            @Override
            protected void configure() {
                map().setOwnerTeam(source.getOwnerTeam().getName());
                map().setAssignedUser(source.getAssignedUser().getUsername());
                map().setAssignedTeam(source.getAssignedTeam().getName());
                using(ctx -> {
                    Ticket ticket = (Ticket) ctx.getSource();
                    return ticket.getAttachments() != null ? ticket.getAttachments().stream()
                            .map(attachment -> modelMapper.map(attachment, AttachmentDTO.class))
                            .collect(Collectors.toList()) : new ArrayList<>();
                }).map(source, destination.getAttachments());
            }
        });
    }
}
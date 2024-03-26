package com.YusufFakhreddin.TicketingSystem.ticket.dto;

import com.YusufFakhreddin.TicketingSystem.ticket.enums.TicketStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TicketResolutionDTO {
    private String resolution;
    private TicketStatus status;
}

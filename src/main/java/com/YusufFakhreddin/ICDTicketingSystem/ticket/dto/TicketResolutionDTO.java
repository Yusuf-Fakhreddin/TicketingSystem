package com.YusufFakhreddin.ICDTicketingSystem.ticket.dto;

import com.YusufFakhreddin.ICDTicketingSystem.ticket.enums.TicketStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TicketResolutionDTO {
    private String resolution;
    private TicketStatus status;
}

package com.YusufFakhreddin.ICDTicketingSystem.dto;

import com.YusufFakhreddin.ICDTicketingSystem.enums.TicketStatus;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class TicketResolutionDTO {
    private String resolution;
    private TicketStatus status;
}

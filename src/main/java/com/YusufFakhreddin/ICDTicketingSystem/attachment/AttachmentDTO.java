package com.YusufFakhreddin.ICDTicketingSystem.attachment;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AttachmentDTO {
    private Long id;
    private String fileName;
    private String fileType;

    private byte[] data;

    public byte[] getData() {
        return this.data;
    }
}

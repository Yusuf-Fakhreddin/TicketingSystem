package com.YusufFakhreddin.TicketingSystem.attachment;

import org.springframework.stereotype.Component;

@Component
public class AttachmentMapper {
    public AttachmentDTO toAttachmentDTO(Attachment attachment) {
        AttachmentDTO attachmentDTO = new AttachmentDTO();
        attachmentDTO.setId(attachment.getId());
        attachmentDTO.setFileName(attachment.getFileName());
        attachmentDTO.setFileType(attachment.getFileType());
        return attachmentDTO;
    }

    public Attachment toAttachment(AttachmentDTO attachmentDTO) {
        Attachment attachment = new Attachment();
        attachment.setId(attachmentDTO.getId());
        attachment.setFileName(attachmentDTO.getFileName());
        attachment.setFileType(attachmentDTO.getFileType());
        return attachment;
    }
}

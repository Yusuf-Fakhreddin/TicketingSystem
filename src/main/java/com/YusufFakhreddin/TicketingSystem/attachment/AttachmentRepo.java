package com.YusufFakhreddin.TicketingSystem.attachment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, String> {

    Attachment save (Attachment attachment);
}

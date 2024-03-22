package com.YusufFakhreddin.ICDTicketingSystem.dao;

import com.YusufFakhreddin.ICDTicketingSystem.entity.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepo extends JpaRepository<Attachment, String> {

    Attachment save (Attachment attachment);
}

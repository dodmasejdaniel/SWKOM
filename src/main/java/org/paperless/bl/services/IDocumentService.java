package org.paperless.bl.services;

import org.paperless.model.DocumentDTO;
import org.springframework.web.multipart.MultipartFile;

public interface IDocumentService {

    void uploadDocument(DocumentDTO documentDTO, MultipartFile file);

}

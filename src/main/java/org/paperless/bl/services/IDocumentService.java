package org.paperless.bl.services;

import org.paperless.model.Document;
import org.springframework.web.multipart.MultipartFile;

public interface IDocumentService {

    void uploadDocument(Document document, MultipartFile file);

}

package org.paperless.bl.services;

import org.paperless.model.DocumentDTO;
import org.paperless.model.GetDocument200Response;
import org.springframework.web.multipart.MultipartFile;

public interface IDocumentService {

    void uploadDocument(DocumentDTO documentDTO, MultipartFile file);

    GetDocument200Response getDocument(Integer id, Integer page, Boolean fullPerms);

}

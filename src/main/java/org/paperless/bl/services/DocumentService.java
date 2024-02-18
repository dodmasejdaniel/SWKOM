package org.paperless.bl.services;

import org.paperless.bl.mapper.DocumentMapper;
import org.paperless.model.DocumentDTO;
import org.paperless.persistence.entities.DocumentsDocument;
import org.paperless.persistence.repositories.DocumentsDocumentRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService implements IDocumentService {
    private final DocumentsDocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public DocumentService(DocumentsDocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public void uploadDocument(DocumentDTO documentDTO, MultipartFile file) {

        documentDTO.setCreated(OffsetDateTime.now());
        documentDTO.content("");
        documentDTO.setAdded(OffsetDateTime.now());
        documentDTO.setModified(OffsetDateTime.now());

        DocumentsDocument entity = documentMapper.dtoToEntity(documentDTO);

        entity.setStorageType("pdf");
        entity.setMimeType("pdf");
        entity.setChecksum("checksum");

        documentRepository.save(entity);


        List<DocumentsDocument> all = documentRepository.findAll();
        System.out.println("==========================");
        System.out.println(all.size());
        for (DocumentsDocument documentsDocument : all) {
            System.out.println(documentsDocument.getTitle());
        }
    }
}

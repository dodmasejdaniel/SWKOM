package org.paperless.bl.services;

import org.paperless.model.Document;
import org.paperless.persistence.entities.DocumentsDocument;
import org.paperless.persistence.repositories.DocumentsDocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentService implements IDocumentService {
    private final DocumentsDocumentRepository documentRepository;

    public DocumentService(DocumentsDocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    @Override
    public void uploadDocument(Document document, MultipartFile file) {

        document.setCreated(OffsetDateTime.now());
        document.setAdded(OffsetDateTime.now());
        document.setModified(OffsetDateTime.now());
        document.content("");
        document.setAdded(OffsetDateTime.now());


        // DocumentsDocument documentToBeSaved = documentMapper.dtoToEntity(documentDTO);

        DocumentsDocument documentToBeSaved = new DocumentsDocument();
        documentToBeSaved.setTitle("Sample Title" + UUID.randomUUID());
        documentToBeSaved.setContent("Sample Content");
        documentToBeSaved.setCreated(OffsetDateTime.now()); // Set the current time for created
        documentToBeSaved.setModified(OffsetDateTime.now()); // Set the current time for modified
        documentToBeSaved.setChecksum("sampleChecksum");
        documentToBeSaved.setAdded(OffsetDateTime.now()); // Set the current time for added
        documentToBeSaved.setStorageType("pdf");
        documentToBeSaved.setMimeType("application/pdf"); // Example mime type for PDF

        documentToBeSaved.setFilename("sample.pdf");
        documentToBeSaved.setArchiveSerialNumber(123);
        documentToBeSaved.setArchiveChecksum("sampleArchiveChecksum");
        documentToBeSaved.setArchiveFilename("sampleArchiveFilename");
        documentToBeSaved.setOriginalFilename("sampleOriginalFilename");

        documentRepository.save(documentToBeSaved);

        List<DocumentsDocument> all = documentRepository.findAll();
        System.out.println("==========================");
        System.out.println(all.size());
        for (DocumentsDocument documentsDocument : all) {
            System.out.println(documentsDocument.getTitle());
        }
    }
}

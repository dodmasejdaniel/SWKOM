package org.paperless.bl.services;

import org.paperless.bl.mapper.DocumentMapper;
import org.paperless.bl.mapper.GetDocument200ResponseMapper;
import org.paperless.model.DocumentDTO;
import org.paperless.model.GetDocument200Response;
import org.paperless.model.GetDocuments200Response;
import org.paperless.persistence.entities.DocumentsDocument;
import org.paperless.persistence.repositories.DocumentsDocumentRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class DocumentService implements IDocumentService {
    private final DocumentsDocumentRepository documentRepository;
    private final DocumentMapper documentMapper;
    private final GetDocument200ResponseMapper getDocument200ResponseMapper;


    public DocumentService(DocumentsDocumentRepository documentRepository, DocumentMapper documentMapper, GetDocument200ResponseMapper getDocument200ResponseMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
        this.getDocument200ResponseMapper = getDocument200ResponseMapper;
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

    @Override
    public GetDocument200Response getDocument(Integer id, Integer page, Boolean fullPerms) {
        DocumentsDocument documentsDocument = documentRepository.getReferenceById(id);
        return getDocument200ResponseMapper.entityToDto(documentsDocument);
    }

    @Override
    public ResponseEntity<GetDocuments200Response> getDocuments(Integer page, Integer pageSize, String query, String ordering, List<Integer> tagsIdAll, Integer documentTypeId, Integer storagePathIdIn, Integer correspondentId, Boolean truncateContent) throws IOException {
        List<DocumentDTO> foundDocuments = new ArrayList<>();

        if (query == null || query.isEmpty()) {
            for (DocumentsDocument document : documentRepository.findAll()) {
                foundDocuments.add(documentMapper.entityToDto(document));
            }
        } else {
            for (DocumentsDocument document : documentRepository.findAll()) {
                foundDocuments.add(documentMapper.entityToDto(document));
            }
            // TODO: ES vonë
        }


        GetDocuments200Response sampleResponse = new GetDocuments200Response();
        sampleResponse.setCount(50);
        sampleResponse.setNext(1);
        sampleResponse.setPrevious(1);
        sampleResponse.addAllItem(50);
        foundDocuments.forEach(documentDTO -> sampleResponse.addResultsItem(documentDTO.toGetDocumentInnerResponse()));

        return ResponseEntity.ok(sampleResponse);
    }
}

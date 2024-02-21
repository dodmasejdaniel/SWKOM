package org.paperless;

import org.paperless.bl.mapper.GetDocument200ResponseMapper;
import org.paperless.bl.services.DocumentService;
import org.paperless.model.DocumentDTO;
import org.paperless.model.GetDocuments200Response;
import org.paperless.persistence.entities.*;
import org.paperless.persistence.repositories.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;
import org.paperless.bl.mapper.DocumentMapper;
import org.paperless.persistence.repositories.DocumentsCorrespondentRepository;
import org.paperless.persistence.repositories.DocumentsDocumentTagsRepository;
import org.paperless.persistence.repositories.DocumentsDocumenttypeRepository;
import org.paperless.persistence.repositories.DocumentsStoragepathRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import java.util.Collections;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class})
public class ServiceTests {

    @Mock
    private DocumentsDocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private GetDocument200ResponseMapper getDocument200ResponseMapper;

    @InjectMocks
    private DocumentService documentService;


    @Test
    void testUploadDocument() {
        // Prepare test data
        DocumentDTO documentDTO = new DocumentDTO();
        MultipartFile file = mock(MultipartFile.class);

        // Mock behavior
        when(documentMapper.dtoToEntity(documentDTO)).thenReturn(new DocumentsDocument());

        // Call method under test
        documentService.uploadDocument(documentDTO, file);

        // Verify interactions
        verify(documentMapper).dtoToEntity(documentDTO);
        verify(documentRepository).save(any(DocumentsDocument.class));
    }

    @Test
    void testGetDocument() {
        // Prepare test data
        Integer id = 1;
        DocumentsDocument documentsDocument = new DocumentsDocument();
        when(documentRepository.getReferenceById(id)).thenReturn(documentsDocument);

        // Call method under test
        documentService.getDocument(id, null, null);

        // Verify interactions
        verify(documentRepository).getReferenceById(id);
        verify(getDocument200ResponseMapper).entityToDto(documentsDocument);
    }

    @Test
    void testGetDocuments() throws IOException {
        // Prepare test data
        Integer page = 1;
        Integer pageSize = 10;
        String query = null;
        String ordering = null;
        List<Integer> tagsIdAll = Collections.emptyList();
        Integer documentTypeId = null;
        Integer storagePathIdIn = null;
        Integer correspondentId = null;
        Boolean truncateContent = null;

        // Mock behavior
        when(documentRepository.findAll()).thenReturn(Collections.emptyList());

        // Call method under test
        ResponseEntity<GetDocuments200Response> responseEntity = documentService.getDocuments(page, pageSize, query, ordering, tagsIdAll, documentTypeId, storagePathIdIn, correspondentId, truncateContent);

        // Verify interactions
        verify(documentRepository).findAll();
        assertEquals(200, responseEntity.getStatusCodeValue());
    }

}

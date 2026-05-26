package co.edu.usbcali.ecommerceusb.Service;

import co.edu.usbcali.ecommerceusb.dto.CreateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.DocumentTypeResponse;

import java.util.List;

public interface DocumentTypeService {
    List<DocumentTypeResponse> getDocumentTypes();
    DocumentTypeResponse getDocumentTypeById(Integer id);
    DocumentTypeResponse createDocumentType(CreateDocumentTypeRequest request);
    DocumentTypeResponse updateDocumentType(Integer id, CreateDocumentTypeRequest request);
    void deleteDocumentType(Integer id);
}
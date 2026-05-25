package co.edu.usbcali.ecommerceusb.Service.impl;

import co.edu.usbcali.ecommerceusb.Service.DocumentTypeService;
import co.edu.usbcali.ecommerceusb.dto.CreateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.DocumentTypeResponse;
import co.edu.usbcali.ecommerceusb.mapper.DocumentTypeMapper;
import co.edu.usbcali.ecommerceusb.model.DocumentType;
import co.edu.usbcali.ecommerceusb.repository.DocumentTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class DocumentTypeServiceImpl implements DocumentTypeService {

    @Autowired
    private DocumentTypeRepository documentTypeRepository;

    @Override
    public List<DocumentTypeResponse> getDocumentTypes() {
        List<DocumentType> documentTypes = documentTypeRepository.findAll();
        if (documentTypes.isEmpty()) return List.of();
        return DocumentTypeMapper.modelToDocumentTypeResponseList(documentTypes);
    }

    @Override
    public DocumentTypeResponse getDocumentTypeById(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para buscar");
        }
        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Tipo de documento no encontrado con el id: %d", id)));
        return DocumentTypeMapper.modelToDocumentTypeResponse(documentType);
    }

    @Override
    public DocumentTypeResponse createDocumentType(CreateDocumentTypeRequest request) throws Exception {
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateDocumentTypeRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getCode()) || request.getCode().isBlank()) {
            throw new Exception("El campo code no puede ser nulo.");
        }
        if (Objects.isNull(request.getName()) || request.getName().isBlank()) {
            throw new Exception("El campo name no puede ser nulo.");
        }
        if (documentTypeRepository.existsByCode(request.getCode())) {
            throw new Exception("Ya existe un tipo de documento con ese code.");
        }
        if (documentTypeRepository.existsByName(request.getName())) {
            throw new Exception("Ya existe un tipo de documento con ese name.");
        }

        DocumentType documentType = DocumentTypeMapper.createDocumentTypeRequestToDocumentType(request);
        documentType = documentTypeRepository.save(documentType);
        return DocumentTypeMapper.modelToDocumentTypeResponse(documentType);
    }

    @Override
    public DocumentTypeResponse updateDocumentType(Integer id, CreateDocumentTypeRequest request) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para actualizar");
        }
        if (Objects.isNull(request)) {
            throw new Exception("El objeto CreateDocumentTypeRequest no puede ser nulo.");
        }
        if (Objects.isNull(request.getCode()) || request.getCode().isBlank()) {
            throw new Exception("El campo code no puede ser nulo.");
        }
        if (Objects.isNull(request.getName()) || request.getName().isBlank()) {
            throw new Exception("El campo name no puede ser nulo.");
        }

        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Tipo de documento no encontrado con el id: %d", id)));

        // Verificar duplicados solo si el valor cambió
        if (!documentType.getCode().equals(request.getCode()) &&
                documentTypeRepository.existsByCode(request.getCode())) {
            throw new Exception("Ya existe un tipo de documento con ese code.");
        }
        if (!documentType.getName().equals(request.getName()) &&
                documentTypeRepository.existsByName(request.getName())) {
            throw new Exception("Ya existe un tipo de documento con ese name.");
        }

        documentType.setCode(request.getCode());
        documentType.setName(request.getName());

        documentType = documentTypeRepository.save(documentType);
        return DocumentTypeMapper.modelToDocumentTypeResponse(documentType);
    }
    @Override
    public void deleteDocumentType(Integer id) throws Exception {
        if (id == null || id <= 0) {
            throw new Exception("Debe ingresar el id para eliminar");
        }
        DocumentType documentType = documentTypeRepository.findById(id)
                .orElseThrow(() -> new Exception(
                        String.format("Tipo de documento no encontrado con el id: %d", id)));

        documentTypeRepository.delete(documentType);
    }

}
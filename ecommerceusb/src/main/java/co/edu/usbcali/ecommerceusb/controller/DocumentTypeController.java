package co.edu.usbcali.ecommerceusb.controller;

import co.edu.usbcali.ecommerceusb.Service.DocumentTypeService;
import co.edu.usbcali.ecommerceusb.dto.CreateDocumentTypeRequest;
import co.edu.usbcali.ecommerceusb.dto.DocumentTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/document-type")
public class DocumentTypeController {

    @Autowired
    private DocumentTypeService documentTypeService;

    @GetMapping("/all")
    public List<DocumentTypeResponse> getAll() {
        return documentTypeService.getDocumentTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> getById(@PathVariable Integer id) {
        return new ResponseEntity<>(documentTypeService.getDocumentTypeById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DocumentTypeResponse> create(@RequestBody CreateDocumentTypeRequest request) {
        return new ResponseEntity<>(documentTypeService.createDocumentType(request), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentTypeResponse> update(
            @PathVariable Integer id,
            @RequestBody CreateDocumentTypeRequest request) {
        return new ResponseEntity<>(documentTypeService.updateDocumentType(id, request), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        documentTypeService.deleteDocumentType(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
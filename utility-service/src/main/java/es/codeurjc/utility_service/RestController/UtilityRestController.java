package es.codeurjc.utility_service.RestController;

import es.codeurjc.utility_service.DTOs.PDFDeckDTO;
import es.codeurjc.utility_service.service.PDFService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

@RestController
@RequestMapping("/api/v1/utilities")
public class UtilityRestController {

    @Autowired
    private PDFService pdfService;

    @PostMapping("/pdf")
    public ResponseEntity<byte[]> generateDecksPdf(@RequestBody List<PDFDeckDTO> dataList) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        
        pdfService.exportDecksToPdf(dataList, baos);
        
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=Mis_Mazos.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(baos.toByteArray());
    }
}
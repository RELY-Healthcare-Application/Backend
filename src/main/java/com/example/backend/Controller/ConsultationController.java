package com.example.backend.Controller;

import com.example.backend.Bean.Consultation;
import com.example.backend.Bean.PrevConsultations;
import com.example.backend.Bean.DocumentDetails;
import com.example.backend.Service.ConsultationService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/consultation")
public class ConsultationController {
    @Resource(name = "consultationService")
    private ConsultationService consultationService;

    @GetMapping("/getAllDocumentsByCid/{consultationId}")
    public List<DocumentDetails> getAllDocuments(@PathVariable int consultationId) {
        return consultationService.getAllDocumentDetails(consultationId);
    }

    @GetMapping("/addDocumentByCid_Docuid/{consultationId}/{documentId}")
    public void addDocumentByCidDid(@PathVariable int consultationId, @PathVariable int documentId){
        consultationService.addDocument(consultationId, documentId);
    }

    @GetMapping("/addDocumentByCid_PrescriptionId/{consultationId}/{prescriptionId}")
    public  void addDocumentByCidPid(@PathVariable int consultationId, @PathVariable int prescriptionId){
        consultationService.addDocument(consultationId, prescriptionId);
    }

    @GetMapping("/removeDocumentByCid_Docuid/{consultationId}/{documentId}")
    public void removeDocumentByCidDid(@PathVariable int consultationId, @PathVariable int documentId){
        consultationService.removeDocument(consultationId, documentId);
    }

    @GetMapping("/getPrevConsultations/{patientId}")
    public List<PrevConsultations> getPrevConsultationsByPid(@PathVariable int patientId){
        return consultationService.getPrevConsultations(patientId);
    }
    @PostMapping("/addConsultation")
    public int addConsulation(@RequestBody Consultation consultation){
        return consultationService.addConsultation(consultation);
    }

    @PostMapping("/updateConsultationEndTime")
    public void updateConsultationEndTime(@RequestBody Consultation consultation){
        consultationService.updateConsultationEndtime(consultation);
    }

}

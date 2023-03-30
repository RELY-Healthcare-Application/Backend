package com.example.backend.Service;

import com.example.backend.Bean.Documents;
import com.example.backend.DocumentDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentsService {
    Documents saveDocument(MultipartFile file,int Id) throws Exception;
    Documents getDocument(int Id) throws Exception;

    void delDocument(int id);

    List<DocumentDetails> getAll(int id);
}
package com.example.backend.Service;

import com.example.backend.Bean.DocumentDetails;
import com.example.backend.Bean.Documents;
import com.example.backend.Bean.Patient;
import com.example.backend.Bean.User;
import com.example.backend.Repository.DocumentsRepository;
import com.example.backend.Repository.PatientRepository;
import com.example.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DocumentsRepository documentsRepository;


    public List<Patient> getAllPatients (Integer userId){
        User user = userRepository.findUserById(userId);
        //System.out.println("working file till step 1");
        List<Patient> patients = patientRepository.getPatients(userId);
        //System.out.println("working fine till step 2");
        return patients;
    }

    public Patient getPatient (Integer patientId){
        Patient patient=patientRepository.findPatientById(patientId);
        return patient;
    }

    public void uploadDoc(Patient patient){
        patientRepository.save(patient);
    }

    public void addPatient(Patient patient, Integer userId){
        //get user with the given id
        User user=userRepository.findUserById(userId);
        //add the new patient profile to the list of profiles and update
        List<Patient>patients = user.getProfiles();
        if (patients==null){
            patients=new ArrayList<>();
        }
        patient.setUser(user);
        patients.add(patient);
        user.setProfiles(patients);
        userRepository.save(user);
    }

    public void editPatient (Integer patientId, String fname, String lname, String DOB,char sex, String blood_group,String city,String state,String abdm_no,String photo_url,String relationship){
        patientRepository.updatePatient(patientId,fname,lname,DOB,sex,blood_group,city,state,abdm_no,relationship);
    }

    public void removePatient (Integer patientId){
        patientRepository.deletePatientById(patientId);
        //remove all the documents uploded by the patient
        List<Documents>patientDocuments=documentsRepository.getAll(patientId);
        for (Documents patientDocument :patientDocuments) {
            Integer id=patientDocument.getId();
            documentsRepository.removeDocumentById(id);
        }
    }

}
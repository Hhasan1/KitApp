package com.kitapp.service;

import org.springframework.stereotype.Service;

import com.kitapp.dto.ContactRequest;

@Service
public class ContactService {

    public boolean sendContactMessage(ContactRequest request) {

        System.out.println("Contact message received from: " + request.getFullName());
        System.out.println("Email: " + request.getEmail());
        System.out.println("Message: " + request.getMessage());
        
        return true;
    }
}

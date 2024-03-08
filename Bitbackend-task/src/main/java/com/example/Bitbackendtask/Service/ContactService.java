package com.example.Bitbackendtask.Service;

import com.example.Bitbackendtask.Enums.LinkPrecedence;
import com.example.Bitbackendtask.Model.Contact;
import com.example.Bitbackendtask.Repository.ContactRepository;
import com.example.Bitbackendtask.Request.ContactRequest;
import com.example.Bitbackendtask.Response.ContactInfo;
import com.example.Bitbackendtask.Response.ContactResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ContactService {


    @Autowired
    ContactRepository contactRepository;
     boolean update = false;


    public ContactResponse identify(ContactRequest contactRequest) {
        Optional<Contact> existingEmailId = contactRepository.findByEmail(contactRequest.getEmail());
        Optional<Contact> existingPhoneNumber = contactRepository.findByPhoneNumber(contactRequest.getPhoneNumber());
        Contact  primaryContact = null;
        Contact secondaryContact = null;
        List<Contact> secondaryContacts = new ArrayList<>();
        if(existingEmailId.isPresent() && existingPhoneNumber.isPresent()){
            if(!existingEmailId.get().getId().equals(existingPhoneNumber.get().getId())) {
                primaryContact = existingEmailId.get().getId() < existingPhoneNumber.get().getId() ?existingEmailId.get():existingPhoneNumber.get();
                secondaryContact = existingEmailId.get().getId() < existingPhoneNumber.get().getId() ?existingPhoneNumber.get():existingEmailId.get();
                secondaryContact.setLinkedContact(primaryContact);
                secondaryContact.setLinkPrecedence(LinkPrecedence.SECONDARY);
                secondaryContacts.add(secondaryContact);
                contactRepository.save(secondaryContact);
                secondaryContacts.add(secondaryContact);
            } else {
                primaryContact = existingEmailId.get();
            }
        } else if (existingEmailId.isPresent()){
                primaryContact = existingEmailId.get();
                 secondaryContact = updateContact(primaryContact,null,contactRequest.getPhoneNumber());
                secondaryContacts.add(secondaryContact);
        } else if(existingPhoneNumber.isPresent()){
                primaryContact = existingPhoneNumber.get();
                 secondaryContact = updateContact(primaryContact,contactRequest.getEmail(),null);
                secondaryContacts.add(secondaryContact);
        }
        else {
           primaryContact = createContact(contactRequest);
        }
        return buildResponse(primaryContact,secondaryContacts);
    }



    private ContactResponse buildResponse(Contact primaryContact, List<Contact> secondaryContact) {
            if (primaryContact ==null){
                return new ContactResponse(null);
            }

            Set<String> emails = new HashSet<>();
            emails.add(primaryContact.getEmail());

            Set<String> phoneNumbers = new HashSet<>();
            phoneNumbers.add(primaryContact.getPhoneNumber());

            List<Long> secondaryContactId = new ArrayList<>();
            if (!secondaryContact.isEmpty()) {
                secondaryContact.forEach(contact -> {
                    if(contact.getEmail() != null){
                        emails.add(contact.getEmail());
                    }

                    if(contact.getPhoneNumber() != null){
                        phoneNumbers.add(contact.getPhoneNumber());
                    }
                    secondaryContactId.add(contact.getId());
                });
            }
            ContactInfo contactInfo  =new ContactInfo(primaryContact.getId(),emails,phoneNumbers,secondaryContactId);
            ContactResponse response = new ContactResponse(contactInfo);
            return  response;

    }

    private Contact createContact(ContactRequest contactRequest) {

        Contact newContact = new Contact();
        newContact.setEmail(contactRequest.getEmail());
        newContact.setPhoneNumber(contactRequest.getPhoneNumber());
        newContact.setLinkPrecedence(LinkPrecedence.PRIMARY);
        newContact.setCreatedAt(LocalDateTime.now());
        newContact.setUpdatedAt(LocalDateTime.now());
        contactRepository.save(newContact);
        return  newContact;
    }
    private  Contact updateContact(Contact existingContact,String newEmail,String newPhoneNumber){
        Contact secondaryContact  = new Contact();
        if (newEmail != null && !newEmail.isEmpty() && !newEmail.equals(existingContact.getEmail())) {
            secondaryContact.setEmail(newEmail);
        }
        if (newPhoneNumber != null && !newPhoneNumber.isEmpty() && !newPhoneNumber.equals(existingContact.getPhoneNumber())) {
            secondaryContact.setPhoneNumber(newPhoneNumber);
        }
        secondaryContact.setLinkPrecedence(LinkPrecedence.SECONDARY);
        secondaryContact.setLinkedContact(existingContact);
        secondaryContact.setCreatedAt(LocalDateTime.now());
        secondaryContact.setUpdatedAt(LocalDateTime.now());
        contactRepository.save(secondaryContact);
        return secondaryContact;
    }
}

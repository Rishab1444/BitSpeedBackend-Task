package com.example.Bitbackendtask.Service;

import com.example.Bitbackendtask.Model.Contact;
import com.example.Bitbackendtask.Repository.ContactRepository;
import com.example.Bitbackendtask.Request.ContactRequest;
import com.example.Bitbackendtask.Response.ContactInfo;
import com.example.Bitbackendtask.Response.ContactResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContactService {

    ContactRepository contactRepository;


    public ContactResponse identify(ContactRequest contactRequest) {
        Contact  primaryContact = null;
        List<Contact> secondaryContact = new ArrayList<>();

        if (contactRequest.getEmail()!=null){
            primaryContact = contactRepository.findByEmail(contactRequest.getEmail());
        }

        if (primaryContact == null && contactRequest.getPhoneNumber()!=null){
            primaryContact = contactRepository.findByPhoneNumber(contactRequest.getPhoneNumber());
        }

        if (primaryContact !=null){
            List<Contact> allContacts = contactRepository.findAllByPhoneNumber(contactRequest.getPhoneNumber());
            Contact temp = primaryContact;
            secondaryContact = allContacts.stream().filter(contact -> !contact.equals(temp)).collect(Collectors.toList());
        }
        return  buildResponse(primaryContact,secondaryContact);
    }

    private ContactResponse buildResponse(Contact primaryContact, List<Contact> secondaryContact) {
            if (primaryContact ==null){
                return new ContactResponse(null);
            }
            List<String> emails = new ArrayList<>();
            emails.add(primaryContact.getEmail());

            List<String> phoneNumbers = new ArrayList<>();
            phoneNumbers.add(primaryContact.getPhoneNumber());

            List<Long> secondaryContactId = new ArrayList<>();
            secondaryContactId  = secondaryContact.stream().map(Contact::getId).collect(Collectors.toList());

            ContactInfo contactInfo  =new ContactInfo(primaryContact.getId(),emails,phoneNumbers,secondaryContactId);
            ContactResponse response = new ContactResponse(contactInfo);
            return  response;

    }
}

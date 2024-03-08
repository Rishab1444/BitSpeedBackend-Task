package com.example.Bitbackendtask.Controller;

import com.example.Bitbackendtask.Request.ContactRequest;
import com.example.Bitbackendtask.Response.ContactResponse;
import com.example.Bitbackendtask.Service.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
public class ContactController {


    private ContactService contactService;
    @Autowired
   public ContactController(ContactService contactService){
        this.contactService = contactService;
    }

    @PostMapping("/identify")
    public ResponseEntity<ContactResponse> identify(@RequestBody ContactRequest contactRequest){
        if (contactRequest == null){
            return ResponseEntity.badRequest().body(new ContactResponse(null));
        }
        ContactResponse response = contactService.identify(contactRequest);
        if(response ==null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(response);
    }

}

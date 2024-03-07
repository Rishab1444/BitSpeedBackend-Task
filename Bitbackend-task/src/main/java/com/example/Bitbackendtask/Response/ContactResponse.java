package com.example.Bitbackendtask.Response;

import lombok.Data;

import java.util.List;

@Data
public class ContactResponse {

  ContactInfo contactInfo;

  public ContactResponse(ContactInfo contactInfo){
      this.contactInfo = contactInfo;
  }

}

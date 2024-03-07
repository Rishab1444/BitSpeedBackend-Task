package com.example.Bitbackendtask.Repository;

import com.example.Bitbackendtask.Model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Contact findByEmail(String email);
    Contact findByPhoneNumber(String phoneNumber);
    List<Contact> findAllByPhoneNumber(String phoneNumber);
}
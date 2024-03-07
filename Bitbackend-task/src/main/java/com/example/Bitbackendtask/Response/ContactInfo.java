package com.example.Bitbackendtask.Response;

import lombok.Data;

import java.util.List;

@Data
public class ContactInfo {
    private Long primaryContatctId;
    private List<String> emails;
    private List<String> phoneNumbers;
    private List<Long> secondaryContactIds;

    public ContactInfo(Long primaryContatctId, List<String> emails, List<String> phoneNumbers, List<Long> secondaryContactIds) {
        this.primaryContatctId = primaryContatctId;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.secondaryContactIds = secondaryContactIds;
    }
}

package com.example.Bitbackendtask.Response;

import lombok.Data;

import javax.crypto.SecretKey;
import java.util.List;
import java.util.Set;

@Data
public class ContactInfo {
    private Long primaryContatctId;
    private Set<String> emails;
    private Set<String> phoneNumbers;
    private List<Long> secondaryContactIds;

    public ContactInfo(Long primaryContatctId, Set<String> emails, Set<String> phoneNumbers, List<Long> secondaryContactIds) {
        this.primaryContatctId = primaryContatctId;
        this.emails = emails;
        this.phoneNumbers = phoneNumbers;
        this.secondaryContactIds = secondaryContactIds;
    }
}

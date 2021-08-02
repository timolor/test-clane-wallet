package com.clane.wallet.service.impl;

import com.clane.wallet.service.MailService;
import org.springframework.stereotype.Service;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Service
public class MailServiceImpl implements MailService {
    @Override
    public boolean send(String from, String to, String subject, String htmlBody) {
        // TODO: 28/07/2021
        //  Implement email service
        return true;
    }
}

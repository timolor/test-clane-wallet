package com.clane.wallet.service;

/**
 * @author timolor
 * @created 28/07/2021
 */
public interface MailService {
    boolean send(String from, String to, String subject, String htmlBody);
}

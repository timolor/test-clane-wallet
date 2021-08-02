package com.clane.wallet.service.impl;

import com.clane.wallet.exception.ClaneBadRequestException;
import com.clane.wallet.exception.ClaneResourceNotFoundException;
import com.clane.wallet.model.Transaction;
import com.clane.wallet.model.User;
import com.clane.wallet.model.Wallet;
import com.clane.wallet.model.dto.AccountDto;
import com.clane.wallet.model.dto.AccountEnquiryDto;
import com.clane.wallet.model.dto.TopUpDto;
import com.clane.wallet.model.dto.TransferFundsDto;
import com.clane.wallet.model.response.Response;
import com.clane.wallet.model.response.ResponseCodes;
import com.clane.wallet.respository.TransactionRepository;
import com.clane.wallet.respository.UserRepository;
import com.clane.wallet.respository.WalletRepository;
import com.clane.wallet.service.WalletService;
import com.clane.wallet.util.Constants;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * @author timolor
 * @created 28/07/2021
 */
@Service
public class WalletServiceImpl implements WalletService {

    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletServiceImpl(
            UserRepository userRepository, WalletRepository walletRepository,
            TransactionRepository transactionRepository
    ) {
        this.userRepository = userRepository;
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Response<AccountDto> accountEnquiry(AccountEnquiryDto data) {
        if (data.getEmail() != null && !data.getEmail().isEmpty()) {
            Optional<User> user = userRepository.findByEmail(data.getEmail());
            if (user.isPresent()) {
                Optional<Wallet> wallet = walletRepository.findByUserId(user.get().getId());
                if (wallet.isPresent()) {
                    AccountDto accountDto = new AccountDto(wallet.get().getAccountNumber(), user.get().getFirstName(), user.get().getLastName(), user.get().getEmail());
                    return Response.build(accountDto);
                }
            }
        }

        if (data.getAccountNumber() != null && !data.getAccountNumber().isEmpty()) {
            Optional<Wallet> wallet = walletRepository.findByAccountNumber(data.getAccountNumber());
            if (wallet.isPresent()) {
                Optional<User> user = userRepository.findById(wallet.get().getUser().getId());
                if (user.isPresent()) {
                    AccountDto accountDto = new AccountDto(wallet.get().getAccountNumber(), user.get().getFirstName(), user.get().getLastName(), user.get().getEmail());
                    return Response.build(accountDto);
                }
            }
        }

        return Response.build(ResponseCodes.ENTITY_NOT_FOUND.getCode(), "Account not found", null);
    }

    @Transactional
    @Override
    public Response transferFunds(TransferFundsDto transferFundsDto, long userId) {

        Map<String, Wallet> accountMap = validateAccountDetails(transferFundsDto, userId);
        Wallet receiverWallet = accountMap.get(Constants.RECEIVER_MAP_KEY);
        Wallet senderWallet = accountMap.get(Constants.SENDER_MAP_KEY);

        //debit sender
        debitAccount(senderWallet, transferFundsDto.getAmount());
        logTransaction( transferFundsDto.getAmount(), "DR");

        //credit receiver
        creditAccount(receiverWallet, transferFundsDto.getAmount());
        logTransaction( transferFundsDto.getAmount(), "CR");

        return Response.build(null);
    }

    @Override
    public Response topUp(TopUpDto topUpDto) {
        //verify payment using ref
        if (!verifyPayment(topUpDto))
            throw new ClaneBadRequestException("Could not validate payment", ResponseCodes.BAD_REQUEST.getCode());
        Optional<Wallet> wallet = walletRepository.findById(topUpDto.getWalletId());
        if(!wallet.isPresent())
            throw new ClaneBadRequestException("Could not find corresponding account for user", ResponseCodes.BAD_REQUEST.getCode());

        creditAccount(wallet.get(), topUpDto.getAmount());

        return Response.build(wallet.get());
    }

    private Map<String, Wallet> validateAccountDetails(TransferFundsDto transferFundsDto, long userId) {
        Map<String, Wallet> accountMap = new HashMap<>();
        if (transferFundsDto.getAmount() == null || transferFundsDto.getAmount() < 1)
            throw new ClaneBadRequestException("Invalid amount", ResponseCodes.BAD_REQUEST.getCode());

        Optional<Wallet> optionalSenderWallet = walletRepository.findByUserId(userId);
        if (!optionalSenderWallet.isPresent())
            throw new ClaneResourceNotFoundException("Error retrieving your account details", ResponseCodes.ENTITY_NOT_FOUND.getCode());

        BigDecimal transferAmount = new BigDecimal(transferFundsDto.getAmount());
        if (optionalSenderWallet.get().getBalance().compareTo(transferAmount) < 0)
            throw new ClaneBadRequestException("Insufficient funds", ResponseCodes.BAD_REQUEST.getCode());

        Optional<Wallet> optionalReceiverWallet = walletRepository.findByAccountNumber(transferFundsDto.getAccountNumber());
        if (!optionalReceiverWallet.isPresent())
            throw new ClaneResourceNotFoundException("Error retrieving your account details", ResponseCodes.ENTITY_NOT_FOUND.getCode());

        accountMap.put(Constants.SENDER_MAP_KEY, optionalSenderWallet.get());
        accountMap.put(Constants.RECEIVER_MAP_KEY, optionalReceiverWallet.get());
        return accountMap;
    }

    private boolean verifyPayment(TopUpDto topUpDto) {
        // TODO: 31/07/2021
        //  call payment party to verify that made
        //  also validate amount

        return true;
    }

    private Wallet debitAccount(Wallet wallet, Long transferAmount) {
        BigDecimal amount = new BigDecimal(transferAmount);

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);

        return wallet;
    }

    private Wallet creditAccount(Wallet wallet, Long transferAmount) {
        BigDecimal amount = new BigDecimal(transferAmount);

        wallet.setBalance(wallet.getBalance().add(amount));
        walletRepository.save(wallet);

        return wallet;
    }

    private Transaction logTransaction(Long transferAmount, String type){
        BigDecimal amount = new BigDecimal(transferAmount);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setPaymentReference(String.format("%s|%s|%s","TRF", type, UUID.randomUUID().toString()));
//        transaction.setReceiver();
//        transaction.setSender();

        transactionRepository.save(transaction);
        return transaction;
    }
}

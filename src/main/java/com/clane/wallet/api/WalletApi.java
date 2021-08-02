package com.clane.wallet.api;

import com.clane.wallet.model.dto.AccountDto;
import com.clane.wallet.model.dto.AccountEnquiryDto;
import com.clane.wallet.model.dto.TopUpDto;
import com.clane.wallet.model.dto.TransferFundsDto;
import com.clane.wallet.model.response.Response;
import com.clane.wallet.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @author timolor
 * @created 28/07/2021
 */
@RestController
@RequestMapping("/api/v1/wallet")
public class WalletApi {
    private final WalletService walletService;

    @Autowired
    public WalletApi(WalletService walletService){
        this.walletService = walletService;
    }

    @PostMapping(path = "/account-enquiry", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response<AccountDto> accountEnquiry(@RequestBody @Valid AccountEnquiryDto accountEnquiryDto){
        return walletService.accountEnquiry(accountEnquiryDto);
    }

    @PostMapping(path = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response transferFunds(@RequestBody @Valid TransferFundsDto transferFundsDto, HttpServletRequest httpServletRequest){
        long userId = 0L;
        return walletService.transferFunds(transferFundsDto, userId);
    }

    @PostMapping(path = "/top-up", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response topUp(@RequestBody @Valid TopUpDto topUpDto){

        return walletService.topUp(topUpDto);
    }
}

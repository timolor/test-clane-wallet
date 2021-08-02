package com.clane.wallet.service;

import com.clane.wallet.model.dto.AccountDto;
import com.clane.wallet.model.dto.AccountEnquiryDto;
import com.clane.wallet.model.dto.TopUpDto;
import com.clane.wallet.model.dto.TransferFundsDto;
import com.clane.wallet.model.response.Response;

/**
 * @author timolor
 * @created 28/07/2021
 */
public interface WalletService {
    Response<AccountDto> accountEnquiry(AccountEnquiryDto accountEnquiryDto);
    Response transferFunds(TransferFundsDto transferFundsDto, long userId);

    Response topUp(TopUpDto topUpDto);
}

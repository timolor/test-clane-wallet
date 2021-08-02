package com.clane.wallet.service;

import com.clane.wallet.model.User;
import com.clane.wallet.model.dto.UpdateKycDto;
import com.clane.wallet.model.response.Response;

/**
 * @author timolor
 * @created 27/07/2021
 */
public interface UserService {
    Response register(User user);

    Response verify(String email, String code);

    Response updateKyc(UpdateKycDto updateKycDto);
}

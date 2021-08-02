package com.clane.wallet.service.impl;

import com.clane.wallet.exception.ClaneBadRequestException;
import com.clane.wallet.exception.ClaneResourceExistsException;
import com.clane.wallet.exception.ClaneResourceNotFoundException;
import com.clane.wallet.model.KycLevel;
import com.clane.wallet.model.KycName;
import com.clane.wallet.model.User;
import com.clane.wallet.model.dto.UpdateKycDto;
import com.clane.wallet.model.dto.UserRegisterDto;
import com.clane.wallet.model.response.Response;
import com.clane.wallet.model.response.ResponseCodes;
import com.clane.wallet.respository.KycLevelRepository;
import com.clane.wallet.respository.UserRepository;
import com.clane.wallet.service.MailService;
import com.clane.wallet.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author timolor
 * @created 27/07/2021
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final KycLevelRepository kycLevelRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MailService mailService;

    public UserServiceImpl(UserRepository userRepository, KycLevelRepository kycLevelRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder, MailService mailService){
        this.userRepository = userRepository;
        this.kycLevelRepository = kycLevelRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mailService = mailService;
    }

    @Override
    public Response register(User user) {
        if(userRepository.findByEmail(user.getEmail()).isPresent())
            throw new ClaneResourceExistsException(String.format("user with email %s already exist", user.getEmail()), ResponseCodes.RESOURCE_ALREADY_EXISTS.getCode());

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        mailService.send("no-reply@mail.com", user.getEmail(), "Email Verification", "message");

        return Response.build(null);
    }

    @Override
    public Response verify(String email, String code) {
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent())
            throw new ClaneResourceNotFoundException(String.format("user with email %s doesn't exist", email), ResponseCodes.ENTITY_NOT_FOUND.getCode());
        User concreteUser = user.get();
        //dummy verification
        concreteUser.setVerified(true);

        //set minimum kyc level
        Optional<KycLevel> kycLevel = kycLevelRepository.findByName(KycName.LEVEL_ONE.name());
        if(!kycLevel.isPresent())
            throw new ClaneResourceNotFoundException("KYC Levels not set", ResponseCodes.ENTITY_NOT_FOUND.getCode());

        concreteUser.setKycLevel(kycLevel.get());
        userRepository.save(user.get());
        return Response.build(null);
    }

    @Override
    public Response updateKyc(UpdateKycDto updateKycDto) {
        if(KycName.getByName(updateKycDto.getKycLevel()) == null)
            throw new ClaneBadRequestException("Invalid KYC level", ResponseCodes.BAD_REQUEST.getCode());

        boolean isSuccessful = updateUserDetailsBasedOnKycLevel(updateKycDto);
        if(isSuccessful)
            Response.build(ResponseCodes.BAD_REQUEST.getCode(), "Error updating user KYC Level", null);
        return Response.build(null);
    }

    private boolean updateUserDetailsBasedOnKycLevel(UpdateKycDto updateKycDto) {
        // TODO: 29/07/2021
        //  implementation of user update based on kyc level
        return true;
    }


}

package com.clane.wallet.api;

import com.clane.wallet.model.User;
import com.clane.wallet.model.dto.UpdateKycDto;
import com.clane.wallet.model.dto.UserRegisterDto;
import com.clane.wallet.model.response.Response;
import com.clane.wallet.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author timolor
 * @created 27/07/2021
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserApi {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserApi(UserService userService, ModelMapper modelMapper){
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response registerUser(@RequestBody @Valid UserRegisterDto userRegisterDto){
        return userService.register(modelMapper.map(userRegisterDto, User.class));
    }

    @GetMapping(path = "/verify", produces = MediaType.APPLICATION_JSON_VALUE)
    public Response verifyUser(@RequestParam("email") String email , @RequestParam("code") String code){
        return userService.verify(email, code);
    }

    @PostMapping(path = "/update-kyc", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Response updateKyc(@RequestBody @Valid UpdateKycDto updateKycDto){
        return userService.updateKyc(updateKycDto);
    }
}

package com.intorwacja.securitylabtask.service;

import com.intorwacja.securitylabtask.domain.User;
import com.intorwacja.securitylabtask.dto.UserRequest;
import com.intorwacja.securitylabtask.dto.UserResponse;
import com.intorwacja.securitylabtask.repository.UserRepository;
import com.intorwacja.securitylabtask.validation.UserValidatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserValidatorService userValidatorService;

    public UserResponse createUser(UserRequest userRequest) {

        userValidatorService.validate(userRequest);

        User user = User.builder()
                .username(userRequest.username())
                .email(userRequest.email())
                .build();

        userRepository.save(user);

        return new UserResponse(
                user.getUsername(),
                user.getEmail()
        );
    }
}

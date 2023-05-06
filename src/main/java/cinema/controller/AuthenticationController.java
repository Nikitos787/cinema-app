package cinema.controller;

import cinema.dto.request.UserLoginDto;
import cinema.dto.request.UserRegistrationDto;
import cinema.dto.response.UserResponseDto;
import cinema.exception.AuthenticationException;
import cinema.model.User;
import cinema.security.jwt.JwtTokenProvider;
import cinema.service.AuthenticationService;
import cinema.service.mapper.ResponseDtoMapper;
import java.util.Map;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {
    private final AuthenticationService authService;
    private final ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationService authService,
                                    ResponseDtoMapper<UserResponseDto, User> userDtoResponseMapper,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authService = authService;
        this.userDtoResponseMapper = userDtoResponseMapper;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public UserResponseDto register(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        User user = authService.register(userRegistrationDto.getEmail(),
                userRegistrationDto.getPassword());
        return userDtoResponseMapper.mapToDto(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserLoginDto userLoginDto)
            throws AuthenticationException {
        User user = authService.login(userLoginDto.getLogin(),
                userLoginDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRoles().stream()
                .map(r -> r.getRoleName().name())
                .collect(Collectors.toList()));
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}

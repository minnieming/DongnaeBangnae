package com.nawabali.nawabali.service;

import com.nawabali.nawabali.constant.UserRoleEnum;
import com.nawabali.nawabali.domain.User;
import com.nawabali.nawabali.dto.SignupDto;
import com.nawabali.nawabali.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public SignupDto.SignupResponseDto signup(SignupDto.SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String email = requestDto.getEmail();
        String nickname = requestDto.getNickname();
        String rawPassword = requestDto.getPassword();

        // 비밀번호 일치 검증
        if(!rawPassword.equals(requestDto.getConfirmPassword())){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        String password = passwordEncoder.encode(rawPassword);


        boolean certificated = requestDto.isCertificated();
        if(!certificated){
            throw new IllegalArgumentException("이메일 인증을 진행해주세요.");
        }
        // 관리자 권한 부여
        UserRoleEnum role = UserRoleEnum.USER;
        if(requestDto.isAdmin()){
            role = UserRoleEnum.ADMIN;
        }

        User user = User.builder()
                .username(username)
                .nickname(nickname)
                .email(email)
                .password(password)
                .role(role)
                .build();
        userRepository.save(user);
        User responseUser = userRepository.findByEmail(user.getEmail());
        return new SignupDto.SignupResponseDto(responseUser.getId());
    }
}
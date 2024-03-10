package com.david.service;

import com.david.common.CustomerException;
import com.david.common.HttpStatusEnum;
import com.david.dto.SystemUserDTO;
import com.david.entity.SystemUser;
import com.david.repository.SystemUserRepository;
import com.david.vo.SystemUserVO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    final PasswordEncoder passwordEncoder;
    final SystemUserRepository systemUserRepository;

    @Transactional
    public SystemUserDTO createUser(SystemUserVO userVO) {

        Optional<SystemUser> sysmUserOptional = systemUserRepository.findByAccount(userVO.getAccount());
        if (sysmUserOptional.isPresent()) {
            throw new CustomerException(HttpStatusEnum.USER_EXIST_ERROR);
        }

        String encodePwd = passwordEncoder.encode(userVO.getPassword());
        SystemUser systemUser = new SystemUser();
        systemUser.setName(userVO.getName());
        systemUser.setAccount(userVO.getAccount());
        systemUser.setPassword(encodePwd);
        systemUserRepository.save(systemUser);
        return SystemUserDTO.builder()
                .account(systemUser.getAccount())
                .username(systemUser.getName())
                .build();
    }

    public SystemUserDTO login(SystemUserVO systemUserVO) {
        SystemUser systemUser = systemUserRepository.findByAccount(systemUserVO.getAccount()).orElseThrow(() -> new CustomerException(HttpStatusEnum.ACCOUNT_PASSWORD_ERROR));
        if (!passwordEncoder.matches(systemUserVO.getPassword(), systemUser.getPassword())) {
            throw new CustomerException(HttpStatusEnum.ACCOUNT_PASSWORD_ERROR);
        }
        return SystemUserDTO
                .builder()
                .account(systemUser.getAccount())
                .username(systemUser.getName())
                .build();

    }

}

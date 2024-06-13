package com.enerkom.karyawan.service;


import com.enerkom.karyawan.entity.Users;
import com.enerkom.karyawan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;




    public Users registration(String email, String password){
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(passwordEncoder.encode(password));
        return userRepository.save(users);
    }

    public boolean findSameEmail(String email){
        return this.userRepository.getUserByEmail(email).isPresent();
    }

}

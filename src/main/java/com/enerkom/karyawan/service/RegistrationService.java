package com.enerkom.karyawan.service;

import com.enerkom.karyawan.entity.Roles;
import com.enerkom.karyawan.entity.Users;
import com.enerkom.karyawan.repository.RoleRepository;
import com.enerkom.karyawan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;


@Service
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public Set<Roles> getRoles(){
        var roleUser = this.roleRepository.findRolesByERole("ROLE_REGULAR_USER");
        Set<Roles> roles = new HashSet<>();
        if(!roleUser.isEmpty()){
            log.info("roles dalam getRole registration service:{}", roleUser);
            roles.addAll(roleUser);
        }

        return roles;
    }


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

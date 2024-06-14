package com.enerkom.karyawan.service;


import com.enerkom.karyawan.entity.Roles;
import com.enerkom.karyawan.entity.Users;
import com.enerkom.karyawan.enums.ERole;
import com.enerkom.karyawan.repository.RoleRepository;
import com.enerkom.karyawan.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Autowired
    private RoleRepository roleRepository;


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Roles> role(){

        List<Roles> roles = new ArrayList<>();

        Optional<Roles> optional = this.roleRepository.findRolesByERole(ERole.ROLE_EMPLOYEE.name());

        roles.add(optional.get());
        return roles;
    }


    public Users registration(String email, String password){
        Users users = new Users();
        users.setEmail(email);
        users.setPassword(passwordEncoder.encode(password));
        users.setRoles(this.role());
        return userRepository.save(users);
    }

    public boolean findSameEmail(String email){
        return this.userRepository.getUserByEmail(email).isPresent();
    }

}

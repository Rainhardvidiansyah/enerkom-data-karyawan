package com.enerkom.karyawan.service;

import com.enerkom.karyawan.entity.Employee;
import com.enerkom.karyawan.entity.Roles;
import com.enerkom.karyawan.entity.Users;
import com.enerkom.karyawan.repository.RoleRepository;
import com.enerkom.karyawan.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {

    @InjectMocks
    private RegistrationService registrationService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void registration() {

        var user = new Users();
        user.setId(1L);
        user.setEmail("rainhard@email");
        user.setEmployee(new Employee());
        user.setPassword("password");
        Set<Roles> role = new HashSet<Roles>();
        user.setRoles(role);

        Mockito.when(userRepository.save(any(Users.class))).thenReturn(user);

        Users registration = registrationService.registration("rainhard@email", "password");

        assertNotNull(registration);

        System.out.println(user.getRoles());
        Mockito.verify(userRepository).save(any(Users.class));
    }

    @Test
    void findSameEmail() {
        var user = new Users();
        user.setEmail("rainhard@email.com");

        Mockito.when(userRepository.getUserByEmail("rainhard@email.com"))
                .thenReturn(Optional.of(user));

        Boolean service = registrationService.findSameEmail("rainhard@email.com");

        assertEquals(true, service);
        assertNotNull(service);


        Mockito.verify(userRepository).getUserByEmail("rainhard@email.com");

    }
}
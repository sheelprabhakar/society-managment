package com.c4c.housing.impl;

import com.c4c.housing.core.entity.Role;
import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.repository.UserRepository;
import com.c4c.housing.core.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {
    public static final String MOBILE = "9898989898";
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        UserEntity entity = new UserEntity();
        entity.setId(1L);
        entity.setEmail("ssp@c4c.com");
        entity.setIntro("");
        entity.setMobile(MOBILE);
        entity.setProfile("");
        entity.setLastLogin(null);
        entity.setRegisteredAt(Calendar.getInstance());
        entity.setLastName("prabhakar");
        entity.setMiddleName("s");
        entity.setPasswordHash("");
        entity.setFirstName("sheel");
        List<Role> roles = new ArrayList<>();
        roles.add(Role.ROLE_ADMIN);
        entity.setRoles(roles);
        Mockito.when(userRepository.save(ArgumentMatchers.any()))
                .thenReturn(entity);

       Mockito.when(userRepository.findById(1L))
                .thenReturn(Optional.of(entity));
       Mockito.when(userRepository.findById(ArgumentMatchers.eq(100L)))
                .thenReturn(Optional.empty());

    }
    @Test
    public void test_save_ok(){
        UserEntity userEntity = this.userService.save(new UserEntity());
        assertEquals(userEntity.getId(),1);
        assertNull(userEntity.getLastLogin());
    }

    @Test
    public void test_get_ok(){
        UserEntity userEntity = this.userService.findById(1L);
        assertEquals(userEntity.getId(),1L);
        assertNull(userEntity.getLastLogin());

        userEntity = this.userService.findById(100L);
        assertNull(userEntity);
    }

    @Test
    public void test_update_ok(){
        UserEntity userEntity = this.userService.update(new UserEntity());
        assertEquals(userEntity.getId(),1);
        assertEquals(userEntity.getMobile(),MOBILE);
        assertNull(userEntity.getLastLogin());
    }
}

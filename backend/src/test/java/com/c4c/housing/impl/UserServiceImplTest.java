package com.c4c.housing.impl;

import com.c4c.housing.core.entity.UserEntity;
import com.c4c.housing.core.repository.UserRepository;
import com.c4c.housing.core.service.impl.UserServiceImpl;
import com.c4c.housing.utils.UserEntityHelper;
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

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    UserEntity userEntity1 = UserEntityHelper.getNew(UUID.randomUUID());
    @BeforeEach
    void setUp() {
        UserEntity entity = userEntity1;

        Mockito.when(userRepository.save(ArgumentMatchers.any()))
                .thenReturn(entity);

       Mockito.when(userRepository.findById(userEntity1.getId()))
                .thenReturn(Optional.of(entity));
       Mockito.when(userRepository.findById(ArgumentMatchers.eq(UUID.randomUUID())))
                .thenReturn(Optional.empty());

    }
    @Test
    public void test_save_ok(){
        UserEntity userEntity = this.userService.save(new UserEntity());
        assertEquals(userEntity.getId(),userEntity1.getId());
        assertNull(userEntity.getLastLogin());
    }

    @Test
    public void test_get_ok(){
        UserEntity userEntity = this.userService.findById(userEntity1.getId());
        assertEquals(userEntity.getId(),userEntity1.getId());
        assertNull(userEntity.getLastLogin());

        userEntity = this.userService.findById(UUID.randomUUID());
        assertNull(userEntity);
    }

    @Test
    public void test_update_ok(){
        UserEntity userEntity = this.userService.update(new UserEntity());
        assertEquals(userEntity.getId(),userEntity1.getId());
        assertEquals(userEntity.getMobile(),UserEntityHelper.MOBILE);
        assertNull(userEntity.getLastLogin());
    }
}

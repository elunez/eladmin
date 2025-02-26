package me.zhengjie.modules.system.service.impl;

import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.repository.UserRepository;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.modules.system.service.mapstruct.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        
        // Create a test user
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setAge(30);
        
        // Create a test userDto
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testuser");
        userDto.setAge(30);
        
        // Mock repository findById
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        
        // Mock mapper toDto
        when(userMapper.toDto(user)).thenReturn(userDto);
    }

    @Test
    public void testUpdateWithAgeField() throws Exception {
        // Create a user with updated age
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("testuser");
        updatedUser.setAge(35);
        
        // Call update method
        userService.update(updatedUser);
        
        // Verify that the age field is updated
        verify(userRepository).save(argThat(savedUser -> 
            savedUser.getAge() != null && savedUser.getAge() == 35
        ));
    }

    @Test
    public void testUpdateCenterWithAgeField() {
        // Create a user with updated age
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("testuser");
        updatedUser.setAge(40);
        
        // Call updateCenter method
        userService.updateCenter(updatedUser);
        
        // Verify that the age field is updated
        verify(userRepository).save(argThat(savedUser -> 
            savedUser.getAge() != null && savedUser.getAge() == 40
        ));
    }
}

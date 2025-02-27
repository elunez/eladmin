package me.zhengjie.modules.system.service.dto;

import me.zhengjie.modules.system.domain.User;
import me.zhengjie.modules.system.service.mapstruct.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserDtoTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testAgeFieldMapping() {
        // Create a new User instance with age field
        User user = new User();
        Integer expectedAge = 25;
        user.setAge(expectedAge);
        
        // Map User to UserDto
        UserDto userDto = userMapper.toDto(user);
        
        // Verify that the age field is correctly mapped
        assertEquals(expectedAge, userDto.getAge(), "Age field should be correctly mapped from User to UserDto");
        
        // Create a new UserDto instance with age field
        UserDto newUserDto = new UserDto();
        Integer newExpectedAge = 35;
        newUserDto.setAge(newExpectedAge);
        
        // Map UserDto to User
        User newUser = userMapper.toEntity(newUserDto);
        
        // Verify that the age field is correctly mapped
        assertEquals(newExpectedAge, newUser.getAge(), "Age field should be correctly mapped from UserDto to User");
    }
}

package me.zhengjie.modules.system.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testAgeField() {
        // Create a new User instance
        User user = new User();
        
        // Set the age field
        Integer expectedAge = 30;
        user.setAge(expectedAge);
        
        // Verify that the age field can be retrieved
        assertEquals(expectedAge, user.getAge(), "Age field should be correctly set and retrieved");
        
        // Test with null value
        user.setAge(null);
        assertNull(user.getAge(), "Age field should allow null values");
        
        // Test with boundary values
        user.setAge(0);
        assertEquals(0, user.getAge(), "Age field should accept 0 as a valid value");
        
        user.setAge(Integer.MAX_VALUE);
        assertEquals(Integer.MAX_VALUE, user.getAge(), "Age field should accept maximum integer value");
    }
}

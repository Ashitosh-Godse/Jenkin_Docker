package com.epam.restcontroller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.epam.service.UserService;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

  

    @Test
     void testLogin() throws Exception {
        String username = "john_doe";
        String password = "secretpassword";

        mockMvc.perform(get("/gym/users/login")
                .param("username", username)
                .param("password", password))
                .andExpect(status().isOk());

        verify(userService, times(1)).login(username, password);
    }

    @Test
     void testChangePassword() throws Exception {
        String username = "john_doe";
        String oldPassword = "oldpassword";
        String newPassword = "newpassword";

        mockMvc.perform(get("/gym/users/change-password")
                .param("username", username)
                .param("oldPassword", oldPassword)
                .param("newPassword", newPassword))
                .andExpect(status().isOk());

        verify(userService, times(1)).changePassword(username, oldPassword, newPassword);
    }

}

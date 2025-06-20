package com.example.demo.service.impl;

import com.example.demo.db.entity.User;
import com.example.demo.repository.IUserRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Mock private IUserRepository userRepository;
    @InjectMocks private UserServiceImpl userService;
    private AutoCloseable mocks;

    @BeforeEach void setUp() { mocks = MockitoAnnotations.openMocks(this); }
    @AfterEach void tearDown() throws Exception { mocks.close(); }

    @Test void getAllUsers() {
        var list = List.of(new User(1,"A","a@x.com",null));
        when(userRepository.findAll()).thenReturn(list);

        var res = userService.getAllUsers();
        assertEquals(1, res.size());
        verify(userRepository).findAll();
    }

    @Test void getUserById_found() {
        var u = new User(1,"A","a@x.com",null);
        when(userRepository.findById(1)).thenReturn(Optional.of(u));

        var res = userService.getUserById(1);
        assertEquals("A", res.getName());
    }

    @Test void getUserById_notFound() {
        when(userRepository.findById(9)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserById(9));
    }

    @Test void createUser_success() {
        var u = new User(0,"B","b@x.com",null);
        when(userRepository.existsByEmail("b@x.com")).thenReturn(false);
        when(userRepository.save(u)).thenReturn(u);
        var res = userService.createUser(u);
        assertSame(u, res);
    }

    @Test void createUser_duplicate() {
        var u = new User(0,"B","b@x.com",null);
        when(userRepository.existsByEmail("b@x.com")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> userService.createUser(u));
    }

    @Test void deleteUser() {
        doNothing().when(userRepository).deleteById(5);
        userService.deleteUser(5);
        verify(userRepository).deleteById(5);
    }
}

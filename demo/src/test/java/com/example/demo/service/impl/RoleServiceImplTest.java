package com.example.demo.service.impl;

import com.example.demo.db.entity.UserRole;
import com.example.demo.repository.IRoleRepository;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RoleServiceImplTest {

    @Mock private IRoleRepository repo;
    @InjectMocks private RoleServiceImpl service;
    private AutoCloseable mocks;

    @BeforeEach void init() { mocks = MockitoAnnotations.openMocks(this); }
    @AfterEach void close() throws Exception { mocks.close(); }

    @Test void getAllRoles() {
        var r = new UserRole(1,"X",null);
        when(repo.findAll()).thenReturn(List.of(r));
        var out = service.getAllRoles();
        assertEquals(1, out.size());
    }

    @Test void getRoleById_found() {
        var r = new UserRole(1,"X",null);
        when(repo.findById(1)).thenReturn(Optional.of(r));
        assertEquals("X", service.getRoleById(1).getName());
    }

    @Test void getRoleById_notFound() {
        when(repo.findById(2)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.getRoleById(2));
    }

    @Test void createRole() {
        var r = new UserRole(0,"New",null);
        when(repo.save(r)).thenReturn(r);
        assertSame(r, service.createRole(r));
    }

    @Test void updateRole() {
        var old = new UserRole(1,"A",null);
        when(repo.findById(1)).thenReturn(Optional.of(old));
        when(repo.save(any())).thenAnswer(i -> i.getArgument(0));
        var updated = service.updateRole(1, new UserRole(0,"B",null));
        assertEquals("B", updated.getName());
    }

    @Test void deleteRole() {
        doNothing().when(repo).deleteById(3);
        service.deleteRole(3);
        verify(repo).deleteById(3);
    }
}

package controller;

import com.nttdata.users.api.controller.UserController;
import com.nttdata.users.api.data.dto.UserDto;
import com.nttdata.users.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.nttdata.users.api.data.dto.UserDtoType.ADMIN;
import static com.nttdata.users.api.data.dto.UserDtoType.FORNECEDOR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Registrar novo usuário com sucesso")
    public void testRegisterUser_Success() {
        UserDto userDto = new UserDto(1L, "Fernanda", "123456789", "fernanda@example.com", "senha", ADMIN);
        when(userService.existsByCpf(userDto.getCpf())).thenReturn(false);
        when(userService.existsByEmail(userDto.getEmail())).thenReturn(false);
        when(userService.registerUser(userDto)).thenReturn(userDto);
        ResponseEntity<?> response = userController.registerUser(userDto);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    @DisplayName("Registrar usuário com CPF duplicado")
    public void testRegisterUser_DuplicateCPF() {
        UserDto userDto = new UserDto(1L, "Fernanda", "123456789", "fernanda@example.com", "senha", FORNECEDOR);
        when(userService.existsByCpf(userDto.getCpf())).thenReturn(true);
        ResponseEntity<?> response = userController.registerUser(userDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("CPF já está em uso", response.getBody());
    }

    @Test
    @DisplayName("Registrar usuário com e-mail duplicado")
    public void testRegisterUser_DuplicateEmail() {
        UserDto userDto = new UserDto(1L, "Fernanda", "123456789", "fernanda@example.com", "senha", null);
        when(userService.existsByCpf(userDto.getCpf())).thenReturn(false);
        when(userService.existsByEmail(userDto.getEmail())).thenReturn(true);
        ResponseEntity<?> response = userController.registerUser(userDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("E-mail já está em uso", response.getBody());
    }

    @Test
    @DisplayName("Buscar usuário por e-mail existente")
    public void testGetUserByEmail_ExistingUser() {
        String email = "john@example.com";
        UserDto userDto = new UserDto(1L, "Fernanda", "123456789", "fernanda@example.com", "senha", null);
        when(userService.findByEmail(email)).thenReturn(userDto);
        ResponseEntity<?> response = userController.getUserByEmail(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userDto, response.getBody());
    }

    @Test
    @DisplayName("Buscar usuário por e-mail inexistente")
    public void testGetUserByEmail_NonExistingUser() {
        String email = "naoexiste@example.com";
        when(userService.findByEmail(email)).thenReturn(null);
        ResponseEntity<?> response = userController.getUserByEmail(email);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}

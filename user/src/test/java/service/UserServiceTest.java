package service;

import com.nttdata.users.api.data.dto.UserDto;
import com.nttdata.users.api.data.entity.UserEntity;
import com.nttdata.users.api.data.mapper.UserMapper;
import com.nttdata.users.api.exception.UserRegistrationException;
import com.nttdata.users.api.repository.UserRepository;
import com.nttdata.users.api.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static com.nttdata.users.api.data.dto.UserDtoType.ADMIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Registrar novo usuário com sucesso")
    public void testRegisterUser_Success() {
        UserDto userDto = new UserDto(1L, "Fernanda", "56586869030", "fernanda@example.com", "password", ADMIN);
        UserEntity userEntity = new UserEntity(1L, "Fernanda", "56586869030", "fernanda@example.com", "senha", null);
        when(userMapper.toEntity(userDto)).thenReturn(userEntity);
        when(passwordEncoder.encode(userDto.getSenha())).thenReturn("senha");
        when(userRepository.existsByCpf(userDto.getCpf())).thenReturn(false);
        when(userRepository.save(userEntity)).thenReturn(userEntity);
        when(userMapper.toDto(userEntity)).thenReturn(userDto);
        UserDto result = userService.registerUser(userDto);
        assertNotNull(result);
        assertEquals(userDto, result);
        verify(userRepository, times(1)).existsByCpf(userDto.getCpf());
        verify(userRepository, times(1)).save(userEntity);
        verify(userMapper, times(1)).toDto(userEntity);
    }

    @Test
    @DisplayName("Falha ao registrar usuário com CPF duplicado")
    public void testRegisterUser_DuplicateCPF() {
        UserDto userDto = new UserDto(1L, "Fernanda", "56586869030", "fernanda@example.com", "password", null);
        when(userRepository.existsByCpf(userDto.getCpf())).thenReturn(true);
        assertThrows(UserRegistrationException.class, () -> userService.registerUser(userDto));
        verify(userRepository, times(1)).existsByCpf(userDto.getCpf());
        verify(userRepository, never()).save(any());
        verify(userMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Buscar usuário por e-mail existente")
    public void testFindByEmail_ExistingUser() {
        String email = "john@example.com";
        UserEntity userEntity = new UserEntity(1L, "Fernanda", "56586869030", "fernanda@example.com", "senha", null);
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(userEntity));
        when(userMapper.toDto(userEntity)).thenReturn(new UserDto(1L, "Fernanda", "56586869030", "fernanda@example.com", null, null));
        UserDto result = userService.findByEmail(email);
        assertNotNull(result);
        assertEquals("Fernanda", result.getNome());
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, times(1)).toDto(userEntity);
    }

    @Test
    @DisplayName("Buscar usuário por e-mail inexistente")
    public void testFindByEmail_NonExistingUser() {
        String email = "naoexistet@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());
        UserDto result = userService.findByEmail(email);
        assertNull(result);
        verify(userRepository, times(1)).findByEmail(email);
        verify(userMapper, never()).toDto(any());
    }

    @Test
    @DisplayName("Verificar existência de usuário por e-mail")
    public void testExistsByEmail() {
        String email = "fernanda@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(new UserEntity()));
        boolean result = userService.existsByEmail(email);
        assertTrue(result);
        verify(userRepository, times(1)).findByEmail(email);
    }
}

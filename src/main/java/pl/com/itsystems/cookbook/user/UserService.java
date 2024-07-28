package pl.com.itsystems.cookbook.user;

import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.com.itsystems.cookbook.user.dto.UserCredentialsDto;
import pl.com.itsystems.cookbook.user.dto.UserRegistrationDto;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private static final String USER_ROLE = "USER";
    private static final String ADMIN_AUTHORITY = "ROLE_ADMIN";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<UserCredentialsDto> findCredentialsByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(UserCredentialsDtoMapper::map);
    }

    public List<String> findAllUserEmails() {
        return userRepository.findAllUsersByRoles_Name(USER_ROLE)
                .stream()
                .map(User::getEmail)
                .toList();
    }

    @Transactional
    public void deleteUserByEmail(String email) {
        if (isCurrentUserAdmin()) {
            userRepository.deleteByEmail(email);
        }
    }

    @Transactional
    public void register(UserRegistrationDto registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        String passwordHash = passwordEncoder.encode(registration.getPassword());
        user.setPassword(passwordHash);
        Optional<UserRole> userRole = userRoleRepository.findByName(USER_ROLE);
        userRole.ifPresentOrElse(
                role -> user.getRoles().add(role),
                () -> {
                    throw new NoSuchElementException();
                }
        );
        userRepository.save(user);
    }

    private boolean isCurrentUserAdmin() {
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(ADMIN_AUTHORITY));
    }

    public List<User> findAllWithoutCurrentUser() {
        Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findAll()
                .stream()
                .filter(user -> !user.getEmail().equals(currentUser.getName()))
                .collect(Collectors.toList());
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Set<UserRole> findAllUserRoles() {
        return userRoleRepository.findAll();
    }

    public void save(User user, Set<UserRole> userRoles) {
        user.setRoles(userRoles);
        userRepository.save(user);
    }

    public void save(User user, String newPassword) {
        userRepository.findById(user.getId()).ifPresent(
                u -> {
                    u.setEmail(user.getEmail());
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    String passwordHash = passwordEncoder.encode(newPassword);
                    System.out.println("Hasło: " + passwordHash);
                    //u.setPassword(passwordEncoder.encode(user.getPassword()));
                    u.setPassword(passwordHash);
                    userRepository.save(u);
                    System.out.println(u.toString());
                }
        );
    }
}
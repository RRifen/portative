package mirea.pracs.productcrud.service;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import mirea.pracs.productcrud.dto.auth.SignUpRequestDto;
import mirea.pracs.productcrud.entity.User;
import mirea.pracs.productcrud.entity.enums.UserRole;
import mirea.pracs.productcrud.exceptions.ForbiddenException;
import mirea.pracs.productcrud.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public List<User> findByUsername(String username){
    return userRepository.findByUsername(username);
  }

  public User findByPrincipal(Principal principal){
    List<User> users = userRepository.findByUsername(principal.getName());
    if (users.isEmpty()) {
      throw new ForbiddenException("Bad token");
    }
    return users.getFirst();
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    List<User> users = findByUsername(username);
    if (users.isEmpty()) {
      throw new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username));
    } else {
      User user = users.getFirst();
      Collection<String> userRoles = List.of(user.getRole().toString());
      List<GrantedAuthority> grantedAuthoritiesRoles = userRoles.stream()
              .map(SimpleGrantedAuthority::new)
              .collect(Collectors.toList());
      return new org.springframework.security.core.userdetails.User(
              user.getUsername(),
              user.getPassword(),
              grantedAuthoritiesRoles
      );
    }

  }

  @Transactional
  public User createNewUser(SignUpRequestDto signUpRequestDto) {
    User user = new User();
    user.setUsername(signUpRequestDto.getUsername());
    user.setPassword(passwordEncoder.encode(signUpRequestDto.getPassword()));
    user.setRole(UserRole.USER);
    userRepository.save(user);
    return user;
  }
}

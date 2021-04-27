package ru.romasini.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.romasini.persist.User;
import ru.romasini.persist.UserRepository;
import ru.romasini.service.dto.UserDto;


import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import java.util.List;
import java.util.stream.Collectors;

@Stateless
@PermitAll
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @EJB
    private UserRepository userRepository;

    @RolesAllowed({"ADMIN"})
    @TransactionAttribute
    public void saveOrUpdate(UserDto user) {
        User saved = userRepository.saveOrUpdate(new User(user));
        user.setId(saved.getId());
    }

    @RolesAllowed({"ADMIN"})
    @TransactionAttribute
    public void delete(long id) {
        userRepository.delete(id);
    }

    @TransactionAttribute
    public UserDto findById(int id) {
        return new UserDto(userRepository.findById(id));
    }

    @TransactionAttribute
    public boolean existsById(int id) {
        return userRepository.findById(id) != null;
    }

    @TransactionAttribute
    public List<UserDto> getAllUsers() {
        return userRepository.getAllUsers().stream()
                .map(UserDto::new)
                .collect(Collectors.toList());
    }

}
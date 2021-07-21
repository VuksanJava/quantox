package com.quantox.service;

import com.quantox.api.v1.dto.UserDtoRequest;
import com.quantox.entity.*;
import com.quantox.repository.MaschineRepository;
import com.quantox.repository.RoleRepository;
import com.quantox.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private MaschineRepository maschineRepository;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
            User user = userRepository.findByUsername(username);
            if (user == null) throw new UsernameNotFoundException("User not exist");
            return UserPrinciple.build(user);
    }

    @Transactional
    public UserDtoRequest saveUser(UserDtoRequest userDtoRequest) {
        try {
           User user =  userMapper.toUser(userDtoRequest);

            Set<Role> set = roleRepository.findAll().stream().filter(role -> String.valueOf(role.getRoleName()).equals("ROLE_USER")
            ).collect(Collectors.toSet());
            user.setRoles(set);
            userRepository.save(user);
            Maschine maschine = new Maschine();
            maschine.setActive(true);
            maschine.setCreateAt(LocalDateTime.now());
            UUID uuid = UUID.randomUUID();
            String uid  = String.valueOf(uuid);
            maschine.setUid(uid);
            maschine.setStatus(Status.STOPPED);
            maschine.setCreatedBy(user);
            maschineRepository.save(maschine);
            return new UserDtoRequest(user.getUsername(), user.getPassword(), user.getFirstName(), user.getLastName());

        }catch (Exception e){
            e.printStackTrace();
            throw new UsernameNotFoundException("The user not insert ");
        }

    }

    private String username (){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails)principal).getUsername();
    }
}
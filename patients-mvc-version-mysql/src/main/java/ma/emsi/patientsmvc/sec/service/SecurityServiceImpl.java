package ma.emsi.patientsmvc.sec.service;

import groovy.util.logging.Slf4j;
import lombok.AllArgsConstructor;
import ma.emsi.patientsmvc.sec.entities.AppRole;
import ma.emsi.patientsmvc.sec.entities.AppUser;
import ma.emsi.patientsmvc.sec.repositories.AppRoleRepository;
import ma.emsi.patientsmvc.sec.repositories.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class SecurityServiceImpl implements SecurityService {
    private AppUserRepository appUserRepository;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;



    @Override
    public AppUser saveNewUser(String username, String password, String verifiedPassword) {
        if(!password.equals(verifiedPassword)) throw new RuntimeException("Passwords don't match");
        String hashedPassword = passwordEncoder.encode(password);
        AppUser user = new AppUser();
        user.setUserId(UUID.randomUUID().toString());
        user.setUsername(username);
        user.setPassword(hashedPassword);
        user.setActive(true);
        AppUser savedUser = appUserRepository.save(user);
        return user;
    }

    @Override
    public AppRole saveNewRole(String roleName, String description) {
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        if(appRole != null) throw new RuntimeException("Role " + roleName + " already exists");
        AppRole role = new AppRole();
        role.setRoleName(roleName);
        role.setDescription(description);
        AppRole savedAppRole = appRoleRepository.save(role);
        return savedAppRole;
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser user = appUserRepository.findByUsername(username);
        if(user == null) throw new RuntimeException("user  not found");
        AppRole role = appRoleRepository.findByRoleName(roleName);
        if(role == null) throw new RuntimeException("role not found");
        user.getAppRoles().add(role);
    }

    @Override
    public void removeRoleFromUser(String username, String roleName) {
        AppUser user = appUserRepository.findByUsername(username);
        if(user == null) throw new RuntimeException("user  not found");
        AppRole role = appRoleRepository.findByRoleName(roleName);
        if(role == null) throw new RuntimeException("role not found");
        user.getAppRoles().remove(role);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
        return appUserRepository.findByUsername(username);
    }
}

package org.isp.web.configuration;

import org.isp.domain.users.entities.Privilege;
import org.isp.domain.users.entities.Role;
import org.isp.repositories.user_repositories.PrivilegeRepository;
import org.isp.repositories.user_repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class InitialRoleLoader implements ApplicationListener<ContextRefreshedEvent> {

    private boolean isSetup = false;

    private RoleRepository roleRepository;

    private PrivilegeRepository privilegeRepository;

    @Autowired
    public InitialRoleLoader(RoleRepository roleRepository,
                             PrivilegeRepository privilegeRepository) {
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(isSetup) {
            return;
        }

        Privilege read = createPrivilegeIfNotFound("READ");
        Privilege write = createPrivilegeIfNotFound("WRITE");

        List<Privilege> adminPrivileges = Arrays.asList(read, write);

        createRoleIfNotFound("ROLE_USER", Arrays.asList(read));
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);

        this.isSetup = true;
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String privilegeName) {
        Privilege prvlg = this.privilegeRepository.findByName(privilegeName);
        if(prvlg == null) {
            prvlg = new Privilege();
            prvlg.setName(privilegeName);
            this.privilegeRepository.saveAndFlush(prvlg);
        }
        return prvlg;
    }

    @Transactional
    Role createRoleIfNotFound(String roleName, Collection<Privilege> privileges) {
        Role role = this.roleRepository.findByName(roleName);
        if(role == null) {
            role = new Role();
            role.setName(roleName);
            role.setPrivileges(privileges);
            this.roleRepository.saveAndFlush(role);
        }
        return role;
    }
}

package org.isp.applications.users.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {
    UserApplication findByEmail(String email);

    UserApplication findByUsername(String username);
}


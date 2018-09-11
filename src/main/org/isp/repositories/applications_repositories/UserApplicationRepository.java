package org.isp.repositories.applications_repositories;

import org.isp.domain.applications.user_applications.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserApplicationRepository extends JpaRepository<UserApplication, Long> {
    UserApplication findByEmail(String email);

    UserApplication findByUsername(String username);

    List<UserApplication> findAllByUsername(String username);
}


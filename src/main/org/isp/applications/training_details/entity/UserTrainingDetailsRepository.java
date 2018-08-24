package org.isp.applications.training_details.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrainingDetailsRepository extends JpaRepository<UserTrainingDetails, Long> {
    UserTrainingDetails findByUsername(String username);
}

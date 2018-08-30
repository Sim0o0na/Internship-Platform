package org.isp.repositories.applications_repositories;

import org.isp.domain.applications.training_details.UserTrainingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTrainingDetailsRepository extends JpaRepository<UserTrainingDetails, Long> {
    UserTrainingDetails findByUsername(String username);
}

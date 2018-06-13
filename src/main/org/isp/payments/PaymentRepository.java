package org.isp.payments;

import org.isp.payments.models.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Payment findByTaskId(String taskId);
    Page<Payment> findAllByTaskAssigneeUsername(Pageable pageable, String username);
    List<Payment> findAllByTaskAssigneeUsername(String username);
    List<Payment> findAllByDueDateAfterAndDueDateBeforeAndUserUsername(Date dateFrom, Date dateAfter, String username);
}

package org.isp.applications.users.entity;

import org.isp.applications.training_details.entity.UserTrainingDetails;

import javax.persistence.*;

@Entity
@Table(name = "user_applications")
public class UserApplication {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "is_approved")
    @Enumerated(EnumType.STRING)
    private UserApplicationStatus status;

    @OneToOne(targetEntity = UserTrainingDetails.class)
    private UserTrainingDetails userTrainingDetails;

    public UserApplication() {}

    public UserApplication(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public UserApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(UserApplicationStatus status) {
        this.status = status;
    }

    public UserTrainingDetails getUserTrainingDetails() {
        return userTrainingDetails;
    }

    public void setUserTrainingDetails(UserTrainingDetails userTrainingDetails) {
        this.userTrainingDetails = userTrainingDetails;
    }


}

package org.isp.users.models.dtos;

import org.hibernate.validator.constraints.Length;
import org.isp.users.models.entities.Role;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

public abstract class UserDto {
    protected String firstName;

    protected String lastName;

    @Min(4)
    @Max(10)
    @NotNull
    protected String username;

    @Length(min = 6)
    @NotNull
    private String password;

    @Email
    @NotNull
    protected String email;

    protected String facebook;

    protected String instagram;

    protected String skype;

    protected MultipartFile profilePhoto;

    protected String profilePhotoLocation;

    private Set<Role> privileges;

    public UserDto() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Role> privileges) {
        this.privileges = privileges;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public MultipartFile getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(MultipartFile profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getProfilePhotoLocation() {
        return profilePhotoLocation;
    }

    public void setProfilePhotoLocation(String profilePhotoLocation) {
        this.profilePhotoLocation = profilePhotoLocation;
    }
}
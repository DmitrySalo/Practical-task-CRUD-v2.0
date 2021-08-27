package com.example.webappcrud.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @Setter(AccessLevel.PROTECTED)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 30, message = "Name length should be between 2 and 30 characters!")
    private String name;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater than 0!")
    @Max(value = 125, message = "Age should not be greater than 100!")
    private int age;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email should not be empty!")
    @Email(message = "Email should be valid!")
    @Size(min = 6, max = 46, message = "Email length should be between 6 and 46 characters!")
    private String email;

    @Column(name = "login", unique = true)
    @NotBlank(message = "Enter the login!")
    @Size(min = 4, max = 16, message = "Login length should be between 4 and 16 characters!")
    private String login;

    @Column(name = "password")
    @NotBlank(message = "Enter the password!")
    @Size(min = 4, max = 80, message = "Password length should be between 4 and 80 characters!")
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_ID"),
            inverseJoinColumns = @JoinColumn(name = "role_ID"))
    private Set<Role> roles;

    public User(String name, int age, String email, String login, String password, Set<Role> roles) {
        super();
        this.name = name;
        this.age = age;
        this.email = email;
        this.login = login;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if ((o == null) || (this.getClass() != o.getClass())) {
            return false;
        }
        User user = (User) o;
        return id == user.id
                && age == user.age
                && name.equals(user.name)
                && email.equals(user.email)
                && login.equals(user.login)
                && password.equals(user.password)
                && roles.equals(user.roles);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hash(id, name, age, email, login, password, roles);
    }
}
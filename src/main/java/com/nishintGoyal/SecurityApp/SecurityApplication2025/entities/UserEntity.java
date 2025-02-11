package com.nishintGoyal.SecurityApp.SecurityApplication2025.entities;

import jakarta.persistence.*;
import lombok.Builder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.List;

@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String name;

    private String password;


    public void setId(Long id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // 🔹 Default constructor required by JPA
    public UserEntity() {}

    // 🔹 Private constructor for builder
    private UserEntity(UserEntityBuilder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.name = builder.name;
        this.password = builder.password;
    }

    // ✅ Custom Builder Class
    public static class UserEntityBuilder {
        private Long id;
        private String email;
        private String name;
        private String password;

        public UserEntityBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public UserEntityBuilder email(String email) {
            this.email = email;
            return this;
        }

        public UserEntityBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserEntityBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserEntity build() {
            return new UserEntity(this);
        }
    }

    // ✅ Static method to access the builder
    public static UserEntityBuilder builder() {
        return new UserEntityBuilder();
    }

    // 🔹 Getters
    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}

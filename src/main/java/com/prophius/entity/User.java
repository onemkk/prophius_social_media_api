package com.prophius.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @NotNull(message = "Password is required")
    @Column(name = "password", nullable = false)
    private String password;

    @NotNull(message = "Username is required")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "account_non_expired", nullable = false, columnDefinition = "default true")
    private boolean accountNonExpired;

    @Column(name = "account_non_locked", nullable = false, columnDefinition = "default true")
    private boolean accountNonLocked;

    @Column(name = "credentials_non_expired", columnDefinition = "default true")
    private boolean credentialsNonExpired;

    @Column(name = "enabled", nullable = false, columnDefinition = "default true")
    private boolean enabled;

    @Column(name = "verified", nullable = false, columnDefinition = "default false")
    private boolean verified;

    @ManyToMany(cascade= CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = {
            @JoinColumn(name = "user_id", referencedColumnName = "id"),
    }, inverseJoinColumns = {
            @JoinColumn(name = "role_id", referencedColumnName = "id")
    })
    private Set<Role> roles;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_liked_posts",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "post_id")
    )
    private Set<Post> likedPosts = new HashSet<>();
}

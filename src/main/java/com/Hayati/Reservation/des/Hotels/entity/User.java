package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.Set;

@Data
@Entity
@Table(name = "users", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"email"})
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private long id_user;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Email
    @Pattern(regexp = "^[\\w.+\\-]+@gmail\\.com$", message = "Email must be a valid gmail address")
    @Column(nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(nullable = false)
    private String password;

    @NotBlank
    @Pattern(regexp = "\\d{8}", message = "Phone number must be 8 digits")
    @Column(nullable = false, unique = true)
    private String numerodetelephone;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reservation> reservations;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id_user"),
        inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id_role"))
    private Set<Role> roles;
}

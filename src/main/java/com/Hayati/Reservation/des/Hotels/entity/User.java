package com.Hayati.Reservation.des.Hotels.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Gardez 'id' comme nom de colonne
    private long id;
    @Column(nullable = false)
    private boolean isEmailVerified = false;
    @Column(nullable = false)
    private String name;

@Email(message = "Veuillez fournir une adresse email valide")
@Pattern(regexp = "^[\\w-\\.]+@gmail\\.com$", message = "L'email doit être une adresse Gmail valide")
@Size(max = 254, message = "L'email ne peut pas dépasser 254 caractères")
@Column(nullable = false, unique = true)
private String email;


    @Column(name = "verification_code", length = 64)
    private String verificationCode;

    @Column(nullable = false)
    private String password;
  ;

    @Column(name = "is_enabled")
    private Boolean isEnabled;


    @Pattern(regexp = "^\\d{8}$", message = "Phone number must be exactly 8 digits")
    @Column(nullable = true, unique = true)
    private String numerodetelephone;
    

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    // Méthodes pour obtenir les rôles
    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    // Méthode pour ajouter un rôle
    public void addRole(String role) {
        roles.add(role);
    }

    // Setters en chaîne (facultatif)
    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setNumerodetelephone(String numerodetelephone) {
        this.numerodetelephone = numerodetelephone;
        return this;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> (GrantedAuthority) () -> role)
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return isEmailVerified;  // Le compte est activé uniquement après la vérification de l'email
    }

    // Générer le code de vérification
    public void generateVerificationCode() {
        this.verificationCode = java.util.UUID.randomUUID().toString();
    }
}

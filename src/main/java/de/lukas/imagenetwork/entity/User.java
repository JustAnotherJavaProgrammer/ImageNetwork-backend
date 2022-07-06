package de.lukas.imagenetwork.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
public class User implements UserDetails {
    private @Id @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;
    private String name;
    private String email;
    private String nickname;
    @JsonIgnore
    private String password; // TODO: Don't save password as plaintext
    @Column(name="createdat")
    private Instant createdAt;
    @Column(name="updatedat")
    private Instant updatedAt;

//    @Type(type = "org.hibernate.type.NumericBooleanType")
//    @Column(columnDefinition = "deleted BOOLEAN DEFAULT FALSE", nullable = false)
//    @ColumnDefault(value = "FALSE")
    private Boolean deleted = false;

    @JsonIgnore
    @Override
    public String getPassword() {
        return "{bcrypt}" + password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return getName();
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true; // TODO: expiring credentials
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return !getDeleted();
    }
}

package de.lukas.imagenetwork.entity;

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
    private String password; // TODO: Don't save password as plaintext
    @Column(name="createdat")
    private Instant createdAt;
    @Column(name="updatedat")
    private Instant updatedAt;

//    @Type(type = "org.hibernate.type.NumericBooleanType")
//    @Column(columnDefinition = "deleted BOOLEAN DEFAULT FALSE", nullable = false)
//    @ColumnDefault(value = "FALSE")
    private Boolean deleted = false;

    @Override
    public String getPassword() {
        if(password.startsWith("{bcrypt}"))
            return password;
        return "{noop}" + password;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @Override
    public String getUsername() {
        return getName();
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
        return true; // TODO: expiring credentials
    }

    @Override
    public boolean isEnabled() {
        return !getDeleted();
    }
}

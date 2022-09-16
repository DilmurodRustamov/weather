package uz.marta.weather.security;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uz.marta.weather.entity.User;
import uz.marta.weather.ref.UserRole;

import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private Long userId;
    private String phoneNumber;
    private String password;
    private UserRole userRole;

    public SecurityUser(User user) {
        this.userId = user.getId();
        this.userRole = user.getUserRole();
        this.phoneNumber = user.getPhoneNumber();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + userRole.name()));
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return phoneNumber;
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
}
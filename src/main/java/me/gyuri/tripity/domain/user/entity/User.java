package me.gyuri.tripity.domain.user.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.gyuri.tripity.domain.user.dto.ProviderType;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "nickname", nullable = false, unique = true)
    private String nickname;

    @Column(name = "password")
    private String password;

    @Column(name = "image")
    private String image;

    @Enumerated(EnumType.STRING)
    private ProviderType providerType;

    @Builder
    public User(String email, String nickname, String password, ProviderType providerType) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.providerType = providerType;
    }

    // 권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자의 id를 반환 (고유한 값)
    @Override
    public String getUsername() {
        return email;
    }

    // 사용자의 패스워드 반환
    @Override
    public String getPassword() {
        return password;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    public User update(String nickname) {
        this.nickname = nickname;

        return this;
    }
}

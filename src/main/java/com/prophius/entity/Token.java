package com.prophius.entity;

import com.prophius.enums.TokenType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Token is required")
    @Column(name = "token", nullable = false, columnDefinition = "varchar(500)")
    private String token;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Token type is required")
    @Column(name = "token_type", nullable = false, columnDefinition = "varchar(500)")
    private TokenType tokenType;

    @Column(name = "revoked", nullable = false)
    private boolean revoked;

    @ManyToOne
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Token token = (Token) o;
        return getId() != null && Objects.equals(getId(), token.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

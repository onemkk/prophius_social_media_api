package com.prophius.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long post_id;

    @Column(name ="post_content", nullable = false, columnDefinition = "TEXT")
    private String postContent;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn (name = "user_id", referencedColumnName = "id")
    private User user;

    @ManyToMany(mappedBy = "likedPosts")
    @ToString.Exclude
    private Set<User> likedByUsers = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Post post = (Post) o;
        return getPost_id() != null && Objects.equals(getPost_id(), post.getPost_id());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

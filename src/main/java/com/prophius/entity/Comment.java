package com.prophius.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;


@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name = "comment_content", nullable = false, columnDefinition = "TEXT")
    private String commentContent;

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn (name = "post_id", referencedColumnName = "post_id")
    private Post post;

    private long likesCount = 0;

    @ManyToOne
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Comment comment = (Comment) o;
        return getCommentId() != null && Objects.equals(getCommentId(), comment.getCommentId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
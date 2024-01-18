package com.prophius.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {
    private String comment_body;

    public String getComment_body() {
        return comment_body;
    }

    public void setComment_body(String comment_body) {
        this.comment_body = comment_body;
    }
}

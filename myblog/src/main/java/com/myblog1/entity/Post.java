package com.myblog1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
 private long id;
    @Column(name="title",nullable = false,unique = true)
    @NotNull
    @Size(min=5, message = "Title cannot be Smaller than 5 characters")
 private String title;
    @Column(name="description",nullable = false,unique = true)
    @NotNull
    @Size(min=5, message = "description cannot be Smaller than 5 characters")
 private String description;
    @Column(name="content",nullable = false,unique = true)
    @NotNull
    @Size(min=5, message = "Title cannot be Smaller than 5 characters")
 private String content;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    List<Comment> comments = new ArrayList<>();
}

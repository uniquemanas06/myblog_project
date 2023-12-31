package com.myblog1.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private long id;
    @NotEmpty
    @Size(min=5,message = "post title should have at least 5 characters")
    private String title;

    @NotEmpty
    private String description;

    @NotEmpty
    private String content;
}

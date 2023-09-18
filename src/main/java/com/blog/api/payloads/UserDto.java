package com.blog.api.payloads;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
    private int id;
    @NotEmpty
    @Size(min = 4, message = "Must be min of 4 chars.")
    private String name;
    @Email(message = "Email not valid")
    @JsonIgnore
    private String email;
    @NotEmpty
    @Size(min = 8, message = "password must be less than 8 and a max of 10", max = 10)
    @JsonIgnore
    private String password;
    @NotEmpty
    private String about;
}

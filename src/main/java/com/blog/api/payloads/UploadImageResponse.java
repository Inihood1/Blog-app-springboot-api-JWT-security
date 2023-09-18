package com.blog.api.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UploadImageResponse {
    private String message;
    private boolean success;
    private String fileName;
    private String filePath;

}




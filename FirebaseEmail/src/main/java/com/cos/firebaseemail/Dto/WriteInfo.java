package com.cos.firebaseemail.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WriteInfo {
    private String title;
    private String content;
    private String publisher;
}
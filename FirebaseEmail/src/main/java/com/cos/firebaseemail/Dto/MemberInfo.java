package com.cos.firebaseemail.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberInfo {
    private String name;
    private String phoneNumber;
    private String birthDay;
    private String address;
}


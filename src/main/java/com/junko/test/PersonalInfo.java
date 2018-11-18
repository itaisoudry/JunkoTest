package com.junko.test;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonalInfo {

    private String fullName;

    private int age;

    private String gender;

    private String address;
}

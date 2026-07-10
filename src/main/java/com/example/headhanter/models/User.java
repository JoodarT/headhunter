package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter

public class User {


   private long id;
   private String name;
   private String sureName;
   private Integer age;
   private String email;
   private String password;
   private String phoneNumber;
   private String avatar;

   private AccountType accountType;


}

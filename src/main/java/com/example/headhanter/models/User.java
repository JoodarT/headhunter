package com.example.headhanter.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
   private Long id;
   private String email;
   private String password;
   private String name;
   private AccountType accountType;
   private List<ContactsInfo> contacts;
   private String avatarFileName;
}
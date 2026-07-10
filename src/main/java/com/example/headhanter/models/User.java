package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;
   private String surname;
   private Integer age;

   @Column(unique = true, nullable = false)
   private String email;

   @Column(nullable = false)
   private String password;

   @Column(name = "phone_number")
   private String phoneNumber;

   private String avatar;

   @Enumerated(EnumType.STRING)
   @Column(name = "account_type", nullable = false)
   private AccountType accountType;
}
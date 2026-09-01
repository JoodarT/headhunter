package com.example.headhanter.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String name;

   @Column(nullable = false, unique = true)
   private String email;

   @Column(nullable = false)
   private String password;

   private String phone;

   @Column(name = "avatar_url")
   private String avatarUrl;

   @ManyToOne(fetch = FetchType.EAGER)
   @JoinColumn(name = "role_id")
   private RoleEntity role;

   @Column(name = "reset_password_token")
   private String resetPasswordToken;

   @Column(name = "reset_password_token_expiry")
   private LocalDateTime resetPasswordTokenExpiry;

   @OneToMany(mappedBy = "employer")
   @Builder.Default

   private List<Vacancy> vacancies =new ArrayList<>();

   @OneToMany(mappedBy = "user")
   @Builder.Default

   private List<Resume> resumes =new ArrayList<>();


}
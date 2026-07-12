//package com.example.headhanter.models;
//
//import jakarta.persistence.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "messages")
//@Getter @Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class Message {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "responded_applicants_id", nullable = false)
//    private RespondedApplicant respondedApplicant;
//
//    @Column(nullable = false, columnDefinition = "TEXT")
//    private String content;
//
//    private LocalDateTime timestamp;
//}
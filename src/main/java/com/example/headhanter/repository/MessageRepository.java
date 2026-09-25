package com.example.headhanter.repository;

import com.example.headhanter.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRespondedApplicantIdOrderByTimestampAsc(Long respondedApplicantId);
}

package com.example.headhanter.repository;

import com.example.headhanter.models.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContactTypeRepository extends JpaRepository<ContactType, Long> {

    Optional<ContactType> findByTypeName(String typeName);
}

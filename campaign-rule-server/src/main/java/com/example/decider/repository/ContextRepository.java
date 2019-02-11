package com.example.decider.repository;

import com.example.decider.model.Context;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ContextRepository extends JpaRepository<Context, UUID> {
}

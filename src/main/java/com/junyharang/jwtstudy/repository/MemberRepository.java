package com.junyharang.jwtstudy.repository;

import com.junyharang.jwtstudy.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByUsername(String username);
} // interface 끝

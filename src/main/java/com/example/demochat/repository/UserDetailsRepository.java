package com.example.demochat.repository;

import com.example.demochat.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository <Users, String> {
}

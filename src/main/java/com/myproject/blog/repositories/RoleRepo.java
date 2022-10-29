package com.myproject.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myproject.blog.models.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}

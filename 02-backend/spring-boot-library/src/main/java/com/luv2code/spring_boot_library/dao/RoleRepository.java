package com.luv2code.spring_boot_library.dao;

import com.luv2code.spring_boot_library.entity.AppRole;
import com.luv2code.spring_boot_library.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByRoleName(AppRole name);

}

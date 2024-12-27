package com.luv2code.spring_boot_library.dao;

import com.luv2code.spring_boot_library.entity.Checkout;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckoutRepository extends JpaRepository<Checkout, Long> {

    Checkout findByUserEmailAndBookId(String userEmail, Long bookId);
}

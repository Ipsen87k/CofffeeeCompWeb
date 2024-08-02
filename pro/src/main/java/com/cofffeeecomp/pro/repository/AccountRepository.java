package com.cofffeeecomp.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cofffeeecomp.pro.entity.Account;

public interface AccountRepository extends JpaRepository<Account,Integer>{
    
}

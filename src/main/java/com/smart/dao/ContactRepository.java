package com.smart.dao;

import com.smart.entities.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact,Integer> {


    //Pagination
    //give pageable to current page and per page
    @Query("from Contact as c where c.user.id=:userId")
    public Page<Contact> findContactsByUser(@Param("userId") int userId, Pageable pageable);
}

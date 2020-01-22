package com.example.task.repository;

import com.example.task.model.NumbersData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NumbersDataRepository extends JpaRepository<NumbersData,Integer> {
}

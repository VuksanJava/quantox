package com.quantox.repository;

import com.quantox.entity.Maschine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MaschineRepository extends JpaRepository<Maschine, Long> {
}

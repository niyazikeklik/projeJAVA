package com.example.dataAcces;

import com.example.entities.concretes.Language;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface LanguageDao extends JpaRepository<Language,Integer> {
    List<Language> findByCvId(int id);
}

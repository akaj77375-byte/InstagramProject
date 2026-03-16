package com.example.instagramproject.repo;

import com.example.instagramproject.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ImageRepo extends JpaRepository<Image, Long> {}

package com.backend.pouch;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PouchRepository extends CustomPouchRepository, JpaRepository<Pouch, Long> {

}

package com.thoughtworks.flywayDbMigration.repository;

import com.thoughtworks.flywayDbMigration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {


}

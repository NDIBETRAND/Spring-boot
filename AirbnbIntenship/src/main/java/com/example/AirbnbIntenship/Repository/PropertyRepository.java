package com.example.AirbnbIntenship.Repository;

import com.example.AirbnbIntenship.model.Property;
import com.example.AirbnbIntenship.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property,Long> {


    List<Property> findAllByOwner(User user);
}

package com.example.AirbnbIntenship.service;

import com.example.AirbnbIntenship.model.Property;
import com.example.AirbnbIntenship.model.User;

import java.util.List;
import java.util.Optional;

public interface PropertyService {
    Property createProperty(Property property, User user) throws Exception;

    List<Property> getAllProperties() throws  Exception;

    Optional<Property> getPropertyById(Long propertyId)throws Exception;

    Property updateProperty(Property updatedProperty,Long propertyId)throws Exception;

    String deleteProperty(Long UserId,Long propertyId)throws  Exception;

    List<Property> getUserProperties(Long userId)throws  Exception;


}

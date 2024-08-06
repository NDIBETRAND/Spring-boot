package com.example.AirbnbIntenship.serviceImpl;

import com.example.AirbnbIntenship.Repository.PropertyRepository;
import com.example.AirbnbIntenship.model.Property;
import com.example.AirbnbIntenship.model.User;
import com.example.AirbnbIntenship.service.PropertyService;
import com.example.AirbnbIntenship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProperServiceImpl implements PropertyService {
    @Autowired
    private PropertyRepository propertyRepository;

    @Autowired
    private UserService userService;

    @Override
    public Property createProperty(Property property, User user) throws Exception {
         Property createdProperty=new Property();
         createdProperty.setOwner(user);
         createdProperty.setPricePerNight(property.getPricePerNight());
         createdProperty.setNumberOfBedrooms(property.getNumberOfBedrooms());
         createdProperty.setNumberOfBathrooms(property.getNumberOfBathrooms());
         createdProperty.setAvailable(property.isAvailable());
         createdProperty.setDrinkAllowed(property.isDrinkAllowed());
         createdProperty.setAvailable(property.isAvailable());
         createdProperty.setMaxCheckoutTimeInNights(property.getMaxCheckoutTimeInNights());
         createdProperty.setExtraCharges(property.getExtraCharges());

         Property savedProperty=propertyRepository.save(createdProperty);

        return savedProperty;
    }

    @Override
    public List<Property> getAllProperties() throws Exception {

        return propertyRepository.findAll();
    }

    @Override
    public Optional<Property> getPropertyById(Long propertyId) throws Exception {
        return propertyRepository.findById(propertyId);
    }

    @Override
    public Property updateProperty(Property updatedProperty,Long propertyId) throws Exception {
        Optional<Property> property1 = propertyRepository.findById(propertyId);
        if (property1.isPresent()) {

            Property property = property1.get();

            property.setPricePerNight(updatedProperty.getPricePerNight());
            property.setNumberOfBedrooms(updatedProperty.getNumberOfBedrooms());
            property.setNumberOfBathrooms(updatedProperty.getNumberOfBathrooms());
            property.setAvailable(updatedProperty.isAvailable());
            property.setDrinkAllowed(updatedProperty.isDrinkAllowed());
            property.setAvailable(updatedProperty.isAvailable());
            property.setMaxCheckoutTimeInNights(updatedProperty.getMaxCheckoutTimeInNights());
            property.setExtraCharges(updatedProperty.getExtraCharges());

            return propertyRepository.save(property);
        }
        return null;
    }

    @Override
    public String deleteProperty(Long userId,Long propertyId) throws Exception {
      Optional<User> user=userService.findUserById(userId);
      Optional<Property> property=propertyRepository.findById(propertyId);

      if (user.equals(property.get().getOwner())){
          propertyRepository.delete(property.get());
          return "Property has been deleted";
      }

        return " You are not authorized to delate this property";
    }

    @Override
    public List<Property> getUserProperties(Long userId) throws Exception {
        Optional<User> user1=userService.findUserById(userId);
        User user=user1.get();
        List<Property> properties=propertyRepository.findAllByOwner(user);
        return properties;
    }
}

package com.example.AirbnbIntenship.Controller;

import com.example.AirbnbIntenship.model.Property;
import com.example.AirbnbIntenship.model.User;
import com.example.AirbnbIntenship.service.PropertyService;
import com.example.AirbnbIntenship.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController

public class PropertyController {

    @Autowired
    private PropertyService propertyService;
     private UserService userService;

    @PostMapping("api/properties")
    public ResponseEntity<Property> createProperty(@RequestBody Property property,
                                                   @RequestParam("userId") Long userId,
                                                   @RequestHeader("Authorization") String jwt) {
        try {
            User user = new User();
            user.setId(userId);
            Property createdProperty = propertyService.createProperty(property, user);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdProperty);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("api/properties")
    public ResponseEntity<List<Property>> getAllProperties() {
        try {
            List<Property> properties = propertyService.getAllProperties();
            return ResponseEntity.ok(properties);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("api/properties{propertyId}")
    public ResponseEntity<Property> getPropertyById(@PathVariable Long propertyId,
                                                    @RequestHeader("Authorization") String jwt) {
        try {
            Optional<Property> property = propertyService.getPropertyById(propertyId);
            return property.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("api/properties/{propertyId}")
    public ResponseEntity<Property> updateProperty(@RequestBody Property updatedProperty,
                                                   @PathVariable Long propertyId,
                                                   @RequestHeader("Authorization") String jwt) {
        try {
            Property property = propertyService.updateProperty(updatedProperty, propertyId);
            if (property != null) {
                return ResponseEntity.ok(property);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("api/properties/{propertyId}")
    public ResponseEntity<String> deleteProperty(
                                                 @PathVariable Long propertyId,
                                                 @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserProfileByJwt(jwt);
        Long userId=user.getId();
        try {
            String result = propertyService.deleteProperty(userId, propertyId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("api/users/{userId}/properties")
    public ResponseEntity<List<Property>> getUserProperties(@PathVariable Long userId,
                                                            @RequestHeader("Authorization") String jwt) {
        try {
            List<Property> userProperties = propertyService.getUserProperties(userId);
            return ResponseEntity.ok(userProperties);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
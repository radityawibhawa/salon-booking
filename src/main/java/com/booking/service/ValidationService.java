package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Person;

public class ValidationService {
    // Buatlah function sesuai dengan kebutuhan
    public static void validateInput(){

    }
    

     public static Customer validateCustomerId(String customerId, List<Person> listPerson) {
        for (Person person : listPerson) {
            if (person instanceof Customer && person.getId().equals(customerId)) {
                return (Customer) person;
            }
        }
        return null;
    }
}

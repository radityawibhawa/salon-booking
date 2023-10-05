package com.booking.service;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ReservationRepository;
import com.booking.repositories.ServiceRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class ReservationService {
    public static void createReservation() {
        Scanner scanner = new Scanner(System.in);
    
        List<Person> listPerson = PersonRepository.getAllPerson();
        List<Service> listService = ServiceRepository.getAllService();
    
        Customer customer = null;
        Employee employee = null;
        List<Service> services = new ArrayList<>();
        
        while (customer == null) {
            System.out.println("Silahkan masukkan Customer ID : ");
            String customerId = scanner.nextLine();
            customer = ValidationService.validateCustomerId(customerId, listPerson);
            if (customer == null) {
                System.out.println("Customer yang dicari tidak tersedia.");
            }
        }

        while (employee == null) {
            System.out.println("Silahkan masukkan Employee ID : ");
            String employeeId = scanner.nextLine();
            for (Person person : listPerson) {
                if (person instanceof Employee && person.getId().equals(employeeId)) {
                    employee = (Employee) person;
                    break;
                }
            }
            if (employee == null) {
                System.out.println("Employee yang dicari tidak tersedia.");
            }
        }
    
        Set<Service> selectedServices = new HashSet<>();
        String anotherService = "Y";
        while (anotherService.equalsIgnoreCase("Y")) {
            System.out.println("Silahkan masukkan Service ID  : ");
            String serviceId = scanner.nextLine();
            Service service = null;
            for (Service serv : listService) {
                if (serv.getServiceId().equals(serviceId)) {
                    if (selectedServices.contains(serv)) {
                        System.out.println("Service sudah dipilih");
                        service = null;
                    } else {
                        service = serv;
                        services.add(service);
                        selectedServices.add(service);
                    }
                    break;
                }
            }
            if (service == null) {
                System.out.println("Service yang dicari tidak tersedia.");
            } else {
                if (services.size() == listService.size()) {
                    System.out.println("Semua service sudah dipilih.");
                    break;
                }
                boolean validInput = false;
                while (!validInput) {
                    System.out.println("Ingin pilih service yang lain (Y/T)?");
                    anotherService = scanner.nextLine();
                    if (anotherService.equalsIgnoreCase("Y") || anotherService.equalsIgnoreCase("T")) {
                        validInput = true;
                    } else {
                        System.out.println("Error: Invalid input. Please enter either 'Y' or 'T'.");
                    }
                }
            }
        }
    
        String reservationId = "";
        boolean idExists = true;
        
        while (idExists) {
            System.out.println("Silahkan masukkan Reservation ID : ");
            reservationId = scanner.nextLine();
        
            idExists = false;
            for (Reservation existingReservation : ReservationRepository.getAllReservation()) {
                if (existingReservation.getReservationId().equals(reservationId)) {
                    System.out.println("Error: A reservation with this ID already exists. Please enter a unique ID.");
                    idExists = true;
                    break;
                }
            }
        }
    
        if (customer != null && employee != null && !services.isEmpty()) {
            Reservation reservation = new Reservation(reservationId, customer, employee, services, "In process");
            
            String action = "";
            while (!action.equalsIgnoreCase("Finish") && !action.equalsIgnoreCase("Cancel")) {
                System.out.println("Selesaikan reservasi:");
                action = scanner.nextLine();
                
                if (action.equalsIgnoreCase("Finish")) {
                    ReservationRepository.getAllReservation().add(reservation);
                    System.out.println("Reservasi dengan id " + reservation.getReservationId() + " sudah Finish");
                    double totalBookingPrice = services.stream().mapToDouble(Service::getPrice).sum();
                    if (customer.getMember().getMembershipName().equals("Silver")) {
                        totalBookingPrice *= 0.95;  
                    } else if (customer.getMember().getMembershipName().equals("Gold")) {
                        totalBookingPrice *= 0.90;  
                    }
                    reservation.setReservationPrice(totalBookingPrice);
                    System.out.format("Total Biaya Booking : Rp%,.2f%n", totalBookingPrice);
                } else if (action.equalsIgnoreCase("Cancel")) {
                    reservation.setWorkstage("Cancel");
                    System.out.println("Reservasi dengan id " + reservation.getReservationId() + " sudah Cancel");
                } else {
                    System.out.println("Error: Invalid action. Please enter either 'Finish' or 'Cancel'.");
                }
            }
        } else {
            System.out.println("Error: One or more IDs do not exist.");
        }
    }

    public static void getCustomerByCustomerId(){
        
    }

    public static void editReservationWorkstage() {
        Scanner scanner = new Scanner(System.in);
        List<Reservation> reservations = ReservationRepository.getAllReservation();
        Reservation reservation = null;
    
        while (reservation == null) {
            System.out.println("Silahkan Masukkan Reservation Id:");
            String reservationId = scanner.nextLine();
    
            for (Reservation res : reservations) {
                if (res.getReservationId().equals(reservationId)) {
                    if (!res.getWorkstage().equalsIgnoreCase("In Process")) {
                        System.out.println("Reservation yang dicari sudah selesai");
                        return;
                    } else {
                        reservation = res;
                        break;
                    }
                }
            }
    
            if (reservation == null) {
                System.out.println("Reservation yang dicari tidak tersedia");
            }
        }
    
        String action = "";
        while (!action.equalsIgnoreCase("Finish") && !action.equalsIgnoreCase("Cancel")) {
            System.out.println("Selesaikan reservasi:");
            action = scanner.nextLine();
    
            if (action.equalsIgnoreCase("Finish")) {
                reservation.setWorkstage("Finish");
                System.out.println("Reservasi dengan id " + reservation.getReservationId() + " sudah Finish");
            } else if (action.equalsIgnoreCase("Cancel")) {
                reservation.setWorkstage("Cancel");
                System.out.println("Reservasi dengan id " + reservation.getReservationId() + " sudah Cancel");
            } else {
                System.out.println("Error: Invalid action. Please enter either 'Finish' or 'Cancel'.");
            }
        }
    }
    // Silahkan tambahkan function lain, dan ubah function diatas sesuai kebutuhan
}

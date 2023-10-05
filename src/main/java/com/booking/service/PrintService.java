package com.booking.service;

import java.util.List;

import com.booking.models.Customer;
import com.booking.models.Employee;
import com.booking.models.Person;
import com.booking.models.Reservation;
import com.booking.models.Service;
import com.booking.repositories.PersonRepository;
import com.booking.repositories.ReservationRepository;

public class PrintService {
    public static void printMenu(String title, String[] menuArr){
        int num = 1;
        System.out.println(title);
        for (int i = 0; i < menuArr.length; i++) {
            if (i == (menuArr.length - 1)) {   
                num = 0;
            }
            System.out.println(num + ". " + menuArr[i]);   
            num++;
        }
    }

    public static String printServices(List<Service> serviceList){
        String result = "";
        // Bisa disesuaikan kembali
        for (Service service : serviceList) {
            result += service.getServiceName() + ", ";
        }
        return result;
    }

    // Function yang dibuat hanya sebgai contoh bisa disesuaikan kembali
    public void showRecentReservation(List<Reservation> reservationList){
        int num = 1;
        System.out.println("+================================================================================================+");
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+================================================================================================+");
        for (Reservation reservation : reservationList) {
            if (reservation.getWorkstage().equalsIgnoreCase("Waiting") || reservation.getWorkstage().equalsIgnoreCase("In process")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
            }
        }
        System.out.println("+================================================================================================+");
    }

    public static void showAllCustomer() {
        List<Person> listPerson = PersonRepository.getAllPerson();
        int no = 1;
        System.out.println("+====================================================================+");
        System.out.printf("| %-6s | %-7s | %-15s | %-15s | %-11s |\n",
                "No.", "ID", "Nama", "Alamat", "Membership");
        System.out.println("+====================================================================+");
    
        for (Person person : listPerson) {
            if (person instanceof Customer) {
                Customer customer = (Customer) person;
                String membership = customer.getMember() != null ? customer.getMember().getMembershipName() : "-";
                System.out.printf("| %-6s | %-7s | %-15s | %-15s | %-11s |\n",
                        no, customer.getId(), customer.getName(), customer.getAddress(), membership);
                no++;
            }
        }
    
        System.out.println("+====================================================================+");
    }

    public static void showAllEmployee(){
        List<Person> listPerson = PersonRepository.getAllPerson();
        int no = 1;
        System.out.println("+====================================================================+");
        System.out.printf("| %-6s | %-7s | %-15s | %-15s | %-11s |\n",
        "No.", "ID", "Nama", "Alamat", "Pengalaman");
        System.out.println("+====================================================================+");
        for (Person person : listPerson) {
            if (person instanceof Employee) {
                Employee employee = (Employee) person;
                System.out.printf("| %-6s | %-7s | %-15s | %-15s | %-11d |\n",
                        no, employee.getId(), employee.getName(), employee.getAddress(), employee.getExperience());
                no++;
            }
        }
        System.out.println("+====================================================================+");
    }

    public static void showHistoryReservation() {
        List<Reservation> reservations = ReservationRepository.getAllReservation();
        int num = 1;
        double totalProfit = 0;
    
        System.out.println("+================================================================================================+");
        System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                "No.", "ID", "Nama Customer", "Service", "Biaya Service", "Pegawai", "Workstage");
        System.out.println("+================================================================================================+");
    
        for (Reservation reservation : reservations) {
            if (reservation.getWorkstage().equalsIgnoreCase("Finish") || reservation.getWorkstage().equalsIgnoreCase("Cancel")) {
                System.out.printf("| %-4s | %-4s | %-11s | %-15s | %-15s | %-15s | %-10s |\n",
                        num, reservation.getReservationId(), reservation.getCustomer().getName(), printServices(reservation.getServices()), reservation.getReservationPrice(), reservation.getEmployee().getName(), reservation.getWorkstage());
                num++;
                if (reservation.getWorkstage().equalsIgnoreCase("Finish")) {
                    totalProfit += reservation.getReservationPrice();
                }
            }
        }
    
        System.out.println("+================================================================================================+");
        System.out.printf("Total Keuntungan: Rp%,.2f%n", totalProfit);
    }
}

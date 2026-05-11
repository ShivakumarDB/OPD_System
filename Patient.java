package opd;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Patient registered in the OPD Management System.
 */
public class Patient {

    // ── Fields ──────────────────────────────────────────────────────────────
    private static int idCounter = 1000;

    private final String patientId;
    private String name;
    private int age;
    private String gender;
    private String contactNumber;
    private String address;
    private String bloodGroup;
    private final LocalDate registrationDate;
    private final List<Appointment>   appointments;
    private final List<Consultation>  consultations;

    // ── Constructors ─────────────────────────────────────────────────────────

    /** Full constructor */
    public Patient(String name, int age, String gender,
                   String contactNumber, String address, String bloodGroup) {
        this.patientId        = "P" + (++idCounter);
        this.name             = name;
        this.age              = age;
        this.gender           = gender;
        this.contactNumber    = contactNumber;
        this.address          = address;
        this.bloodGroup       = bloodGroup;
        this.registrationDate = LocalDate.now();
        this.appointments     = new ArrayList<>();
        this.consultations    = new ArrayList<>();
    }

    /** Basic constructor (address and blood-group optional) */
    public Patient(String name, int age, String gender, String contactNumber) {
        this(name, age, gender, contactNumber, "N/A", "Unknown");
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String    getPatientId()       { return patientId; }
    public String    getName()            { return name; }
    public int       getAge()             { return age; }
    public String    getGender()          { return gender; }
    public String    getContactNumber()   { return contactNumber; }
    public String    getAddress()         { return address; }
    public String    getBloodGroup()      { return bloodGroup; }
    public LocalDate getRegistrationDate(){ return registrationDate; }
    public List<Appointment>  getAppointments()  { return appointments; }
    public List<Consultation> getConsultations() { return consultations; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setName(String name)               { this.name = name; }
    public void setAge(int age)                    { this.age  = age;  }
    public void setContactNumber(String num)       { this.contactNumber = num; }
    public void setAddress(String address)         { this.address = address; }
    public void setBloodGroup(String bloodGroup)   { this.bloodGroup = bloodGroup; }

    // ── Business Methods ──────────────────────────────────────────────────────
    public void addAppointment(Appointment a)  { appointments.add(a);  }
    public void addConsultation(Consultation c){ consultations.add(c); }

    /** Display compact summary */
    public void displaySummary() {
        System.out.printf("  %-8s %-20s %3d  %-6s  %-13s  %s%n",
            patientId, name, age, gender, contactNumber, bloodGroup);
    }

    /** Display full profile */
    public void displayFullProfile() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.printf ("║          PATIENT PROFILE  %-22s║%n", "");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf ("║  Patient ID    : %-31s║%n", patientId);
        System.out.printf ("║  Name          : %-31s║%n", name);
        System.out.printf ("║  Age / Gender  : %d / %-27s║%n", age, gender);
        System.out.printf ("║  Blood Group   : %-31s║%n", bloodGroup);
        System.out.printf ("║  Contact       : %-31s║%n", contactNumber);
        System.out.printf ("║  Address       : %-31s║%n", address);
        System.out.printf ("║  Registered On : %-31s║%n", registrationDate);
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf ("║  Total Appointments : %-27d║%n", appointments.size());
        System.out.printf ("║  Total Consultations: %-27d║%n", consultations.size());
        System.out.println("╚══════════════════════════════════════════════════╝");

        if (!consultations.isEmpty()) {
            System.out.println("\n  ── Medical History ──────────────────────────────");
            for (Consultation c : consultations) {
                c.displaySummary();
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Patient[%s | %s | Age:%d | %s]",
            patientId, name, age, gender);
    }
}

package opd;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Doctor working in the OPD.
 */
public class Doctor {

    // ── Fields ──────────────────────────────────────────────────────────────
    private static int idCounter = 200;

    private final String doctorId;
    private String name;
    private String specialization;
    private String qualification;
    private String contactNumber;
    private String availableDays;   // e.g. "Mon, Wed, Fri"
    private String consultingHours; // e.g. "09:00 - 13:00"
    private int    consultationFee;
    private final List<Appointment> appointments;

    // ── Constructors ─────────────────────────────────────────────────────────

    /** Full constructor */
    public Doctor(String name, String specialization, String qualification,
                  String contactNumber, String availableDays,
                  String consultingHours, int consultationFee) {
        this.doctorId         = "D" + (++idCounter);
        this.name             = name;
        this.specialization   = specialization;
        this.qualification    = qualification;
        this.contactNumber    = contactNumber;
        this.availableDays    = availableDays;
        this.consultingHours  = consultingHours;
        this.consultationFee  = consultationFee;
        this.appointments     = new ArrayList<>();
    }

    /** Basic constructor */
    public Doctor(String name, String specialization, String qualification) {
        this(name, specialization, qualification, "N/A", "Mon-Fri", "09:00-17:00", 500);
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String getDoctorId()        { return doctorId; }
    public String getName()            { return name; }
    public String getSpecialization()  { return specialization; }
    public String getQualification()   { return qualification; }
    public String getContactNumber()   { return contactNumber; }
    public String getAvailableDays()   { return availableDays; }
    public String getConsultingHours() { return consultingHours; }
    public int    getConsultationFee() { return consultationFee; }
    public List<Appointment> getAppointments() { return appointments; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setName(String name)                   { this.name = name; }
    public void setSpecialization(String spec)         { this.specialization = spec; }
    public void setContactNumber(String num)           { this.contactNumber = num; }
    public void setAvailableDays(String days)          { this.availableDays = days; }
    public void setConsultingHours(String hours)       { this.consultingHours = hours; }
    public void setConsultationFee(int fee)            { this.consultationFee = fee; }

    // ── Business Methods ──────────────────────────────────────────────────────
    public void addAppointment(Appointment a) { appointments.add(a); }

    public long getPendingAppointmentsCount() {
        return appointments.stream()
            .filter(a -> a.getStatus() == Appointment.Status.SCHEDULED)
            .count();
    }

    /** Compact summary row */
    public void displaySummary() {
        System.out.printf("  %-8s %-20s %-20s %-12s  ₹%d%n",
            doctorId, name, specialization, availableDays, consultationFee);
    }

    /** Full profile */
    public void displayFullProfile() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.printf ("║           DOCTOR PROFILE  %-22s║%n", "");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf ("║  Doctor ID     : %-31s║%n", doctorId);
        System.out.printf ("║  Name          : Dr. %-27s║%n", name);
        System.out.printf ("║  Specialization: %-31s║%n", specialization);
        System.out.printf ("║  Qualification : %-31s║%n", qualification);
        System.out.printf ("║  Contact       : %-31s║%n", contactNumber);
        System.out.printf ("║  Available Days: %-31s║%n", availableDays);
        System.out.printf ("║  Consulting Hrs: %-31s║%n", consultingHours);
        System.out.printf ("║  Fee           : ₹%-30d║%n", consultationFee);
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf ("║  Total Appointments  : %-25d║%n", appointments.size());
        System.out.printf ("║  Pending Appointments: %-25d║%n", getPendingAppointmentsCount());
        System.out.println("╚══════════════════════════════════════════════════╝");
    }

    @Override
    public String toString() {
        return String.format("Doctor[%s | Dr.%s | %s]",
            doctorId, name, specialization);
    }
}

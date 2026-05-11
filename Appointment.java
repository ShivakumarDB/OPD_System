package opd;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Represents a scheduled appointment between a Patient and a Doctor.
 */
public class Appointment {

    // ── Status Enum ──────────────────────────────────────────────────────────
    public enum Status {
        SCHEDULED, COMPLETED, CANCELLED, NO_SHOW
    }

    // ── Fields ────────────────────────────────────────────────────────────────
    private static int apptCounter = 5000;

    private final String      appointmentId;
    private final Patient     patient;
    private final Doctor      doctor;
    private LocalDate         date;
    private LocalTime         time;
    private String            reason;
    private Status            status;
    private final LocalDate   bookedOn;

    // ── Constructors ─────────────────────────────────────────────────────────

    /** Full constructor */
    public Appointment(Patient patient, Doctor doctor,
                       LocalDate date, LocalTime time, String reason) {
        this.appointmentId = "A" + (++apptCounter);
        this.patient       = patient;
        this.doctor        = doctor;
        this.date          = date;
        this.time          = time;
        this.reason        = reason;
        this.status        = Status.SCHEDULED;
        this.bookedOn      = LocalDate.now();

        // Register with patient and doctor
        patient.addAppointment(this);
        doctor.addAppointment(this);
    }

    /** Constructor without explicit reason */
    public Appointment(Patient patient, Doctor doctor,
                       LocalDate date, LocalTime time) {
        this(patient, doctor, date, time, "General Consultation");
    }

    // ── Getters ───────────────────────────────────────────────────────────────
    public String    getAppointmentId() { return appointmentId; }
    public Patient   getPatient()       { return patient; }
    public Doctor    getDoctor()        { return doctor; }
    public LocalDate getDate()          { return date; }
    public LocalTime getTime()          { return time; }
    public String    getReason()        { return reason; }
    public Status    getStatus()        { return status; }
    public LocalDate getBookedOn()      { return bookedOn; }

    // ── Setters ───────────────────────────────────────────────────────────────
    public void setDate(LocalDate date)     { this.date = date; }
    public void setTime(LocalTime time)     { this.time = time; }
    public void setReason(String reason)    { this.reason = reason; }
    public void setStatus(Status status)    { this.status = status; }

    // ── Business Methods ──────────────────────────────────────────────────────
    public void cancel()   { this.status = Status.CANCELLED; }
    public void complete() { this.status = Status.COMPLETED; }
    public void markNoShow(){ this.status = Status.NO_SHOW; }

    public boolean isToday() {
        return date.equals(LocalDate.now());
    }

    /** Summary row for lists */
    public void displaySummary() {
        System.out.printf("  %-8s  %-12s  %-20s  Dr.%-18s  %s  %-10s%n",
            appointmentId,
            patient.getPatientId(),
            patient.getName(),
            doctor.getName(),
            date + " " + time,
            status);
    }

    /** Detailed card */
    public void displayDetails() {
        System.out.println();
        System.out.println("  ┌─────────────────────────────────────────────┐");
        System.out.printf ("  │  Appointment ID : %-26s│%n", appointmentId);
        System.out.printf ("  │  Patient        : %s (%s)%n", patient.getName(), patient.getPatientId());
        System.out.printf ("  │  Doctor         : Dr.%-24s│%n", doctor.getName());
        System.out.printf ("  │  Specialization : %-26s│%n", doctor.getSpecialization());
        System.out.printf ("  │  Date & Time    : %-26s│%n", date + " at " + time);
        System.out.printf ("  │  Reason         : %-26s│%n", reason);
        System.out.printf ("  │  Status         : %-26s│%n", status);
        System.out.printf ("  │  Booked On      : %-26s│%n", bookedOn);
        System.out.println("  └─────────────────────────────────────────────┘");
    }

    @Override
    public String toString() {
        return String.format("Appointment[%s | %s → Dr.%s | %s %s | %s]",
            appointmentId, patient.getName(), doctor.getName(), date, time, status);
    }
}

package opd;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ╔══════════════════════════════════════════════════════╗
 * ║        OPD MANAGEMENT SYSTEM — MAIN CONTROLLER      ║
 * ║  Classes   : Patient, Doctor, Appointment,           ║
 * ║              Consultation, OPDManagementSystem       ║
 * ║  Features  : Register patients, manage doctors,      ║
 * ║              schedule appointments, record           ║
 * ║              consultations, display reports.         ║
 * ╚══════════════════════════════════════════════════════╝
 */
public class OPDManagementSystem {

    // ── Storage ───────────────────────────────────────────────────────────────
    private final List<Patient>      patients      = new ArrayList<>();
    private final List<Doctor>       doctors       = new ArrayList<>();
    private final List<Appointment>  appointments  = new ArrayList<>();
    private final List<Consultation> consultations = new ArrayList<>();

    private final Scanner sc = new Scanner(System.in);

    // ═════════════════════════════════════════════════════════════════════════
    //  ENTRY POINT
    // ═════════════════════════════════════════════════════════════════════════
    public static void main(String[] args) {
        OPDManagementSystem system = new OPDManagementSystem();
        system.loadDemoData();
        system.run();
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  MAIN MENU LOOP
    // ═════════════════════════════════════════════════════════════════════════
    public void run() {
        printBanner();
        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = readInt("Enter choice: ");
            switch (choice) {
                case 1  -> patientMenu();
                case 2  -> doctorMenu();
                case 3  -> appointmentMenu();
                case 4  -> consultationMenu();
                case 5  -> reportsMenu();
                case 0  -> { running = false; printGoodbye(); }
                default -> System.out.println("  ⚠  Invalid option. Try again.");
            }
        }
        sc.close();
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  1. PATIENT MENU
    // ═════════════════════════════════════════════════════════════════════════
    private void patientMenu() {
        boolean back = false;
        while (!back) {
            printSubMenu("PATIENT MANAGEMENT",
                new String[]{
                    "Register New Patient",
                    "View All Patients",
                    "Search Patient by ID",
                    "Search Patient by Name",
                    "View Patient Full Profile",
                    "Back to Main Menu"
                });
            int ch = readInt("Enter choice: ");
            switch (ch) {
                case 1 -> registerPatient();
                case 2 -> viewAllPatients();
                case 3 -> searchPatientById();
                case 4 -> searchPatientByName();
                case 5 -> viewPatientProfile();
                case 6 -> back = true;
                default -> System.out.println("  ⚠  Invalid option.");
            }
        }
    }

    private void registerPatient() {
        System.out.println("\n  ── Register New Patient ──────────────────────────");
        String name    = readString("  Full Name      : ");
        int    age     = readInt   ("  Age            : ");
        String gender  = readString("  Gender (M/F/O) : ");
        String contact = readString("  Contact Number : ");
        String address = readString("  Address        : ");
        String blood   = readString("  Blood Group    : ");

        Patient p = new Patient(name, age, gender, contact, address, blood);
        patients.add(p);
        System.out.println("\n  ✔ Patient registered successfully!");
        System.out.printf ("  Patient ID: %s%n%n", p.getPatientId());
    }

    private void viewAllPatients() {
        if (patients.isEmpty()) { System.out.println("\n  No patients registered yet.\n"); return; }
        System.out.println("\n  ── All Registered Patients ───────────────────────");
        System.out.printf ("  %-8s %-20s %3s  %-6s  %-13s  %s%n",
            "ID", "Name", "Age", "Gender", "Contact", "Blood");
        System.out.println("  " + "─".repeat(72));
        patients.forEach(Patient::displaySummary);
        System.out.printf("%n  Total: %d patient(s)%n%n", patients.size());
    }

    private void searchPatientById() {
        String id = readString("\n  Enter Patient ID: ").toUpperCase();
        Patient p = findPatientById(id);
        if (p == null) System.out.println("  ⚠  Patient not found.");
        else           p.displaySummary();
        System.out.println();
    }

    private void searchPatientByName() {
        String name = readString("\n  Enter patient name (or partial): ").toLowerCase();
        List<Patient> found = patients.stream()
            .filter(p -> p.getName().toLowerCase().contains(name))
            .collect(Collectors.toList());
        if (found.isEmpty()) { System.out.println("  ⚠  No match found.\n"); return; }
        System.out.println("\n  ── Search Results ────────────────────────────────");
        System.out.printf ("  %-8s %-20s %3s  %-6s  %-13s  %s%n",
            "ID", "Name", "Age", "Gender", "Contact", "Blood");
        System.out.println("  " + "─".repeat(72));
        found.forEach(Patient::displaySummary);
        System.out.println();
    }

    private void viewPatientProfile() {
        String id = readString("\n  Enter Patient ID: ").toUpperCase();
        Patient p = findPatientById(id);
        if (p == null) System.out.println("  ⚠  Patient not found.");
        else           p.displayFullProfile();
        System.out.println();
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  2. DOCTOR MENU
    // ═════════════════════════════════════════════════════════════════════════
    private void doctorMenu() {
        boolean back = false;
        while (!back) {
            printSubMenu("DOCTOR MANAGEMENT",
                new String[]{
                    "Add New Doctor",
                    "View All Doctors",
                    "Search Doctor by ID",
                    "View Doctor Full Profile",
                    "Back to Main Menu"
                });
            int ch = readInt("Enter choice: ");
            switch (ch) {
                case 1 -> addDoctor();
                case 2 -> viewAllDoctors();
                case 3 -> searchDoctorById();
                case 4 -> viewDoctorProfile();
                case 5 -> back = true;
                default -> System.out.println("  ⚠  Invalid option.");
            }
        }
    }

    private void addDoctor() {
        System.out.println("\n  ── Add New Doctor ────────────────────────────────");
        String name    = readString("  Full Name        : ");
        String spec    = readString("  Specialization   : ");
        String qual    = readString("  Qualification    : ");
        String contact = readString("  Contact Number   : ");
        String days    = readString("  Available Days   : ");
        String hours   = readString("  Consulting Hours : ");
        int    fee     = readInt   ("  Consultation Fee : ₹");

        Doctor d = new Doctor(name, spec, qual, contact, days, hours, fee);
        doctors.add(d);
        System.out.println("\n  ✔ Doctor added successfully!");
        System.out.printf ("  Doctor ID: %s%n%n", d.getDoctorId());
    }

    private void viewAllDoctors() {
        if (doctors.isEmpty()) { System.out.println("\n  No doctors registered yet.\n"); return; }
        System.out.println("\n  ── Doctors on Staff ──────────────────────────────");
        System.out.printf ("  %-8s %-20s %-20s %-12s  %s%n",
            "ID", "Name", "Specialization", "Available", "Fee");
        System.out.println("  " + "─".repeat(70));
        doctors.forEach(Doctor::displaySummary);
        System.out.printf("%n  Total: %d doctor(s)%n%n", doctors.size());
    }

    private void searchDoctorById() {
        String id = readString("\n  Enter Doctor ID: ").toUpperCase();
        Doctor d = findDoctorById(id);
        if (d == null) System.out.println("  ⚠  Doctor not found.\n");
        else           d.displaySummary();
        System.out.println();
    }

    private void viewDoctorProfile() {
        String id = readString("\n  Enter Doctor ID: ").toUpperCase();
        Doctor d = findDoctorById(id);
        if (d == null) System.out.println("  ⚠  Doctor not found.");
        else           d.displayFullProfile();
        System.out.println();
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  3. APPOINTMENT MENU
    // ═════════════════════════════════════════════════════════════════════════
    private void appointmentMenu() {
        boolean back = false;
        while (!back) {
            printSubMenu("APPOINTMENT MANAGEMENT",
                new String[]{
                    "Schedule New Appointment",
                    "View All Appointments",
                    "View Today's Appointments",
                    "Cancel Appointment",
                    "Mark as No-Show",
                    "Back to Main Menu"
                });
            int ch = readInt("Enter choice: ");
            switch (ch) {
                case 1 -> scheduleAppointment();
                case 2 -> viewAllAppointments();
                case 3 -> viewTodaysAppointments();
                case 4 -> cancelAppointment();
                case 5 -> markNoShow();
                case 6 -> back = true;
                default -> System.out.println("  ⚠  Invalid option.");
            }
        }
    }

    private void scheduleAppointment() {
        System.out.println("\n  ── Schedule Appointment ──────────────────────────");
        viewAllPatients();
        String pid = readString("  Patient ID   : ").toUpperCase();
        Patient p = findPatientById(pid);
        if (p == null) { System.out.println("  ⚠  Patient not found.\n"); return; }

        viewAllDoctors();
        String did = readString("  Doctor ID    : ").toUpperCase();
        Doctor d = findDoctorById(did);
        if (d == null) { System.out.println("  ⚠  Doctor not found.\n"); return; }

        LocalDate date = readDate("  Date (YYYY-MM-DD): ");
        LocalTime time = readTime("  Time (HH:MM)     : ");
        String reason  = readString("  Reason           : ");

        Appointment a = new Appointment(p, d, date, time, reason);
        appointments.add(a);
        System.out.println("\n  ✔ Appointment scheduled!");
        System.out.printf ("  Appointment ID: %s%n%n", a.getAppointmentId());
    }

    private void viewAllAppointments() {
        if (appointments.isEmpty()) { System.out.println("\n  No appointments yet.\n"); return; }
        System.out.println("\n  ── All Appointments ──────────────────────────────");
        printAppointmentHeader();
        appointments.forEach(Appointment::displaySummary);
        System.out.printf("%n  Total: %d appointment(s)%n%n", appointments.size());
    }

    private void viewTodaysAppointments() {
        List<Appointment> today = appointments.stream()
            .filter(Appointment::isToday)
            .collect(Collectors.toList());
        System.out.printf("%n  ── Today's Appointments (%s) ─────────────────%n", LocalDate.now());
        if (today.isEmpty()) { System.out.println("  No appointments today.\n"); return; }
        printAppointmentHeader();
        today.forEach(Appointment::displaySummary);
        System.out.printf("%n  Total: %d appointment(s) today%n%n", today.size());
    }

    private void cancelAppointment() {
        String id = readString("\n  Enter Appointment ID: ").toUpperCase();
        Appointment a = findAppointmentById(id);
        if (a == null)                           { System.out.println("  ⚠  Not found.\n"); return; }
        if (a.getStatus() != Appointment.Status.SCHEDULED)
            { System.out.println("  ⚠  Only SCHEDULED appointments can be cancelled.\n"); return; }
        a.cancel();
        System.out.println("  ✔ Appointment cancelled.\n");
    }

    private void markNoShow() {
        String id = readString("\n  Enter Appointment ID: ").toUpperCase();
        Appointment a = findAppointmentById(id);
        if (a == null) { System.out.println("  ⚠  Not found.\n"); return; }
        a.markNoShow();
        System.out.println("  ✔ Marked as No-Show.\n");
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  4. CONSULTATION MENU
    // ═════════════════════════════════════════════════════════════════════════
    private void consultationMenu() {
        boolean back = false;
        while (!back) {
            printSubMenu("CONSULTATION MANAGEMENT",
                new String[]{
                    "Record New Consultation",
                    "View Consultation by ID",
                    "View All Consultations",
                    "Back to Main Menu"
                });
            int ch = readInt("Enter choice: ");
            switch (ch) {
                case 1 -> recordConsultation();
                case 2 -> viewConsultationById();
                case 3 -> viewAllConsultations();
                case 4 -> back = true;
                default -> System.out.println("  ⚠  Invalid option.");
            }
        }
    }

    private void recordConsultation() {
        System.out.println("\n  ── Record Consultation ───────────────────────────");

        // Pick appointment
        List<Appointment> scheduled = appointments.stream()
            .filter(a -> a.getStatus() == Appointment.Status.SCHEDULED)
            .collect(Collectors.toList());

        if (scheduled.isEmpty()) {
            System.out.println("  ⚠  No scheduled appointments to record consultation for.\n");
            return;
        }
        System.out.println("\n  Scheduled Appointments:");
        printAppointmentHeader();
        scheduled.forEach(Appointment::displaySummary);

        String aid = readString("\n  Appointment ID : ").toUpperCase();
        Appointment a = findAppointmentById(aid);
        if (a == null || a.getStatus() != Appointment.Status.SCHEDULED) {
            System.out.println("  ⚠  Invalid or non-scheduled appointment.\n");
            return;
        }

        String symptoms   = readString("  Symptoms       : ");
        String diagnosis  = readString("  Diagnosis      : ");
        String treatment  = readString("  Treatment Plan : ");

        List<String> meds = new ArrayList<>();
        System.out.println("  Medicines (enter blank to stop):");
        while (true) {
            String med = readString("    Medicine name : ");
            if (med.isBlank()) break;
            meds.add(med);
        }

        String labTests  = readString("  Lab Tests      : ");
        String followUp  = readString("  Follow-Up Date : ");
        String notes     = readString("  Doctor Notes   : ");

        Consultation c = new Consultation(
            a.getPatient(), a.getDoctor(), a,
            symptoms, diagnosis, treatment,
            meds, labTests, followUp, notes);
        consultations.add(c);

        System.out.println("\n  ✔ Consultation recorded successfully!");
        System.out.printf ("  Consultation ID : %s%n%n", c.getConsultationId());
    }

    private void viewConsultationById() {
        String id = readString("\n  Enter Consultation ID: ").toUpperCase();
        Consultation c = consultations.stream()
            .filter(x -> x.getConsultationId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
        if (c == null) System.out.println("  ⚠  Not found.\n");
        else           c.displayFullReport();
        System.out.println();
    }

    private void viewAllConsultations() {
        if (consultations.isEmpty()) { System.out.println("\n  No consultations recorded yet.\n"); return; }
        System.out.println("\n  ── All Consultations ─────────────────────────────");
        System.out.printf ("  %-8s  %-12s  %-20s  %-20s  %s%n",
            "ID", "Date", "Patient", "Doctor", "Diagnosis");
        System.out.println("  " + "─".repeat(80));
        for (Consultation c : consultations) {
            System.out.printf("  %-8s  %-12s  %-20s  Dr.%-17s  %s%n",
                c.getConsultationId(),
                c.getConsultationDateTime().toLocalDate(),
                c.getPatient().getName(),
                c.getDoctor().getName(),
                c.getDiagnosis());
        }
        System.out.printf("%n  Total: %d consultation(s)%n%n", consultations.size());
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  5. REPORTS MENU
    // ═════════════════════════════════════════════════════════════════════════
    private void reportsMenu() {
        boolean back = false;
        while (!back) {
            printSubMenu("REPORTS & STATISTICS",
                new String[]{
                    "OPD Summary Dashboard",
                    "Doctor-wise Appointment Count",
                    "Patient Medical History",
                    "Back to Main Menu"
                });
            int ch = readInt("Enter choice: ");
            switch (ch) {
                case 1 -> opdDashboard();
                case 2 -> doctorWiseCount();
                case 3 -> { String id = readString("\n  Patient ID: ").toUpperCase();
                             Patient p = findPatientById(id);
                             if (p != null) p.displayFullProfile();
                             else System.out.println("  ⚠  Not found."); }
                case 4 -> back = true;
                default -> System.out.println("  ⚠  Invalid option.");
            }
        }
    }

    private void opdDashboard() {
        long scheduled  = appointments.stream().filter(a -> a.getStatus() == Appointment.Status.SCHEDULED).count();
        long completed  = appointments.stream().filter(a -> a.getStatus() == Appointment.Status.COMPLETED).count();
        long cancelled  = appointments.stream().filter(a -> a.getStatus() == Appointment.Status.CANCELLED).count();
        long noShow     = appointments.stream().filter(a -> a.getStatus() == Appointment.Status.NO_SHOW).count();
        double revenue  = consultations.stream().mapToDouble(Consultation::getBillAmount).sum();

        System.out.println("\n╔══════════════════════════════════════════════════╗");
        System.out.println("║           OPD SUMMARY DASHBOARD                 ║");
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf ("║  Total Patients         : %-23d║%n", patients.size());
        System.out.printf ("║  Total Doctors          : %-23d║%n", doctors.size());
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf ("║  Total Appointments     : %-23d║%n", appointments.size());
        System.out.printf ("║    ▸ Scheduled          : %-23d║%n", scheduled);
        System.out.printf ("║    ▸ Completed          : %-23d║%n", completed);
        System.out.printf ("║    ▸ Cancelled          : %-23d║%n", cancelled);
        System.out.printf ("║    ▸ No-Show            : %-23d║%n", noShow);
        System.out.println("╠══════════════════════════════════════════════════╣");
        System.out.printf ("║  Total Consultations    : %-23d║%n", consultations.size());
        System.out.printf ("║  Total Revenue          : ₹%-22.2f║%n", revenue);
        System.out.println("╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void doctorWiseCount() {
        System.out.println("\n  ── Doctor-wise Appointment Count ─────────────────");
        System.out.printf ("  %-8s %-20s %-20s  %s%n",
            "ID", "Doctor", "Specialization", "Appointments");
        System.out.println("  " + "─".repeat(60));
        for (Doctor d : doctors) {
            System.out.printf("  %-8s %-20s %-20s  %d%n",
                d.getDoctorId(), d.getName(),
                d.getSpecialization(), d.getAppointments().size());
        }
        System.out.println();
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  HELPER — Find entities
    // ═════════════════════════════════════════════════════════════════════════
    private Patient findPatientById(String id) {
        return patients.stream()
            .filter(p -> p.getPatientId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
    }

    private Doctor findDoctorById(String id) {
        return doctors.stream()
            .filter(d -> d.getDoctorId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
    }

    private Appointment findAppointmentById(String id) {
        return appointments.stream()
            .filter(a -> a.getAppointmentId().equalsIgnoreCase(id))
            .findFirst().orElse(null);
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  HELPER — Input utilities
    // ═════════════════════════════════════════════════════════════════════════
    private String readString(String prompt) {
        System.out.print(prompt);
        return sc.nextLine().trim();
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  ⚠  Please enter a valid number.");
            }
        }
    }

    private LocalDate readDate(String prompt) {
        while (true) {
            try {
                return LocalDate.parse(readString(prompt));
            } catch (Exception e) {
                System.out.println("  ⚠  Use YYYY-MM-DD format (e.g. 2025-06-15).");
            }
        }
    }

    private LocalTime readTime(String prompt) {
        while (true) {
            try {
                return LocalTime.parse(readString(prompt));
            } catch (Exception e) {
                System.out.println("  ⚠  Use HH:MM format (e.g. 10:30).");
            }
        }
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  HELPER — UI prints
    // ═════════════════════════════════════════════════════════════════════════
    private void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║                                                  ║");
        System.out.println("  ║      OPD MANAGEMENT SYSTEM  v1.0                 ║");
        System.out.println("  ║      Outpatient Department — City Hospital        ║");
        System.out.println("  ║                                                  ║");
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    private void printMainMenu() {
        System.out.println("  ┌──────────────────────────────────┐");
        System.out.println("  │           MAIN  MENU             │");
        System.out.println("  ├──────────────────────────────────┤");
        System.out.println("  │  1. Patient Management           │");
        System.out.println("  │  2. Doctor Management            │");
        System.out.println("  │  3. Appointment Management       │");
        System.out.println("  │  4. Consultation Management      │");
        System.out.println("  │  5. Reports & Statistics         │");
        System.out.println("  │  0. Exit                         │");
        System.out.println("  └──────────────────────────────────┘");
    }

    private void printSubMenu(String title, String[] options) {
        System.out.println("\n  ┌─────────────────────────────────────┐");
        System.out.printf ("  │  %-36s│%n", title);
        System.out.println("  ├─────────────────────────────────────┤");
        for (int i = 0; i < options.length - 1; i++)
            System.out.printf("  │  %d. %-33s│%n", i + 1, options[i]);
        System.out.printf ("  │  %d. %-33s│%n", options.length, options[options.length - 1]);
        System.out.println("  └─────────────────────────────────────┘");
    }

    private void printAppointmentHeader() {
        System.out.printf ("  %-8s  %-12s  %-20s  %-20s  %-16s  %s%n",
            "ApptID", "PatientID", "Patient Name", "Doctor", "Date & Time", "Status");
        System.out.println("  " + "─".repeat(95));
    }

    private void printGoodbye() {
        System.out.println("\n  ╔══════════════════════════════════════╗");
        System.out.println("  ║  Thank you for using OPD System!     ║");
        System.out.println("  ║  Stay healthy. Goodbye!              ║");
        System.out.println("  ╚══════════════════════════════════════╝\n");
    }

    // ═════════════════════════════════════════════════════════════════════════
    //  DEMO DATA — Pre-loaded sample records
    // ═════════════════════════════════════════════════════════════════════════
    private void loadDemoData() {
        // ── Doctors ──────────────────────────────────────────────────────────
        Doctor d1 = new Doctor("Rajesh Sharma",   "Cardiology",
            "MD, DM (Cardiology)",    "9876500001",
            "Mon, Wed, Fri",          "09:00-13:00", 800);
        Doctor d2 = new Doctor("Priya Nair",      "Dermatology",
            "MBBS, MD (Skin)",        "9876500002",
            "Tue, Thu, Sat",          "10:00-14:00", 600);
        Doctor d3 = new Doctor("Arjun Mehta",     "Orthopedics",
            "MBBS, MS (Ortho)",       "9876500003",
            "Mon-Sat",                "08:00-12:00", 700);
        doctors.addAll(List.of(d1, d2, d3));

        // ── Patients ─────────────────────────────────────────────────────────
        Patient p1 = new Patient("Ananya Krishnan", 34, "Female",
            "9900001111", "12 MG Road, Bengaluru", "B+");
        Patient p2 = new Patient("Vikram Reddy",    52, "Male",
            "9900002222", "45 Indiranagar, Bengaluru", "O-");
        Patient p3 = new Patient("Sunita Patel",    28, "Female",
            "9900003333", "7 Koramangala, Bengaluru", "A+");
        patients.addAll(List.of(p1, p2, p3));

        // ── Appointments ─────────────────────────────────────────────────────
        Appointment a1 = new Appointment(p1, d1,
            LocalDate.now(), LocalTime.of(10, 0), "Chest pain follow-up");
        Appointment a2 = new Appointment(p2, d3,
            LocalDate.now(), LocalTime.of(11, 30), "Knee pain");
        Appointment a3 = new Appointment(p3, d2,
            LocalDate.now().plusDays(1), LocalTime.of(10, 30), "Skin rash");
        appointments.addAll(List.of(a1, a2, a3));

        // ── Consultations ─────────────────────────────────────────────────────
        List<String> meds1 = List.of("Aspirin 75mg OD", "Atorvastatin 40mg HS");
        Consultation c1 = new Consultation(
            p1, d1, a1,
            "Occasional chest tightness", "Stable Angina", "Medication + Lifestyle change",
            new ArrayList<>(meds1), "ECG, Lipid Profile",
            LocalDate.now().plusWeeks(4).toString(),
            "Patient to avoid strenuous activity");
        consultations.add(c1);

        System.out.println("  ✔ Demo data loaded: "
            + doctors.size() + " doctors, "
            + patients.size() + " patients, "
            + appointments.size() + " appointments, "
            + consultations.size() + " consultation.\n");
    }
}

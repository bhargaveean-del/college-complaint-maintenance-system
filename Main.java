import java.util.*;

// 1. Status Workflow Enum
enum Status {
    PENDING, IN_PROGRESS, RESOLVED, REJECTED
}

// 2. Department Category Enum
enum Category {
    ELECTRICAL, PLUMBING, IT_INFRASTRUCTURE, CLEANING, GENERAL
}

// 3. Complaint Class representing a single ticket
class Complaint {
    private static int idCounter = 1001;
    private int complaintId;
    private String title;
    private String description;
    private Category category;
    private Status status;
    private String raisedBy;
    private String assignedTo;
    private Date dateRaised;

    public Complaint(String title, String description, Category category, String raisedBy) {
        this.complaintId = idCounter++;
        this.title = title;
        this.description = description;
        this.category = category;
        this.status = Status.PENDING;
        this.raisedBy = raisedBy;
        this.assignedTo = "Unassigned";
        this.dateRaised = new Date();
    }

    public int getComplaintId() { return complaintId; }
    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }

    @Override
    public String toString() {
        return String.format("[ID: %d] %s\n  Category: %s | Status: %s\n  Raised By: %s | Assigned To: %s\n  Description: %s\n  Date: %s",
                complaintId, title, category, status, raisedBy, assignedTo, description, dateRaised);
    }
}

// 4. Manager Class to handle operations
class ComplaintSystemManager {
    private List<Complaint> complaints = new ArrayList<>();

    public void addComplaint(String title, String description, Category category, String raisedBy) {
        Complaint c = new Complaint(title, description, category, raisedBy);
        complaints.add(c);
        System.out.println("\n[Success] Complaint registered successfully! Your Ticket ID is: " + c.getComplaintId());
    }

    public void viewAllComplaints() {
        if (complaints.isEmpty()) {
            System.out.println("\nNo complaints registered in the system.");
            return;
        }
        System.out.println("\n=== All College Complaints ===");
        for (Complaint c : complaints) {
            System.out.println(c);
            System.out.println("--------------------------------------------------");
        }
    }

    public void updateComplaintStatus(int id, Status newStatus, String technician) {
        Complaint c = findComplaintById(id);
        if (c != null) {
            c.setStatus(newStatus);
            if (technician != null && !technician.trim().isEmpty()) {
                c.setAssignedTo(technician);
            }
            System.out.println("\n[Success] Complaint ID " + id + " updated to " + newStatus);
        } else {
            System.out.println("\n[Error] Complaint with ID " + id + " not found.");
        }
    }

    public void viewComplaintsByStatus(Status status) {
        System.out.println("\n=== Complaints with Status: " + status + " ===");
        boolean found = false;
        for (Complaint c : complaints) {
            if (c.getStatus() == status) {
                System.out.println(c);
                System.out.println("--------------------------------------------------");
                found = true;
            }
        }
        if (!found) {
            System.out.println("No complaints found with status: " + status);
        }
    }

    private Complaint findComplaintById(int id) {
        for (Complaint c : complaints) {
            if (c.getComplaintId() == id) {
                return c;
            }
        }
        return null;
    }
}

// 5. Main Application Runner
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ComplaintSystemManager manager = new ComplaintSystemManager();

        // Pre-loaded sample data for quick testing
        manager.addComplaint("Projector Fuse Blown", "Seminar Hall 2 projector won't turn on.", Category.IT_INFRASTRUCTURE, "Prof. Sharma");
        manager.addComplaint("Water Leakage", "Pipe leaking in Hostel Block A washroom.", Category.PLUMBING, "Rahul (Student)");

        while (true) {
            System.out.println("\n=== College Maintenance & Complaint System ===");
            System.out.println("1. Submit a New Complaint");
            System.out.println("2. View All Complaints");
            System.out.println("3. Filter Complaints by Status");
            System.out.println("4. Update Complaint Status & Assign Technician");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.print("Enter Complaint Title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter Description: ");
                    String desc = scanner.nextLine();

                    System.out.println("Select Category:\n1. ELECTRICAL\n2. PLUMBING\n3. IT_INFRASTRUCTURE\n4. CLEANING\n5. GENERAL");
                    System.out.print("Enter choice (1-5): ");
                    int catChoice = Integer.parseInt(scanner.nextLine());
                    Category category = Category.GENERAL;
                    switch (catChoice) {
                        case 1: category = Category.ELECTRICAL; break;
                        case 2: category = Category.PLUMBING; break;
                        case 3: category = Category.IT_INFRASTRUCTURE; break;
                        case 4: category = Category.CLEANING; break;
                    }

                    System.out.print("Enter your Name and Role (e.g., Alex (Student)): ");
                    String raisedBy = scanner.nextLine();

                    manager.addComplaint(title, desc, category, raisedBy);
                    break;

                case 2:
                    manager.viewAllComplaints();
                    break;

                case 3:
                    System.out.println("Select Status:\n1. PENDING\n2. IN_PROGRESS\n3. RESOLVED\n4. REJECTED");
                    System.out.print("Enter choice (1-4): ");
                    int statChoice = Integer.parseInt(scanner.nextLine());
                    Status status = Status.PENDING;
                    switch (statChoice) {
                        case 1: status = Status.PENDING; break;
                        case 2: status = Status.IN_PROGRESS; break;
                        case 3: status = Status.RESOLVED; break;
                        case 4: status = Status.REJECTED; break;
                    }
                    manager.viewComplaintsByStatus(status);
                    break;

                case 4:
                    System.out.print("Enter Complaint ID to update: ");
                    int id = Integer.parseInt(scanner.nextLine());
                    
                    System.out.println("Select New Status:\n1. PENDING\n2. IN_PROGRESS\n3. RESOLVED\n4. REJECTED");
                    System.out.print("Enter choice (1-4): ");
                    int newStatChoice = Integer.parseInt(scanner.nextLine());
                    Status newStatus = Status.PENDING;
                    switch (newStatChoice) {
                        case 1: newStatus = Status.PENDING; break;
                        case 2: newStatus = Status.IN_PROGRESS; break;
                        case 3: newStatus = Status.RESOLVED; break;
                        case 4: newStatus = Status.REJECTED; break;
                    }

                    System.out.print("Enter Assigned Technician Name: ");
                    String technician = scanner.nextLine();

                    manager.updateComplaintStatus(id, newStatus, technician);
                    break;

                case 5:
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid option. Please choose between 1 and 5.");
            }
        }
    }
}
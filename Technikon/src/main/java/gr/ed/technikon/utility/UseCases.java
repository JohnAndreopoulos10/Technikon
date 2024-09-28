package gr.ed.technikon.utility;

import gr.ed.technikon.Repositories.OwnerRepository;
import gr.ed.technikon.Repositories.OwnerRepositoryInterface;
import gr.ed.technikon.Repositories.PropertyRepository;
import gr.ed.technikon.Repositories.PropertyRepositoryInterface;
import gr.ed.technikon.Repositories.RepairRepository;
import gr.ed.technikon.models.Owner;
import gr.ed.technikon.services.IOService;
import gr.ed.technikon.services.IOServiceInterface;
import gr.ed.technikon.services.OwnerService;
import gr.ed.technikon.services.OwnerServiceInterface;
import static java.io.File.separator;
import gr.ed.technikon.Repositories.RepairRepositoryInterface;
import gr.ed.technikon.enums.PropertyType;
import gr.ed.technikon.enums.RepairStatus;
import gr.ed.technikon.enums.RepairType;
import gr.ed.technikon.exceptions.CustomException;
import gr.ed.technikon.exceptions.OwnerNotFoundException;
import gr.ed.technikon.exceptions.ResourceNotFoundException;
import gr.ed.technikon.models.Property;
import gr.ed.technikon.models.Repair;
import gr.ed.technikon.services.AdminService;
import gr.ed.technikon.services.AdminServiceInterface;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UseCases {

    private static final OwnerRepositoryInterface ownerRepo = new OwnerRepository();
    private static final PropertyRepositoryInterface propertyRepo = new PropertyRepository();
    private static final RepairRepositoryInterface repairRepo = new RepairRepository();

    private static final IOServiceInterface ioService = new IOService(ownerRepo, propertyRepo, repairRepo);
    private static final OwnerServiceInterface ownerService = new OwnerService(propertyRepo);
    private static final AdminServiceInterface adminService = new AdminService();

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
    private static Property property;
    private static Owner owner;
    private static Repair repair;
    private static final Scanner scanner = new Scanner(System.in);

    public static void dataPopulation() {
        System.out.println("|-----------------------------START-----------------------------------|");
        System.out.println("|-----------------------------USE CASE 4.1-----------------------------|");

        ioService.readOwnersCsv("data" + separator + "owner.csv");
        ioService.readPropertiesCsv("data" + separator + "property.csv");
        ioService.readRepairsFromCsv("data" + separator + "repair.csv");

        System.out.println();
        System.out.println("|-----------------------------data population achieved-----------------------------------|");
    }

    public static void ownerWithTwoProperties() {
        System.out.println("|-----------------------------USE CASE------------------------------|");
        owner = new Owner();
        owner.setVatNumber(123456789L);
        owner.setName("Nikos");
        owner.setSurName("Koukos");
        owner.setAddress("Kleanthous 14");
        owner.setPhoneNumber("6978756512");
        owner.setEmail("nikosk@gmail.com");
        owner.setUsername("NickKouk");
        owner.setPassword("12345678");

        ownerRepo.save(owner);

        Property property1 = new Property(123456789L, "Kleanthous 4", 1992, PropertyType.MAISONETTE, owner);
        Property property2 = new Property(321456789L, "Kleanthous 23", 1996, PropertyType.APARTMENT_BUILDING, owner);

        List<Property> koukosProperties = new ArrayList();
        koukosProperties.add(property1);
        koukosProperties.add(property2);
        owner.setPropertyList(koukosProperties);

        System.out.println(owner.toString());
        for (Property property : owner.getPropertyList()) {
            System.out.println("PropertyCode:" + property.getPropertyCode() + " Address: " + property.getAddress());

            propertyRepo.save(property1);
            propertyRepo.save(property2);

            System.out.println("|-----------------------------new owner with two properties achieved-----------------------------------|");
        }

    }

    public static Repair repairsForPropertiesOwner(long propertyId) throws ParseException, CustomException {
        System.out.println("|-------------------4.3-------------------|");
        try {
            Optional<Property> propertyOwner = propertyRepo.findById(propertyId);
            property = propertyOwner.get();

            repair = new Repair();
        } catch (Exception e) {
            throw new CustomException("Property not found");
        }
        repair.setDateOfSubmission(DATE_FORMAT.parse("19-09-2024"));
//        repair.setDeletedRepair(false);
        repair.setDescriptionOfWork("Painting of the living room walls in shades of grey.");
        repair.setShortDescription("Wall Painting");
        repair.setRepairType(RepairType.PAINTING);
        repair.setRepairStatus(RepairStatus.PENDING);
        repair.setProperty(property);
        repairRepo.save(repair);
        System.out.println("|-----------------------------new repair for property owner achieved-----------------------------------|");
        return repair;

    }

    public static void selectPropertiesFromOwnerById(long ownerId) {
        try {
            Optional<Owner> ownerProperty = ownerRepo.findByOwnerId(ownerId);
//          owner = ownerProperty.get();
            OwnerService ownerService = new OwnerService(propertyRepo);
        } catch (Exception e) {
            throw new OwnerNotFoundException("Owner not Found");
        }
        for (Property property : ownerService.getPropertiesByOwnerId(ownerId)) {
            System.out.println("The property ID for this unique ownerID is/are: " + property.getPropertyId());

            System.out.println("|-----------------------------END-----------------------------------|");

        }

    }

    public static boolean ownerAcceptanceOfRepairs(long repairId) throws ParseException, ResourceNotFoundException {
        System.out.println("|-------------------4.4-------------------|");
        try {
            OwnerService ownerService = new OwnerService(propertyRepo);

            Optional<Repair> repair = repairRepo.findById(repairId);

            if (repair.isEmpty()) {
                return false;
            }
            Repair repairObj = repair.get();

            Boolean rp = ownerService.acceptance(repairObj);
            repairRepo.save(repairObj);
            System.out.println("|-----------------------------acceptance achieved-----------------------------------|");
            return rp;
        } catch (Exception e) {
            throw new ResourceNotFoundException("");
        }

    }

    public static void adminGetsPendingRepairs() {
        AdminService adminService = new AdminService();
        for (Repair rs : adminService.getPendingRepairs()) {
            System.out.println("Pending Repairs" + rs.toString());
        }
    }

    public static void adminGetsAllPendingRepairsWithProposedCostAndDates() throws ParseException {
        List<Repair> pendingRepairs = adminService.getPendingRepairs();

        for (Repair repair : pendingRepairs) {
            Date proposedStartDate = repair.getProposedDateOfStart();
            Date proposedEndDate = repair.getProposedDateOfEnd();
            BigDecimal proposedCost = repair.getProposedCost();

            System.out.println("Repair ID: " + repair.getRepairId());
            System.out.println("Short Description: " + repair.getShortDescription());
            System.out.println("Description of Work: " + repair.getDescriptionOfWork());
            System.out.println("Proposed Cost: " + proposedCost);

            if (proposedStartDate != null && proposedEndDate != null) {
                System.out.println("Proposed Start Date: " + DATE_FORMAT.format(proposedStartDate));
                System.out.println("Proposed End Date: " + DATE_FORMAT.format(proposedEndDate));
            } else {
                System.out.println("Proposed Dates: Not set.");
            }

        }
        System.out.println("|-----------------------------END-----------------------------------|");
    }

    public static void generateOwnerReport() {
        System.out.println("|-----------------------------START-----------------------------|");
        System.out.println("|-----------------------------OWNER REPORT-----------------------------|");

        System.out.print("Enter the Owner ID: ");
        long ownerId = scanner.nextLong();

        try {
            List<Property> properties = propertyRepo.findByOwnerId(ownerId);

            if (properties.isEmpty()) {
                System.out.println("No properties found for this owner.");
            } else {
                for (Property property : properties) {
//            System.out.println("Property ID: " + property.getPropertyId());
                    System.out.println("Address: " + property.getAddress());
                    System.out.println("Year of Construction: " + property.getYearOfConstruction());
                    System.out.println("Type: " + property.getPropertyType());

                    List<Repair> repairs = repairRepo.findByPropertyId(property.getPropertyId());

                    if (repairs.isEmpty()) {
                        System.out.println("No repairs found for this property/properties .");
                    } else {
                        for (Repair repair : repairs) {
                            System.out.println("Repair Status: " + repair.getRepairStatus());
                            System.out.println("---------------------------");
                        }
                    }
                    System.out.println();

                }
            }
        } catch (Exception e) {
            throw new OwnerNotFoundException("Owner not Found");
        }

        System.out.println("|----------------------------END OF OWNER REPORT-----------------------------|");
    }

    public static void generateAdminReport() {
        System.out.println("|-----------------------------ADMIN REPORT-----------------------------|");

        System.out.println("Generating full report for all repairs:");

        try {
            List<Repair> repairs = repairRepo.findAll();

            if (repairs.isEmpty()) {
                System.out.println("No repairs found.");
            } else {
                for (Repair repair : repairs) {
                    System.out.println("Repair ID: " + repair.getRepairId());

                    if (repair.getProperty() != null) {
                        System.out.println("Property ID: " + repair.getProperty().getPropertyId());
                    } else {
                        System.out.println("Property: Not assigned");
                    }

                    System.out.println("Type: " + repair.getRepairType());
                    System.out.println("Short Description: " + repair.getShortDescription());
                    System.out.println("Date of Submission: " + repair.getDateOfSubmission());
                    System.out.println("Description of Work: " + repair.getDescriptionOfWork());
                    System.out.println("Proposed Date of Start: " + repair.getProposedDateOfStart());
                    System.out.println("Repair Status: " + repair.getRepairStatus());
                    System.out.println("---------------------------");
                }
            }
        } catch (Exception e) {
            throw new ResourceNotFoundException("Resource not found");
        }

        System.out.println("|-----------------------------END OF ADMIN REPORT-----------------------------|");
        System.out.println("|-------------------------------------------------------------------------------|");
        System.out.println("|---------------------------------------END---------------------------------------|");
    }

//    private Date parseDate(String dateStr) throws ParseException {
//        Date date = DATE_FORMAT.parse(dateStr);
//        return date;
//    }
//
//    private long parseLong(String value) {
//        try {
//            return Long.parseLong(value.trim());
//        } catch (NumberFormatException e) {
//            return -1;
//        }
//    }
//
//    private int parseInt(String value) {
//        try {
//            return Integer.parseInt(value.trim());
//        } catch (NumberFormatException e) {
//
//            return -1;
//        }
//    }
//
//    private String formatDate(Date date) {
//        return date == null ? "" : DATE_FORMAT.format(date);
//    }
}

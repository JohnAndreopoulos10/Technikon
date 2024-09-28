package gr.ed.technikon;

import gr.ed.technikon.utility.JPAUtil;
import gr.ed.technikon.utility.UseCases;
import java.text.ParseException;
import java.util.Scanner;

public class Technikon {

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        try {
            while (true) {
                System.out.println("Welcome to Technikon");
                System.out.println("1. Populate Database");
                System.out.println("2. Register Owner with Properties");
                System.out.println("3. Create a new Repair for Properties Owner");
                System.out.println("4. Select Properties from owner Id");
                System.out.println("5. Owner has to accept or not the repair");
                System.out.println("6. Get all pending Repairs with proposed Cost and Dates");
                System.out.println("7. Generate Report for Property-Owner");
                System.out.println("8. Generate Report for Admin");
                System.out.println("9.Exit");

                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); 

                switch (choice) {
                    case 1:
                        UseCases.dataPopulation();
                        break;
                    case 2:
                        UseCases.ownerWithTwoProperties();
                        break;
                    case 3:
                        UseCases.repairsForPropertiesOwner(9L);
                        break;
                    case 4:
                        UseCases.selectPropertiesFromOwnerById(3);
                        break;
                    case 5:
                        UseCases.ownerAcceptanceOfRepairs(7L);
                        break;
                    case 6:
                        UseCases.adminGetsAllPendingRepairsWithProposedCostAndDates();
                        break;
                    case 7:
                        UseCases.generateOwnerReport();
                        break;

                    case 8:
                        UseCases.generateAdminReport();
                        break;
                    case 9:
                        JPAUtil.shutdown();
                        System.out.println("EXIT");
                        System.exit(0);
                        break;
                    default:
                        System.out.println("");
                }
            }
        } finally {
            scanner.close();
        }
    }
}

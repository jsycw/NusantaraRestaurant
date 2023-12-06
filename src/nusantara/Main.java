package nusantara;

import java.util.*;

public class Main {
    private static Map<String, MenuItem> menu = new HashMap<>();
    private static ArrayList<CustomerOrder> customerOrders = new ArrayList<>();

    public static void main(String[] args) {
        initializeMenu();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add Customer\n2. Add Order\n3. View Bill\n4. Delete Order\n5. View Menu\n6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addCustomer(scanner);
                    break;
                case 2:
                    addOrder(scanner);
                    break;
                case 3:
                    viewBill(scanner);
                    break;
                case 4:
                    deleteOrder(scanner);
                    break;
                case 5:
                    viewMenu();
                    break;
                case 6:
                    System.out.println("Exiting program. Goodbye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    private static void initializeMenu() {
        menu.put("nasgor", new Food("Nasi Goreng", 12000));
        menu.put("jeruk", new Beverage("Jeruk", 5000));
        menu.put("mieayam", new Food("Mie Ayam", 10000));
        menu.put("teh", new Beverage("Teh", 3000));
        menu.put("burger", new Food("Burger", 15000));
        menu.put("kopi", new Beverage("Kopi", 6000));
        menu.put("sate", new Food("Sate", 20000));
        menu.put("soda", new Beverage("Soda", 7000));
        menu.put("pizza", new Food("Pizza", 18000));
        menu.put("airmineral", new Beverage("Air Mineral", 2000));
    }

    private static void addCustomer(Scanner scanner) {
        System.out.print("Enter customer name: ");
        String customerName = scanner.next();

        char firstLetter = customerName.charAt(0);
        String customerId = generateCustomerId(firstLetter);

        CustomerOrder customerOrder = new CustomerOrder(customerId, customerName);
        customerOrders.add(customerOrder);

        System.out.println("Customer added successfully! Customer ID: " + customerId);
    }

    private static String generateCustomerId(char firstLetter) {
        int count = 1;
        for (CustomerOrder order : customerOrders) {
            if (order.getCustomerId().charAt(0) == firstLetter) {
                count++;
            }
        }
        return Character.toUpperCase(firstLetter) + String.format("%03d", count);
    }

    private static void addOrder(Scanner scanner) {
        if (customerOrders.isEmpty()) {
            System.out.println("No customers. Please add a customer first.");
            return;
        }

        System.out.print("Enter customer ID: ");
        String customerId = scanner.next();

        CustomerOrder currentOrder = findCustomerOrder(customerId);

        if (currentOrder != null) {
            char addMore = 'y';
            while (addMore == 'y') {
                System.out.println("Choose from the menu:");
                viewMenu();
                System.out.print("Enter the name of the food or beverage: ");
                String itemName = scanner.next();

                if (!menu.containsKey(itemName)) {
                    System.out.println("Invalid menu item. Please choose from the existing menu.");
                    continue;
                }

                System.out.println("Enter the quantity: ");
                int qty = scanner.nextInt();

                currentOrder.addOrderItem(new OrderItem(menu.get(itemName), qty));
                System.out.println("Order added successfully!");

                System.out.print("Do you want to add more items? (y/n): ");
                addMore = scanner.next().charAt(0);
            }
        } else {
            System.out.println("Customer not found. Please add a customer first.");
        }
    }

    private static void viewBill(Scanner scanner) {
        if (customerOrders.isEmpty()) {
            System.out.println("No customers. Please add a customer first.");
            return;
        }

        System.out.print("Enter customer ID: ");
        String customerId = scanner.next();

        CustomerOrder currentOrder = findCustomerOrder(customerId);

        if (currentOrder != null) {
            currentOrder.printBill();
        } else {
            System.out.println("Customer not found. Please add a customer first.");
        }
    }

    private static void deleteOrder(Scanner scanner) {
        if (customerOrders.isEmpty()) {
            System.out.println("No customers. Please add a customer first.");
            return;
        }

        System.out.print("Enter customer ID: ");
        String customerId = scanner.next();

        CustomerOrder currentOrder = findCustomerOrder(customerId);

        if (currentOrder != null) {
            currentOrder.deleteOrderItem(scanner);
        } else {
            System.out.println("Customer not found. Please add a customer first.");
        }
    }

    private static void viewMenu() {
        System.out.println("MENU:");
        System.out.printf("%-15s%-10s\n", "Menu", "Harga");
        for (Map.Entry<String, MenuItem> entry : menu.entrySet()) {
            System.out.printf("%-15s%-10d\n", entry.getKey(), entry.getValue().getPrice());
        }
    }


    private static CustomerOrder findCustomerOrder(String customerId) {
        for (CustomerOrder order : customerOrders) {
            if (order.getCustomerId().equalsIgnoreCase(customerId)) {
                return order;
            }
        }
        return null;
    }
}

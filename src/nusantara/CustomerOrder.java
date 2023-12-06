package nusantara;

import java.util.ArrayList;
import java.util.Scanner;

class CustomerOrder {
        private String customerId;
        private String customerName;
        private ArrayList<OrderItem> orderItems = new ArrayList<>();

        public CustomerOrder(String customerId, String customerName) {
            this.customerId = customerId;
            this.customerName = customerName;
        }

        public String getCustomerId() {
            return customerId;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void addOrderItem(OrderItem orderItem) {
            orderItems.add(orderItem);
        }

        public void printBill() {
            System.out.println("ID CUSTOMER PUNYA BILL");
            System.out.println(customerId);

            System.out.println("No\tMenu\tHarga\tQty\tTotal");
            int itemNo = 1;
            for (OrderItem item : orderItems) {
                System.out.println(itemNo++ + "\t" + item.getMenuItem().getName() + "\t" +
                        item.getMenuItem().getPrice() + "\t" + item.getQty() + "\t" + item.getTotal());
            }

            double totalBill = calculateTotalBill();
            double discount = calculateDiscount(totalBill);
            double priceToPay = totalBill - (totalBill * discount);

            System.out.println("\nTotal bill: " + totalBill);
            System.out.println("Discount: " + (discount * 100) + "%");
            System.out.println("Price you must pay: " + priceToPay);
        }

        public void deleteOrderItem(Scanner scanner) {
            if (orderItems.isEmpty()) {
                System.out.println("No items to delete. The order is empty.");
                return;
            }

            System.out.println("Current Order:");
            displayOrder();

            System.out.print("Enter the item number you want to modify: ");
            int itemToModify = scanner.nextInt();

            if (itemToModify > 0 && itemToModify <= orderItems.size()) {
                OrderItem targetItem = orderItems.get(itemToModify - 1);

                System.out.println("Current quantity of " + targetItem.getMenuItem().getName() + ": " + targetItem.getQty());
                System.out.print("Enter the quantity to delete: ");
                int qtyToDelete = scanner.nextInt();

                if (qtyToDelete > 0 && qtyToDelete <= targetItem.getQty()) {
                    targetItem.setQty(targetItem.getQty() - qtyToDelete);
                    System.out.println("Quantity deleted successfully!");
                    if (targetItem.getQty() == 0) {
                        orderItems.remove(itemToModify - 1);
                        System.out.println("Item removed from the order.");
                    }
                } else {
                    System.out.println("Invalid quantity. Please enter a valid quantity.");
                }
            } else {
                System.out.println("Invalid item number. Please enter a valid item number.");
            }
        }

        private void displayOrder() {
            System.out.println("No\tMenu\tHarga\tQty\tTotal");
            int itemNo = 1;
            for (OrderItem item : orderItems) {
                System.out.println(itemNo++ + "\t" + item.getMenuItem().getName() + "\t" +
                        item.getMenuItem().getPrice() + "\t" + item.getQty() + "\t" + item.getTotal());
            }
        }

        private double calculateTotalBill() {
            double total = 0;
            for (OrderItem item : orderItems) {
                total += item.getTotal();
            }
            return total;
        }

        private double calculateDiscount(double totalBill) {
            if (totalBill > 50000 && totalBill <= 100000) {
                return 0.05;
            } else if (totalBill > 100000 && totalBill <= 150000) {
                return 0.15;
            } else if (totalBill > 150000) {
                return 0.20;
            } else {
                return 0;
            }
        }
    }
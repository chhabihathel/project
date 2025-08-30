

import java.io.Serializable;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


class Order1 implements Serializable {
    private static final long serialVersionUID = 1L;
    private String itemName;
    private int quantity;
    private double price;

    public Order1(String itemName, int quantity, double price) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
    }

    public double getTotal() {
        return quantity * price;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Item: " + itemName + ", Quantity: " + quantity + ", Price: " + price + ", Total: " + getTotal();
    }

}


  public class A  {
    private static final String FILE_NAME = "orders.dat";
    private Map<String, Order1> orders = new HashMap<>();
    private Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        A obj = new A();
        obj.loadOrders();
        obj.run();
        obj.saveOrders();
    }

    

    void run() {
        while (true) {
            System.out.println("1. Add Order");
            System.out.println("2. Update Order Quantity");
            System.out.println("3. Calculate Total Order Amount");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); 
            switch (choice) {
                case 1:
                    addOrder();
                    break;
                case 2:
                    updateOrderQuantity();
                    break;
                case 3:
                    calculateTotalAmount();
                    break;
                case 4:
                    return;

                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void addOrder() {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        scanner.nextLine(); 

        orders.put(itemName, new Order1(itemName, quantity, price));
        System.out.println("Order added successfully.");
    }

    private void updateOrderQuantity() {
        System.out.print("Enter item name to update: ");
        String itemName = scanner.nextLine();
        if (!orders.containsKey(itemName)) {
            System.out.println("Order not found.");
            return;
        }

        Order1 existingOrder = orders.get(itemName);

        System.out.print("Enter new quantity: ");
        int newQuantity = scanner.nextInt();
        scanner.nextLine(); 

        orders.put(itemName, new Order1(itemName, newQuantity, existingOrder.getPrice()));
        System.out.println("Order updated successfully.");
    }

    private void calculateTotalAmount() {
        double totalAmount = 0;
        for (Order1 order : orders.values()) {
            totalAmount += order.getTotal();
        }
        System.out.println("Total Order Amount: " + totalAmount);
    }

    private void saveOrders() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(orders);
            System.out.println("Orders saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving orders: " + e.getMessage());
        }
    }

    private void loadOrders() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            Object obj = ois.readObject();

            if (obj instanceof Map<?, ?>) {
                @SuppressWarnings("unchecked")
                Map<String, Order1> tempOrders = (Map<String, Order1>) obj;
                orders = tempOrders;
                System.out.println("Orders loaded successfully.");
            } else {
                System.out.println("Loaded object is not of expected type.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No previous orders found, starting fresh.");
            orders = new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading orders: " + e.getMessage());
            orders = new HashMap<>(); 
        }
    }
}




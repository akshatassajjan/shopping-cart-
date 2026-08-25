import java.util.ArrayList;
import java.util.Scanner;

class Product {
    int id;
    String name;
    double price;

    Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

class Order {
    int orderId;
    String user;
    String section;
    ArrayList<Product> products = new ArrayList<>();
    String status = "CONFIRMED";

    Order(int orderId, String user, String section) {
        this.orderId = orderId;
        this.user = user;
        this.section = section;
    }

    double getTotal() {
        double total = 0;

        for (Product p : products) {
            total += p.price;
        }

        return total;
    }
}

public class SajjanShopping {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<Order> orders = new ArrayList<>();

    static int nextOrderId = 101;

    static Product[] menProducts = {
        new Product(1, "Shirt", 799),
        new Product(2, "Jeans", 1199),
        new Product(3, "Shoes", 1499),
        new Product(4, "Watch", 999),
        new Product(5, "Wallet", 499)
    };

    static Product[] womenProducts = {
        new Product(1, "Dress", 1299),
        new Product(2, "Top", 699),
        new Product(3, "Saree", 1999),
        new Product(4, "Shoes", 1499),
        new Product(5, "Handbag", 899)
    };

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n====================================");
            System.out.println("       SAJJAN SHOPPING");
            System.out.println("       Shop Smart, Live Better!");
            System.out.println("====================================");

            System.out.println("1. User 1");
            System.out.println("2. User 2");
            System.out.println("3. Exit");

            System.out.print("Select User: ");
            int userChoice = sc.nextInt();

            if (userChoice == 3) {
                System.out.println("\nThank you for visiting SAJJAN SHOPPING!");
                break;
            }

            if (userChoice != 1 && userChoice != 2) {
                System.out.println("Invalid user!");
                continue;
            }

            String user = "User " + userChoice;

            userMenu(user);
        }
    }

    static void userMenu(String user) {

        while (true) {

            System.out.println("\n------------------------------------");
            System.out.println("Welcome " + user);
            System.out.println("------------------------------------");

            System.out.println("1. Men Section");
            System.out.println("2. Women Section");
            System.out.println("3. View My Orders");
            System.out.println("4. Cancel Order");
            System.out.println("5. Logout");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    shoppingMenu(user, "Men");
                    break;

                case 2:
                    shoppingMenu(user, "Women");
                    break;

                case 3:
                    viewOrders(user);
                    break;

                case 4:
                    cancelOrder(user);
                    break;

                case 5:
                    System.out.println("\nLogged out successfully!");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    static void shoppingMenu(String user, String section) {

        Product[] products;

        if (section.equals("Men")) {
            products = menProducts;
        } else {
            products = womenProducts;
        }

        while (true) {

            System.out.println("\n====================================");
            System.out.println(section.toUpperCase() + " SECTION");
            System.out.println("====================================");

            for (Product p : products) {
                System.out.println(
                    p.id + ". " + p.name + " - Rs." + p.price
                );
            }

            System.out.println("0. Back");

            System.out.print("\nEnter product number: ");
            int productChoice = sc.nextInt();

            if (productChoice == 0) {
                return;
            }

            if (productChoice < 1 || productChoice > products.length) {
                System.out.println("Invalid product!");
                continue;
            }

            Product selected = products[productChoice - 1];

            Order order = new Order(nextOrderId++, user, section);

            order.products.add(selected);

            System.out.println("\n====================================");
            System.out.println("ORDER MENU");
            System.out.println("====================================");

            System.out.println("1. Place Order");
            System.out.println("2. Modify Order");
            System.out.println("3. View Order");
            System.out.println("4. Cancel Order");

            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    placeOrder(order);
                    break;

                case 2:
                    modifyOrderBeforePlace(order, products);
                    break;

                case 3:
                    viewSingleOrder(order);
                    break;

                case 4:
                    System.out.println("Order cancelled.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

            if (choice == 1) {
                return;
            }
        }
    }

    static void placeOrder(Order order) {

        orders.add(order);

        System.out.println("\n====================================");
        System.out.println("       ORDER PLACED SUCCESSFULLY");
        System.out.println("====================================");

        System.out.println("Order ID : ORD" + order.orderId);
        System.out.println("User     : " + order.user);
        System.out.println("Section  : " + order.section);

        for (Product p : order.products) {
            System.out.println(p.name + " - Rs." + p.price);
        }

        System.out.println("------------------------------------");
        System.out.println("Total    : Rs." + order.getTotal());
        System.out.println("Status   : " + order.status);
    }

    static void modifyOrderBeforePlace(Order order, Product[] products) {

        System.out.println("\n--- MODIFY ORDER ---");

        System.out.println("Current Item:");

        for (Product p : order.products) {
            System.out.println(p.name + " - Rs." + p.price);
        }

        System.out.println("\nAvailable Products:");

        for (Product p : products) {
            System.out.println(
                p.id + ". " + p.name + " - Rs." + p.price
            );
        }

        System.out.print("Choose new product: ");
        int choice = sc.nextInt();

        if (choice >= 1 && choice <= products.length) {

            order.products.clear();

            order.products.add(products[choice - 1]);

            System.out.println("Order modified successfully!");

        } else {

            System.out.println("Invalid product!");
        }
    }

    static void viewOrders(String user) {

        System.out.println("\n====================================");
        System.out.println("          MY ORDERS");
        System.out.println("====================================");

        boolean found = false;

        for (Order order : orders) {

            if (order.user.equals(user)) {

                found = true;

                viewSingleOrder(order);
            }
        }

        if (!found) {
            System.out.println("No orders found.");
        }
    }

    static void viewSingleOrder(Order order) {

        System.out.println("\n------------------------------------");

        System.out.println(
            "Order ID : ORD" + order.orderId
        );

        System.out.println(
            "Section  : " + order.section
        );

        System.out.println("Items:");

        for (Product p : order.products) {
            System.out.println(
                "  " + p.name + " - Rs." + p.price
            );
        }

        System.out.println(
            "Total    : Rs." + order.getTotal()
        );

        System.out.println(
            "Status   : " + order.status
        );

        System.out.println("------------------------------------");
    }

    static void cancelOrder(String user) {

        System.out.println("\n====================================");
        System.out.println("          CANCEL ORDER");
        System.out.println("====================================");

        boolean found = false;

        for (Order order : orders) {

            if (order.user.equals(user) &&
                order.status.equals("CONFIRMED")) {

                found = true;

                System.out.println(
                    "Order ID: ORD" + order.orderId +
                    " | Total: Rs." + order.getTotal()
                );
            }
        }

        if (!found) {
            System.out.println("No active orders found.");
            return;
        }

        System.out.print("Enter Order ID number: ");
        int id = sc.nextInt();

        for (Order order : orders) {

            if (order.orderId == id &&
                order.user.equals(user)) {

                if (order.status.equals("CANCELLED")) {

                    System.out.println("Order already cancelled.");

                } else {

                    order.status = "CANCELLED";

                    System.out.println(
                        "Order ORD" + order.orderId +
                        " cancelled successfully!"
                    );
                }

                return;
            }
        }

        System.out.println("Order not found!");
    }
}
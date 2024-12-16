import java.util.Scanner;

public class FruitShopApplication {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static FruitShopManager manager = FruitShopManager.getInstance();
    private static DiscountStrategy discountStrategy = new NoDiscountStrategy();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        OrderProcessor orderProcessor = new OrderProcessor();
        boolean running = true;

        manager.addFruit("apple", 10);
        manager.addFruit("orange", 10);
        manager.addFruit("banana", 10);

        System.out.println("Welcome to the Fruit Shop!");

        while (running) {
            System.out.println("\nSelect mode:");
            System.out.println("1. Seller");
            System.out.println("2. Customer");
            System.out.println("3. Exit");
            int selection = scanner.nextInt();
            scanner.nextLine();

            switch (selection) {
                case 1:
                    sellerMode(scanner, manager, discountStrategy);
                    break;
                case 2:
                    customerMode(scanner, manager, orderProcessor, discountStrategy);
                    break;
                case 3:
                    System.out.println("Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid selection");
            }
        }
        scanner.close();
    }

    private static void sellerMode(Scanner scanner, FruitShopManager manager, DiscountStrategy discountStrategy) {
        System.out.println("Enter Username:");
        String username = scanner.nextLine();
        System.out.println("Enter Password:");
        String password = scanner.nextLine();


        if (!username.equals(ADMIN_USERNAME) || !password.equals(ADMIN_PASSWORD)) {
            System.out.println("Invalid credentials");
            return;
        }
        boolean sellerRunning = true;
        while (sellerRunning) {
            System.out.println("\nSelect an option:");
            System.out.println("1. View Stock");
            System.out.println("2. Add Stock");
            System.out.println("3. Add New Fruit Type");
            System.out.println("4. Change Discount Strategy");
            System.out.println("5. Exit");
            int selector = scanner.nextInt();
            scanner.nextLine();

            switch (selector) {
                case 1:
                    System.out.println("Available Fruits (with Prices):");
                    FruitFactory.getFruitRegistry().forEach((fruit, price) -> {
                        int stock = manager.getStock().getOrDefault(fruit, 0);
                        System.out.printf("%s: $%.2f (%d in stock)%n", fruit, price, stock);
                    });

                    break;
                case 2: {
                    System.out.println("Enter fruit name:");
                    String fruitName = scanner.nextLine().toLowerCase();
                    System.out.println("Enter quantity:");
                    int quantity = scanner.nextInt();
                    manager.addFruit(fruitName, quantity);
                    System.out.println("Added " + quantity + " " + fruitName + "(s) to stock");
                }
                break;
                case 3:
                    System.out.println("Enter new fruit name:");
                    String fruitName = scanner.nextLine().toLowerCase();
                    if (FruitFactory.isFruitRegistered(fruitName)) {
                        System.out.println(fruitName + " is already registered.");
                        break;
                    }
                    System.out.println("Enter price:");
                    double price = scanner.nextDouble();
                    scanner.nextLine();
                    FruitFactory.registerFruit(fruitName, price);

                    System.out.println("Added " + fruitName + " to the registry");
                    break;
                case 4: {
                    System.out.println("Choose a new discount strategy:");
                    System.out.println("1. No Discount");
                    System.out.println("2. 10% Discount");
                    System.out.println("3. Buy Two Get One Free");
                    int discountChoice = scanner.nextInt();
                    discountStrategy = switch (discountChoice) {
                        case 1 -> new NoDiscountStrategy();
                        case 2 -> new PercentageDiscountStrategy(10);
                        case 3 -> new BuyTwoGetOneFreeStrategy();
                        default -> {
                            System.out.println("Invalid choice! Keeping current strategy.");
                            yield discountStrategy;
                        }
                    };
                    System.out.println("Discount strategy updated.");
                }
                case 5:
                    sellerRunning = false;
                    break;
                default:
                    System.out.println("Invalid selection");
            }
        }
    }

    private static void customerMode(Scanner scanner, FruitShopManager manager, OrderProcessor orderProcessor, DiscountStrategy discountStrategy) {
        System.out.println("Entering Customer Mode");
        boolean customerRunning = true;

        while (customerRunning) {
            System.out.println("\nSelect an option:");
            System.out.println("1. View Stock");
            System.out.println("2. View Discounts");
            System.out.println("3. Place Order");
            System.out.println("4. Submit Order");
            System.out.println("5. Exit");
            int selector = scanner.nextInt();
            scanner.nextLine();

            switch (selector) {
                case 1:
                    System.out.println("Current Stock:");
                    FruitFactory.getFruitRegistry().forEach((fruit, price) -> {
                        System.out.println(fruit + ": " + manager.getStock().getOrDefault(fruit, 0));
                    });
                    break;
                case 2:
                    System.out.println("Current Discounts: "+ discountStrategy.getClass().getSimpleName());
                    break;
                case 3: {
                    System.out.print("Enter new fruit type name: ");
                    String fruitName = scanner.nextLine().toLowerCase();
                    if (FruitFactory.isFruitRegistered(fruitName)) {
                        System.out.println(fruitName + " is already registered.");
                        break;
                    }
                    System.out.print("Enter price for " + fruitName + ": ");
                    double price = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    // Register the new fruit type
                    FruitFactory.registerFruit(fruitName, price);
                    System.out.println("Successfully registered new fruit: " + fruitName + " with price $" + price);
                    break;
                }
                case 4:
                    System.out.println("Submitting Orders");
                    orderProcessor.processOrders();
                    break;
                case 5:
                    customerRunning = false;
                    break;
            }
        }
    }
}
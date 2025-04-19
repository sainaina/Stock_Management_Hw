import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Stock {
    public static void main(String[] args) throws InterruptedException {
        Scanner input = new Scanner(System.in);
        String[][] stock = null;
        List<String> history = new ArrayList<>();
        Date date = Date.from(Instant.now());
        Thread.sleep(1000);
        System.out.println("---------->> 📦 Welcome to the Stock Management System 📦 <<-----------");

        while (true) {
            System.out.println("""
                    [🛠️] 1. Set up Stock
                    [📊] 2. View Stock
                    [➕] 3. Insert Product to Stock
                    [🔄] 4. Update Product in Stock
                    [❌] 5. Delete Product from Stock
                    [📝] 6. View All History
                    [🚪] 7. Exit Program
                    """);
            int option = readValidInt(input, "[+] Insert Option (1-7): ");

            switch (option) {
                case 1 -> {
                    int rows = readValidInt(input, "[+] Insert the number of Stock: ");
                    if (rows > 0) {
                        stock = setStock(rows, input);
                        System.out.println("---------->> ✅ SET UP STOCK SUCCEEDED! <<-----------");
                        displayStock(stock);
                    } else {
                        System.out.println("---------->>❗ Stock size must be positive. <<-----------");
                    }
                }
                case 2 -> displayStock(stock);
                case 3 -> insertProduct(input, stock, history, date);
                case 4 -> updateProduct(input, stock, history, date);
                case 5 -> deleteProduct(input, stock, history, date);
                case 6 -> showHistory(history, input);
                case 7 -> {
                    System.out.println("---------->> Exiting the program... <<-----------");
                    input.close();
                    System.exit(0);
                }
                default -> System.out.println("---------->> ❌ Invalid option. <<-----------");
            }
            System.out.println(">>> Press Enter to continue...");
            input.nextLine();
        }
    }

    private static int readValidInt(Scanner input, String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(input.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("---------->>❗ Invalid input. Please enter a valid number. <<-----------");
            }
        }
    }

    private static String[][] setStock(int rows, Scanner input) {
        String[][] stock = new String[rows][];
        for (int i = 0; i < rows; i++) {
            System.out.print("[+] Insert number of catalogues for Stock [" + (i + 1) + "]: ");
            int cols = readValidInt(input, "Catalogues: ");
            stock[i] = new String[cols];
        }
        return stock;
    }

    private static void displayStock(String[][] stock) {
        if (stock == null) {
            System.out.println("---------->>❗ Stock not set up. <<-----------");
            return;
        }
        for (int i = 0; i < stock.length; i++) {
            System.out.print("Stock [" + (i + 1) + "] =>");
            boolean isFull = true;
            for (int j = 0; j < stock[i].length; j++) {
                String displayValue = (stock[i][j] == null) ? (j + 1) + "-EMPTY" : (j + 1) + "-" + stock[i][j];
                System.out.print(" [" + displayValue + "] ");

                if (stock[i][j] == null) {
                    isFull = false;
                }
            }
            if (isFull) {
                System.out.print("     [❗] STOCK IS FULL");
            }
            System.out.println();
        }
    }

    private static void insertProduct(Scanner input, String[][] stock, List<String> history, Date date) {
        if (stock == null) {
            System.out.println("❗ Stock not set up.");
            return;
        }
        System.out.println("Insert Product to Stock:");
        displayStock(stock);

        if (isStockFull(stock)) {
            System.out.println("⚠️ Alert: The stock is full! No more products can be added.");
            return;
        }

        int stockNumber = readValidInt(input, "[+] Insert stock number: ") - 1;
        int catalogueNumber = readValidInt(input, "[+] Insert catalogue number: ") - 1;
        if (stockNumber >= 0 && stockNumber < stock.length && catalogueNumber >= 0 && catalogueNumber < stock[stockNumber].length) {
            if (stock[stockNumber][catalogueNumber] == null) {
                System.out.print("[+] Insert Product name: ");
                String productName = input.nextLine().trim();
                if (!productName.isEmpty()) {
                    stock[stockNumber][catalogueNumber] = productName;
                    history.add("[+] Inserted [" + productName + "] in Stock [" + (stockNumber + 1) + "] at catalogue [" + (catalogueNumber + 1) + "] at " + date);
                    System.out.println("----------->> ✅ Product inserted. <<-----------");
                } else {
                    System.out.println("----------->>❗ Product name is empty. <<-----------");
                }
            } else {
                System.out.println("----------->> ⚠️ Slot already occupied. <<-----------");
            }
        } else {
            System.out.println("----------->>❗ Invalid stock or catalogue number. <<-----------");
        }
    }

    private static void updateProduct(Scanner input, String[][] stock, List<String> history, Date date) {
        if (stock == null) {
            System.out.println("----------->>❗ Stock not set up. <<-----------");
            return;
        }
        System.out.println("Update Product to Stock:");
        displayStock(stock);
        int stockNumber = readValidInt(input, "[+] Insert stock number to update product: ") - 1;
        int catalogueNumber = readValidInt(input, "[+] Insert catalogue number to update: ") - 1;
        if (stockNumber >= 0 && stockNumber < stock.length && catalogueNumber >= 0 && catalogueNumber < stock[stockNumber].length) {
            String oldProduct = stock[stockNumber][catalogueNumber];
            if (oldProduct != null) {
                System.out.print("[+] Insert new product name: ");
                String newProduct = input.nextLine().trim();
                if (!newProduct.isEmpty()) {
                    stock[stockNumber][catalogueNumber] = newProduct;
                    history.add("[*] Updated [" + oldProduct + "] to [" + newProduct + "] in Stock [" + (stockNumber + 1) + "] at catalogue [" + (catalogueNumber + 1) + "] at " + date);
                    System.out.println("----------->> ✅ Product updated. <<-----------");
                } else {
                    System.out.println("----------->>❗ Product name cannot be empty. <<-----------");
                }
            } else {
                System.out.println("----------->>❗ Product not found. <<-----------");
            }
        } else {
            System.out.println("----------->>❗ Invalid stock or catalogue number. <<-----------");
        }
    }

    private static void deleteProduct(Scanner input, String[][] stock, List<String> history, Date date) {
        if (stock == null) {
            System.out.println("----------->>❗ Stock not set up. <<-----------");
            return;
        }
        System.out.println("Delete Product from Stock:");
        displayStock(stock);
        int stockNumber = readValidInt(input, "[+] Insert stock number to delete product: ") - 1;
        int catalogueNumber = readValidInt(input, "[+] Insert catalogue number to delete: ") - 1;
        if (stockNumber >= 0 && stockNumber < stock.length && catalogueNumber >= 0 && catalogueNumber < stock[stockNumber].length) {
            String productToDelete = stock[stockNumber][catalogueNumber];
            if (productToDelete != null) {
                stock[stockNumber][catalogueNumber] = null;
                history.add("[-] Deleted [" + productToDelete + "] from Stock [" + (stockNumber + 1) + "] at catalogue [" + (catalogueNumber + 1) + "] at " + date);
                System.out.println("✅ Product deleted.");
            } else {
                System.out.println("----------->>❗ Product not found. <<-----------");
            }
        } else {
            System.out.println("----------->>❗ Invalid stock or catalogue number. <<-----------");
        }
    }

    private static void showHistory(List<String> history, Scanner input) {
        if (history.isEmpty()) {
            System.out.println("----------->>❗ No history available. <<-----------");
            return;
        }
        System.out.println("📝 History:");
        history.forEach(System.out::println);
    }

    private static boolean isStockFull(String[][] stock) {
        for (int i = 0; i < stock.length; i++) {
            for (int j = 0; j < stock[i].length; j++) {
                if (stock[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }
}

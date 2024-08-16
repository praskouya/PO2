import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class Zadanie4 {
    public static void parse(Map<String, List<String>> lista, File file) throws Error1 {
        try (Scanner in = new Scanner(file)) {
            String kategoria = "";
            while (in.hasNextLine()) {
                String string1 = in.nextLine();
                if (string1.isEmpty() || string1.charAt(0) != '-') {
                    throw new Error1();
                }
                if (string1.length() > 1 && string1.charAt(1) == '-') {
                    if (kategoria.isEmpty()) {
                        throw new Error1();
                    }
                    String zahirKebab = string1.substring(2);
                    lista.get(kategoria).add(zahirKebab);
                } else {
                    kategoria = string1.substring(1);
                    lista.put(kategoria, new ArrayList<>());
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + file.getAbsolutePath());
        }
    }

    public static void printCategory(Map<String, List<String>> lista, String kategoria) throws Error2 {
    List<String> items = lista.get(kategoria);
    if (items != null && !items.isEmpty()) {
        System.out.println("*" + kategoria + ":");
        for (String item : items) {
            System.out.println(item);
        }
    } else {
        System.out.println("There is no such category in the shopping list.");
    }
}


    public static void print(Map<String, List<String>> lista) {
        boolean hasItems = false;
        for (String category : lista.keySet()) {
            if (!lista.get(category).isEmpty()) {
                hasItems = true;
                break;
            }
        }

        if (!hasItems) {
            System.out.println("The list is empty.");
        } else {
            lista.keySet().forEach(k -> {
                try {
                    printCategory(lista, k);
                } catch (Error2 e) {
                    System.out.println("Error: " + e.getMessage());
                }
            });
        }
    }

    public static void printCategories(Map<String, List<String>> lista) {
        System.out.println("Categories:");
        lista.keySet().forEach(System.out::println);
    }

    public static void addCategory(Map<String, List<String>> lista, String kategoria) {
        lista.put(kategoria, new ArrayList<>());
    }

    public static void addItem(Map<String, List<String>> lista, String kategoria, String Item) throws ProductAlreadyExistsException {
        List<String> items = lista.get(kategoria);
        if (items != null && items.contains(Item)) {
            throw new ProductAlreadyExistsException("Product '" + Item + "' already exists in category '" + kategoria + "'.");
        }
        lista.computeIfAbsent(kategoria, key -> new ArrayList<>()).add(Item);
    }

    public static void removeCategory(Map<String, List<String>> lista, String kategoria) {
    if (lista.containsKey(kategoria)) {
        lista.get(kategoria).clear();
        System.out.println("All items from category removed successfully.");
    } else {
        System.out.println("There is no such category in the shopping list.");
    }
}

    public static void removeAllElements(Map<String, List<String>> lista) {
        lista.values().forEach(List::clear);
    }

    public static void removeProduct(Map<String, List<String>> lista, String kategoria, String Item) throws Error3 {
        List<String> items = lista.get(kategoria);
        if (items == null || !items.contains(Item)) {
            throw new Error3();
        }
        items.remove(Item);
    }

    public static void saveToFile(Map<String, List<String>> lista, File file) {
        try (FileWriter writer = new FileWriter(file)) {
            for (Map.Entry<String, List<String>> entry : lista.entrySet()) {
                writer.write("-" + entry.getKey() + "\n");
                for (String item : entry.getValue()) {
                    writer.write("--" + item + "\n");
                }
            }
            System.out.println("List saved to file successfully.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        File file = new File("lista_do_wczytania.lista");
        Map<String, List<String>> shoppingList = new HashMap<>();

        try {
            parse(shoppingList, file);
        } catch (Error1 e) {
            System.out.println("Error parsing file: " + e.getMessage());
            return;
        }

        boolean exit = false;
        Scanner scanner = new Scanner(System.in);

        while (!exit) {
            try {
                System.out.println("Pick what do you want to do:\n" +
                        "1. Add category/product\n" +
                        "2. Show all categories&products\n" +
                        "3. Show all products in a category\n" +
                        "4. Remove all products from the shopping list\n" +
                        "5. Delete a product from a specified category\n" +
                        "6. Remove all the items from a category\n" +
                        "7. Save shopping list to a file\n" +
                        "8. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        printCategories(shoppingList);
                        System.out.println("Enter category name: ");
                        String category = scanner.nextLine();

                        System.out.println("Enter product name: ");
                        String product = scanner.nextLine();
                        try {
                            addItem(shoppingList, category, product);
                            System.out.println("Product added successfully.");
                        } catch (ProductAlreadyExistsException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;
                    case 2:
                        print(shoppingList);
                        break;
                    case 3:
                        System.out.println("Enter category name: ");
                        String showCategory = scanner.nextLine();
                        printCategory(shoppingList, showCategory);
                        break;
                    case 4:
                        removeAllElements(shoppingList);
                        System.out.println("All products removed.");
                        break;
                    case 5:
                        printCategories(shoppingList);
                        System.out.println("Enter category name: ");
                        String removecategory = scanner.nextLine();
                        System.out.println("Enter product name: ");
                        String removeProduct = scanner.nextLine();
                        removeProduct(shoppingList, removecategory, removeProduct);
                        System.out.println("Product removed successfully.");
                        break;
                    case 6:
                        printCategories(shoppingList);
                        System.out.println("Enter category name: ");
                        String clearCategory = scanner.nextLine();
                        removeCategory(shoppingList, clearCategory);
                        break;
                    case 7:
                        saveToFile(shoppingList, file);
                        exit = true;
                        break;
                    case 8:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid choice. Please enter a number between 1 and 8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); 
            } catch (Exception e) {
                System.out.println("An error occurred: " + e.getMessage());
            }
        }

        scanner.close();
        System.out.println("Exiting the program.");
    }
}

class ProductAlreadyExistsException extends Exception {
    public ProductAlreadyExistsException(String message) {
        super(message);
    }
}

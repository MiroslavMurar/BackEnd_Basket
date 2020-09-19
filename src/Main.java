import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static ShopList shopList = new ShopList();
    private static Basket basket;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        welcomeMenu();
    }

    private static void welcomeMenu() {
        System.out.println("Welcome in eShop, please enter your name");
        String name = scanner.nextLine();
        basket = new Basket(name);
        printMenu();
        boolean isTrue = true;
        while (isTrue) {
            int option = scanner.nextInt();
            switch (option) {
                case 0:
                    cancelOrder();
                    isTrue = false;
                    break ;
                case 1:
                    shopList.printShopList();
                    break;
                case 2:
                    addToBasket();
                    break;
                case 3:
                    removeFromBasket();
                    break;
                case 4:
                    updateBasket();
                    break;
                case 5:
                    basket.makeOrder();
                    isTrue=false;
                    break;
                case 6:
                    basket.showBasket();
                    break;
                case 7:
                    printMenu();
                    break;
            }
        }
        System.out.println("Good bey");
    }

    private static void printMenu() {
        System.out.println("Choose number for options: \n" +
                "0 exit\n" +
                "1 show all chemicals available\n" +
                "2 buy chemical\n" +
                "3 remove chemical from basket\n" +
                "4 update amount of chemical in basket\n" +
                "5 complete purchase\n" +
                "6 show basket\n" +
                "7 show menu"
        );
    }

    private static void updateBasket() {
        if (basket.getAmounts().isEmpty()) {
            System.out.println("Basket is empty");
            return;
        }
        basket.showBasket();
        System.out.println("\nWhich one would you like to update ? (Enter a number) ");
        int option = getIdOfItem();
        String[] idArray = getKey(option);
        Item.Key key = Item.createKey(idArray[0], idArray[1], Double.parseDouble(idArray[2]));
        Item item = shopList.getItems().get(key);
        int count;
        while (true) {
            System.out.println("Change amount of your item (Enter a number) ");
            count = getCount();
            if (basket.unreserveItem(item, basket.getAmounts().get(item))) {
                if (basket.reserveItem(item, count)) {
                    System.out.println("Successfully changed: " + item.getName() + " in " + count + " amount");
                    System.out.println("Press 7 for menu option or 0 for EXIT");
                    return;
                }
            } else {
                System.out.println("You enter bigger amount than you have already orderred, pleasy try again");
                continue;
            }
        }
    }

    private static void removeFromBasket() {
        if (basket.getAmounts().isEmpty()) {
            System.out.println("Basket is empty");
            return;
        }
        basket.showBasket();
        System.out.println("\nWhich one would you like to remove ? (Enter a number) ");
        int option = getIdOfItem();
        String[] idArray = getKey(option);
        Item.Key key = Item.createKey(idArray[0], idArray[1], Double.parseDouble(idArray[2]));
        Item item = shopList.getItems().get(key);
        int count;
        while (true) {
            System.out.println("How many would you like to remove from basket ? (Enter a number) ");
            count = getCount();
            if (basket.unreserveItem(item, count)) {
                System.out.println("Successfully unreserved: " + item.getName() + " in " + count + " amount");
                System.out.println("Press 7 for menu option or 0 for EXIT");
                return;
            } else {
                System.out.println("You enter bigger amount than you have already orderred, pleasy try again");
            }
        }
    }

    private static void addToBasket() {
        System.out.println("Hello " + basket.getCustomer() + " we have these compounds for you ");
        shopList.printShopList();
        System.out.println("\nWhich one would you like to order ? (Enter a number) ");
        int option = getIdOfItem();
        String[] idArray = getKey(option);
        Item.Key key = Item.createKey(idArray[0], idArray[1], Double.parseDouble(idArray[2]));
        Item item = shopList.getItems().get(key);
        int count;
        while (true) {
            System.out.println("How many would you like to order ? (Enter a number) ");
            count = getCount();
            if (basket.reserveItem(item, count)) {
                System.out.println("Successfully orderred: " + item.getName() + " in " + count + " amount");
                System.out.println("Press 7 for menu option or 0 for EXIT");
                return;
            } else {
                System.out.println("Not available amount");
            }
        }
    }

    private static String[] getKey(int option) {
        String choice = "(" + option + ". )";
        String shopListString = shopList.getShopList();
        String[] idArray = new String[3];
        String line;
        String id = "(.+?)(\n)";
        Pattern pattern = Pattern.compile(choice + id);
        Matcher matcher = pattern.matcher(shopListString);
        if (matcher.find()) {
            line = matcher.group(2);
            if (line != null) {
                id = "(Name: )(.+?)(; mass: )";
                pattern = Pattern.compile(id);
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    idArray[0] = matcher.group(2);
                }

                id = "(; mass: )(.+?)(; price)";
                pattern = Pattern.compile(id);
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    idArray[1] = matcher.group(2);
                }

                id = "(; price: )(.+?)(; toxicity:)";
                pattern = Pattern.compile(id);
                matcher = pattern.matcher(line);
                if (matcher.find()) {
                    idArray[2] = matcher.group(2);
                }
            }
        }
        return idArray;
    }

    private static int getIdOfItem() {
        while (true) {
            try {
                int option = scanner.nextInt();
                return option;
            } catch (InputMismatchException e) {
                System.out.println("Must insert integer of compound");
                scanner.nextLine();
            }
        }
    }

    private static int getCount() {
        while (true) {
            try {
                int option = scanner.nextInt();
                return option;
            } catch (InputMismatchException e) {
                System.out.println("Must insert integer of compound");
                scanner.nextLine();
            }
        }
    }

    private static void cancelOrder() {
        basket.clear();
        System.out.println("Orderr cancelled");
    }
}

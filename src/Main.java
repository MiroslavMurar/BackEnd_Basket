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
            try {
                int option = scanner.nextInt();
                switch (option) {
                    case 0:
                        cancelOrder();
                        isTrue = false;
                        break;
                    case 1:
                        shopList.printShopList();
                        optionMenu();
                        break;
                    case 2:
                        addToBasket();
                        optionMenu();
                        break;
                    case 3:
                        removeFromBasket();
                        optionMenu();
                        break;
                    case 4:
                        updateBasket();
                        optionMenu();
                        break;
                    case 5:
                        basket.makeOrder();
                        isTrue = false;
                        break;
                    case 6:
                        basket.showBasket();
                        optionMenu();
                        break;
                    case 7:
                        printMenu();
                        break;
                }
            } catch (Exception e) {
                System.out.println("You probably did mistake please insert just integer");
                scanner.nextLine();
            }

        }
        System.out.println("Good bey");
    }

    private static void optionMenu() {
        System.out.println("Press 7 for option menu");
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

    private static void addToBasket() {
        System.out.println("Hello " + basket.getCustomer() + " we have these compounds for you ");
        shopList.printShopList();
        System.out.println("\nWhich one would you like to order or 0 for exit ? (Enter a number) ");
        Item item = getItem(shopList);
        if (item != null) {
            int count;
            while (true) {
                System.out.println("How many would you like to order ? (Enter a number) ");
                count = getCount();
                int reserved = basket.reserveItem(item, count);
                if (reserved > 0) {
                    System.out.println("Successfully ordered: " + item.getName() + " in " + reserved + " amount");
                    return;
                } else {
                    if (count == 0) {
                        break;
                    }
                    System.out.println("Not possible use negative amount\nEnter correct number or 0 for cancel");
                }
            }
        } else {
            System.out.println("Exiting from section buy chemicals ");
        }
    }

    private static void updateBasket() {
        if (basket.getAmounts().isEmpty()) {
            System.out.println("Basket is empty");
            return;
        }
        basket.showBasket();
        System.out.println("\nWhich one would you like to update or 0 for exit ? (Enter a number) ");
        Item item = getItem(basket);
        if (item != null) {
            int count;
            while (true) {
                System.out.println("Change current amount of your item to the new amount, zero for exit (Enter a number) ");
                count = getCount();
                int originalAmount = basket.getAmounts().get(item);
                basket.unreserveItem(item, basket.getAmounts().get(item));
                int reserved = basket.reserveItem(item, count);
                if (reserved > 0) {
                    System.out.println("Successfully changed: " + item.getName() + " in " + reserved + " amount");
                    return;
                } else {
                    if (count == 0) {
                        basket.reserveItem(item, originalAmount);
                        break;
                    }
                    System.out.println("Not possible use negative amount\nPleasy try again");
                }
                basket.reserveItem(item, originalAmount);
            }
        } else {
            System.out.println("Exiting from section buy chemicals ");
        }
    }

    private static void removeFromBasket() {
        if (basket.getAmounts().isEmpty()) {
            System.out.println("Basket is empty");
            return;
        }
        basket.showBasket();
        System.out.println("\nWhich one would you like to remove or 0 for exit ? (Enter a number) ");
        Item item = getItem(basket);
        if (item != null) {
            basket.unreserveItem(item, basket.getAmounts().get(item));
            basket.getAmounts().remove(item);
            basket.getItems().remove(item.getKey());
        }
    }

    private static Item getItem(ShopElements element) {
        while (true) {
            int option = getIdOfItem();
            if (option == 0) {
                return null;
            }
            String[] idArray = getKey(option, element);
            if (idArray[0] == null && idArray[1] == null && idArray[2] == null) {
                System.out.println("Compound does not exit please enter correct number");
                continue;
            }
            Item.Key key = Item.createKey(idArray[0], idArray[1], Double.parseDouble(idArray[2]));
            return element.getItems().get(key);
        }
    }

    private static int getIdOfItem() {
        while (true) {
            try {
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Must insert integer of compound");
                scanner.nextLine();
            }
        }
    }

    private static int getCount() {
        while (true) {
            try {
                int number = scanner.nextInt();
                if (number < 0) {
                    System.out.println("Can not use negative number");
                    continue;
                }
                return number;
            } catch (InputMismatchException e) {
                System.out.println("Must insert integer of compound");
                scanner.nextLine();
            }
        }
    }

    private static String[] getKey(int option, ShopElements element) {
        String choice = "(\t" + option + ". )";
        String shopListString = element.getList();
        String[] idArray = new String[3];
        String line;
        String id = "(.+?)(\\n)";
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

    private static void cancelOrder() {
        basket.clear();
        System.out.println("Orderr cancelled");
    }
}

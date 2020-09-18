import java.nio.file.attribute.BasicFileAttributes;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public static ShopList shopList = new ShopList();
    public static Basket basket;
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        Item item1 = new Item("benzene", "10 grams", 0.80, true, false, 5);
        Item item2 = new Item("pyridine", "56 grams", 20.2, true, false, 17);
        Item item3 = new Item("acetophenone", "1000 milliliters", 10.7, false, false, 2);
        Item item4 = new Item("4-methylacetophenone", "150 milliliters", 80.5, false, false, 8);
        Item item5 = new Item("benzene", "10 grams", 0.80, true, false, 100);

        shopList.addItem(item1);
        shopList.addItem(item2);
        shopList.addItem(item3);
        shopList.addItem(item4);
        shopList.updateItem(item5);
//        shopList.removeItem(item1);
//        shopList.printShopList();

//        Basket basket = new Basket("Miro");
//        basket.reserveItem(item2, 4);
//        basket.reserveItem(item2, 11);
//        basket.reserveItem(item2, 11);
//        basket.showBasket();
//        basket.unreserveItem(item2, 12);
//        basket.showBasket();
//        basket.makeOrder();
        welcomeMenu();

    }

    public static void welcomeMenu() {
        System.out.println("Welcome in eShop, please enter your name");
        String name = scanner.nextLine();
        basket = new Basket(name);
        printMenu();
        boolean isTrue = true;
        while (isTrue) {
            int option = scanner.nextInt();
            switch (option) {
                case 0:
                    isTrue = false;
                    break ;
                case 1:
                    shopList.printShopList();
                    break;
                case 2:
                    addToBasket();
                    break;

            }
        }
        System.out.println("Good bey");
    }

    public static void printMenu() {
        System.out.println("Choose number for options: \n" +
                "1 show all chemicals available\n" +
                "2 buy chemical\n" +
                "3 remove chemical from basket\n" +
                "4 update chemical from basket\n" +
                "5 complete purchase\n" +
                "6 cancel order\n" +
                "7 exit\n"
        );
    }

    public static void addToBasket() {
        System.out.println("Hello " + basket.getCustomer() + " we have these compounds for you ");
        shopList.printShopList();
        System.out.println("\nWhich one would you like to order ? (Enter a number) ");
        int option = scanner.nextInt();


    }
}

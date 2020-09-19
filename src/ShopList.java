import java.util.*;

public class ShopList {

    public static Map<Item.Key, Item> items = new TreeMap<>();

    static {
        Item item1 = new Item("benzene", "1 kilograms", 1.00, true, false, 100);
        Item item2 = new Item("benzene", "10 kilograms", 8.58, true, false, 80);
        Item item3 = new Item("benzene", "100 kilograms", 4.50, true, false, 20);
        Item item4 = new Item("benzene", "1000 kilograms", 228.1, true, false, 5);
        Item item5 = new Item("pyridine", "10 grams", 2.59, true, false, 3000);
        Item item6 = new Item("pyridine", "100 grams", 120, true, false, 169);
        Item item7 = new Item("pyridine", "1000 grams", 446.9, true, false, 17);
        Item item8 = new Item("acetophenone", "1 liter", 4.10, false, false, 23);
        Item item9 = new Item("acetophenone", "10 liter", 30.9, false, false, 19);
        Item item10 = new Item("acetophenone", "50 liter", 135, false, false, 15);
        Item item11 = new Item("acetophenone", "100 liter", 210, false, false, 9);
        Item item12 = new Item("4-methylacetophenone", "100 milliliters", 8.56, false, false, 8);
        Item item13 = new Item("4-methylacetophenone", "1 liter", 77.8, false, false, 8);
        Item item14 = new Item("4-methylacetophenone", "10 liter", 420, false, false, 8);
        Item item15 = new Item("4-methylacetophenone", "50 liter", 980, false, false, 8);
        Item item16 = new Item("4-methylacetophenone", "100 liter", 1600, false, false, 8);

        items.put(item1.getKey(), item1);
        items.put(item2.getKey(), item2);
        items.put(item3.getKey(), item3);
        items.put(item4.getKey(), item4);
        items.put(item5.getKey(), item5);
        items.put(item6.getKey(), item6);
        items.put(item7.getKey(), item7);
        items.put(item8.getKey(), item8);
        items.put(item9.getKey(), item9);
        items.put(item10.getKey(), item10);
        items.put(item11.getKey(), item11);
        items.put(item12.getKey(), item12);
        items.put(item13.getKey(), item13);
        items.put(item14.getKey(), item14);
        items.put(item15.getKey(), item15);
        items.put(item16.getKey(), item16);
    }

    public boolean addItem(Item item) {
        if (items.containsKey(item.getKey())) {
            Item itemInItems = items.get(item.getKey());
            itemInItems.increaseCount(item.getCount());
            return true;
        }
        items.put(item.getKey(), item);
        return true;
    }

    public boolean updateItem(Item newItem) {
        if (items.containsKey(newItem.getKey())) {
            Item.Key key = items.get(newItem.getKey()).getKey();
            items.put(key, newItem);
            return true;
        }
        return false;
    }

    public boolean removeItem(Item removedItem) {
        if (items.containsKey(removedItem.getKey())) {
            Item.Key key = items.get(removedItem.getKey()).getKey();
            items.remove(key);
            return true;
        }
        return false;
    }

    public void printShopList() {
        System.out.println("ShopList: ");
        int count = 1;
        for (Map.Entry<Item.Key, Item> entry : items.entrySet()) {
            System.out.println("\t" + count + ". " + entry.getValue());
            count++;
        }
    }

    public Map<Item.Key, Item> getItems() {
        return Collections.unmodifiableMap(items);
    }

    public String getShopList() {
        StringBuilder str = new StringBuilder("ShopList: \n");
        int count = 1;
        for (Map.Entry<Item.Key, Item> entry : items.entrySet()) {
            str.append("\t").append(count).append(". ").append(entry.getValue()).append("\n");
            count++;
        }
        return str.toString();
    }

}

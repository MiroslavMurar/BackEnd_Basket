import java.util.*;

public class ShopList {

    private Map<Item.Key, Item> items = new TreeMap<>();

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

    public String printShopList(int x) {
        StringBuilder str = new StringBuilder("ShopList: \n");
        int count = 1;
        for (Map.Entry<Item.Key, Item> entry : items.entrySet()) {
            str.append("\t").append(count).append(". ").append(entry.getValue()).append("\n");
            count++;
        }
        return str.toString();
    }

}

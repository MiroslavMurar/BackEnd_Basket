import java.util.*;

public class Basket implements ShopElements {

    private String customer;
    private Map<Item.Key, Item> items = new TreeMap<>();
    private Map<Item, Integer> amounts = new HashMap<>();

    public Basket(String customer) {
        this.customer = customer;
    }

    public String getCustomer() {
        return customer;
    }

    public int reserveItem(Item item, int count) {
        int reserved = item.reserve(count);
        if (reserved > 0) {
            if (items.containsKey(item.getKey())) {
                amounts.put(item, amounts.get(item) + reserved);
                return reserved;
            } else {
                items.put(item.getKey(), item);
                amounts.put(item, reserved);
                return reserved;
            }
        }
        return -1;
    }

    public int unreserveItem(Item item, int count) {
        if (items.containsKey(item.getKey()) && count > 0) {
            if (amounts.get(item) < count) {
                count = amounts.get(item);
            }
            Item itemInBasket = items.get(item.getKey());
            int unreserved = itemInBasket.unreserve(count);
            if (unreserved > 0) {
                amounts.put(item, amounts.get(item) - unreserved);
                if (amounts.get(item) == 0) {
                    items.remove(item.getKey());
                    amounts.remove(item);
                }
                return unreserved;
            }
        }
        System.out.println("Can not remove non-exiting or zero point items");
        return -1;
    }

    public void makeOrder() {
        for (Map.Entry<Item, Integer> item : amounts.entrySet()) {
            System.out.println(item.getKey().getName() + " has been order in " + item.getValue() + " quantity");
            item.getKey().confirmOrder(item.getValue());
        }
        amounts.clear();
        items.clear();
    }

    public void clear() {
        if (!amounts.isEmpty()) {
            for (Map.Entry<Item, Integer> item : amounts.entrySet()) {
                item.getKey().unreserve(item.getValue());
            }
        }
        items.clear();
        amounts.clear();
    }

    public void showBasket() {
        System.out.println("Basket:");
        int count = 0;
        for (Map.Entry<Item, Integer> itemEntry : amounts.entrySet()) {
            count++;
            System.out.println("\t" + count + ". " + itemEntry.getKey().getName() + " : " + itemEntry.getKey().getMass() + " : " + itemEntry.getValue());
        }
    }

    public Map<Item, Integer> getAmounts() {
        return amounts;
    }

    @Override
    public Map<Item.Key, Item> getItems() {
        return items;
    }

    @Override
    public String getList() {
        StringBuilder str = new StringBuilder("Basket: \n");
        int count = 1;
        for (Map.Entry<Item.Key, Item> entry : items.entrySet()) {
            str.append("\t").append(count).append(". ").append(entry.getValue()).append("\n");
            count++;
        }
        return str.toString();
    }
}

import java.util.*;

public class Basket {

    private String customer;
    private Map<Item.Key, Item> items = new TreeMap<>();
    private Map<Item, Integer> amounts = new HashMap<>();

    public Basket(String customer) {
        this.customer = customer;
    }

    public Map<Item, Integer> getAmounts() {
        return Collections.unmodifiableMap(amounts);
    }

    public String getCustomer() {
        return customer;
    }

    public boolean reserveItem(Item item, int count) {
        if (items.containsKey(item.getKey())) {
            Item itemInBasket = items.get(item.getKey());
            int reserved = itemInBasket.reserve(count);
            if (reserved > 0) {
                amounts.put(item, amounts.get(item) + reserved);
                return true;
            }
            return false;
        }
        if (item.reserve(count) > 0) {
            items.put(item.getKey(), item);
            amounts.put(item, count);
            return true;
        }
        return false;
    }

    public boolean unreserveItem(Item item, int count) {
        if (items.containsKey(item.getKey())) {
            if (amounts.get(item) < count) {
                count = amounts.get(item);
            }
            Item itemInBasket = items.get(item.getKey());
            int amount = itemInBasket.unreserve(count);
            if (amount > 0) {
                int actuallAmount = amounts.get(item);
                if (actuallAmount > amount) {
                    amounts.put(item, actuallAmount - amount);
                    return true;
                } else {
                    items.remove(item.getKey());
                    amounts.remove(item);
                    return true;
                }
            }
        }
        return false;
    }

    public void makeOrder() {
        for (Map.Entry<Item, Integer> item : amounts.entrySet()) {
            System.out.println(item.getKey().getName() + " has been order in " + item.getValue() + " quantity");
        }
        clear();
    }

    public void showBasket() {
        System.out.println("Basket:");
        int count = 0;
        for (Map.Entry<Item, Integer> itemEntry : amounts.entrySet()) {
            count++;
            System.out.println("\t" + count + ". " + itemEntry.getKey().getName() + " : " + itemEntry.getKey().getMass() + " : " + itemEntry.getValue());
        }
    }

    public void clear() {
        for (Map.Entry<Item, Integer> item : amounts.entrySet()) {
            item.getKey().confirmOrder(item.getValue());
        }
        items.clear();
        amounts.clear();
    }
}

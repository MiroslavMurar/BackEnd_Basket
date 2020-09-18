import java.util.ArrayList;

public class Item {

    private String name;
    private String mass;
    private double price;
    private boolean toxicity;
    private boolean explosive;
    private int reserved = 0;
    private int count;
    private Key key;

    public Item(String name, String mass, double price, boolean toxicity, boolean explosive, int count) {
        this.name = name;
        this.mass = mass;
        this.price = price;
        this.toxicity = toxicity;
        this.explosive = explosive;
        this.count = count;
        this.key = new Key(name, mass, price);
    }

    public int reserve(int count) {
        if (count > 0) {
            if(this.count >= this.reserved + count) {
                this.reserved += count;
                return count;
            } else {
                int amount = this.count - this.reserved;
                this.reserved = this.count;
                return amount;
            }
        }
        return -1;
    }

    public int unreserve(int count) {
        if (count > 0) {
            if (this.reserved - count >= 0) {
                this.reserved -= count;
                return count;
            }
        }
        return -1;
    }

    public boolean confirmOrder(int count) {
        if (count > 0 && count <= this.count && count <= reserved) {
            this.reserved -= count;
            this.count -= count;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public Key getKey() {
        return key;
    }

    public void increaseCount(int count) {
        this.count += count;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() + 31;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }

        return this.name.equals(((Item) obj).getName());
    }

    @Override
    public String toString() {
        return this.name + "; mass: " + this.mass + "; price: " + this.price + "; toxicity: " +
                (this.toxicity ? "Yes" : "No") + "; explosive: " + (this.explosive ? "Yes" : "No") +
                "; available: " + (this.count - this.reserved);
    }

    public static Key createKey(String name, String mass, double price) {
        return new Key(name, mass, price);
    }


    public static final class Key implements Comparable<Key> {

        private String name;
        private String mass;
        private double price;

        private Key(String name, String mass, double price) {
            this.name = name;
            this.mass = mass;
            this.price = price;
        }

        public String getName() {
            return name;
        }

        public String getMass() {
            return mass;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public int hashCode() {
            return this.name.hashCode() + this.mass.hashCode() + ((int) price)  + 101;

        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }

            if (obj == null || !obj.getClass().equals(this.getClass())) {
                return false;
            }

            if (this.name.equals(((Key) obj).getName())) {
                if (this.mass.equals(((Key) obj).getMass())) {
                    return this.price == ((Key) obj).getPrice();
                }
            }

            return false;
        }

        @Override
        public int compareTo(Key key) {
            if (this == key) {
                return 0;
            }

            if (key != null) {
                return this.name.compareTo(key.name);
            }

            throw new NullPointerException();
         }

        @Override
        public String toString() {
            return "" + this.name.hashCode() + this.mass.hashCode() + ((int) price)  + 101;
        }
    }
}

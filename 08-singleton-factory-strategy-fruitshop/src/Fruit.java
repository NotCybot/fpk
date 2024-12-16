public abstract  class Fruit {
    protected String name;
    protected double price;

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    /**
     * Following classes are subclasses of Fruit
     */

    public class Apple extends Fruit {
        public Apple() {
            name = "Apple";
            price = 0.75;
        }
    }

    public class Orange extends Fruit {
        public Orange() {
            name = "Orange";
            price = 1.25;
        }
    }

    public class Banana extends Fruit {
        public Banana() {
            name = "Banana";
            price = 0.5;
        }
    }
}

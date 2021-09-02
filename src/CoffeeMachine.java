import java.util.*;

public class CoffeeMachine {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int waterContained = 400;
        int milkContained = 540;
        int coffeeBeansContained = 120;
        int disposableCupsContained = 9;
        int moneyContained = 550;

        doAction(scanner, waterContained, milkContained, coffeeBeansContained, disposableCupsContained, moneyContained);

        displayInfo(waterContained, milkContained, coffeeBeansContained, disposableCupsContained, moneyContained);
    }

    public static void doAction(Scanner scanner, int waterContained, int milkContained, int coffeeBeansContained,
                                int disposableCupsContained, int moneyContained) {
        System.out.println("Write action (buy, fill, take, remaining, exit):");

        String selection = scanner.nextLine();
        while (true) {
            switch (selection) {
                case "buy" -> buyCoffee(scanner, waterContained, milkContained, coffeeBeansContained,
                        disposableCupsContained, moneyContained);
                case "fill" -> {
                    System.out.println("Write how many ml of water you want to add:");
                    waterContained = addWater(scanner.nextInt(), waterContained);
                    System.out.println("Write how many ml of milk you want to add:");
                    milkContained = addMilk(scanner.nextInt(), milkContained);
                    System.out.println("Write how many grams of coffee beans you want to add:");
                    coffeeBeansContained = addCoffeeBeans(scanner.nextInt(), coffeeBeansContained);
                    System.out.println("Write how many disposable cups of coffee you want to add:");
                    disposableCupsContained = addDisposableCups(scanner.nextInt(), disposableCupsContained);
                    doAction(scanner, waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                            moneyContained);
                }
                case "take" -> {
                    System.out.println("I gave you " + moneyContained + "\n");
                    int moneyToTake = moneyContained;
                    moneyContained = takeMoneyOut(moneyToTake, moneyContained);
                    doAction(scanner, waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                            moneyContained);
                }
                case "remaining" -> {
                    displayInfo(waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                            moneyContained);
                    doAction(scanner, waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                            moneyContained);
                }
                case "exit" -> System.exit(0);
            }
            selection = scanner.nextLine();
        }
    }

    public static void displayInfo(int waterContained, int milkContained, int coffeeBeansContained,
                                   int disposableCupsContained, int moneyContained) {
        System.out.println("The coffee machine has:");
        System.out.println(waterContained + " ml of water");
        System.out.println(milkContained + " ml of milk");
        System.out.println(coffeeBeansContained + " g of coffee beans");
        System.out.println(disposableCupsContained + " disposable cups");
        System.out.println(moneyContained + "$ of money");
    }

    public static void buyCoffee(Scanner scanner, int waterContained, int milkContained, int coffeeBeansContained,
                                 int disposableCupsContained, int moneyContained) {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");

        switch (scanner.nextLine()) {
            case "1" -> {
                CoffeeType coffeeEspresso = new CoffeeType(250, 0, 16, 4);
                if (isIngredientsEnough(waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                        coffeeEspresso)) {
                    waterContained = waterContained - coffeeEspresso.water;
                    coffeeBeansContained = coffeeBeansContained - coffeeEspresso.coffeeBeans;
                    disposableCupsContained--;
                    moneyContained = moneyContained + coffeeEspresso.cost;
                }
                doAction(scanner, waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                        moneyContained);
            }
            case "2" -> {
                CoffeeType coffeeLatte = new CoffeeType(350, 75, 20, 7);
                if (isIngredientsEnough(waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                        coffeeLatte)) {
                    waterContained = waterContained - coffeeLatte.water;
                    milkContained = milkContained - coffeeLatte.milk;
                    coffeeBeansContained = coffeeBeansContained - coffeeLatte.coffeeBeans;
                    disposableCupsContained--;
                    moneyContained = moneyContained + coffeeLatte.cost;
                }
                doAction(scanner, waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                        moneyContained);
            }
            case "3" -> {
                CoffeeType coffeeCappuccino = new CoffeeType(200, 100, 12, 6);
                if (isIngredientsEnough(waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                        coffeeCappuccino)) {
                    waterContained = waterContained - coffeeCappuccino.water;
                    milkContained = milkContained - coffeeCappuccino.milk;
                    coffeeBeansContained = coffeeBeansContained - coffeeCappuccino.coffeeBeans;
                    disposableCupsContained--;
                    moneyContained = moneyContained + coffeeCappuccino.cost;
                }
                doAction(scanner, waterContained, milkContained, coffeeBeansContained, disposableCupsContained,
                        moneyContained);
            }
        }
    }

    public static boolean isIngredientsEnough(int waterContained, int milkContained, int coffeeBeansContained,
                                              int disposableCupsContained, CoffeeType coffee) {

        // a partisan method to get away with the division-by-0 issue in buying espresso, so coffee.milk is assigned 1,
//        even though it's not used
        if (coffee.milk == 0) coffee.milk = 1;

        if (waterContained / coffee.water < 1) {
            System.out.println("Sorry, not enough water!");
            return false;
        } else if (milkContained / coffee.milk < 1) {
            System.out.println("Sorry, not enough milk!");
            return false;
        } else if (coffeeBeansContained / coffee.coffeeBeans < 1) {
            System.out.println("Sorry, not enough coffee beans!");
            return false;
        } else if (disposableCupsContained < 1) {
            System.out.println("Sorry, not enough cups!");
            return false;
        } else
            System.out.println("I have enough resources, making you a coffee!");
        return true;
    }

    public static int addWater(int waterToAdd, int waterContained) {
        waterContained = waterToAdd + waterContained;
        return waterContained;
    }

    public static int addMilk(int milkToAdd, int milkContained) {
        milkContained = milkToAdd + milkContained;
        return milkContained;
    }

    public static int addCoffeeBeans(int coffeeBeansToAdd, int coffeeBeansContained) {
        coffeeBeansContained = coffeeBeansToAdd + coffeeBeansContained;
        return coffeeBeansContained;
    }

    public static int addDisposableCups(int disposableCupsToAdd, int disposableCupsContained) {
        disposableCupsContained = disposableCupsToAdd + disposableCupsContained;
        return disposableCupsContained;
    }

    public static int takeMoneyOut(int moneyToTake, int moneyContained) {
        moneyContained = moneyToTake - moneyContained;
        return moneyContained;
    }
}
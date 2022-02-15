import java.util.ArrayList;

public class MenuSystem {
    private ArrayList<DishType> breakfastMenu;
    private ArrayList<DishType> lunchMenu;
    private ArrayList<DishType> dinnerMenu;

    private record DishType(String name, MenuItem item) {}

    private record MenuItem(int id, String name, boolean multAllowed) {}

    MenuSystem(){
        breakfastMenu = new ArrayList<>();
        lunchMenu = new ArrayList<>();
        dinnerMenu = new ArrayList<>();

        breakfastMenu.add(new DishType("Main", new MenuItem(1, "Eggs", false)));
        breakfastMenu.add(new DishType("Side", new MenuItem(2, "Toast", false)));
        breakfastMenu.add(new DishType("Drink", new MenuItem(3, "Coffee", true)));

        lunchMenu.add(new DishType("Main", new MenuItem(1, "Sandwich", false)));
        lunchMenu.add(new DishType("Side", new MenuItem(2, "Chips", true)));
        lunchMenu.add(new DishType("Drink", new MenuItem(3, "Soda", false)));

        dinnerMenu.add(new DishType("Main", new MenuItem(1, "Steak", false)));
        dinnerMenu.add(new DishType("Side", new MenuItem(2, "Potatoes", false)));
        dinnerMenu.add(new DishType("Drink", new MenuItem(3, "Wine", false)));
        dinnerMenu.add(new DishType("Dessert", new MenuItem(4, "Cake", false)));
    }

    public Order getOrder(String type, ArrayList<Integer> itemNumbers){
        boolean foundVals[] = new boolean[4];
        ArrayList<PairStringInt> itemQuant = new ArrayList<>();

        itemQuant.add(new PairStringInt("temp", 0));
        itemQuant.add(new PairStringInt("temp", 0));
        itemQuant.add(new PairStringInt("temp", 0));

        type = type.toLowerCase();

        switch(type){
            case "breakfast":
                for(Integer i: itemNumbers){;
                    if(i > 3)
                        return new Order("Failure", null, "Unable to process: Item number not in the breakfast menu");

                    MenuItem current = breakfastMenu.get(i-1).item;

                    if(foundVals[i-1] && !current.multAllowed)
                        return new Order("Failure", null, "Unable to process: " + current.name + " cannot be ordered more than once");

                    Integer num = itemQuant.get(i-1).getI();

                    if(!foundVals[i-1])
                        itemQuant.get(i-1).setS(current.name);

                    itemQuant.get(i-1).setI(num + 1);

                    foundVals[i - 1] = true;
                }

                if(!foundVals[2]){
                    itemQuant.get(2).setS("Water");
                    foundVals[2] = true;
                }
                break;
            case "lunch":
                for(Integer i: itemNumbers){;
                    if(i > 3)
                        return new Order("Failure", null, "Unable to process: Item number not in the lunch menu");

                    MenuItem current = lunchMenu.get(i-1).item;

                    if(foundVals[i-1] && !current.multAllowed)
                        return new Order("Failure", null, "Unable to process: " + current.name + " cannot be ordered more than once");

                    Integer num = itemQuant.get(i-1).getI();

                    if(!foundVals[i-1])
                        itemQuant.get(i-1).setS(current.name);

                    itemQuant.get(i-1).setI(num + 1);

                    foundVals[i - 1] = true;
                }
                if(!foundVals[2]){
                    itemQuant.get(2).setS("Water");
                    foundVals[2] = true;
                }
                break;
            case "dinner":
                itemQuant.add(new PairStringInt("temp", 0));

                for(Integer i: itemNumbers){;
                    if(i > 4)
                        return new Order("Failure", null, "Unable to process: Item number not in the dinner menu");

                    MenuItem current = dinnerMenu.get(i-1).item;

                    if(foundVals[i-1] && !current.multAllowed)
                        return new Order("Failure", null, "Unable to process: " + current.name + " cannot be ordered more than once");

                    Integer num = itemQuant.get(i-1).getI();

                    if(!foundVals[i-1])
                        itemQuant.get(i-1).setS(current.name);

                    itemQuant.get(i-1).setI(num + 1);

                    foundVals[i - 1] = true;
                }

                if(foundVals[2]){
                    itemQuant.add(3, new PairStringInt("Water", 1));
                }
                else{
                    itemQuant.get(2).setS("Water");
                    itemQuant.get(2).setI(1);
                    foundVals[2] = true;
                }
                break;
        }

        if((type.equals("lunch") || type.equals("breakfast")) && foundVals[0] && foundVals[1] && foundVals[2])
            return new Order(type, itemQuant, "success");

        else if(type.equals("dinner") && foundVals[0] && foundVals[1] && foundVals[2] && foundVals[3])
            return new Order(type, itemQuant, "success");

        else{
            String temp = "";

            if(!foundVals[0])
                temp += "Main is missing, ";

            if(!foundVals[1])
                temp += "Side is missing, ";

            if(!foundVals[2])
                temp += "Drink is missing, ";

            if(!foundVals[3] && type.equals("dinner"))
                temp += "Dessert is missing, ";

            return new Order("Failure", null, temp.substring(0, temp.length() - 2));
        }
    }
}

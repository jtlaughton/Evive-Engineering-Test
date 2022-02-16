
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class MenuSystem {
    private HashMap<Integer, MenuItem> breakfastMenu;
    private HashMap<Integer, MenuItem> lunchMenu;
    private HashMap<Integer, MenuItem> dinnerMenu;

    private record MenuItem(Integer id, String name, String dishType, boolean multAllowed) {}

    MenuSystem(){
        breakfastMenu = new HashMap<>();
        lunchMenu = new HashMap<>();
        dinnerMenu = new HashMap<>();
    }

    public void removeMenuItem(Integer id, String menuType){
        menuType = menuType.toLowerCase();

        switch(menuType){
            case "breakfast":
                breakfastMenu.remove(id);
                break;
            case "lunch":
                lunchMenu.remove(id);
                break;
            case "dinner":
                dinnerMenu.remove(id);
                break;
        }
    }

    public boolean addMenuItem(String name, Integer id, String menuType, String dishType, boolean multAllowed ){
        menuType = menuType.toLowerCase();
        dishType = dishType.toLowerCase();

        if(!dishType.equals("main") && !dishType.equals("side") && !dishType.equals("drink") && !dishType.equals("dessert"))
            return false;

        if(dishType.equals("dessert") && !menuType.equals("dinner"))
            return false;

        switch(menuType){
            case "breakfast":
                if(breakfastMenu.get(id) != null)
                    return false;

                breakfastMenu.put(id, new MenuItem(id, name, dishType, multAllowed));
                return true;
            case "lunch":
                if(lunchMenu.get(id) != null)
                    return false;

                lunchMenu.put(id, new MenuItem(id, name, dishType, multAllowed));
                return true;
            case "dinner":
                if(dinnerMenu.get(id) != null)
                    return false;

                dinnerMenu.put(id, new MenuItem(id, name, dishType, multAllowed));
                return true;
            default:
                return false;
        }
    }

    public Order getOrder(String type, ArrayList<Integer> itemNumbers){
        ArrayList<HashMap<Integer, PairStringInt>> itemQuant = new ArrayList<>();

        int insertPosMain = 0;
        int insertPosSide = 1;
        int insertPosDrink = 2;
        int insertPosDessert = 3;

        itemQuant.add(new HashMap<>());
        itemQuant.add(new HashMap<>());
        itemQuant.add(new HashMap<>());

        type = type.toLowerCase();

        boolean mainFound = false;
        boolean sideFound = false;
        boolean drinkFound = false;
        boolean dessertFound = false;

        boolean addWaterAfter = false;

        HashMap<Integer, MenuItem> current;

        switch(type){
            case "breakfast":
                current = breakfastMenu;
                break;
            case "lunch":
                current = lunchMenu;
                break;
            case "dinner":
                current = dinnerMenu;
                itemQuant.add(new HashMap<>());
                break;
            default:
                return new Order("Failure", null, "Unable to process: Not a valid menu type");
        }

        for(Integer i: itemNumbers){;
            MenuItem item = current.get(i);

            if(item == null)
                return new Order("Failure", null, "Unable to process: Item number not in " + type + " menu");

            int position = -1;

            switch(item.dishType()){
                case "main":
                    position = 0;
                    mainFound = true;
                    break;
                case "side":
                    position = 1;
                    sideFound = true;
                    break;
                case "drink":
                    position = 2;
                    drinkFound = true;
                    break;
                case "dessert":
                    position = 3;
                    dessertFound = true;
                    break;
            }

            PairStringInt prevStored = itemQuant.get(position).get(item.id());

            if(prevStored == null){
                itemQuant.get(position).put(item.id, new PairStringInt(item.name(), 1));
            }

            else{
                Integer val = itemQuant.get(position).get(item.id()).getI();

                if(val >= 1 && item.multAllowed())
                    itemQuant.get(position).get(item.id()).setI(val + 1);
                else
                    return new Order("Failure", null, "Unable to process: " + item.name() + " cannot be ordered more than once");
            }
        }

        if(drinkFound && type.equals("dinner")){
            addWaterAfter = true;
        }
        else if(!drinkFound){
            itemQuant.get(2).put(0, new PairStringInt("Water", 1));
            drinkFound = true;
        }

        ArrayList<PairStringInt> listifiedHashMap = new ArrayList<>();

        int counter = 0;
        for(HashMap<Integer, PairStringInt> map: itemQuant){
            Collection<PairStringInt> values = map.values();

            listifiedHashMap.addAll(values);

            if(addWaterAfter && (counter == 2))
                listifiedHashMap.add(new PairStringInt("Water", 1));

            counter++;
        }

        if((type.equals("lunch") || type.equals("breakfast")) && mainFound && sideFound && drinkFound)
            return new Order(type, listifiedHashMap, "success");

        else if(type.equals("dinner") && mainFound && sideFound && drinkFound && dessertFound)
            return new Order(type, listifiedHashMap, "success");

        else{
            String temp = "Unable to process: ";

            if(!mainFound)
                temp += "Main is missing, ";

            if(!sideFound)
                temp += "Side is missing, ";

            if(!dessertFound && type.equals("dinner"))
                temp += "Dessert is missing, ";

            return new Order("Failure", null, temp.substring(0, temp.length() - 2));
        }
    }
}

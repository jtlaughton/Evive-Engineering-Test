package com.gradle.evive;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

// The main idea here was to take the menu system described and make it somewhat generic
// This means new menu items can be added as long as they're for breakfast, lunch, and dinner,
// and the item is either a main, side, drink, or dessert
public class MenuSystem {
    // Use HashMaps here so that it's fast lookup for potentially high number of menu items
    private HashMap<Integer, MenuItem> breakfastMenu;
    private HashMap<Integer, MenuItem> lunchMenu;
    private HashMap<Integer, MenuItem> dinnerMenu;

    // Internal record for keeping track of menu item data
    private record MenuItem(Integer id, String name, String dishType, boolean multAllowed) {}

    // initialize hashmaps
    MenuSystem(){
        breakfastMenu = new HashMap<>();
        lunchMenu = new HashMap<>();
        dinnerMenu = new HashMap<>();
    }

    /**
     * This function is used to remove a given menu item by id from it's associated menu
     *
     * @param id the id of the menu item
     * @param menuType the type of menu ("breakfast", "lunch", "dinner")
     */
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

    /**
     * Adds a new menu item to its respective menu (breakfast, lunch, or dinner). New item has to be a main, side,
     * drink, or dessert (for dinner only).
     *
     * @param name The name of the new menu item
     * @param id The id of the new menu item
     * @param menuType the type of menu ("breakfast", "lunch", "dinner")
     * @param dishType the type of dish ("main", "side", "drink", "dessert")
     * @param multAllowed boolean that describes whether
     *
     * @return Returns true if it was properly added, and false if otherwise. False occurs when dishType is not
     * "main", "side", "drink", or "dessert" (only for dinner). Another condition is when menuType is not "breakfast",
     * "lunch", "drink", "dessert"
     */
    public boolean addMenuItem(String name, Integer id, String menuType, String dishType, boolean multAllowed ){
        menuType = menuType.toLowerCase();
        dishType = dishType.toLowerCase();

        // Make sure dish type is main, side, drink, or dessert
        if(!dishType.equals("main") && !dishType.equals("side") && !dishType.equals("drink") && !dishType.equals("dessert"))
            return false;

        // Make sure dessert is only being added to dinner
        if(dishType.equals("dessert") && !menuType.equals("dinner"))
            return false;

        // Add to proper hashmap
        switch(menuType){
            case "breakfast":
                // don't add if something is already there
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

    /**
     * getOrder() builds a list of items ordered and their quantity determined by item numbers
     *
     * @param type The type of menu ("breakfast", "lunch", "dinner")
     * @param itemNumbers The list of items ordered by number
     *
     * @return Returns an Order record that contains the type of order ("breakfast", "lunch", "dinner", "Failure"),
     * The list of item names with their quantities, and a message. "Failure" type is used to signify that the method
     * failed to get the order. This can happen for different reasons (invalid menu type, item number not in menu,
     * item ordered more than once when it shouldn't be, or missing a main, side, or dessert (for dinner)). The message
     * outlines the reason why
     * */
    public Order getOrder(String type, ArrayList<Integer> itemNumbers){
        // This is for keeping track of the items found by item number. ArrayList of HashMaps was used
        // To allow for multiple orders of the same dish type
        ArrayList<HashMap<Integer, PairStringInt>> itemQuant = new ArrayList<>();

        itemQuant.add(new HashMap<>());
        itemQuant.add(new HashMap<>());
        itemQuant.add(new HashMap<>());

        // avoid input problems
        type = type.toLowerCase();

        // needed for tracking if we have all dish types
        boolean mainFound = false;
        boolean sideFound = false;
        boolean drinkFound = false;
        boolean dessertFound = false;

        // needed for dinner to make sure water is at the end
        boolean addWaterAfter = false;

        HashMap<Integer, MenuItem> current;

        // set current based on type
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

        // loop through the itemNumbers and add them to itemQuant
        for(Integer i: itemNumbers){;
            MenuItem item = current.get(i);

            // not in the list
            if(item == null)
                return new Order("Failure", null, "Unable to process: Item number not in " + type + " menu");

            int position = -1;

            // determine which HashMap to access and indicate that a dish type was found
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

            // See if we had a previous value or not
            PairStringInt prevStored = itemQuant.get(position).get(item.id());

            // if not add it to HashMap
            if(prevStored == null){
                itemQuant.get(position).put(item.id, new PairStringInt(item.name(), 1));
            }

            // otherwise determine if we should update the quantity or return a failure
            else{
                Integer val = itemQuant.get(position).get(item.id()).getI();

                if(val >= 1 && item.multAllowed())
                    itemQuant.get(position).get(item.id()).setI(val + 1);
                else
                    return new Order("Failure", null, "Unable to process: " + item.name() + " cannot be ordered more than once");
            }
        }

        // If we are in dinner and found a drink indicate to add water at the end
        if(drinkFound && type.equals("dinner")){
            addWaterAfter = true;
        }
        // otherwise add water to drink category
        else if(!drinkFound){
            itemQuant.get(2).put(0, new PairStringInt("Water", 1));
            drinkFound = true;
        }

        // list for turning HashMaps into one list
        ArrayList<PairStringInt> listifiedHashMap = new ArrayList<>();

        // Add all HashMap values to listifiedHashMap
        int counter = 0;
        for(HashMap<Integer, PairStringInt> map: itemQuant){
            Collection<PairStringInt> values = map.values();

            listifiedHashMap.addAll(values);

            if(addWaterAfter && (counter == 2))
                listifiedHashMap.add(new PairStringInt("Water", 1));

            counter++;
        }

        // return success and order names if we found all dish types
        if((type.equals("lunch") || type.equals("breakfast")) && mainFound && sideFound && drinkFound)
            return new Order(type, listifiedHashMap, "success");

        else if(type.equals("dinner") && mainFound && sideFound && drinkFound && dessertFound)
            return new Order(type, listifiedHashMap, "success");

        // otherwise we didn't find all dish types and return a failure indicating that
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

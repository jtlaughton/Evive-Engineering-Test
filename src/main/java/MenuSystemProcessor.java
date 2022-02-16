import java.util.ArrayList;
import java.util.Scanner;

public class MenuSystemProcessor {
    public static void main(String[] args){
        MenuSystem menu = new MenuSystem();

        menu.addMenuItem("Eggs", 1, "breakfast", "main", false);
        menu.addMenuItem("Toast", 2, "breakfast", "side", false);
        menu.addMenuItem("Coffee", 3, "breakfast", "drink", true);

        menu.addMenuItem("Sandwich", 1, "lunch", "main", false);
        menu.addMenuItem("Chips", 2, "lunch", "side", true);
        menu.addMenuItem("Soda", 3, "lunch", "drink", false);

        menu.addMenuItem("Steak", 1, "dinner", "main", false);
        menu.addMenuItem("Potatoes", 2, "dinner", "side", false);
        menu.addMenuItem("Wine", 3, "dinner", "drink", false);
        menu.addMenuItem("Cake", 4, "dinner", "dessert", false);

        System.out.println("Enter Your Order Below Like 'meal items'. Enter Items As Comma Separated List. Enter 'quit' To Exit: ");
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        str = str.toLowerCase();
        str = str.trim();

        while(!str.equals("quit")){
            String parts[];

            parts = str.split(" ", 2);

            if(parts.length != 2){
                Order theOrder = menu.getOrder(str, new ArrayList<>());
                System.out.println(theOrder.message());
            }
            else{
                String menuType = parts[0];
                String food = parts[1];

                food = food.replaceAll(" ", "");

                ArrayList<Integer> items = new ArrayList<>();

                String nums[] = food.split(",");

                for(int i = 0; i < nums.length; i++){
                    items.add(Integer.parseInt(nums[i]));
                }

                Order theOrder = menu.getOrder(menuType, items);

                if(theOrder.type().equals("Failure")){
                    System.out.println(theOrder.message());
                }
                else{
                    String temp = "";

                    for(PairStringInt e: theOrder.itemAndQuant()){
                        temp += e.getS();

                        if(e.getI() > 1)
                            temp += "(" + e.getI() + ")";

                        temp += ", ";
                    }

                    System.out.println(temp.substring(0, temp.length() - 2));
                }
            }

            System.out.println();
            str = sc.nextLine();
            str = str.toLowerCase();
            str = str.trim();
        }
    }
}

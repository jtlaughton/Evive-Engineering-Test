package com.gradle.evive;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

public class MenuSystemTest {
    private MenuSystem menu;

    @BeforeEach
    public void initEach(){
        menu = new MenuSystem();

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
    }

    // Tests for correct input
    @Test
    @DisplayName("Checks that a correct number of items were returned for breakfast order")
    void testBreakfastOrderSize(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);
        items.add(3);

        Order order = menu.getOrder("Breakfast", items);
        Assertions.assertEquals(3, order.itemAndQuant().size());
    }

    @Test
    @DisplayName("Checks that a correct number of items were returned for lunch order")
    void testLunchOrderSize(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);
        items.add(3);

        Order order = menu.getOrder("Lunch", items);
        Assertions.assertEquals(3, order.itemAndQuant().size());
    }

    @Test
    @DisplayName("Checks that a correct number of items were returned for dinner order with no drink")
    void testDinnerOrderSizeNoDrink(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);
        items.add(4);

        Order order = menu.getOrder("Dinner", items);
        Assertions.assertEquals(4, order.itemAndQuant().size());
    }

    @Test
    @DisplayName("Checks that a correct number of items were returned for dinner order with drink")
    void testDinnerOrderSizeDrink(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);

        Order order = menu.getOrder("Dinner", items);
        Assertions.assertEquals(5, order.itemAndQuant().size());
    }

    @ParameterizedTest
    @DisplayName("Tests that breakfast items are returned")
    @ValueSource(ints = {0, 1, 2})
    void testBreakfastInOrder(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);
        items.add(3);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Eggs", 1));
        actual.add(new PairStringInt("Toast", 1));
        actual.add(new PairStringInt("Coffee", 1));

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                             () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that breakfast items are returned (no drink)")
    @ValueSource(ints = {0, 1, 2})
    void testBreakfastInOrderNoDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Eggs", 1));
        actual.add(new PairStringInt("Toast", 1));
        actual.add(new PairStringInt("Water", 1));

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }


    @ParameterizedTest
    @DisplayName("Tests that lunch items are returned")
    @ValueSource(ints = {0, 1, 2})
    void testLunchInOrder(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);
        items.add(3);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Sandwich", 1));
        actual.add(new PairStringInt("Chips", 1));
        actual.add(new PairStringInt("Soda", 1));

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                             () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that lunch items are returned (no drink)")
    @ValueSource(ints = {0, 1, 2})
    void testLunchInOrderNoDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Sandwich", 1));
        actual.add(new PairStringInt("Chips", 1));
        actual.add(new PairStringInt("Water", 1));

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that dinner items are returned (no drink)")
    @ValueSource(ints = {0, 1, 2, 3})
    void testDinnerInOrderNoDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);
        items.add(4);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Steak", 1));
        actual.add(new PairStringInt("Potatoes", 1));
        actual.add(new PairStringInt("Water", 1));
        actual.add(new PairStringInt("Cake", 1));

        Order order = menu.getOrder("dinner", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                             () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that dinner items are returned (with drink)")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testDinnerInOrderDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Steak", 1));
        actual.add(new PairStringInt("Potatoes", 1));
        actual.add(new PairStringInt("Wine", 1));
        actual.add(new PairStringInt("Water", 1));
        actual.add(new PairStringInt("Cake", 1));

        Order order = menu.getOrder("dinner", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                             () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that breakfast items are returned in right order")
    @ValueSource(ints = {0, 1, 2})
    void testBreakfastMixed(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(2);
        items.add(1);
        items.add(3);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Eggs", 1));
        actual.add(new PairStringInt("Toast", 1));
        actual.add(new PairStringInt("Coffee", 1));

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that breakfast items are returned in right order (no drink)")
    @ValueSource(ints = {0, 1, 2})
    void testBreakfastMixedNoDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(2);
        items.add(1);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Eggs", 1));
        actual.add(new PairStringInt("Toast", 1));
        actual.add(new PairStringInt("Water", 1));

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that lunch items are returned in right order")
    @ValueSource(ints = {0, 1, 2})
    void testLunchMixed(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(3);
        items.add(1);
        items.add(2);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Sandwich", 1));
        actual.add(new PairStringInt("Chips", 1));
        actual.add(new PairStringInt("Soda", 1));

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that lunch items are returned in right order (no drink)")
    @ValueSource(ints = {0, 1, 2})
    void testLunchMixedNoDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(2);
        items.add(1);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Sandwich", 1));
        actual.add(new PairStringInt("Chips", 1));
        actual.add(new PairStringInt("Water", 1));

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that dinner items are returned in right order (no drink)")
    @ValueSource(ints = {0, 1, 2, 3})
    void testDinnerMixedNoDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(4);
        items.add(2);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Steak", 1));
        actual.add(new PairStringInt("Potatoes", 1));
        actual.add(new PairStringInt("Water", 1));
        actual.add(new PairStringInt("Cake", 1));

        Order order = menu.getOrder("dinner", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that dinner items are returned in right order (with drink)")
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void testDinnerMixedDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(2);
        items.add(1);
        items.add(4);
        items.add(3);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Steak", 1));
        actual.add(new PairStringInt("Potatoes", 1));
        actual.add(new PairStringInt("Wine", 1));
        actual.add(new PairStringInt("Water", 1));
        actual.add(new PairStringInt("Cake", 1));

        Order order = menu.getOrder("dinner", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that breakfast items have right quantity")
    @ValueSource(ints = {0, 1, 2})
    void testBreakfastMultiple(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(2);
        items.add(1);
        items.add(3);
        items.add(3);
        items.add(3);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Eggs", 1));
        actual.add(new PairStringInt("Toast", 1));
        actual.add(new PairStringInt("Coffee", 3));

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that lunch items have right quantity")
    @ValueSource(ints = {0, 1, 2})
    void testLunchMultiple(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(3);
        items.add(1);
        items.add(2);
        items.add(2);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Sandwich", 1));
        actual.add(new PairStringInt("Chips", 2));
        actual.add(new PairStringInt("Soda", 1));

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that lunch items have right quantity (no drink)")
    @ValueSource(ints = {0, 1, 2})
    void testLunchMultipleNoDrink(int i){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(2);
        items.add(1);
        items.add(2);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Sandwich", 1));
        actual.add(new PairStringInt("Chips", 2));
        actual.add(new PairStringInt("Water", 1));

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that breakfast items are correct when new item is added")
    @ValueSource(ints = {0, 1, 2, 3})
    void testBreakfastNewItem(int i){
        menu.addMenuItem("Omelette", 4, "breakfast", "main", true);

        ArrayList<Integer> items = new ArrayList<>();

        items.add(2);
        items.add(1);
        items.add(3);
        items.add(4);
        items.add(4);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Eggs", 1));
        actual.add(new PairStringInt("Omelette", 2));
        actual.add(new PairStringInt("Toast", 1));
        actual.add(new PairStringInt("Coffee", 1));

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that lunch items are correct when new item is added")
    @ValueSource(ints = {0, 1, 2, 3})
    void testLunchNewItem(int i){
        menu.addMenuItem("Fries", 4, "lunch", "side", true);

        ArrayList<Integer> items = new ArrayList<>();

        items.add(3);
        items.add(1);
        items.add(2);
        items.add(2);
        items.add(4);
        items.add(4);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Sandwich", 1));
        actual.add(new PairStringInt("Chips", 2));
        actual.add(new PairStringInt("Fries", 2));
        actual.add(new PairStringInt("Soda", 1));

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    @ParameterizedTest
    @DisplayName("Tests that dinner items are correct when new item is added")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5})
    void testDinnerNewItem(int i){
        menu.addMenuItem("Beer", 5, "dinner", "drink", true);

        ArrayList<Integer> items = new ArrayList<>();

        items.add(2);
        items.add(1);
        items.add(4);
        items.add(3);
        items.add(5);
        items.add(5);

        ArrayList<PairStringInt> actual = new ArrayList<>();

        actual.add(new PairStringInt("Steak", 1));
        actual.add(new PairStringInt("Potatoes", 1));
        actual.add(new PairStringInt("Wine", 1));
        actual.add(new PairStringInt("Beer", 2));
        actual.add(new PairStringInt("Water", 1));
        actual.add(new PairStringInt("Cake", 1));

        Order order = menu.getOrder("dinner", items);

        Assertions.assertAll(() -> Assertions.assertEquals(actual.get(i).getS(), order.itemAndQuant().get(i).getS()),
                () -> Assertions.assertEquals(actual.get(i).getI(), order.itemAndQuant().get(i).getI()));
    }

    // Testing for failures and bad input to addMenuItem
    @Test
    @DisplayName("Tests that getOrder() fails when one dish type is missing")
    void testMissingDish(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(3);

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals("Failure", order.type()),
                () -> Assertions.assertEquals(order.message(), "Unable to process: Side is missing"));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when two dish types are missing")
    void testTwoMissingDishes(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);

        Order order = menu.getOrder("Dinner", items);

        Assertions.assertAll(() -> Assertions.assertEquals( "Failure", order.type()),
                () -> Assertions.assertEquals("Unable to process: Side is missing, Dessert is missing", order.message()));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when all dish types are missing")
    void testAllMissingDishes(){
        ArrayList<Integer> items = new ArrayList<>();

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals("Failure", order.type()),
                () -> Assertions.assertEquals( "Unable to process: Main is missing, Side is missing", order.message()));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when invalid menu type")
    void testInvalidMenuType(){
        ArrayList<Integer> items = new ArrayList<>();

        Order order = menu.getOrder("Brunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals("Failure", order.type()),
                () -> Assertions.assertEquals( "Unable to process: Not a valid menu type", order.message()));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when invalid menu item id for breakfast")
    void testInvalidMenuItemBreakfast(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(7);

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals( "Failure", order.type()),
                () -> Assertions.assertEquals( "Unable to process: Item number not in breakfast menu", order.message()));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when invalid menu item id for lunch")
    void testInvalidMenuItemLunch(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(7);

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals( "Failure", order.type()),
                () -> Assertions.assertEquals( "Unable to process: Item number not in lunch menu", order.message()));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when invalid menu item id for dinner")
    void testInvalidMenuItemDinner(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(7);

        Order order = menu.getOrder("Dinner", items);

        Assertions.assertAll(() -> Assertions.assertEquals( "Failure", order.type()),
                () -> Assertions.assertEquals( "Unable to process: Item number not in dinner menu", order.message()));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when ordering multiple of a single order item for breakfast")
    void testWrongMultipleBreakfast(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(1);
        items.add(2);
        items.add(3);

        Order order = menu.getOrder("Breakfast", items);

        Assertions.assertAll(() -> Assertions.assertEquals( "Failure", order.type()),
                () -> Assertions.assertEquals( "Unable to process: Eggs cannot be ordered more than once", order.message()));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when ordering multiple of a single order item for lunch")
    void testWrongMultipleLunch(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(1);
        items.add(2);
        items.add(3);

        Order order = menu.getOrder("Lunch", items);

        Assertions.assertAll(() -> Assertions.assertEquals( "Failure", order.type()),
                () -> Assertions.assertEquals( "Unable to process: Sandwich cannot be ordered more than once", order.message()));
    }

    @Test
    @DisplayName("Tests that getOrder() fails when ordering multiple of a single order item for dinner")
    void testWrongMultipleDinner(){
        ArrayList<Integer> items = new ArrayList<>();

        items.add(1);
        items.add(1);
        items.add(2);
        items.add(3);
        items.add(4);

        Order order = menu.getOrder("Dinner", items);

        Assertions.assertAll(() -> Assertions.assertEquals( "Failure", order.type()),
                () -> Assertions.assertEquals( "Unable to process: Steak cannot be ordered more than once", order.message()));
    }

    @Test
    @DisplayName("Test that addMenuItem() returns false when we have a wrong dish type")
    void testAddWrongDish(){
        Assertions.assertFalse(menu.addMenuItem("Test", 10, "dinner", "pasta", false));
    }

    @Test
    @DisplayName("Test that addMenuItem() returns false when we have a wrong menu type")
    void testAddWrongMenu(){
        Assertions.assertFalse(menu.addMenuItem("Test", 10, "brunch", "main", false));
    }

    @Test
    @DisplayName("Test that addMenuItem() returns false when we have a dessert for a non dinner menu")
    void testAddDessertNotDinner(){
        Assertions.assertFalse(menu.addMenuItem("Test", 10, "breakfast", "dessert", false));
    }

    @Test
    @DisplayName("Test that addMenuItem() returns false when we have add an existing id")
    void testAddExistingID(){
        Assertions.assertFalse(menu.addMenuItem("Test", 1, "breakfast", "main", false));
    }


}

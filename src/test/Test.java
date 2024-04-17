package test;

import exception.WarehouseException;
import model.Material;
import model.MaterialType;
import observer.WarehouseObserver;
import observerImpl.TestObserver;
import warehouse.Warehouse;

public class Test {
    public static void test1() {
        MaterialType iron = new MaterialType("Iron", "asd", "aqwe", 1000);
        MaterialType copper = new MaterialType("Copper", "sad", "gsda", 800);

        Warehouse warehouse1 = new Warehouse();
        Warehouse warehouse2 = new Warehouse();

        WarehouseObserver testObserver = new TestObserver();
        warehouse1.registerObserver(testObserver);
        warehouse1.registerObserver(testObserver);

        Material ironMaterial = new Material(iron, 500);
        Material copperMaterial = new Material(copper, 700);

        try {
            warehouse1.addMaterial(ironMaterial);
            warehouse1.addMaterial(copperMaterial);
        } catch (WarehouseException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Warehouse 1 data:");
        warehouse1.printWarehouseData();

        try {
            warehouse1.removeMaterial(ironMaterial);
            warehouse1.removeMaterial(copperMaterial);
        } catch (WarehouseException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Warehouse 1 data after removal:");
        warehouse1.printWarehouseData();

        try {
            warehouse2.moveMaterial(ironMaterial, warehouse1);
            warehouse2.moveMaterial(copperMaterial, warehouse1);
        } catch (WarehouseException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Warehouse 2 data after moving:");
        warehouse2.printWarehouseData();
    }
}

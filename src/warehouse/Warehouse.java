package warehouse;

import exception.WarehouseException;
import model.Material;
import model.MaterialType;
import observer.WarehouseObserver;
import service.MaterialValidationService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Warehouse {
    private Map<MaterialType, Integer> materials;
    private Set<WarehouseObserver> observers;

    public Warehouse() {
        materials = new HashMap<>();
        observers = new HashSet<>();
    }

    public void addMaterial(Material material) throws WarehouseException {
        MaterialValidationService.validateMaterial(material);

        MaterialType type = material.getMaterialType();
        int currQuantity = materials.getOrDefault(type, 0);
        int maxCapacity = type.getMaxCapacity();

        if (currQuantity == maxCapacity) {
            throw new WarehouseException("Not enough space");
        }

        int newQuantity = material.getQuantity();
        int availableCapacity = maxCapacity - currQuantity;

        if (newQuantity > availableCapacity) {
            System.out.println("Only " + availableCapacity + " " + type.getName() + " was added form " + newQuantity);
            newQuantity = availableCapacity;
        } else {
            System.out.println(newQuantity + " " + type.getName() + " was added to " + this);
        }

        materials.put(type, currQuantity + newQuantity);
        notifyObserversOnMaterialAdded(material);
    }

    public void removeMaterial(Material material) throws WarehouseException {
        MaterialValidationService.validateMaterial(material);

        MaterialType type = material.getMaterialType();
        int currQuantity = materials.getOrDefault(type, 0);
        int removeQuantity = material.getQuantity();

        if (removeQuantity > currQuantity) {
            throw new WarehouseException("Not enough material");
        }

        System.out.println(removeQuantity + " " + type.getName() + " was removed from " + this);
        materials.put(type, currQuantity - removeQuantity);
        notifyObserversOnMaterialRemoved(material);
    }

    public void moveMaterial(Material material, Warehouse destination) throws WarehouseException {
        MaterialValidationService.validateMaterial(material);

        MaterialType type = material.getMaterialType();
        int destCurrQuantity = destination.materials.getOrDefault(type, 0);
        int quantityToMove = getQuantityToMove(material, type, destCurrQuantity);

        Material materialToMove = new Material(type, quantityToMove);

        removeMaterial(materialToMove);
        destination.addMaterial(materialToMove);

        System.out.println(quantityToMove + " " + type.getName() + " was moved to the destination warehouse");
    }

    public void printWarehouseData() {
        if (materials.isEmpty()) {
            System.out.println("warehouse.Warehouse is empty");
            return;
        }

        for (Map.Entry<MaterialType, Integer> entry : materials.entrySet()) {
            MaterialType type = entry.getKey();
            int quantity = entry.getValue();
            System.out.println("warehouse.Warehouse: " + this + ", " + type.getName() + ": " + quantity);
        }
        System.out.println("\n");
    }

    public void registerObserver(WarehouseObserver observer) {
        observers.add(observer);
    }

    public void unregisterObserver(WarehouseObserver observer) {
        observers.remove(observer);
    }

    private void notifyObserversOnMaterialAdded(Material material) {
        for (WarehouseObserver observer: observers) {
            observer.onMaterialAdded(material);
        }
    }

    private void notifyObserversOnMaterialRemoved(Material material) {
        for (WarehouseObserver observer: observers) {
            observer.onMaterialRemoved(material);
        }
    }

    private static int getQuantityToMove(Material material, MaterialType type, int currQuantity) throws WarehouseException {
        int moveQuantity = material.getQuantity();
        int maxCapacity = type.getMaxCapacity();

        if (moveQuantity <= 0) {
            throw new WarehouseException("Invalid quantity to move: " + moveQuantity);
        }

        int availableCapacity = maxCapacity - currQuantity;

        int quantityToMove = Math.min(moveQuantity, availableCapacity);

        if (quantityToMove <= 0) {
            throw new WarehouseException("Not enough space in the source warehouse");
        }
        return quantityToMove;
    }
}

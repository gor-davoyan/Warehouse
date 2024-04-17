package model;

public class Material {
    private MaterialType materialType;
    private int quantity;

    public Material(MaterialType materialType, int capacity) {
        this.materialType = materialType;
        this.quantity = capacity;
    }

    public MaterialType getMaterialType() {
        return materialType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

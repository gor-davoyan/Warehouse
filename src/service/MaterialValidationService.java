package service;

import model.Material;
import model.MaterialType;

import java.util.Objects;

public class MaterialValidationService {

    public static void validateMaterial(Material material) {
        Objects.requireNonNull(material, "material cant be null");
        MaterialType type = material.getMaterialType();
        Objects.requireNonNull(type, "material type cant be null");

        int quantity = material.getQuantity();
        if (quantity < 0) {
            throw new IllegalArgumentException("material quantity cant be negative");
        }
    }
}

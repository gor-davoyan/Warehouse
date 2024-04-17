package observer;

import model.Material;

public interface WarehouseObserver {
    void onMaterialAdded(Material material);
    void onMaterialRemoved(Material material);
}

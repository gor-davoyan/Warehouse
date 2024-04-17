package observerImpl;

import model.Material;
import observer.WarehouseObserver;

public class TestObserver implements WarehouseObserver {
    @Override
    public void onMaterialAdded(Material material) {
        System.out.println("add observer called");
    }

    @Override
    public void onMaterialRemoved(Material material) {
        System.out.println("remove observer called");
    }
}

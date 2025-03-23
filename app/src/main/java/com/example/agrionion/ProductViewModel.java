package com.example.agrionion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private final MutableLiveData<List<Product>> productList = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> myProducts = new MutableLiveData<>();

    public ProductViewModel() {
        // Initialize both lists
        List<Product> initialProducts = new ArrayList<>();
        initialProducts.add(new Product("Eggplant", "₱96/kg", "Seller A", R.drawable.eggplant));
        initialProducts.add(new Product("Chinese cabbage", "₱82/kg", "Seller B", R.drawable.petchay));
        initialProducts.add(new Product("String Beans", "₱98/kg", "Seller C", R.drawable.string_beans));
        initialProducts.add(new Product("Chili", "₱599/kg", "Seller D", R.drawable.chili));

        productList.setValue(initialProducts);
        myProducts.setValue(new ArrayList<>()); // Start empty
    }

    // Returns all products (default + user-added)
    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    // Returns only user-added products
    public LiveData<List<Product>> getMyProductList() {
        return myProducts;
    }

    // Adds a new product to both lists
    public void addProduct(Product product) {
        List<Product> updatedProductList = new ArrayList<>(productList.getValue());
        updatedProductList.add(product);
        productList.setValue(updatedProductList); // Update full product list

        List<Product> updatedMyProducts = new ArrayList<>(myProducts.getValue());
        updatedMyProducts.add(product);
        myProducts.setValue(updatedMyProducts); // Update only "My Products"
    }
}

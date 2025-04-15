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
        initialProducts.add(new Product("Eggplant", "123 Farm Rd, Village", "Seller A", "₱96/kg", R.drawable.eggplant));
        initialProducts.add(new Product("Chinese Cabbage", "456 Green St, Town", "Seller B", "₱82/kg", R.drawable.petchay));
        initialProducts.add(new Product("String Beans", "789 Market Ave, City", "Seller C", "₱98/kg", R.drawable.string_beans));
        initialProducts.add(new Product("Chili", "101 Spice Ln, Village", "Seller D", "₱599/kg", R.drawable.chili));

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
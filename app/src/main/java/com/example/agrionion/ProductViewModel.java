package com.example.agrionion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {
    private final MutableLiveData<List<Product>> productList = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> myProducts = new MutableLiveData<>();
    private final MutableLiveData<List<Product>> processingProducts = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Product>> completedProducts = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Product>> cancelledProducts = new MutableLiveData<>(new ArrayList<>());

    public ProductViewModel() {
        // Initialize product list with available products
        List<Product> initialProducts = new ArrayList<>();
        initialProducts.add(new Product(
                "Eggplant", "123 Farm Rd, Village", "Seller A", "₱96/kg", R.drawable.eggplant,
                null, 96.00, null, null, "AVAILABLE"
        ));
        initialProducts.add(new Product(
                "Chinese Cabbage", "456 Green St, Town", "Seller B", "₱82/kg", R.drawable.petchay,
                null, 82.00, null, null, "AVAILABLE"
        ));
        initialProducts.add(new Product(
                "String Beans", "789 Market Ave, City", "Seller C", "₱98/kg", R.drawable.string_beans,
                null, 98.00, null, null, "AVAILABLE"
        ));
        initialProducts.add(new Product(
                "Chili", "101 Spice Ln, Village", "Seller D", "₱599/kg", R.drawable.chili,
                null, 599.00, null, null, "AVAILABLE"
        ));
        productList.setValue(initialProducts);
        myProducts.setValue(new ArrayList<>());

        // Initialize sample data for processing products
        List<Product> initialProcessing = new ArrayList<>();
        initialProcessing.add(new Product(
                "1290", "Eggplant", "123 Farm Rd, Village", "Seller A", "₱96/kg", R.drawable.eggplant,
                "2025-04-20", 96.00, "John Doe", "2", "PROCESSING"
        ));
        initialProcessing.add(new Product(
                "1291", "Chinese Cabbage", "456 Green St, Town", "Seller B", "₱82/kg", R.drawable.petchay,
                "2025-04-21", 82.00, "Jane Smith", "1", "PROCESSING"
        ));
        processingProducts.setValue(initialProcessing);

        // Initialize sample data for completed products
        List<Product> initialCompleted = new ArrayList<>();
        initialCompleted.add(new Product(
                "2356", "String Beans", "789 Market Ave, City", "Seller C", "₱98/kg", R.drawable.string_beans,
                "2025-03-20", 98.00, "Alice Johnson", "3", "COMPLETED"
        ));
        initialCompleted.add(new Product(
                "7532", "Chili", "101 Spice Ln, Village", "Seller D", "₱599/kg", R.drawable.chili,
                "2025-03-21", 599.00, "Bob Brown", "1", "COMPLETED"
        ));
        completedProducts.setValue(initialCompleted);

        // Initialize sample data for cancelled products
        List<Product> initialCancelled = new ArrayList<>();
        initialCancelled.add(new Product(
                "1423", "Eggplant", "123 Farm Rd, Village", "Seller A", "₱96/kg", R.drawable.eggplant,
                "2025-03-22", 96.00, "Carol White", "1", "CANCELLED"
        ));
        initialCancelled.add(new Product(
                "1952", "String Beans", "789 Market Ave, City", "Seller C", "₱98/kg", R.drawable.string_beans,
                "2025-03-23", 98.00, "Dave Green", "2", "CANCELLED"
        ));
        cancelledProducts.setValue(initialCancelled);
    }

    // Returns all products (available + user-added)
    public LiveData<List<Product>> getProductList() {
        return productList;
    }

    // Returns only user-added products
    public LiveData<List<Product>> getMyProductList() {
        return myProducts;
    }

    // Returns processing products
    public LiveData<List<Product>> getProcessingProducts() {
        return processingProducts;
    }

    // Returns completed products
    public LiveData<List<Product>> getCompletedProducts() {
        return completedProducts;
    }

    // Returns cancelled products
    public LiveData<List<Product>> getCancelledProducts() {
        return cancelledProducts;
    }

    // Adds a product to processing products
    public void addProcessingProduct(Product product) {
        product.setStatus("PROCESSING");
        List<Product> updatedProcessing = new ArrayList<>(processingProducts.getValue());
        updatedProcessing.add(product);
        processingProducts.setValue(updatedProcessing);
    }

    // Marks a product as completed
    public void completeProduct(Product product) {
        product.setStatus("COMPLETED");
        List<Product> updatedCompleted = new ArrayList<>(completedProducts.getValue());
        updatedCompleted.add(product);
        completedProducts.setValue(updatedCompleted);
        List<Product> updatedProcessing = new ArrayList<>(processingProducts.getValue());
        updatedProcessing.remove(product);
        processingProducts.setValue(updatedProcessing);
    }

    // Marks a product as cancelled
    public void cancelProduct(Product product) {
        product.setStatus("CANCELLED");
        List<Product> updatedCancelled = new ArrayList<>(cancelledProducts.getValue());
        updatedCancelled.add(product);
        cancelledProducts.setValue(updatedCancelled);
        List<Product> updatedProcessing = new ArrayList<>(processingProducts.getValue());
        updatedProcessing.remove(product);
        processingProducts.setValue(updatedProcessing);
    }

    // Adds a new product to both productList and myProducts
    public void addProduct(Product product) {
        product.setStatus("AVAILABLE");
        List<Product> updatedProductList = new ArrayList<>(productList.getValue());
        updatedProductList.add(product);
        productList.setValue(updatedProductList);

        List<Product> updatedMyProducts = new ArrayList<>(myProducts.getValue());
        updatedMyProducts.add(product);
        myProducts.setValue(updatedMyProducts);
    }
}
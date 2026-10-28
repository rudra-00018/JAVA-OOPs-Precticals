package service;

import exception.InvalidProductException;
import model.Product;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductService {
    private final List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product == null) {
            throw new InvalidProductException("Product cannot be null");
        }
        if (product.getName() == null || product.getName().trim().isEmpty()) {
            throw new InvalidProductException("Product name must not be blank");
        }
        if (product.getPrice() <= 0) {
            throw new InvalidProductException("Product price must be strictly positive (got: " + product.getPrice() + ")");
        }
        if (product.getQuantity() < 0) {
            throw new InvalidProductException("Product quantity cannot be negative");
        }
        for (Product existing : products) {
            if (existing.getId() == product.getId()) {
                throw new InvalidProductException("Duplicate product ID: " + product.getId());
            }
        }
        products.add(product);
    }

    public List<Product> getAllProducts() {
        return Collections.unmodifiableList(products);
    }

    public Product getProductById(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }
        throw new InvalidProductException("Product not found with ID: " + id);
    }

    public void removeProduct(int id) {
        boolean removed = products.removeIf(p -> p.getId() == id);
        if (!removed) {
            throw new InvalidProductException("Cannot remove; Product not found with ID: " + id);
        }
    }

    public int getProductCount() {
        return products.size();
    }

    public void clear() {
        products.clear();
    }

    // Save data to file using ObjectOutputStream
    public void saveToFile(String filepath) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filepath))) {
            oos.writeObject(new ArrayList<>(products));
        }
    }

    // Reload data from file using ObjectInputStream
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filepath) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filepath))) {
            List<Product> loaded = (List<Product>) ois.readObject();
            products.clear();
            products.addAll(loaded);
        }
    }
}

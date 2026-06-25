package flowgraph.services;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("ProductService")
public class ProductService {

    public List<Map<String, Object>> loadProducts() {

        return List.of(
                Map.of("id", 1, "name", "Coca Cola", "price", 1.99, "stock", 120),
                Map.of("id", 2, "name", "Fanta", "price", 1.89, "stock", 95),
                Map.of("id", 3, "name", "Sprite", "price", 1.89, "stock", 88),
                Map.of("id", 4, "name", "Red Bull", "price", 2.49, "stock", 75),
                Map.of("id", 5, "name", "Monster Energy", "price", 2.79, "stock", 60),

                Map.of("id", 6, "name", "Snickers", "price", 1.29, "stock", 150),
                Map.of("id", 7, "name", "Mars", "price", 1.29, "stock", 140),
                Map.of("id", 8, "name", "Twix", "price", 1.39, "stock", 130),
                Map.of("id", 9, "name", "KitKat", "price", 1.19, "stock", 125),
                Map.of("id", 10, "name", "Kinder Bueno", "price", 1.59, "stock", 110),

                Map.of("id", 11, "name", "Pringles Paprika", "price", 2.99, "stock", 90),
                Map.of("id", 12, "name", "Pringles Sour Cream", "price", 2.99, "stock", 85),
                Map.of("id", 13, "name", "Haribo Goldbären", "price", 1.49, "stock", 115),
                Map.of("id", 14, "name", "M&M's Peanut", "price", 2.29, "stock", 70),
                Map.of("id", 15, "name", "Milka Alpenmilch", "price", 1.79, "stock", 100),

                Map.of("id", 16, "name", "Wasser Classic", "price", 0.89, "stock", 200),
                Map.of("id", 17, "name", "Wasser Still", "price", 0.89, "stock", 180),
                Map.of("id", 18, "name", "Apfelsaft", "price", 2.19, "stock", 65),
                Map.of("id", 19, "name", "Orangensaft", "price", 2.29, "stock", 60),
                Map.of("id", 20, "name", "Eistee Pfirsich", "price", 1.69, "stock", 95)
        );
    }
}
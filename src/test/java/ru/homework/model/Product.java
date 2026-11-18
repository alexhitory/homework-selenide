package ru.homework.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/** Immutable product value, compared by name and price. */
public record Product(String name, BigDecimal price) {
    public Product {
        Objects.requireNonNull(name, "name");
        Objects.requireNonNull(price, "price");
        if (name.isBlank() || price.signum() < 0) {
            throw new IllegalArgumentException("Product needs a name and a non-negative price");
        }
        price = price.setScale(2, RoundingMode.UNNECESSARY);
    }

    public String displayedPrice() {
        return "$" + price.toPlainString();
    }
}

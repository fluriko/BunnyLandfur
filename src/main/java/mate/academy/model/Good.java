package mate.academy.model;

import java.util.Objects;

public class Good {
    private Long id;
    private String label;
    private String description;
    private String category;
    private double price;

    public Good(Long id, String label, String description, String category, double price) {
        this.id = id;
        this.label = label;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public Good(String label, String description, String category, double price) {
        this.label = label;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Good)) return false;
        Good good = (Good) o;
        return Double.compare(good.price, price) == 0 &&
                Objects.equals(id, good.id) &&
                Objects.equals(label, good.label) &&
                Objects.equals(description, good.description) &&
                Objects.equals(category, good.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, label, description, category, price);
    }

    @Override
    public String toString() {
        return "Good{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}

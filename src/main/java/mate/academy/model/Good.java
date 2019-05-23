package mate.academy.model;

import org.hibernate.validator.constraints.Length;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.util.Objects;

@Entity
@Table(name = "GOODS")
public class Good {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true)
    private Long id;

    @Length(min = 4, max = 100, message = "incorrect label ")
    @Column(name = "LABEL", nullable = false)
    private String label;

    @Column(name = "DESCRIPTION")
    private String description;

    @Length(min = 3, max = 16, message = "incorrect category ")
    @Column(name = "CATEGORY", nullable = false)
    private String category;

    @DecimalMin(value = "0.1", message = "negative price")
    @Column(name = "PRICE", nullable = false)
    private double price;

    @Min(value = 1, message = "incorrect quantity")
    @Column(name = "QUANTITY", nullable = false)
    private long quantity;

    public Good(String label, String description, String category, double price) {
        this.label = label;
        this.description = description;
        this.category = category;
        this.price = price;
        quantity = 1;
    }

    public Good() {
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
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

    public double getTotalPrice() {
        return price * quantity;
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

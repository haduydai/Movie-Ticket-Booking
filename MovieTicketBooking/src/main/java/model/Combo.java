package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.math.BigDecimal;

@Entity
@Table(name = "combos")
@SQLDelete(sql = "UPDATE combos SET deleted_at = NOW() WHERE combo_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
public class Combo extends AbsBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "combo_id")
    private int id;

    @Column(name = "combo_name", nullable = false)
    private String name;

    @Column(name = "combo_description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "is_active")
    private boolean isActive = true;

    public Combo() {}

    public Combo(String name, String description, BigDecimal price, boolean isActive) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isActive = isActive;
    }
}
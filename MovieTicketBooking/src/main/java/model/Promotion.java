package model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "promotions")
@SQLDelete(sql = "UPDATE promotions SET deleted_at = NOW() WHERE promo_id = ?")
@SQLRestriction("deleted_at IS NULL")
@Getter
@Setter
public class Promotion extends AbsBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "promo_id")
    private int id;

    @Column(name = "promo_code", unique = true, nullable = false)
    private String code;

    @Column(name = "discount_percentage")
    private int discountPercentage;

    @Column(name = "max_discount_amount")
    private BigDecimal maxDiscountAmount;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @Column(name = "is_active")
    private boolean isActive = true;

    public Promotion() {}

    public Promotion(String code, int discountPercentage, BigDecimal maxDiscountAmount, LocalDate expiryDate, boolean isActive) {
        this.code = code;
        this.discountPercentage = discountPercentage;
        this.maxDiscountAmount = maxDiscountAmount;
        this.expiryDate = expiryDate;
        this.isActive = isActive;
    }
}
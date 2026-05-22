package sys_order_meal_healthy.domain.entity;

import sys_order_meal_healthy.domain.constants.PaidEnum;
import sys_order_meal_healthy.domain.entity.base.BaseEntityHasId;
import jakarta.persistence.*;
import lombok.*;
import sys_order_meal_healthy.domain.entity.base.BaseEntityNoId;
import sys_order_meal_healthy.helper.utils.JsonToMapConverter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "tbl_order")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Order extends BaseEntityNoId {

    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "customer_phone")
    private Customer customer;

    private String paymentProofUrl; // Link ảnh Base64/S3

    private String mealPackage;

    private String note;

    private Integer totalPrice;

    private Integer shipFee;

    private String timeReceive;

    @Column(name = "paid_by", columnDefinition = "ENUM('BANK', 'COD')")
    @Enumerated(EnumType.STRING)
    private PaidEnum paidBy; // PENDING, PAID

    @Column(columnDefinition = "LONGTEXT")
    private String metadataOrder;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> items;
}

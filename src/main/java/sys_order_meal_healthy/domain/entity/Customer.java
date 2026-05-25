package sys_order_meal_healthy.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sys_order_meal_healthy.domain.entity.base.BaseEntityNoId;

@Entity
@Table(name = "tbl_customer")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer extends BaseEntityNoId {


    @Id
    @Column(name = "phone_number")
    private String phoneNumber; // Khóa chính là số điện thoại

    private String fullName;
    private String address;
    private String district;
}

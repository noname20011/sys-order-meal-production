package sys_order_meal_healthy.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import sys_order_meal_healthy.domain.constants.PaidEnum;
import sys_order_meal_healthy.helper.validators.enum_validation.EnumPattern;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO implements Serializable {

    @Pattern(
            regexp = "^(0|\\+84)[3|5|7|9][0-9]{8}$",
            message = "PhoneNumber not valid!"
    )
    private String phoneNumber;

    @NotNull(message = "Field total Price must be not null!")
    private Integer totalPrice;

    private MultipartFile paymentProofFile;
    private String paymentProofUrl;

    @EnumPattern(name = "Paid By", enumClass = PaidEnum.class)
    private PaidEnum paidBy;

    @NotBlank(message = "Field metadata order must be not blank!")
    private String metadataOrder;

    @NotBlank(message = "Field mealPackage order must be not blank!")
    private String mealPackage;

    @NotBlank(message = "Field FullName must be not blank!")
    private String fullName;

    @NotBlank(message = "Field District must be not blank!")
    private String district;

    @NotBlank(message = "Field Address must be not blank!")
    private String address;

    @NotBlank(message = "Field note order must be not blank!")
    private String note;

    @NotNull(message = "Field shipFee order must be not blank!")
    private Integer shipFee;

    @NotBlank(message = "Field timeReceive order must be not blank!")
    private String timeReceive;

}

package sys_order_meal_healthy.service;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sys_order_meal_healthy.dto.order.OrderRequestDTO;
import sys_order_meal_healthy.service.cloudinary.CloudinaryService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GoogleSheetService {

    @Value("${google.sheet.id}")
    private String spreadsheetId;

    private final Sheets sheetsService;
    /**
     * Tải file minh chứng chuyển khoản lên Google Drive và trả về đường dẫn URL công khai
     */

    /**
     * Xử lý dữ liệu và đẩy một hàng mới xuống Google Sheet
     */

    public void processAndSaveOrder(OrderRequestDTO formData) throws IOException {
        // Định dạng lại chuỗi thực đơn: Thay đổi thẻ <br/> từ FE gửi sang ký tự xuống dòng "\n" trong ô tính Sheet
        String formattedMenu = "";
        if (formData.getMetadataOrder() != null) {
            formattedMenu = formData.getMetadataOrder()
                    .replaceAll("<br/>", "\n")
                    .replaceAll("<br>", "\n")
                    .replaceAll("<br />", "\n");
        }

        // Tạo mảng dữ liệu tương ứng 100% với các cột trên hình ảnh (Từ cột A đến cột K)
        List<Object> sheetRowData = Arrays.asList(
                formData.getTimeReceive(),     // Cột A: Giờ giao
                formData.getFullName(),        // Cột B: Họ và tên
                formData.getPhoneNumber(),     // Cột C: Số điện thoại
                formData.getAddress(),  // Cột D: Địa chỉ
                formData.getDistrict(),        // Cột E: Quận/Huyện
                formData.getMealPackage(),     // Cột F: Gói đặt (Package)
                formattedMenu,                 // Cột G: Danh sách thực đơn khách đặt
                formData.getNote(),            // Cột H: Ghi chú (Kiêng ăn..)
                formData.getShipFee(),          // Cột I: Phí ship (1 ngày)
                formData.getTotalPrice(),      // Cột J: Tổng tiền
                formData.getStartDate(),       // Cột K: Ngày bắt đầu
                formData.getEndDate(),         // Cột L: Ngày kết thúc
                formData.getPaymentProofUrl()  // Cột M: Link Ảnh CK (Hoặc hiển thị chữ "COD")
        );

        ValueRange appendBody = new ValueRange().setValues(Collections.singletonList(sheetRowData));

        // Tiến hành ghi nối tiếp (append) dữ liệu vào dòng trống tiếp theo dưới tiêu đề Sheet1
        sheetsService.spreadsheets().values()
                .append(spreadsheetId, "Sheet1!A1", appendBody)
                .setValueInputOption("USER_ENTERED")
                .execute();
    }
}

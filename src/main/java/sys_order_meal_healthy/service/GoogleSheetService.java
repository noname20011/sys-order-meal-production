package sys_order_meal_healthy.service;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ValueRange;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sys_order_meal_healthy.dto.order.OrderRequestDTO;

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
                    .replace("<br/>", "\n")
                    .replace("<br>", "\n")
                    .replace("<br />", "\n");
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

        // 3. Xác định Sheet đích dựa trên thông tin gói ăn (MealPackage)
        String targetSheet = "WEEKLY!A1"; // Mặc định nếu không thỏa mãn điều kiện tháng

        if (formData.getMealPackage() != null) {
            String mealPackageStr = formData.getMealPackage().toLowerCase();
            // Kiểm tra nếu chuỗi chứa từ khóa biểu thị gói Tháng
            if (mealPackageStr.contains("4 tuần") || mealPackageStr.contains("tháng")) {
                targetSheet = "MONTHLY!A1";
            }
        }

        log.info("Đang đẩy đơn hàng của [{}] vào sheet: {}", formData.getFullName(), targetSheet);
        ValueRange appendBody = new ValueRange().setValues(Collections.singletonList(sheetRowData));

        // Tiến hành ghi nối tiếp (append) dữ liệu vào dòng trống tiếp theo dưới tiêu đề Sheet1
//        sheetsService.spreadsheets().values()
//                .append(spreadsheetId, targetSheet, appendBody)
//                .setValueInputOption("USER_ENTERED")
//                .execute();
        // 4. Retry Google Sheets API
        int maxRetries = 5;

        for (int attempt = 1; attempt <= maxRetries; attempt++) {

            try {

                sheetsService.spreadsheets().values()
                        .append(spreadsheetId, targetSheet, appendBody)
                        .setValueInputOption("USER_ENTERED")
                        .execute();

                log.info("✅ Đã ghi đơn hàng [{}] vào {}", formData.getFullName(), targetSheet);
                return;

            } catch (GoogleJsonResponseException e) {

                int statusCode = e.getStatusCode();

                // Chỉ retry những lỗi có khả năng tạm thời
                boolean retryable =
                        statusCode == 429 ||
                                statusCode == 500 ||
                                statusCode == 502 ||
                                statusCode == 503 ||
                                statusCode == 504;

                if (!retryable) {
                    log.error("❌ Google Sheets trả về lỗi {} khi ghi đơn [{}]", statusCode, formData.getFullName(), e);
                    throw e;
                }

                if (attempt == maxRetries) {
                    log.error("❌ Không thể ghi đơn [{}] vào {} sau {} lần thử", formData.getFullName(), targetSheet, maxRetries, e);
                    throw e;
                }

                // Exponential backoff:
                // 2s -> 4s -> 8s -> 16s
                long delay = (long) Math.pow(2, attempt) * 1000;

                log.warn(
                        "⚠️ Google Sheets lỗi {}. Retry {}/{} sau {} ms. Order: [{}]",
                        statusCode, attempt, maxRetries, delay, formData.getFullName());
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException interruptedException) {
                    Thread.currentThread().interrupt();
                    throw new IOException("Thread bị interrupt trong quá trình retry Google Sheets", interruptedException);
                }
            }
        }
    }
}

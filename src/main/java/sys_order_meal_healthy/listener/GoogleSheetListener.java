package sys_order_meal_healthy.listener;

import sys_order_meal_healthy.service.GoogleSheetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class GoogleSheetListener {

    private final GoogleSheetService googleSheetService;

    // phase = AFTER_COMMIT: Chỉ chạy khi DB đã lưu xong 100%
    // @Async: Chạy ở một Thread khác để không làm học sinh phải chờ
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleSavedOrder(SaveOrderEvent event) throws IOException {
        log.info("Transaction committed. Start record order to Sheet: {}", event.dto());
        googleSheetService.processAndSaveOrder(event.dto());
    }
}

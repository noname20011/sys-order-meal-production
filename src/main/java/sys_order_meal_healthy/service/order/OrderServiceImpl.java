package sys_order_meal_healthy.service.order;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_order_meal_healthy.domain.entity.Customer;
import sys_order_meal_healthy.domain.entity.Order;
import sys_order_meal_healthy.dto.customer.CustomerRequestDTO;
import sys_order_meal_healthy.dto.order.OrderRequestDTO;
import sys_order_meal_healthy.dto.order.OrderResponseDTO;
import sys_order_meal_healthy.exception.BusinessException;
import sys_order_meal_healthy.listener.SaveOrderEvent;
import sys_order_meal_healthy.mapper.OrderMapper;
import sys_order_meal_healthy.repository.OrderRepository;
import sys_order_meal_healthy.service.cloudinary.CloudinaryService;
import sys_order_meal_healthy.service.customer.CustomerService;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final CustomerService customerService;
    private final CloudinaryService cloudinaryService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final ApplicationEventPublisher eventPublisher;

    private static final LocalTime OPEN_TIME = LocalTime.of(7, 0);
    private static final LocalTime CLOSE_TIME = LocalTime.of(21, 30);

    private static boolean isClosed(LocalDateTime now) {
        DayOfWeek day = now.getDayOfWeek();
        LocalTime time = now.toLocalTime();

        // Chủ nhật  sau 21:30 đóng
        // Thứ 2 trước 07:00 đóng
        return  (day == DayOfWeek.SUNDAY && time.isAfter(CLOSE_TIME))

                // Thứ 2 trước 07:00 đóng
                || (day == DayOfWeek.MONDAY && time.isBefore(OPEN_TIME));
    }

    @Override
    public OrderResponseDTO getOrderById(String orderId) {
        return null;
    }

    @Transactional
    @Override
    public OrderResponseDTO addOrder(OrderRequestDTO dto) {

        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));

        boolean isClosed = isClosed(now);
        if (isClosed) {
            throw new BusinessException(
                    "Gác Bếp chỉ nhận đơn từ 07:00 T2 đến 21:30 CN"
            );
        }

        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO(
                dto.getPhoneNumber(),
                dto.getFullName(),
                dto.getDistrict(),
                dto.getAddress()
        );
        Customer customer = customerService.addUserEntity(customerRequestDTO);
        Order orderRaw = orderMapper.mapToEntity(dto);

        //1. Up photo len Cloudinary
        String proofUrlOrMethod = "COD";

        if(dto.getPaymentProofFile() != null){
            proofUrlOrMethod = cloudinaryService.uploadPhoto(dto.getPaymentProofFile());
        }

        // Create order id
        String customOrderId = "DH" + System.currentTimeMillis();
        orderRaw.setId(customOrderId);
        orderRaw.setCustomer(customer);
        orderRaw.setPaymentProofUrl(proofUrlOrMethod);
        orderRepository.save(orderRaw);
        log.info("Order Added Successfully: {}", orderRaw);

        dto.setPaymentProofUrl(proofUrlOrMethod);
        // 2. Ghi lên Google Sheet (Chạy bất đồng bộ - Async)
        // Bắn Event thay vì gọi trực tiếp Async
        // Spring sẽ giữ Event này lại cho đến khi Transaction thành công
        eventPublisher.publishEvent(new SaveOrderEvent(dto));

        return orderMapper.mapToResponseDto(orderRaw);
    }

    @Override
    public void deleteOrder(String orderId) {

    }

    @Override
    public List<OrderResponseDTO> getOrdersByCustomerId(String customerId) {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        return List.of();
    }

    @Override
    public List<OrderResponseDTO> getAllOrdersByFilterTime(LocalDate startDate, LocalDate endDate) {
        return List.of();
    }
}

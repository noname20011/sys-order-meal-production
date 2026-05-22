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
import sys_order_meal_healthy.listener.SaveOrderEvent;
import sys_order_meal_healthy.mapper.OrderMapper;
import sys_order_meal_healthy.repository.OrderRepository;
import sys_order_meal_healthy.service.cloudinary.CloudinaryService;
import sys_order_meal_healthy.service.customer.CustomerService;

import java.time.LocalDate;
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

    @Override
    public OrderResponseDTO getOrderById(String orderId) {
        return null;
    }

    @Transactional
    @Override
    public OrderResponseDTO addOrder(OrderRequestDTO dto) {
        CustomerRequestDTO customerRequestDTO = new CustomerRequestDTO(
                dto.getPhoneNumber(),
                dto.getFullName(),
                dto.getDistrict(),
                dto.getAddress()
        );
        Customer customer = customerService.addUserEntity(customerRequestDTO);
        Order orderRaw = orderMapper.mapToEntity(dto);

        // Create order id
        String customOrderId = "DH" + System.currentTimeMillis();
        orderRaw.setId(customOrderId);
        orderRaw.setCustomer(customer);
        orderRepository.save(orderRaw);
        log.info("Order Added Successfully: {}", orderRaw);


        //1. Up photo len Cloudinary
        String proofUrlOrMethod = "COD";

        if(dto.getPaymentProofFile() != null){
            proofUrlOrMethod = cloudinaryService.uploadPhoto(dto.getPaymentProofFile());
        }
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

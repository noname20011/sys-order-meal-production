package sys_order_meal_healthy.service.customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sys_order_meal_healthy.domain.entity.Customer;
import sys_order_meal_healthy.dto.customer.CustomerRequestDTO;
import sys_order_meal_healthy.dto.customer.CustomerResponseDTO;
import sys_order_meal_healthy.exception.NotFoundException;
import sys_order_meal_healthy.mapper.CustomerMapper;
import sys_order_meal_healthy.repository.CustomerRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository userRepository;
    private final CustomerMapper userMapper;

    @Override
    public CustomerResponseDTO getUserByPhoneNumber(String phoneNumber) {
        Customer user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        return userMapper.mapToResponseDto(user);
    }

    @Transactional
    @Override
    public Customer addUserEntity(CustomerRequestDTO dto) {
        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            log.info("Customer dto: {}", dto);
            return userRepository.update(dto.getPhoneNumber(), userMapper.mapToEntity(dto));
        }

        Customer user = userMapper.mapToEntity(dto);
        Customer saved = userRepository.save(user);

        log.info("Get customer successfully!  {} ", saved);
        return saved;
    }

    @Override
    public CustomerResponseDTO putUser(String phoneNumber, CustomerRequestDTO dto) {
        Customer saved = userRepository.update(phoneNumber, userMapper.mapToEntity(dto));
        CustomerResponseDTO userResponseDTO = userMapper.mapToResponseDto(saved);

        log.info("Update customer successfully!  {} ", userResponseDTO);
        return userResponseDTO;
    }

    @Transactional
    @Override
    public CustomerResponseDTO addUser(CustomerRequestDTO dto) {
        if (userRepository.existsByPhoneNumber(dto.getPhoneNumber())) {
            return this.putUser(dto.getPhoneNumber(), dto);
        }

        Customer user = userMapper.mapToEntity(dto);
        Customer saved = userRepository.save(user);
        CustomerResponseDTO userResponseDTO = userMapper.mapToResponseDto(saved);

        log.info("Add customer successfully!  {} ", userResponseDTO);
        return userResponseDTO;
    }

    @Override
    public void deleteUser(String phoneNumber) {
        userRepository.delete(phoneNumber);
    }
}

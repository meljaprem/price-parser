package com.prem.priceparser.repository;

import com.prem.priceparser.domain.entity.Product;
import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.domain.enums.ScheduleType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByUser(User user);

    Optional<Product> findByIdAndUser(Long productId, User user);

    Boolean existsByIdAndUser(Long productId, User user);

    List<Product> findAllByScheduledAndScheduleType(boolean scheduled, ScheduleType type);
}

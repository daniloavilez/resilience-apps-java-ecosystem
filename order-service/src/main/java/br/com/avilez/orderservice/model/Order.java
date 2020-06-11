package br.com.avilez.orderservice.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Order {


    public Order() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public Long productId;

    public String productName;

    public BigDecimal totalPurchase;

    public String status;
}

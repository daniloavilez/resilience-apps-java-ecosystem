package br.com.avilez.orderservice.model;

import lombok.Data;
import lombok.NonNull;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Order {


    public Order() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @NotBlank
    public Long productId;

    @NotBlank
    public String productName;

    @DecimalMin(value = "0.01")
    public BigDecimal totalPurchase;

    public String status;

    public Payment payment;
}

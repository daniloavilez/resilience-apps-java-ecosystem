package br.com.avilez.paymentservice.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Payment implements Serializable {
    private BigDecimal totalPurchase;
}

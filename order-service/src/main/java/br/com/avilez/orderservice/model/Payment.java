package br.com.avilez.orderservice.model;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Payment implements Serializable {
    public BigDecimal totalPurchase;
}

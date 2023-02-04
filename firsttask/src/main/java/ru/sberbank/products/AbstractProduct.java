package ru.sberbank.products;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Абстрактный класс представляющий финансовый продукт.
 * Он содержит общие аттрибуты и поведение финансовых продуктов.
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractProduct {
    /**
     * Имя продукта.
     */
    private String name;
    /**
     * Баланс.
     */
    @Setter
    private BigDecimal balance;
    /**
     * Валюта.
     */
    private String currency;
}

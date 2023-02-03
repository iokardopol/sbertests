package ru.sberbank.products;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Абстрактный класс представляющий финансовый продукт.
 * Он содержит общие аттрибуты и поведение финансовых продуктов.
 */
@Slf4j
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractProduct {
    /**
     * Имя продукта.
     */
    @Getter
    private String name;
    /**
     * Баланс.
     */
    @Getter
    @Setter
    private BigDecimal balance;
    /**
     * Валюта.
     */
    @Getter
    private String currency;
}

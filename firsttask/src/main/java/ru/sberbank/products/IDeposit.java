package ru.sberbank.products;

import java.math.BigDecimal;

/**
 * Интерфейс IDeposit для реализации классами представляющих продукты
 * с возможностью депозита.
 *
 * @author Ivan Kardapolov
 */
public interface IDeposit {
    /**
     * Для описания метода и условий внесения денег в продукт.
     *
     * @param amount сумма внесения
     */
    void deposit(BigDecimal amount);
}

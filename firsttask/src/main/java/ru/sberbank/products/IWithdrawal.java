package ru.sberbank.products;

import java.math.BigDecimal;

/**
 * Интерфейс IWithdrawal для реализации классами представляющих продукты
 * с возможностью снятия.
 *
 * @author Ivan Kardapolov
 */
public interface IWithdrawal {
    /**
     * Для описания метода и условий снятия денег с продукта.
     *
     * @param amount сумма снятия
     */
    void withdrawal(BigDecimal amount);
}

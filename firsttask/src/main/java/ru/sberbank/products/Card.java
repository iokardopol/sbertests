package ru.sberbank.products;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Класс Card расширяет класс {@link AbstractProduct}
 * и реализует интерфейсы {@link IWithdrawal} и {@link IDeposit}.
 * Он представляет собой карточный сервис, который хранит имя продукта,
 * баланс, валюту и предлагает методы для ввода и вывода средств.
 *
 * @author Ivan Kardapolov
 */
@Slf4j
public class Card extends AbstractProduct implements IWithdrawal, IDeposit {
    /**
     * Создаёт новый объект Card с заданным именем продукта, балансом и валютой.
     *
     * @param name     имя продукта
     * @param balance  баланс карты
     * @param currency валюта карты
     */
    public Card(String name, BigDecimal balance, String currency) {
        super(name, balance, currency);
    }

    /**
     * Вносит указанную сумму на баланс карты.
     *
     * @param amount сумма пополнения
     */
    @Override
    public void deposit(BigDecimal amount) {
        this.setBalance(this.getBalance().add(amount));
    }

    /**
     * Снимает указанную сумму с баланса карты, если она меньше или равна балансу.
     * В противном случае логирует сообщение об ошибке, указывающее, что сумма снятия превышает баланс.
     *
     * @param amount сумма снятия
     */
    @Override
    public void withdrawal(BigDecimal amount) {
        if (amount.compareTo(this.getBalance()) > 0) {
            log.error("Withdrawal amount {} exceeds the current balance {}", amount, this.getBalance());
        } else {
            this.setBalance(this.getBalance().subtract(amount));
        }
    }
}

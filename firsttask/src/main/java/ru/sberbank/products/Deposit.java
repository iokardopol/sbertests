package ru.sberbank.products;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Класс Deposit расширяет класс {@link AbstractProduct}
 * и реализует интерфейс {@link IDeposit}.
 * Он представляет собой вклад, который хранит имя продукта,
 * баланс, валюту, статус вклада и предлагает методы для ввода средств и закрытия счет.
 *
 * @author Ivan Kardapolov
 */
@Slf4j
public class Deposit extends AbstractProduct implements IDeposit {
    /**
     * Флаг указывающий открыт или закрыт вклад.
     */
    @Getter
    private boolean isOpen;

    /**
     * Создаёт новый Deposit с указанным именем продукта, балансом и валютой.
     *
     * @param name     имя продукта
     * @param balance  баланс
     * @param currency валюта
     */
    public Deposit(String name, BigDecimal balance, String currency) {
        super(name, balance, currency);
        isOpen = true;
    }

    /**
     * Вносит указанную сумму на баланс вклада.
     * Если вклад уже закрыт, логгирует сообщение об ошибке.
     *
     * @param amount - сумма пополнения
     */
    @Override
    public void deposit(BigDecimal amount) {
        if (!isOpen) {
            log.error("It is not possible to make a deposit to a closed account");
        } else {
            this.setBalance(this.getBalance().add(amount));
        }
    }

    /**
     * Закрывает вклад, обнуляет баланс и логгирует сумму, выплачиваемую клиенту.
     * Если вклад уже закрыт, логгирует сообщение об ошибке.
     */
    public void close() {
        if (isOpen) {
            log.info("Client payout amount: " + this.getBalance());
            this.setBalance(BigDecimal.ZERO);
            isOpen = false;
        } else {
            log.error("Deposit already closed");
        }
    }
}

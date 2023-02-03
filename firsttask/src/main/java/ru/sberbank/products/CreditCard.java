package ru.sberbank.products;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Класс CreditCard расширяет класс {@link AbstractProduct}
 * и реализует интерфейсы {@link IWithdrawal} и {@link IDeposit}.
 * Он представляет собой карточный сервис, который хранит имя продукта,
 * баланс, валюту, процентную ставку и предлагает методы для ввода и вывода средств, позволяя
 * взять в кредит и отслеживать задолженность.
 *
 * @author Ivan Kardapolov
 */
@Slf4j
public class CreditCard extends AbstractProduct implements IWithdrawal, IDeposit {
    /**
     * Процентная ставка по кредитной карте.
     */
    @Getter
    private double interestRate;

    /**
     * Создаёт новую CreditCard c указанным именем продукта, балансом, валютой и процентной ставкой.
     *
     * @param name         имя продукта
     * @param balance      баланс
     * @param currency     валюта
     * @param interestRate процентная ставка
     */
    public CreditCard(String name, BigDecimal balance, String currency, double interestRate) {
        super(name, balance, currency);
        this.interestRate = interestRate;
    }

    /**
     * Устанавливает процентную ставку кредитной карты.
     * Если процентная ставка меньше 0, будет логгировано сообщение об ошибке.
     *
     * @param interestRate - процентная ставка по кредитной карте
     */
    public void setInterestRate(double interestRate) {
        if (interestRate < 0) {
            log.error("Interest rate must be greater than 0");
        } else {
            this.interestRate = interestRate;
        }
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
     * Снимает указанную сумму с баланса карты.
     *
     * @param amount сумма снятия
     */
    @Override
    public void withdrawal(BigDecimal amount) {
        this.setBalance(this.getBalance().subtract(amount));
    }

    /**
     * Запрашивает сумму долга по кредитной карте.
     * Если баланс кредитной карты больше 0, значит у клиента не задолженности.
     * В противном случае возвращает размер задолженности.
     *
     * @return сумма задолженности по кредитной карте или сообщение "Client has not debt".
     */
    public String debtRequest() {
        return this.getBalance().compareTo(BigDecimal.ZERO) < 0 ? this.getBalance().abs().toString() : "Client has not debt";
    }
}

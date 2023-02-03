package products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.products.Card;
import ru.sberbank.util.Constants;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


class CardTests {
    private static final String CARD_NAME = "Test card";
    private static final String CURRENCY = "RUB";
    private static final BigDecimal INITIAL_DEPOSIT = BigDecimal.valueOf(Constants.ONE_HUNDRED.getNum());
    private static final BigDecimal OPERATION_AMOUNT = BigDecimal.valueOf(Constants.FIFTY.getNum());
    private Card card;

    @BeforeEach
    void beforeTestDeposit() {
        card = new Card(CARD_NAME, INITIAL_DEPOSIT, CURRENCY);
    }

    @Test
    void testDeposit() {
        var expectedBalance = INITIAL_DEPOSIT.add(OPERATION_AMOUNT);
        card.deposit(OPERATION_AMOUNT);
        assertThat(card)
                .describedAs("Сумма должнать быть: " + expectedBalance)
                .returns(expectedBalance, Card::getBalance);
    }
    @Test
    void testWithdrawalValidAmount() {
        var expectedBalance = INITIAL_DEPOSIT.subtract(OPERATION_AMOUNT);
        card.withdrawal(OPERATION_AMOUNT);
        assertThat(card)
                .describedAs("Сумма должнать быть: " + expectedBalance)
                .returns(expectedBalance, Card::getBalance);
    }

    @Test
    void testWithdrawalInvalidAmount() {
        var expectedBalance = card.getBalance();
        card.withdrawal(BigDecimal.valueOf(Constants.ONE_HUNDRED_FIFTY.getNum()));
        assertThat(card)
                .describedAs("Сумма должнать быть: " + expectedBalance)
                .returns(expectedBalance, Card::getBalance);
    }
}

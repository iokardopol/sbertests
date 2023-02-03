package products;

import static org.assertj.core.api.Assertions.assertThat;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.products.CreditCard;
import ru.sberbank.util.Constants;

import java.math.BigDecimal;

@Slf4j
class CreditCardTests {
    private static final String CARD_NAME = "Test credit card";
    private static final String CURRENCY = "RUB";
    private static final BigDecimal INITIAL_DEPOSIT = BigDecimal.valueOf(Constants.ONE_HUNDRED.getNum());
    private static final BigDecimal OPERATION_AMOUNT = BigDecimal.valueOf(Constants.FIFTY.getNum());
    private CreditCard creditCard;

    @BeforeEach
    public void beforeCreditCardTest() {
        creditCard = new CreditCard(CARD_NAME, INITIAL_DEPOSIT, CURRENCY, Constants.ZERO_POINT_ONE.getNum());
    }

    @Test
    void testInterestRate() {
        creditCard.setInterestRate(Constants.ZERO_POINT_TWO.getNum());
        assertThat(creditCard)
                .describedAs("Процентная ставка должна быть " + Constants.ZERO_POINT_TWO.getNum())
                .returns(Constants.ZERO_POINT_TWO.getNum(), CreditCard::getInterestRate);
    }

    @Test
    void negativeITestInterestRate() {
        var negativeInterestRate = Constants.ZERO_POINT_TWO.getNum() * -1;
        var expectedInterestRate = creditCard.getInterestRate();
        creditCard.setInterestRate(negativeInterestRate);
        assertThat(creditCard)
                .describedAs("Процентная ставка должна быть " + expectedInterestRate)
                .returns(expectedInterestRate, CreditCard::getInterestRate);
    }

    @Test
    void testDeposit() {
        var expectedBalance = INITIAL_DEPOSIT.add(OPERATION_AMOUNT);
        creditCard.deposit(OPERATION_AMOUNT);
        assertThat(creditCard)
                .describedAs("Баланс должен быть: " + expectedBalance)
                .returns(expectedBalance, CreditCard::getBalance);
    }

    @Test
    void testWithdrawal() {
        var expectedBalance = INITIAL_DEPOSIT.subtract(OPERATION_AMOUNT);
        creditCard.withdrawal(OPERATION_AMOUNT);
        assertThat(creditCard)
                .describedAs("Баланс должен быть: " + expectedBalance)
                .returns(expectedBalance, CreditCard::getBalance);
    }

    @Test
    void testDebtRequest() {
        var withdrawalAmount = BigDecimal.valueOf(Constants.ONE_HUNDRED_FIFTY.getNum());
        var expectedDebt = INITIAL_DEPOSIT.subtract(withdrawalAmount).abs().toString();
        creditCard.withdrawal(withdrawalAmount);
        assertThat(creditCard)
                .describedAs("Размер задолженности должен быть: " + expectedDebt)
                .returns(expectedDebt, CreditCard::debtRequest);
    }

    @Test
    void testNoDebtRequest() {
        var expectedResponse = "Client has not debt";
        assertThat(creditCard)
                .describedAs(String.format("Сообщение о задолженности должно быть '%s'", expectedResponse))
                .returns(expectedResponse, CreditCard::debtRequest);
    }
}

package products;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sberbank.products.Deposit;

import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
class DepositTests {
    private static final String DEPOSIT_NAME = "Test deposit";
    private static final String CURRENCY = "RUB";
    private static final BigDecimal INITIAL_DEPOSIT = BigDecimal.valueOf(100);
    private static final BigDecimal OPERATION_AMOUNT = BigDecimal.valueOf(50);
    private Deposit depositAccount;

    @BeforeEach
    public void beforeCreditCardTest() {
        depositAccount = new Deposit(DEPOSIT_NAME, INITIAL_DEPOSIT, CURRENCY);
    }
    @Test
    void testDeposit() {
        var expectedBalance = INITIAL_DEPOSIT.add(OPERATION_AMOUNT);
        depositAccount.deposit(OPERATION_AMOUNT);
        assertThat(depositAccount)
                .describedAs("Баланс должен быть: " + expectedBalance)
                .returns(expectedBalance, Deposit::getBalance);

    }

    @Test
    void testClose() {
        depositAccount.close();
        assertThat(depositAccount)
                .extracting("balance", "isOpen")
                .containsExactly(BigDecimal.ZERO, false);
    }

    @Test
    void testClosedDeposit() {
        depositAccount.close();
        depositAccount.deposit(OPERATION_AMOUNT);
        assertThat(depositAccount)
                .returns(BigDecimal.ZERO, Deposit::getBalance);
    }
}

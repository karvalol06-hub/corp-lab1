import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderPolicyTest {

    private final OrderPolicy policy = new OrderPolicy();

    @ParameterizedTest(name = "{0} -> {1} is {2}")
    @CsvSource({
            "DRAFT,   PAID,    ALLOWED",
            "PAID,    SHIPPED, ALLOWED",
            "DRAFT,   SHIPPED, FORBIDDEN",
            "PAID,    DRAFT,   FORBIDDEN"
    })
    void readmeStatusTableMatchesPolicy(OrderStatus from, OrderStatus to, String expected) {
        if (expected.equals("ALLOWED")) {
            assertEquals(to, policy.move(from, to));
        } else {
            assertThrows(IllegalStateException.class, () -> policy.move(from, to));
        }
    }

    @Test
    void nullOrderIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new OrderId(null));
    }

    @Test
    void blankOrderIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new OrderId("   "));
    }

    @Test
    void emptyOrderIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> new OrderId(""));
    }
}

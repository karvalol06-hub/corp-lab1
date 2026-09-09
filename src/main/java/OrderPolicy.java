public class OrderPolicy {
    public OrderStatus move(OrderStatus from, OrderStatus to) {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Statuses cannot be null");
        }
        if (from == OrderStatus.DRAFT && to == OrderStatus.PAID) {
            return OrderStatus.PAID;
        }
        if (from == OrderStatus.PAID && to == OrderStatus.SHIPPED) {
            return OrderStatus.SHIPPED;
        }
        throw new IllegalStateException("Transition from " + from + " to " + to + " is forbidden");
    }
}
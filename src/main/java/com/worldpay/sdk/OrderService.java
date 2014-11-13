package com.worldpay.sdk;

import com.worldpay.api.client.error.exception.WorldpayException;
import com.worldpay.gateway.clearwater.client.core.dto.request.OrderRequest;
import com.worldpay.gateway.clearwater.client.core.dto.request.RefundOrderRequest;
import com.worldpay.gateway.clearwater.client.core.dto.response.OrderResponse;
import org.apache.commons.lang.StringUtils;

/**
 * Service used for the order related operations.
 */
public class OrderService extends AbstractService {

    private final String ORDERS_URL = "/orders";

    private final String REFUND_URL = "/orders/%s/refund";

    /**
     * Constructor
     *
     * @param http {@link Http}
     */
    protected OrderService(Http http) {
        super(http);
    }

    /**
     * Create an order with the specified request.
     *
     * @param orderRequest {@link OrderRequest} object
     *
     * @return {@link OrderResponse} object
     */
    public OrderResponse create(OrderRequest orderRequest) {
        return http.post(ORDERS_URL, orderRequest, OrderResponse.class);
    }

    /**
     * Refund the order identified by order code.
     *
     * @param orderCode Order code
     */
    public void refund(String orderCode) {
        validate(orderCode);
        http.post(String.format(REFUND_URL, orderCode), null);
    }

    /**
     * Partially Refund the order identified by order code by the amount given
     *
     * @param orderCode the order to be refunded
     * @param amount    the amount to be refunded
     */
    public void refund(String orderCode, int amount) {
        validate(orderCode);
        http.post(String.format(REFUND_URL, orderCode), new RefundOrderRequest(amount));
    }

    /**
     * Validates the order code. This method throws WorldpayException if the order code is null or empty.
     *
     * @param orderCode order code to be validated.
     */
    private void validate(String orderCode) {
        if (StringUtils.isEmpty(orderCode)) {
            throw new WorldpayException("order code should be empty");
        }
    }
}

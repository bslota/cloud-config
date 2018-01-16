package com.bslota.productmanager.product;

import javax.persistence.Embeddable;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by bslota on 2018-01-13.
 */
@Embeddable
class ProductSubscriptionId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long customerId;

    @ManyToOne(optional = false)
    @JoinColumn(name="product_code", nullable=false, updatable=false)
    private Product product;

    public ProductSubscriptionId() {
    }

    public ProductSubscriptionId(Long customerId, Product product) {
        this.customerId = customerId;
        this.product = product;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public Product getProduct() {
        return product;
    }
}

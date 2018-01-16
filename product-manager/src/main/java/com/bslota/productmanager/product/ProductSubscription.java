package com.bslota.productmanager.product;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import java.io.Serializable;

/**
 * Created by bslota on 2018-01-13.
 */
@Entity
public class ProductSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private ProductSubscriptionId id;

    ProductSubscription() {
    }

    ProductSubscription(ProductSubscriptionId id) {
        this.id = id;
    }

    public ProductSubscriptionId getId() {
        return id;
    }
}

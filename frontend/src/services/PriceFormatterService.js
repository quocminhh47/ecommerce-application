import React from "react";

class PriceFormatterService {
    formatPrice(price) {
        const formatter = new Intl.NumberFormat('de-DE', {
            style: 'currency',
            currency: 'VND',
        });
        return formatter.format(price);
    }
}

export default new PriceFormatterService();
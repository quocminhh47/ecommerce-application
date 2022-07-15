import React from "react";
import axios from "axios";
import AuthService from "./AuthService";

export default new class CartService {


    addProductToCart(id) {

        const RATE_PRODUCT_API_BASE_URL = "http://localhost:8080/customer/api/cart/";

        const token = localStorage.getItem("accessToken");
        AuthService.checkUserAuth(token);
        if (id) {
            const productId = id;
            const url = RATE_PRODUCT_API_BASE_URL + id;
            console.log(url)
            axios.post(url, productId, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
                .then(res => {
                    if (res.status == 201) {
                        alert("Add product to cart success!");
                    }
                })
        }
    }

    

    // removeCartItem(id) {
    //     const token = localStorage.getItem("accessToken");
    //     //valid token exist or not
    //     AuthService.checkUserAuth(token);

    //     const url = CART_ITEMS_API_URL + id;

    //     axios.delete(url, {
    //         headers: {
    //             'Authorization': `Bearer ${token}`
    //         }
    //     })
    //         .then(res => {
    //             if (res.status === 200) {
    //                 alert('Remove item success! ');
    //                 setRemovedItem(id);
    //             }
    //         })
    //         .catch(error => {
    //             console.log(error)
    //         })
    // }

}
import axios from "axios";
import React, { useEffect, useState } from "react";
import { Link, useNavigate } from 'react-router-dom'
import Header from "../../components/header/Header";
import AuthService from "../../services/AuthService";
import PriceFormatterService from "../../services/PriceFormatterService";
import BillService from '../../services/BillService'
import './css/style.css'

export default function CartItemsComponent() {

    const navigate = useNavigate()
    const CART_ITEMS_API_URL = "http://localhost:8080/customer/api/cart/";

    const [cartItems, setCartItems] = useState([]);

    const [removedItem, setRemovedItem] = useState();

    const [totalPrice, setTotalPrice] = useState(0);

    //const [billItems, setBillItems] = useState([]);

    var billItems = []

    const removeCartItem = (id) => {
        const token = localStorage.getItem("accessToken");
        //valid token exist or not
        AuthService.checkUserAuth(token);

        const url = CART_ITEMS_API_URL + id;

        axios.delete(url, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(res => {
                if (res.status === 200) {
                    alert('Remove item success! ');
                    setRemovedItem(id);
                }
            })
            .catch(error => {
                console.log(error)
            })
    }

    useEffect(() => {
        //fetch cart items info
        const token = localStorage.getItem("accessToken");
        //valid token exist or not
        AuthService.checkUserAuth(token);

        axios.get(CART_ITEMS_API_URL, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(res => {
                if (res.status == 200) {
                    setCartItems(res.data.cartDetails);
                    setTotalPrice(res.data.totalPrice);
                }
            })
            .catch(error => {
                console.log(error);
                alert(error.data.message);
            })
    }, [removedItem]);

    //useEffect()

    const calculateItemTotalPrice = () => {

    }


    const updateCart = (data) => {
        let total = 0;
        data.map(item => {
            total += (item.productPrice * item.cartDetailQuantity);
            //setTotalPrice(total);
        })
    }



    const purchaseHanlde = () => {

        //     billItems = [];

        //     const cartItemsId = document.querySelectorAll("td[id='itemId']")
        //     const cartItemsQuantity = document.querySelectorAll("input[id='itemQuantity']")
        //     console.log(cartItemsId)
        //     console.log(cartItemsQuantity)

        //     const length = cartItemsId.length;
        //     for (let i = 0; i < length; i++) {
        //         const item = {
        //             productId: cartItemsId[0].innerHTML,
        //             productQuantity: cartItemsQuantity[0].value
        //         }

        //         billItems.push(item)
        //     }

        //     const token = window.localStorage.getItem("accessToken")
        //     console.log(token)
        //     var myHeaders = new Headers();
        //     myHeaders.append("Authorization", `Bearer ${token}`);
        //     myHeaders.append("Content-Type", "application/json");

        //     var raw =



        //     JSON.stringify(
        //         {
        //             "cartDetails": [

        //                 billItems.map(item => 
        //                     {
        //                        {
        //                         "productId" : item.productId,
        //                         "productQuantity" :  item.productQuantity
        //                        }
        //                     }
        //                     )


        //             ]
        //         }
        //    { "cartDetails": billItems }
        //     );

        //     var requestOptions = {
        //         method: 'POST',
        //         headers: myHeaders,
        //         body: raw,
        //         redirect: 'follow'
        //     };

        //     fetch("http://localhost:8080/customer/api/order", requestOptions)
        //         .then(response => response.text())
        //         .then(result => console.log(result))
        //         .catch(error => console.log('error', error));




        billItems = [];

        const cartItemsId = document.querySelectorAll("td[id='itemId']")
        const cartItemsQuantity = document.querySelectorAll("input[id='itemQuantity']")

        const length = cartItemsId.length;
        for (let i = 0; i < length; i++) {
            const item = {
                productId: cartItemsId[i].innerHTML,
                productQuantity: cartItemsQuantity[i].value
            }

            billItems.push(item)
        }


        const dataPayload = {
            cartDetails: billItems
        }

        // var data = JSON.stringify(dataPayload);
        // const token = window.localStorage.getItem("accessToken")
        // var config = {
        //     method: 'post',
        //     url: 'http://localhost:8080/customer/api/order',
        //     headers: {
        //         Authorization: `Bearer ${token}`,
        //         'Content-Type': 'application/json'
        //     },
        //     data: data
        // };


        console.log(dataPayload)

        // axios(config)
        //     .then(function (response) {
        //         console.log(JSON.stringify(response.data));
        //         alert("success")
        //     })

        BillService.purchaseProducts(dataPayload)
            .then(res => {
                console.log(res)
                if (res.status == 200) {
                    alert('Order success')
                    navigate(`/bill/${res.data.billId}`)
                    console.log(res.data);
                }
            })
            .catch(err => {
                console.log(err)
                if (err.response) {

                    if (err.response.status == 403) {
                        alert('You must login first')
                        navigate('/login')
                    }

                    if (err.response.status == 500) {
                        alert('server error, try again later')
                        //navigate('/')
                    }
                }
                else {
                    alert('Order failed, try again later')
                    //navigate('/')
                }

            })

    }


    return (
        <>
            <Header status={true} />
            <section className="shop-cart spad">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="shop__cart__table">
                                <table>
                                    <thead>
                                        <tr>
                                            <th>ID</th>
                                            <th>Product</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Total</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            cartItems.map(item =>

                                                <tr key={item.productId}>
                                                    <td className="cart__product__item" id="itemId" style={{ width: "10%" }}>{item.productId}</td>
                                                    <td className="cart__product__item">
                                                        <img src={item.productThumbnail} alt="item image" style={{ width: "110px", height: "110px" }} />
                                                        <div className="cart__product__item__title">
                                                            <h6>{item.productName}</h6>
                                                        </div>
                                                    </td>
                                                    <td className="cart__price">{PriceFormatterService.formatPrice(item.productPrice)}</td>
                                                    <td className="cart__quantity">
                                                        <div className="pro-qty">
                                                            <input type="number" value={item.cartDetailQuantity} id="itemQuantity"
                                                                onChange={() => calculateItemTotalPrice()} />
                                                        </div>
                                                    </td>
                                                    <td className="cart__total">
                                                        {PriceFormatterService.formatPrice(item.productPrice * item.cartDetailQuantity)}
                                                    </td>
                                                    <td className="cart__close">
                                                        <span className="icon_close"
                                                            onClick={() => removeCartItem(item.productId)}
                                                        >
                                                        </span>
                                                    </td>
                                                </tr>

                                            )
                                        }



                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-lg-6 col-md-6 col-sm-6">
                            <div className="cart__btn">
                                <Link to="#">Continue Shopping</Link>
                            </div>
                        </div>
                        <div className="col-lg-6 col-md-6 col-sm-6">
                            <div className="cart__btn update__btn">
                                <span className="icon_loading" onClick={() => updateCart(cartItems)} ></span> Update cart
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-lg-6">
                            <div className="discount__content">
                                <h6>Discount codes</h6>
                                <form action="#">
                                    <input type="text" placeholder="Enter your coupon code" />
                                    <button type="submit" className="site-btn">Apply</button>
                                </form>
                            </div>
                        </div>
                        <div className="col-lg-4 offset-lg-2">
                            <div className="cart__total__procced">
                                <h6>Cart total</h6>
                                <ul>
                                    <li>Total <span>{PriceFormatterService.formatPrice(totalPrice)}</span></li>
                                </ul>
                                <a className="primary-btn" onClick={purchaseHanlde}>Proceed to checkout</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>

    )
}
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

    const [itemChange, setItemChange] = useState();

    const [totalPrice, setTotalPrice] = useState(0);
    const [cartStatus, setCartStatus] = useState()

    var billUpdate = []

    var billItems = []

    const removeCartItem = (id) => {
        const token = localStorage.getItem("accessToken");

        const url = CART_ITEMS_API_URL + id;

        axios.delete(url, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
            .then(res => {
                if (res.status === 200) {
                    alert('Remove item success! ');
                    setItemChange(res.data);
                }
            })
            .catch(err => {
                console.log(err)
                if (err.response) {
                    if (err.response.status === 403) {
                        navigate('/login')
                    } else alert(err.response.data.message)
                }
                else {
                    alert("Failed, try again")
                    navigate('/login')
                }
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
            .catch(err => {
                console.log(err)
                if (err.response) {
                    if (err.response.status === 403) {
                        navigate('/login')
                    }
                    else alert(err.response.data.message)
                }
                else {
                    alert("Failed, try again")
                    navigate('/login')
                }
            })
    }, [itemChange]);

    //useEffect()

    const calculateItemTotalPrice = (e) => {
        console.log(e.target.value)
    }


    const updateCart = () => {
        //setCartStatus(true)
        // alert("alert")
       // billUpdate = [];
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
        console.log('dataload:');
        console.log(dataPayload)

        BillService.updateCart(dataPayload)
            .then(res => {
                console.log(res)
                if (res.status == 200) {
                    alert('Update success')
                    setItemChange(res.data)
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



    const purchaseHanlde = () => {

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

        console.log(dataPayload)

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
            <Header status={itemChange} />
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
                                                            <input type="number" defaultValue={item.cartDetailQuantity} id="itemQuantity"
                                                            />
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
                                <Link to="#" >Continue Shopping</Link>
                            </div>
                        </div>
                        <div className="col-lg-6 col-md-6 col-sm-6">
                            <div className="cart__btn update__btn">
                                <span className="icon_loading" onClick={updateCart} ></span> Update cart
                            </div>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-lg-6">
                            <div className="cart__btn">
                                {/* <h6>Discount codes</h6> */}
                                <form action="#">
                                    {/* <input type="text" placeholder="Enter your coupon code" /> */}
                                    <button type="button" className="site-btn" onClick={updateCart}>Update cart</button>
                                </form>
                            </div>
                        </div>
                        <div className="col-lg-4 offset-lg-2">
                            <div className="cart__total__procced">
                                <h6>Cart total</h6>
                                <ul>
                                    <li>Total <span>{PriceFormatterService.formatPrice(totalPrice)}</span></li>
                                </ul>
                                <a className="primary-btn" onClick={purchaseHanlde} style={{color:'white'}}>Proceed to checkout</a>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>

    )
}
import React, { useState, useEffect } from "react";
import { useNavigate, Link, useParams } from 'react-router-dom'
import BillService from "../../services/BillService";
import PriceFormatterService from '../../services/PriceFormatterService';
import HeaderComponent from "../header/HeaderComponent";

function SingleBillComponent() {
    const { billId } = useParams();
    const [bill, setBill] = useState({});
    const [billItems, setBillItems] = useState([]);
    const navigate = useNavigate();


    useEffect(() => {
        BillService.getBillDetail(billId)
            .then(res => {
                console.log(res.data);
                if (res.status === 200) {
                    setBill(res.data)
                    setBillItems(res.data.cartDetails)

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
    }, [])


    return (
        <>
            <HeaderComponent status={bill} />
            <section className="checkout spad">
                <div className="container">
                    <form action="#" className="checkout__form">
                        <div className="row">
                            <div className="col-lg-8">
                                <h5>Billing detail</h5>
                                <div className="row">
                                    <div className="col-lg-6 col-md-6 col-sm-6">
                                        <div className="checkout__form__input">
                                            <p>First Name </p>
                                            <input type="text" value={bill.firstName} style={{ color: "black", fontWeight: "500" }} />
                                        </div>
                                    </div>
                                    <div className="col-lg-6 col-md-6 col-sm-6">
                                        <div className="checkout__form__input">
                                            <p>Last Name </p>
                                            <input type="text" value={bill.lastName} style={{ color: "black", fontWeight: "500" }} />
                                        </div>
                                    </div>

                                    <div className="col-lg-6 col-md-6 col-sm-6">
                                        <div className="checkout__form__input">
                                            <p>Email  </p>
                                            <input type="text" value={bill.email} style={{ color: "black", fontWeight: "500" }} />
                                        </div>
                                    </div>
                                    <div className="col-lg-6 col-md-6 col-sm-6">
                                        <div className="checkout__form__input">
                                            <p>Phone </p>
                                            <input type="text" value={bill.phone} style={{ color: "black", fontWeight: "500" }} />
                                        </div>
                                    </div>
                                    <div className="col-lg-12">
                                        <div className="checkout__form__input">
                                            <p>Address </p>
                                            <input type="text" value={bill.address} style={{ color: "black", fontWeight: "500" }} />
                                        </div>

                                    </div>

                                </div>
                            </div>
                            <div className="col-lg-4">
                                <div className="checkout__order">
                                    <h5>Bill Detail</h5>
                                    <div className="checkout__order__product">
                                        <ul>
                                            <li>
                                                <span className="top__text">Product</span>
                                                <span className="top__text__right"></span>
                                            </li>
                                            {
                                                billItems.map(item =>
                                                    <li>{item.productName} :
                                                        {PriceFormatterService.formatPrice(item.productPrice)} x
                                                        {item.productQuantity}
                                                        <span>
                                                            {PriceFormatterService.formatPrice(item.productPrice * item.productQuantity)}
                                                        </span>
                                                    </li>
                                                )

                                            }
                                        </ul>
                                    </div>
                                    <div className="checkout__order__total">
                                        <ul>
                                            <li>Total <span>{PriceFormatterService.formatPrice(bill.priceTotal)}</span></li>
                                        </ul>
                                    </div>
                                    <div className="checkout__order__widget">
                                        <label htmlFor="o-acc">
                                            Quy trình
                                            <input type="checkbox" id="o-acc" />
                                            <span className="checkmark"></span>
                                        </label>
                                        <p>Vui lòng chuẩn bị tinh thần nghe máy khi nhân viên gọi điện chốt đơn.</p>

                                        <label htmlFor="paypal">
                                            Status
                                            <input type="checkbox" id="paypal" />
                                            <span className="checkmark"></span>
                                        </label>
                                    </div>
                                    {/* <button type="submit" className="site-btn" onClick={() => navigate('/')}>Back to home</button> */}
                                   
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
            </section>
        </>
    )
}

export default SingleBillComponent;
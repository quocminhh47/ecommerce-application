import React, { useState, useEffect } from "react"
import Header from "../header/Header"
import BillService from "../../services/BillService"
import { Link } from 'react-router-dom'
import PriceFormatterService from './../../services/PriceFormatterService'

export default function ListBillsComponent() {

    const [bills, setBills] = useState([])

    useEffect(() => {
        BillService.getAllBills()
            .then(res => {
                console.log(res.data)
                setBills(res.data)
            })
            .catch(err => {
                console.log(err)
            })
    }, [])


    return (
        <>
            <Header status={true} />
            <section className="shop-cart spad">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-12">
                            <div className="shop__cart__table">
                                <h2 className="text-center"> Bill history    </h2>
                                <table className="table">
                                    <thead>
                                        <th> ID </th>
                                        <th> Date order</th>
                                        <th> Status</th>
                                        <th> Price total</th>
                                        <th> Action </th>
                                    </thead>
                                    <tbody>
                                        {
                                            bills.map(
                                                bill =>
                                                    <tr key={bill.billId}  style={{fontWeight :"500",  fontSize: "large"}}>
                                                        <td>{bill.billId} </td>
                                                        <td>{bill.createDate} </td>
                                                        <td>{bill.status == 0 ? 'Processing ' : 'Accepted'} </td>
                                                        <td>{PriceFormatterService.formatPrice(bill.priceTotal)} </td>
                                                        <td>
                                                            <Link className="btn btn-info" to={`/bill/${bill.billId}`} >Detail</Link>
                                                        </td>
                                                    </tr>
                                            )
                                        }
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>

            </section>
        </>
    )
}
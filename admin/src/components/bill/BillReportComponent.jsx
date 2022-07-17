import React, { useState, useEffect } from 'react'
import { useNavigate, Link, useParams } from 'react-router-dom'
import BillService from '../../services/BillService'
import HeaderComponent from '../header/HeaderComponent'
import AuthService from '../../services/AuthService'
import PriceFormatterService from '../../services/PriceFormatterService'


export default function BillReportComponent() {
    const [bills, setBills] = useState({})
    const [loginStatus, setLoginStatus] = useState({})
    const [billContent, setBillContent] = useState([])
    const [billReload, setBillReload] = useState();
    const [sale, setSale] = useState(0)
    const [billUpdate, setBillUpdate] = useState()
    const navigate = useNavigate()

    // ----------Fetch list bill---------
    useEffect(() => {
        BillService.getAllBillsByStatusAdmin('purchased')
            .then(res => {
                if (!billUpdate) {
                    if (res.status === 200) {
                        setBills(res.data)
                        setLoginStatus(true)
                        setBillContent(res.data.billContent)
                    }
                }
                console.log(res.data)

            })
            .catch(err => {
                console.log(err)
                if (err.response.status == '403') {
                    alert("You are not authorized!")
                    setLoginStatus(false)
                    navigate('/login')
                }
            })


    }, [billReload])

    const getSale = () => {
        let start = document.getElementById('dateStart').value
        let end = document.getElementById('dateEnd').value
        console.log(start, end)
        BillService.getSaleFromDateRange(start, end)
            .then(res => {
                console.log(res.data);

                if (res.status === 200) {
                    if (sale) {
                        setSale(0)
                    }
                    setSale(res.data.sale)
                    alert('Success')
                    setBillContent(res.data.listBills)
                    setBillUpdate(res.data.listBills)
                    setBillReload(res.data.listBills)
                }
            })
            .catch(err => {
                console.log(err);
                if (err.response) {
                    alert(err.response.data.message)
                }
                else {
                    alert("Failed")
                }

            })
    }


    return (
        <>
            <HeaderComponent status={loginStatus} />
            <h2 className="text-center"> Total Sale </h2>
            <div className="container" style={{ textAlign: "center" }}>
                <div className="row">
                    <div className="col-lg-6 col-md-6 col-sm-6">
                        <div className="checkout__form__input">
                            <label>From </label> <br />
                            <input type="date" id='dateStart' />
                        </div>
                    </div>
                    <div className="col-lg-6 col-md-6 col-sm-6">
                        <div className="checkout__form__input">
                            <label>To </label> <br />
                            <input type="date" id='dateEnd' />
                        </div>
                    </div>

                </div>
                <button className="btn btn-info" onClick={() => getSale()}> Check</button>

            </div>
            <section className="ftco-section" >
                <div className="container">
                    <h3 style={{ color: "green", textAlign: "center" }} >
                        {PriceFormatterService.formatPrice(sale)}
                    </h3>
                    <table className="table">
                        <thead className="thead-primary">
                            <th> ID</th>
                            <th>Customers </th>
                            <th>Created at </th>
                            <th>Bill Total</th>
                            <th> Status</th>
                            <th> </th>
                        </thead>
                        <tbody>
                            {
                                billContent.map(
                                    bill =>
                                        <tr key={bill.billId}  >
                                            <td>{bill.billId} </td>
                                            <td>{bill.username} </td>
                                            <td>{bill.createDate} </td>
                                            <td>{PriceFormatterService.formatPrice(bill.priceTotal)}</td>
                                            <td style={{ color: "green" }}>
                                                {BillService.getBillStatus(bill.status)}
                                            </td>
                                            <td>
                                                <Link className="btn btn-info" to={`/bills/${bill.billId}`} >Detail</Link>
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>
                    </table>
                </div>

            </section>
        </>
    )
}


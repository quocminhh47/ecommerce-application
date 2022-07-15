import axios from 'axios'
import React, { useState, useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import BillService from '../../services/BillService'
import HeaderComponent from '../header/HeaderComponent'
import AuthService from '../../services/AuthService'


export default function ListBillComponent() {

    const [bills, setBills] = useState({})
    const [loginStatus, setLoginStatus] = useState({})
    const [billContent, setBillContent] = useState([])
    const statusBIll = ['Unsolved', 'Canceled', 'Accepted', 'Purchased']
    const [billReload, setBillReload] = useState();

    const navigate = useNavigate()

    useEffect(() => {
        BillService.getAllBillsAdmin()
            .then(res => {
                console.log(res.data)
                if (res.status === 200) {
                    setBills(res.data)
                    setLoginStatus(true)
                    setBillContent(res.data.billContent)
                }
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

    const changeBillStatusHandler = (e, billId) => {
        const statusValue = e.target.innerText;
        console.log(statusValue);
        console.log(billId)

        // const token = localStorage.getItem("accessToken")
        // AuthService.checkUserAuth(token);
        // const url = `http://localhost:8080/admin/api/bills/change/status?bill=${billId}&status=${statusValue}`
        // const params = {        
        //     headers: {
        //         'Authorization': `Bearer ${token}`
        //     }
        // }
        BillService.changeBillStatus(billId, statusValue)
        // axios.put(url, params)
        .then(res => {
            console.log(res.data)
            if(res.status === 200) {
                alert("Update Success")
                setBillReload(res.data)
            }
        })
        .catch(err => {
            console.log(err)
            alert("Update bill failed")
        })
    }


    return (
        <>
            <HeaderComponent status={loginStatus} />
            <section className="ftco-section">
                <div className="container">
                    <h2 className="text-center"> List Bills </h2>
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
                                            <td>{bill.priceTotal}</td>
                                            <td>
                                                <div className="nav-item dropdown">
                                                    <a href="#" className="nav-link dropdown-toggle active"
                                                        data-toggle="dropdown" style={{ color: "blue" }}>
                                                        {BillService.getBillStatus(bill.status)}
                                                    </a>
                                                    <div className="dropdown-menu rounded-0 m-0">
                                                        {statusBIll.map((item) => 
                                                            <a onClick={(e) => changeBillStatusHandler(e, bill.billId)} value={item}
                                                            className="dropdown-item">
                                                            {item}
                                                        </a>
                                                        )}

                                                    </div>
                                                </div>
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

// function ListBillStatusComponent(props, bill, changeBillStatusHandler) {

//     props.map((item) => {
//         if (item != BillService.getBillStatus(bill.status)) {
//             return (
//                 <a onClick={(e) => changeBillStatusHandler(e)} value={item}
//                     className="dropdown-item">
//                     {item}
//                 </a>
//             )
//         }
//     })

// }

// export { ListBillComponent };

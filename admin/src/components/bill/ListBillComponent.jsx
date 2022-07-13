import React, {useState, useEffect} from 'react'
import {useNavigate, Link} from 'react-router-dom'
import BillService from '../../services/BillService'
import HeaderComponent from '../header/HeaderComponent'


export default function ListBillComponent() {

    const [bills, setBills] = useState({})  
    const [loginStatus, setLoginStatus] = useState({})
    const [billContent, setBillContent] = useState([])

    const navigate = useNavigate()

    useEffect(() => {
        BillService.getAllUnsolvedBills()
        .then(res => {
            console.log(res.data)
            if(res.status === 200) {
                setBills(res.data)
                setLoginStatus(res.data.loginStatusResponse)
                setBillContent(res.data.billContent)
            }
        })
        .catch(err => {
            console.log(err)
            if(err.response.status == '403') {
                navigate('/login')
            }
        })
    }, [])

    return (
        <>
            <HeaderComponent status={loginStatus.roleName == "ADMIN" ? true : false} />
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
                            <th> Accept</th>
                            <th> Cancel</th>
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
                                            <td>{bill.status === 0 ? 'Unsolved' : 'Accepted'}</td>
                                            <td>
                                                <Link className="btn btn-info" to={`/bill/`} >Accept</Link>
                                            </td>
                                            <td>
                                                <Link className="btn btn-info" to={`/bill/`} >Cancel</Link>
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

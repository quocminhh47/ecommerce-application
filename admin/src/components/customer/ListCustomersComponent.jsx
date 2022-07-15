import React, { useEffect, useState } from 'react'
import CustomerService from '../../services/CustomerService'
import { Link , useNavigate} from 'react-router-dom'
import './ListCustomersTable.css'
import LoginService from '../../services/LoginService'
import HeaderComponent from "../header/HeaderComponent";

export default function ListCustomersComponent() {
    const navigate = useNavigate()
    if (!LoginService.checkAuthorization()) {
        navigate('/login')
     } 

    const [customers, setCustomers] = useState([]);
    const [loginStatus, setLoginStatus] = useState();

    // if (!LoginService.checkAuthorization()) {
    //     window.location.href = '/login'
    //  } 

    useEffect(() => {
        CustomerService.fetchAllCustomers()
            .then(res => {
                console.log(res.data)
                setCustomers(res.data.userAccountContent)
                //setLoginStatus(LoginService.checkLoginStatus(res.data));   
                if(res.data.loginStatus.roleName == 'ADMIN') {
                    setLoginStatus(true)
                }             
            })
            .catch(err => {
                console.log(err)
                if(err.response.status == '403') {
                    navigate('/login')
                }
                else alert("Failed, try again")
            })
    }, [])

    return (
        <>
        <HeaderComponent status={loginStatus} />
        <section className="ftco-section">
            <div className="container">
                <h2 className="text-center"> List Customers </h2>
                <table className="table">
                    <thead className="thead-primary">
                        <th> ID </th>
                        <th>First name </th>
                        <th>Last Name </th>
                        <th> Address</th>
                        <th> Email</th>
                        <th> Phone</th>
                        <th> Enabled</th>
                        <th> Non locked </th>
                        <th> Action </th>
                    </thead>
                    <tbody>
                        {
                            customers.map(
                                customer =>
                                    <tr key={customer.id}  >
                                        <td>{customer.id} </td>
                                        <td>{customer.firstName} </td>
                                        <td>{customer.lastName} </td>
                                        <td>{customer.address}</td>
                                        <td>{customer.email}</td>
                                        <td>{customer.phone}</td>
                                        <td>{customer.enabled ? 'Yes' : 'No'}</td>
                                        <td>{customer.nonLocked ? 'Yes' : 'No'}</td>
                                        <td>
                                            <Link className="btn btn-info" to={`/customers/${customer.id}`} >Detail</Link>
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

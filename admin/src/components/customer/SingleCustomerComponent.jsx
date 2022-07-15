import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom';
import CustomerService from '../../services/CustomerService';
import LoginService from '../../services/LoginService';
import HeaderComponent from "../header/HeaderComponent";
import './SingleCustomer.css'
import './RadioButton.css'


export default function SingleCustomerComponent() {
    let navigate  = useNavigate();
    const [loginStatus, setLoginStatus] = useState();


    if (!LoginService.checkAuthorization()) {
        navigate('/login')
     } 

    const { customerId } = useParams();
    //customer props
    const [customer, setCustomer] = useState({})
    const [isEnabled, setIsEnabled] = useState()
    const [isNonLocked, setIsNonLocked] = useState()

    useEffect(() => {
        CustomerService.getCustomerInfoById(customerId)
            .then(res => {
                console.log(res.data)
                setCustomer(res.data)
                setIsEnabled(res.data.enabled)
                setIsNonLocked(res.data.nonLocked)
                setLoginStatus(true)
            })
            .catch(err => {
                console.log(err)
                if(err.response.status == '403') {
                    setLoginStatus(false)
                    navigate('/login')
                }
            })
    }, [])

    

    const updateCustomer = () => {
        const userPayload = {isEnabled, isNonLocked}
        console.log(userPayload)
        CustomerService.updateCustomer(userPayload, customerId)
        .then(res => {
            console.log(res.data)
            if(res.status == 200){ 
                alert("Update success")
                navigate("/customers")
            }            
        })
        .catch(err => {
            console.log(err)
            alert("Failed")
        })
    }

    return (
        <>
        <HeaderComponent status={loginStatus}/>
        <div className="container-fluid px-1 py-5 mx-auto">
            <div className="row d-flex justify-content-center">
                <div className="col-xl-7 col-lg-8 col-md-9 col-11 text-center">
                    <h3>Customer Detail</h3>
                    <div className="card">
                        <form className="form-card" onsubmit="event.preventDefault()">

                            <div className="row justify-content-between text-left">
                                <div className="form-group col-sm-6 flex-column d-flex">
                                    <label className="form-control-label px-3">ID
                                        <span className="text-danger"> *</span></label>
                                    <input type="text" id="fname" name="fname" value={customer.id} />
                                </div>
                                <div className="form-group col-sm-6 flex-column d-flex">
                                    <label className="form-control-label px-3">Email:
                                        <span className="text-danger"> *</span>
                                    </label>
                                    <input type="text" id="lname" name="lname" value={customer.email} />
                                </div>
                            </div>

                            <div className="row justify-content-between text-left">
                                <div className="form-group col-sm-6 flex-column d-flex">
                                    <label className="form-control-label px-3">First name:
                                        <span className="text-danger"> *</span>
                                    </label>
                                    <input type="text" id="email" name="email" value={customer.firstName} />
                                </div>
                                <div className="form-group col-sm-6 flex-column d-flex">
                                    <label className="form-control-label px-3">Last name:
                                        <span className="text-danger"> *</span>
                                    </label>
                                    <input type="text" id="mob" name="mob" value={customer.lastName} />
                                </div>
                            </div>

                            <div className="row justify-content-between text-left">
                                <div className="form-group col-sm-6 flex-column d-flex">
                                    <label className="form-control-label px-3">Address:
                                        <span className="text-danger"> *</span>
                                    </label>
                                    <input type="text" id="text" name="address" value={customer.address} />
                                </div>
                                <div className="form-group col-sm-6 flex-column d-flex">
                                    <label className="form-control-label px-3">Phone:
                                        <span className="text-danger"> *</span>
                                    </label>
                                    <input type="text" id="phone" name="phone" value={customer.phone} />
                                </div>
                            </div>

                            <div className="row justify-content-between text-left">
                                <div className="form-group col-sm-6 flex-column d-flex">
                                    <div>
                                        <label htmlFor="enabled">Enabled</label> &emsp;
                                        <select 
                                         onChange={(e) => {
                                            setIsEnabled(e.target.value)
                                           
                                        }}>
                                            <option name="enabled" value={true}
                                            selected={isEnabled}
                                            >Enabled
                                            </option>

                                            <option name="disabled" value={false}
                                            selected={!isEnabled}
                                            >Disabled
                                            </option>
                                        </select>
                                    </div>


                                </div>

                                <div className="form-group col-sm-6 flex-column d-flex">
                                    <div>
                                        <label htmlFor="enabled">Actived</label> &emsp;
                                        <select onChange={(e) => {
                                            setIsNonLocked(e.target.value)
                                           
                                        }}>
                                            <option name="nonLocked" value={true}
                                            selected={isNonLocked}
                                            >Actived
                                            </option>

                                            <option name="locked" value={false}
                                            selected={!isNonLocked}
                                            >Locked
                                            </option>
                                        </select>
                                    </div>
                                </div>
                            </div>


                            <div className="row justify-content-end">
                                <div className="form-group col-sm-6">
                                    <button type="button" className="btn-block btn-primary"
                                    onClick={() => updateCustomer()}
                                    >Modify
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
        </>
    )
}

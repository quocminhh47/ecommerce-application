import React, { useState } from "react";
import { useNavigate, Link } from 'react-router-dom'
import AuthService from "../../services/AuthService";
import RegisterService from '../../services/RegisterService';
import HeaderComponent from "../header/HeaderComponent";

export default function SignupComponent() {

    const navigate = useNavigate()

    const userStatus = AuthService.checkTokenExist()
    if (!userStatus) {
        //alert("You must login first!");
       window.location.href = '/login'
    }
    const [noti, setNoti] = useState('');

    //registration request data
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [phone, setPhone] = useState('');
    const [address, setAddress] = useState('');

    //server validation response
    const [firstNameErr, setFirstNameErr] = useState('')
    const [lastNameErr, setLastNameErr] = useState('')
    const [emailErr, setEmailErr] = useState('')
    const [passwordErr, setPasswordErr] = useState('')
    const [phoneErr, setPhoneErr] = useState('')
    const [addressErr, setAddressErr] = useState('')


    const register = () => {
        const userPayload = { firstName, lastName, email, password, phone, address }
        RegisterService.register(userPayload)
            .then(res => {
                console.log(res)
                if (res.status === 201) {
                    setNoti(res.data.message);
                    alert("Register success, confirm mail to continue")
                    window.open('https://mail.google.com/mail/u/0/')
                    navigate('/login')
                }
            })
            .catch(err => {
                console.log(err);
                if (err.response.data.validationErrors) {
                    const validattion = err.response.data.validationErrors
                    setFirstNameErr(validattion.firstName)
                    setLastNameErr(validattion.lastName)
                    setEmailErr(validattion.email)
                    setPasswordErr(validattion.password)
                    setPhoneErr(validattion.phone)
                    setAddressErr(validattion.address)
                }
                else if (err.response) {
                    setNoti(err.response.data.message)
                }
                else {
                    setNoti('Register failed')
                }

            })
    }


    return (
        <>
            <HeaderComponent status={ userStatus } />
            <section className="ftco-section">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-12 col-lg-10">
                            <div className="wrap d-md-flex">
                                <div className="text-wrap p-4 p-lg-5 text-center d-flex align-items-center order-md-last">
                                    <div className="text w-100">
                                        <h2>Registration now</h2>
                                        <p>Have an account?</p>
                                        <Link to="/login" className="btn btn-white btn-outline-white">Login</Link> <br />
                                    </div>
                                </div>
                                <div className="login-wrap p-4 p-lg-5">
                                    <div className="d-flex">
                                        <p style={{ color: "red" }} >
                                            {noti}
                                        </p>
                                    </div>
                                    <form action="#" className="signin-form">
                                        <div className="form-group mb-3">
                                            <label className="label" htmlFor="name">First name :<p style={{ color: "red" }}> {firstNameErr || ''}</p></label>
                                            <input type="text" className="form-control" placeholder="First name"
                                                onChange={(e) => {
                                                    setFirstNameErr('')
                                                    setFirstName(e.target.value)
                                                }}
                                            />
                                        </div>
                                        <div className="form-group mb-3">
                                            <label className="label" htmlFor="name">Last name : <p style={{ color: "red" }}> {lastNameErr || ''} </p>  </label>
                                            <input type="text" className="form-control" placeholder="Last name"
                                                onChange={(e) => {
                                                    setLastNameErr('')
                                                    setLastName(e.target.value)
                                                }}
                                            />
                                        </div>
                                        <div className="form-group mb-3">
                                            <label className="label" htmlFor="name"> Email : <p style={{ color: "red" }}> {emailErr || ''}</p></label>
                                            <input type="text" className="form-control" placeholder="Email"
                                                onChange={(e) => {
                                                    setEmailErr('')
                                                    setEmail(e.target.value)
                                                }}
                                            />
                                        </div>
                                        <div className="form-group mb-3">
                                            <label className="label" htmlFor="name">Password :<p style={{ color: "red" }}> {passwordErr || ''}</p> </label>
                                            <input type="password   " className="form-control" placeholder="Password"
                                                onChange={(e) => {
                                                    setPasswordErr('')
                                                    setPassword(e.target.value)
                                                }}
                                            />
                                        </div>
                                        <div className="form-group mb-3">
                                            <label className="label" htmlFor="password">Phone : <p style={{ color: "red" }}>{phoneErr || ''} </p></label>
                                            <input type="text" className="form-control" placeholder="Phone"
                                                onChange={(e) => {
                                                    setPhoneErr('')
                                                    setPhone(e.target.value)
                                                }}
                                            />
                                        </div>
                                        <div className="form-group mb-3">
                                            <label className="label" htmlFor="password">Address :<p style={{ color: "red" }}> {addressErr || ''}</p></label>
                                            <input type="text" className="form-control" placeholder="Address"
                                                onChange={(e) => {
                                                    setAddressErr('')
                                                    setAddress(e.target.value)
                                                }}
                                            />
                                        </div>
                                        <div className="form-group">
                                            <button type="button" className="form-control btn btn-primary submit px-3"
                                                onClick={register}
                                            >Sign In</button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        </>
    )
}

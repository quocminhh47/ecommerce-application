import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import RegisterService from "../../services/RegisterService";

export default function RegistrationComponent() {
    const navigate = useNavigate()
    const GMAIL_INBOX_URL = "https://mail.google.com/mail/u/0/#inbox";
    //state valid input
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
        setNoti('npm')
        const userPayload = { firstName, lastName, email, password, phone, address }
        RegisterService.register(userPayload)
            .then(res => {
                console.log(res)
                if (res.status === 201) {
                    setNoti(res.data.message);
                    alert("Register success, confirm mail to continue")
                    window.open(GMAIL_INBOX_URL)
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
                    console.log(phone)
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
        <section className="signin-page account">
            <div className="container">
                <div className="row">
                    <div className="col-md-6 col-md-offset-3">
                        <div className="block text-center">
                            <Link className="logo" to="/">
                                <img src="https://res.cloudinary.com/duoih0eqa/image/upload/v1657269647/Untitled_rj2t0s.png" alt="" />
                            </Link>
                            <h2 className="text-center">Create Your Account</h2>

                            <form className="text-left clearfix" >
                                <span style={{ color: "red" }}> {noti}</span>
                                <div className="form-group">
                                    <span htmlFor="password" >First Name :<p style={{ color: "red" }}> {firstNameErr || ''}</p></span>
                                    <input id="firstName" type="text" className="form-control" placeholder="First Name"
                                        onChange={(e) => {
                                            setFirstNameErr('')
                                            setFirstName(e.target.value)
                                        }} />
                                </div>

                                <div className="form-group">
                                    <span htmlFor="password" >Last name : <p style={{ color: "red" }}> {lastNameErr || ''} </p></span>
                                    <input id="lastName" type="text" className="form-control" placeholder="Last Name"
                                        onChange={(e) => {
                                            setLastNameErr('')
                                            setLastName(e.target.value)
                                        }} />
                                </div>
                                <div className="form-group">
                                    <span htmlFor="password" >Email :<p style={{ color: "red" }}> {emailErr || ''}</p></span>
                                    <input id="email" type="email" className="form-control" placeholder="Email"
                                        onChange={(e) => {
                                            setEmailErr('')
                                            setEmail(e.target.value)
                                        }} />
                                </div>
                                <div className="form-group">
                                    <span htmlFor="password" >Password :<p style={{ color: "red" }}> {passwordErr || ''}</p></span>
                                    <input id="password" type="password" className="form-control" placeholder="Password"
                                        onChange={(e) => {
                                            setPasswordErr('')
                                            setPassword(e.target.value)
                                        }}
                                    />
                                </div>
                                <div className="form-group">
                                    <span htmlFor="password" >Phone :<p style={{ color: "red" }}>{phoneErr || ''} </p></span>
                                    <input id="phone" type="text" className="form-control" placeholder="Phone"
                                        onChange={(e) => {
                                            setPhoneErr('')
                                            setPhone(e.target.value)
                                        }}
                                    />
                                </div>
                                <div className="form-group">
                                    <span htmlFor="password" >Address :<p style={{ color: "red" }}> {addressErr || ''}</p></span>
                                    <input id="address" type="text" className="form-control" placeholder="Address" onChange={(e) => {
                                        setAddressErr('')
                                        setAddress(e.target.value)
                                    }} />
                                </div>
                                <div className="text-center">
                                    <button type="button" className="btn btn-main text-center"
                                        onClick={register}
                                    >Sign In
                                    </button>
                                </div>
                            </form>
                            <p className="mt-20">Already hava an account ?<Link to="/login"> Login</Link></p>
                        </div>
                    </div>
                </div>
            </div>
        </section>
    )
}
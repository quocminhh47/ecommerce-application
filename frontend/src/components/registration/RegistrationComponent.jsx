import React, { useState } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import CheckRegistrationService from "../../services/CheckRegistrationService";

export default function RegistrationComponent() {

    const REGISTRATION_LINK_URL = "http://localhost:8080/v1/api/registration";
    const GMAIL_INBOX_URL = "https://mail.google.com/mail/u/0/#inbox";
    //state valid input
    const [mess, setMess] = useState('Vui lòng nhập đầy đủ thông tin');

    const registerHandler = () => {
        const firstName = document.getElementById('firstName').value;
        const lastName = document.getElementById('lastName').value;
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;
        const phone = document.getElementById('phone').value;
        const address = document.getElementById('address').value;

        if (CheckRegistrationService.checkInfo(firstName, lastName, email, password, phone, address)) {
           // setMess('Nhập thông tin thành công');
            const dataPayload = {
                firstName: firstName,
                lastName: lastName,
                email: email,
                password: password,
                phone: phone,
                address: address    
            }
            console.log(dataPayload)

            axios.post(REGISTRATION_LINK_URL, dataPayload)
            .then(res => {
                console.log(res.data);
                if(res.status == 201) {
                    alert(res.data.message);
                    //open mail
                    window.open(GMAIL_INBOX_URL)
                    //redirect to login page
                    window.location.href = "/login";
                }
            })
            .catch(error => {
                setMess(error.mess)
            })

        } else setMess("Phone tối thiểu 10 và tối đa 12 ký tự. Email phải nhập đúng định dạng!!")
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
                                <span style={{ color: "red" }}>{mess}</span>
                                <div className="form-group">
                                    <span htmlFor="password" >First Name</span>
                                    <input id="firstName" type="text" className="form-control" placeholder="First Name"  />
                                </div>

                                <div className="form-group">
                                    <span htmlFor="password" >Last name</span>
                                    <input id="lastName" type="text" className="form-control" placeholder="Last Name"  />
                                </div>
                                <div className="form-group">
                                    <span htmlFor="password" >Email</span>
                                    <input id="email" type="email" className="form-control" placeholder="Email"  />
                                </div>
                                <div className="form-group">
                                    <span htmlFor="password" >Password</span>
                                    <input id="password" type="password" className="form-control" placeholder="Password"  />
                                </div>
                                <div className="form-group">
                                    <span htmlFor="password" >Phone</span>
                                    <input id="phone" type="text" className="form-control" placeholder="Phone"  />
                                </div>
                                <div className="form-group">
                                    <span htmlFor="password" >Address</span>
                                    <input id="address" type="text" className="form-control" placeholder="Address"  />
                                </div>
                                <div className="text-center">
                                    <button type="button" className="btn btn-main text-center"
                                        onClick={registerHandler}
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
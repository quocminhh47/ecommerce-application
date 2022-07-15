import axios from "axios";
import React, { useState } from "react";
import { Link } from "react-router-dom";
import './css/LoginStyle.css'
import {useNavigate} from 'react-router-dom'
import HeaderComponent from "../header/HeaderComponent";

function LoginComponent() {

    
    const [noti, setNoti] = useState('');
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();

    const loginHandler = () => {
        let loginPayload = {};
        if (username && password) {
            loginPayload = {
                username: username,
                password: password
            };

            axios.post('http://localhost:8080/api/auth/login', loginPayload)
                .then(res => {
                    const roles = res.data.roles;
                    if (roles[0] === "ROLE_ADMIN" && res.data.status === 200) {

                        alert("Login success!")

                        //get token from response
                        const token = res.data.accessToken;

                        //set token to local
                        localStorage.setItem("accessToken", token)

                        //set token to axios common header
                        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

                        //redirect to home page
                        window.location.href = '/';
                    }
                    else setNoti("Login failed !!");
                })
                .catch((err) => {
                    setNoti("Login failed !!");
                });

        } else setNoti('Please enter the fields');

    }

    return (
        <div className="limiter">
            <div className="container-login100">
                <div className="wrap-login100 p-l-85 p-r-85 p-t-55 p-b-55">
                    <p id="notification" style={{ color: 'red' }}></p>

                    <form className="login100-form validate-form flex-sb flex-w" id="form_id" >
                        <span className="login100-form-title p-b-32">
                            Account Login
                        </span>



                        <span className="txt1 p-b-11">
                            Username
                        </span>
                        <div className="wrap-input100 validate-input m-b-36" data-validate="Email is required">
                            <input id="email" className="input100" type="text" placeholder="Email"
                                required="required" onChange={(e) => setUsername(e.target.value)} />
                            <span className="focus-input100"></span>
                        </div>

                        <span className="txt1 p-b-11">
                            Password
                        </span>
                        <div className="wrap-input100 validate-input m-b-12" data-validate="Password is required">
                            <span className="btn-show-pass">
                                <i className="fa fa-eye"></i>
                            </span>
                            <input id="password" className="input100" type="password" placeholder="Password" onChange={(e) => setPassword(e.target.value)} />
                            <span className="focus-input100"></span>
                        </div>

                        <div className="flex-sb-m w-full p-b-48">

                            <div>
                                <a to="" className="txt3">
                                    New here? Click to register!
                                </a>
                                <br />
                                <span className="txt1 p-b-11" style={{ color: 'red' }}>{noti}</span>
                            </div>

                        </div>

                        <div className="container-login100-form-btn">
                        </div>

                    </form>
                    <button type="button" className="login100-form-btn" id="btnLogin" onClick={loginHandler} >
                        Login
                    </button>
                </div>
            </div>
        </div>
    )
}
function LoginComponent2() {

    window.localStorage.removeItem("accessToken");
    const [noti, setNoti] = useState('');
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();

    const navigate = useNavigate()

    const loginHandler = () => {
        let loginPayload = {};
        if (username && password) {
            loginPayload = {
                username: username,
                password: password
            };

            axios.post('http://localhost:8080/api/auth/login', loginPayload)
                .then(res => {
                    const roles = res.data.roles;
                    if (roles[0] === "ROLE_ADMIN") {

                        alert("Login success!")

                        //get token from response
                        const token = res.data.accessToken;

                        //set token to local
                        localStorage.setItem("accessToken", token)

                        //set token to axios common header
                        axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

                        //redirect to home page
                        window.location.href = '/'
                    }
                    else  if (roles[0] === "ROLE_USER") {
                        setNoti("You're not authorized to do this!!");
                    }
                     else if(res.status === 403) {
                        setNoti('You are not authorized')
                    }
                    else {
                        console.log(res)
                        setNoti("Login failed !!");
                    }
                })
                .catch((e) => {                    
                    console.log(e)
                    if(e.response.data.message) {
                        setNoti(e.response.data.message);
                    }
                    else if(e.response.status === 400) {
                        setNoti("Username/ password wrong !!");
                    }
                    else {
                        setNoti("Login failed !!");
                    }
                });

        } else setNoti('Please enter the fields');

    }

    return (
        <>
        <HeaderComponent status={false}/>
        <section className="ftco-section">
            <div className="container">
                <div className="row justify-content-center">
                    <div className="col-md-12 col-lg-10">
                        <div className="wrap d-md-flex">
                            <div className="text-wrap p-4 p-lg-5 text-center d-flex align-items-center order-md-last">
                                <div className="text w-100">
                                    <h2>Welcome to Quoc Minh Shop</h2>
                                    <p>Don't have an account?</p>
                                </div>
                            </div>
                            <div className="login-wrap p-4 p-lg-5">
                                <div className="d-flex">
                                    <div className="w-100">
                                        <h3 className="mb-4">Sign In</h3>
                                    </div>
                                    <p style={{color: "red"}} >
                                        {noti}
                                    </p>
                                </div>
                                <form action="#" className="signin-form">
                                    <div className="form-group mb-3">
                                        <label className="label" htmlFor="name">Username/ Email</label>
                                        <input type="text" className="form-control" placeholder="Username"
                                            onChange={(e) => {
                                                setUsername(e.target.value)
                                                setNoti('')
                                                }} />
                                    </div>
                                    <div className="form-group mb-3">
                                        <label className="label" htmlFor="password">Password</label>
                                        <input type="password" className="form-control" placeholder="Password"
                                            onChange={(e) => {
                                                setPassword(e.target.value)
                                                setNoti('')
                                                }}
                                        />
                                    </div>
                                    <div className="form-group">
                                        <button type="button" className="form-control btn btn-primary submit px-3"
                                            onClick={loginHandler}>Sign In</button>
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

export default LoginComponent2;


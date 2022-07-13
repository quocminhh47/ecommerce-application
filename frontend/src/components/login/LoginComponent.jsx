import axios from "axios";
import React, { useState } from "react";
import { Link } from "react-router-dom";

function LoginComponent() {

    localStorage.removeItem("accessToken");

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
                    console.log(res.status)
                    if (res.status == 200) {

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
                  
                    else setNoti(res.message);
                })
                .catch((error) => {
                    console.log(error)
                    console.log(error.response.status)
                    //Error
                    if(error.response) {
                        if(error.response.status == 403) {setNoti('Account is invalid')}   
                        setNoti(error.response.data.message);
                    } else if(error.request) {

                    }
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
                                <Link to="/sigin" className="txt3">
                                    New here? Click to register!
                                </Link>
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

export default LoginComponent;
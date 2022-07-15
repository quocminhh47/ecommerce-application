import React from "react";

export default new class AuthService {

    checkUserAuth(token) {
        if(!token) {
            alert("You must login first");
           return  window.location.href = "/login";
        }        
    }

    checkTokenExist(navigate) {
        const token = window.localStorage.getItem("accessToken");
        if(!token) {        
            navigate('/login')   
           return  false;
        }  else return false;
    }
}
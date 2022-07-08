import React from "react";

export default new class AuthService {

    checkUserAuth(token) {
        if(!token) {
            alert("You must login first");
           return  window.location.href = "/login";
        }        
    }
}
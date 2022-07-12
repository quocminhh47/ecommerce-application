import React from "react";

class AuthService {

    checkUserAuth(token) {
        if(!token) {
            alert("You must login first");
           return  window.location.href = "/login";
        }        
    }

    checkTokenExist(){
        const token = window.localStorage.getItem("accessToken")
        if(token) return true;
        else return false
    }
}

export default new AuthService();
import React from "react";

import HeaderComponent from "../../components/header/HeaderComponent";;
import FooterCompnent from "../../components/footer/FooterComponent";
import LoginComponent from "../../components/login/LoginComponent";


export default function LoginPage() {
    return (
        <>
            <HeaderComponent />
            <LoginComponent />
            <FooterCompnent />
        </>
    )
}
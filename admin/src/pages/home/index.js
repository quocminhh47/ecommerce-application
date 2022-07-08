import React from "react";
import HeaderComponent from "../../components/header/HeaderComponent";
import FooterCompnent from "../../components/footer/FooterComponent";
import ListProductComponent from "../../components/product/ListProductComponent";


export default function ListProductPage() {
    return (
        <>
            <HeaderComponent />
            <ListProductComponent />
            <FooterCompnent />
        </>

    );
}
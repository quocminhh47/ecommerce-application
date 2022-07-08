import React from "react";
import Footer from "../../components/footer/Footer";
import Discount from "../../components/services/Discount";
import ListProduct from "../../components/product/home/ListProduct";


export default function HomePage() {
    return (
        <>
            <ListProduct />
            <Discount />
            <Footer />
        </>

    );
}
import React from "react";
import Header from "../../components/header/Header";
import Banner from "../../components/banner/Banner";
import Services from "../../components/services/Service";
import Footer from "../../components/footer/Footer";
import Discount from "../../components/services/Discount";
import ListProduct from "../../components/product/home/ListProduct";


export default function HomePage() {
    return (
        <>
            <Header />
            <Banner />
            <Services />
            <ListProduct />
            <Discount />
            <Footer />
        </>

    );
}
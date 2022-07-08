import React from "react";
import DetailProductComponent2 from "../../../components/product/detail/DetailProductComponent";
import Services from "../../../components/services/Service";
import Footer from "../../../components/footer/Footer";

export default function DetailProduct() {
    return(
        <>              
            <DetailProductComponent2/>
            <Services />
            <Footer/>
        </>
    )
}
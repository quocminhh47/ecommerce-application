import React, { useEffect, useState } from "react";
import axios from "axios";

export default function ProductsComponent() {

    const PRODUCTS_API_BASE_URL = "http://localhost:8080/user/api/products/";

    const [products, setProducts] = useState([])

    const fetchProducts = () => {
        const token = localStorage.getItem("accessToken");
        axios.get(PRODUCTS_API_BASE_URL, {
            // params: {
            //     pageNo: pageNo
            // },
            // headers: {
            //     'Authorization': `Bearer ${token}`
            // }
        })
            .then(res => {
                console.log(res.data)
                setProducts(res.data.productContent);
                // setPageNo(res.data.pageNo);
                // setPageSize(res.data.pageSize);
                // setTotalPage(res.data.totalPages);
                // setLoginStatus(res.data.loginStatusResponse);
            })
    }

    useEffect(() => {
        fetchProducts();
    }, []);

    return (
        <section className="ftco-section bg-light">
            <div className="container">
                <div className="row justify-content-center mb-3 pb-3">
                    <div className="col-md-12 heading-section text-center ftco-animate">
                        <h2 className="mb-4">New Shoes Arrival</h2>

                    </div>
                </div>
            </div>
            <div className="container">
                <div className="row">

                {products.map(product => 
                    <div className="col-sm-12 col-md-6 col-lg-3 ftco-animate d-flex">
                        <div className="product d-flex flex-column">
                            <a href="#" className="img-prod"><img className="img-fluid" src="https://cdn.shopdongho.com/2018/09/AE-1200WHD-1AVDF-3-1.jpg" alt="Colorlib Template"/>
                                <div className="overlay"></div>
                            </a>
                            <div className="text py-3 pb-4 px-3">
                                <div className="d-flex">
                                    <div className="cat">
                                        <span>Lifestyle</span>
                                    </div>
                                    <div className="rating">
                                        <p className="text-right mb-0">
                                            <a href="#"><span className="ion-ios-star-outline"></span></a>
                                            <a href="#"><span className="ion-ios-star-outline"></span></a>
                                            <a href="#"><span className="ion-ios-star-outline"></span></a>
                                            <a href="#"><span className="ion-ios-star-outline"></span></a>
                                            <a href="#"><span className="ion-ios-star-outline"></span></a>
                                        </p>
                                    </div>
                                </div>
                                <h3><a href="#">Nike Free RN 2019 iD</a></h3>
                                <div className="pricing">
                                    <p className="price"><span>$120.00</span></p>
                                </div>
                                <p className="bottom-area d-flex px-3">
                                    <a href="#" className="add-to-cart text-center py-2 mr-1"><span>Add to cart <i className="ion-ios-add ml-1"></i></span></a>
                                    <a href="#" className="buy-now text-center py-2">Buy now<span><i className="ion-ios-cart ml-1"></i></span></a>
                                </p>
                            </div>
                        </div>
                    </div>
                )}
                 

                </div>
            </div>
        </section>
    )
}

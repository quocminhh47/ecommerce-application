import axios from "axios";
import React, { Component, useEffect, useState } from "react";
import { useParams, useNavigate } from 'react-router-dom'
import CartService from '../../../services/CartService';
import './Rating.css';
import BreadCrumbComponent from "../../../components/product/breadcrumb/BreadCrumbComponent";
import Header from "../../../components/header/Header";
import AuthService from "../../../services/AuthService";
import PriceFormatterService from "../../../services/PriceFormatterService";

function DetailProductComponent() {
    let params = useParams();
    const navigate = useNavigate();

    const PRODUCT_INFO_BASE_URL = "http://localhost:8080/user/api/products/" + params.id;
    console.log(PRODUCT_INFO_BASE_URL)
    const RATING_PRODUCT_URL = "http://localhost:8080/customer/api/ratings";


    const [product, setProduct] = useState({});

    const [images, setImages] = useState([]);
    //product rating avg info
    const [productRating, setProductRating] = useState();
    //rating of user
    const [userRating, setUserRating] = useState();

    const [buttonRating, setButtonRating] = useState('Submit');

    //show rating
    const showUserRating = (rating) => {
        if (rating != null) {
            setButtonRating('Update your rating')
            const ratingElement = document.getElementById(rating);
            ratingElement.setAttribute('checked', true);
        }
    }

    //fetch api
    const fetchProductInfo = () => {

        const token = localStorage.getItem("accessToken");
        const params = {
            
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(PRODUCT_INFO_BASE_URL, params)
            .then(res => {
                if (res.status == 200) {
                    setProduct(res.data);
                    console.log(res.data)
                    setImages(res.data.productImages);

                    setProductRating(res.data.ratingResponse.productRatingPoints)

                    setUserRating(res.data.ratingResponse.userRatingPoints)

                    showUserRating(res.data.ratingResponse.userRatingPoints);

                }
            })

    }

    useEffect(() => {
        fetchProductInfo();

    }, [userRating]);

    //put product to cart
    const putProductToCart = (id) => {
        CartService.addProductToCart(id);
    }

    //rating/ update rating
    const ratingProduct = () => {
        let rating = document.querySelector('input[name="rating"]:checked');
        console.log('rating' + rating);

        if (rating) {
            const ratingPoint = rating.value;
            const ratingRequest = {
                ratingPoints: ratingPoint,
                productId: product.id
            }

            const token = localStorage.getItem("accessToken");

            AuthService.checkUserAuth(token);


            return axios.post(RATING_PRODUCT_URL, ratingRequest, {
                headers: {
                    'Authorization': `Bearer ${token}`
                }
            })
                .then((res) => {
                    if (res.status == 201) {
                        alert(res.data.mess);
                        setUserRating(res.ratingPoints)
                    }

                    else if (res.status == 403) {
                        alert("You must login first");
                        navigate("/login");
                    }
                    else {
                        alert(res.data.mess);
                    }

                })
        } else alert("Enter your rating first!");
    }

    return (
        <>
            <Header status={product.isUserLogged} />
            <section className="product-details spad">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-6">
                            <div className="product__details__pic">
                                <div className="product__details__pic__left product__thumb nice-scroll"
                                    tabIndex="1" style={{ overflowY: 'hidden', outline: 'none' }}>
                                    {
                                        images.map(image =>

                                            <a className="pt" key={image.id}>
                                                <img src={image.image} alt="" />
                                            </a>

                                        )
                                    }
                                </div>
                                <div className="product__details__slider__content">
                                    <div className="product__details__pic__slider owl-carousel owl-loaded">
                                        <div className="owl-stage-outer">
                                            <div className="owl-stage"
                                                style={{ transform: "translate3d(0px, 0px, 0px)", transition: "all 0s ease 0s", width: "1652px" }}>

                                                <div className="owl-item active" style={{ width: "412.9px" }} >
                                                    <img className="product__big__img" src={product.thumbnail} alt="" />
                                                </div>
                                                {
                                                    images.map(image =>
                                                        <div className="owl-item active" style={{ width: "412.9px" }} key={image.id}>
                                                            <img className="product__big__img" src={image.image} alt="" />
                                                        </div>
                                                    )
                                                }
                                            </div>
                                        </div>
                                        <div className="owl-nav"><button type="button" role="presentation" className="owl-prev disabled">
                                            <i className="arrow_carrot-left"></i>
                                        </button>
                                            <button type="button" role="presentation" className="owl-next">
                                                <i className="arrow_carrot-right"></i>
                                            </button>
                                        </div>
                                        <div className="owl-dots disabled">

                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div className="rate bg-success py-3 text-white mt-3">
                                <h6 className="mb-0">Rating product</h6>
                                <div className="rating2">
                                    <input type="radio" name="rating" value="5" id="5" /><label htmlFor="5">☆</label>
                                    <input type="radio" name="rating" value="4" id="4" /><label htmlFor="4">☆</label>
                                    <input type="radio" name="rating" value="3" id="3" /><label htmlFor="3">☆</label>
                                    <input type="radio" name="rating" value="2" id="2" /><label htmlFor="2">☆</label>
                                    <input type="radio" name="rating" value="1" id="1" /><label htmlFor="1">☆</label>
                                </div>
                                <div className="buttons px-4 mt-0">
                                    <button className="btn btn-warning btn-block rating-submit" onClick={ratingProduct}>{buttonRating}</button>
                                </div>
                            </div>
                        </div>
                        <div className="col-lg-6">
                            <div className="product__details__text">
                                <h3>Name: {product.name} <span>Brand: {product.brandName}</span></h3>
                                <div className="rating">
                                    <i className="fa fa-star"></i>
                                    <i className="fa fa-star"></i>
                                    <i className="fa fa-star"></i>
                                    <i className="fa fa-star"></i>
                                    <i className="fa fa-star"></i>
                                    <p>(Đánh giá :  {parseFloat(productRating).toFixed(2)}/5 )</p>
                                </div>
                                <div className="product__details__price">Giá: {PriceFormatterService.formatPrice(product.price)} <span>  {PriceFormatterService.formatPrice(product.price)}  </span></div>
                                <p>{product.description}</p>
                                <div className="product__details__button">

                                    <a style={{ color: "white" }} className="cart-btn" onClick={() => putProductToCart(product.id)}>
                                        <span className="icon_bag_alt"></span>
                                        Add to cart
                                    </a>
                                    <ul>
                                        <li><a href="#"><span className="icon_heart_alt"></span></a></li>
                                        <li><a href="#"><span className="icon_adjust-horiz"></span></a></li>
                                    </ul>
                                </div>
                                <div className="product__details__widget">
                                    <ul>
                                        <li>
                                            <span>Số lượng:</span>
                                            <p>{product.quantity}</p>
                                        </li>
                                        <li>
                                            <span>Trạng thái:</span>
                                            <p>{product.status}</p>
                                        </li>
                                        <li>
                                            <span>Giới tính:</span>
                                            <p>Phái {product.gender ? 'nam' : 'nữ'} </p>
                                        </li>
                                        <li>
                                            <span>Bảo hành:</span>
                                            <p>{product.guaranteeTime} tháng  </p>
                                        </li>
                                        <li>
                                            <span>Chống nước:</span>
                                            <p>{product.isWaterProof ? 'Có kháng nước' : 'Không kháng nước'}</p>
                                        </li>
                                        <li>
                                            <span>Size:</span>
                                            <p>{product.size} mm</p>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
        </>
    );
}



// export default SingleProduct;
export default DetailProductComponent;


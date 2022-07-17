import axios from "axios";
import React, { Component, useEffect, useState } from "react";
import { useParams, useNavigate } from 'react-router-dom'
import CartService from '../../../services/CartService';
import './Rating.css';
import BreadCrumbComponent from "../../../components/product/breadcrumb/BreadCrumbComponent";
import Header from "../../../components/header/Header";
import AuthService from "../../../services/AuthService";
import PriceFormatterService from "../../../services/PriceFormatterService";
import ListCommentComponent from "../../comment/ListCommentComponent";
import CommentService from "../../../services/CommentService";


function DetailProductComponent() {
    let params = useParams();
    const navigate = useNavigate();

    const PRODUCT_INFO_BASE_URL = "http://localhost:8080/user/api/products/" + params.id;
    const RATING_PRODUCT_URL = "http://localhost:8080/customer/api/ratings";


    const [product, setProduct] = useState({});
    const [comments, setComments] = useState({})
    const [commentList, setCommentList] = useState([])
    const [userAccess, setUserAccess] = useState('')
    const [images, setImages] = useState([]);
    //product rating avg info
    const [productRating, setProductRating] = useState();
    //rating of user
    const [userRating, setUserRating] = useState();
    const [buttonRating, setButtonRating] = useState('Submit');
    //comment
    const [cmtContent, setCmtContent] = useState('');
    //flag
    const [updatePage, setUpdatePage] = useState();

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
                    setImages(res.data.productImages);

                    setProductRating(res.data.ratingResponse.productRatingPoints)

                    setUserRating(res.data.ratingResponse.userRatingPoints)

                    showUserRating(res.data.ratingResponse.userRatingPoints);

                }
            })

    }

    //fetch comments
    const fetchCommentsList = () => {
        CommentService.fetchListComments(params.id)
            .then(res => {
                setComments(res.data)
                setCommentList(res.data.commentContent)
                setUserAccess(res.data.usernameAccess);
            })
            .catch(err => {
                console.log(err);
            })
    }

    useEffect(() => {
        fetchProductInfo();
        fetchCommentsList()

    }, [updatePage]
        // [userRating]
    );

    //put product to cart
    const putProductToCart = (id) => {
        CartService.addProductToCart(id);
    }

    //rating/ update rating
    const ratingProduct = () => {
        let rating = document.querySelector('input[name="rating"]:checked');

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
                        setUpdatePage(res.data)
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

    //comment handle
    const delCommentHandler = (id) => {
        console.log(id);
        CommentService.deleteComment(id)
        .then(res => {
            console.log(res.data);
            if(res.status === 200) {
                alert("Delete comment success!")
                setUpdatePage(res.data)
            }
        })
        .catch(err => {
            console.log(err);
            if(err.response.data) {
                alert(err.response.data.message)
            }
        })
    }

    const commentProduct = () => {
        const dataPayload = {
            message: cmtContent
        }
        console.log(dataPayload);
        CommentService.comment(params.id, dataPayload )
        .then(res => {
            console.log(res.data);
            if(res.status === 201) {
                alert("Comment success")
                setUpdatePage(res.data)
                document.getElementById('cmtBox').value = ''
            }

        })
        .catch(err => {
            console.log(err);
        })
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
            <hr />
            <h4 style={{ textAlign: "center", marginTop: "20px" }}>Comments</h4>
            {/* <ListCommentComponent commentContent={comments.commentContent}  userAccess={comments.userAccess}  /> */}
            <div className="container bootdey">
                <div className="col-md-12 bootstrap snippets">
                    <div className="panel">
                        <div className="panel-body">
                            <textarea className="form-control" rows="2" placeholder="What are you thinking?" id="cmtBox"
                                onChange={(e) => setCmtContent(e.target.value)}
                                style={{ fontSize: "large" }}></textarea>
                            <div className="mar-top clearfix">
                                <button className="btn btn-sm btn-primary pull-right" type="button"
                                onClick={() => commentProduct()}
                                >
                                    <i className="fa fa-pencil fa-fw"></i>
                                    Share
                                </button>
                                <a className="btn btn-trans btn-icon fa fa-video-camera add-tooltip" href="#"></a>
                                <a className="btn btn-trans btn-icon fa fa-camera add-tooltip" href="#"></a>
                                <a className="btn btn-trans btn-icon fa fa-file add-tooltip" href="#"></a>
                            </div>
                        </div>
                    </div>
                    <div className="panel">
                        <div className="panel-body">
                            {
                                commentList.map(cmt =>
                                    <div className="media-block">
                                        <a className="media-left" href="#"><img className="img-circle img-sm" alt="Profile Picture"
                                            src="https://image2.tin247.news/pictures/2021/11/30/hvb1638286486.jpg"
                                            style={{ marginRight: "15px" }} /></a>
                                        <div className="media-body">
                                            <div className="mar-btm">
                                                <a href="#" className="btn-link text-semibold media-heading box-inline">{cmt.userName}</a>
                                                <p className="text-muted text-sm"><i className="fa fa-mobile fa-lg"></i> {cmt.cmtTime}</p>
                                            </div>
                                            <p>{cmt.message}</p>
                                            <div className="pad-ver">
                                                <div className="btn-group">
                                                    <a className="btn btn-sm btn-default btn-hover-success" href="#"><i className="fa fa-thumbs-up"></i></a>
                                                    <a className="btn btn-sm btn-default btn-hover-danger" href="#"><i className="fa fa-thumbs-down"></i></a>
                                                </div>
                                                <a className="btn btn-sm btn-default btn-hover-primary" onClick={() => delCommentHandler(cmt.id)}>
                                                    {cmt.userName == userAccess ? 'Delete' : ''}
                                                </a>
                                            </div>
                                            <hr />
                                        </div>
                                    </div>
                                )
                            }


                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}



// export default SingleProduct;
export default DetailProductComponent;


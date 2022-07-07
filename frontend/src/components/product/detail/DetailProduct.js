import React, { Component } from "react";
import ProductService from "../../../services/ProductService";

class DetailProduct extends Component {

    constructor(props) {
        super(props)

        this.state = {
            //id: this.props.match.params.id,
            product: {},
            images: [],
            rating: {}
        }
    }

    componentDidMount() {
        //ProductService.getSingleProduct(this.state.id).then((res) => {     
        ProductService.getSingleProduct().then((res) => {
            this.setState({ product: res.data });
            this.setState({ images: res.data.productImages });
            this.setState({rating: res.data.ratingResponse});
        })
    }

    render() {
        return (
            <section className="product-details spad">
                <div className="container">
                    <div className="row">
                        <div className="col-lg-6">
                            <div className="product__details__pic">
                                <div className="product__details__pic__left product__thumb nice-scroll"
                                    tabIndex="1" style={{ overflowY: 'hidden', outline: 'none' }}>
                                    {
                                        this.state.images.map(image =>
                                            <>
                                                <a className="pt">
                                                    <img src={image.image} alt="" />
                                                </a>
                                            </>
                                        )
                                    }
                                </div>
                                <div className="product__details__slider__content">
                                    <div className="product__details__pic__slider owl-carousel owl-loaded">
                                        <div className="owl-stage-outer">
                                            <div className="owl-stage"
                                                style={{ transform: "translate3d(0px, 0px, 0px)", transition: "all 0s ease 0s", width: "1652px" }}>
                                                {
                                                    this.state.images.map(image =>                                                       
                                                            <div className="owl-item active" style={{ width: "412.9px" }}>
                                                                <img  className="product__big__img" src={image.image} alt="" />

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
                        </div>
                        <div className="col-lg-6">
                            <div className="product__details__text">
                                <h3>Name: {this.state.product.name} <span>Brand: {this.state.product.brandName}</span></h3>
                                <div className="rating">
                                    <i className="fa fa-star"></i>
                                    <i className="fa fa-star"></i>
                                    <i className="fa fa-star"></i>
                                    <i className="fa fa-star"></i>
                                    <i className="fa fa-star"></i>
                                    <p>(Đánh giá :  {this.state.rating.productRatingPoints}/5 )</p>
                                </div>
                                <div className="product__details__price">Giá: {this.state.product.price * (1-this.state.product.discount)} <span> {this.state.product.price} </span></div>
                                <p>{this.state.product.description}</p>
                                <div className="product__details__button">
                                    <div className="quantity">
                                        <span>Quantity:</span>
                                        <div className="pro-qty"><span className="dec qtybtn">-</span>
                                            <input type="text" defaultValue="1" />
                                            <span className="inc qtybtn">+</span></div>
                                    </div>
                                    <a href="#" className="cart-btn"><span className="icon_bag_alt"></span> Add to cart</a>
                                    <ul>
                                        <li><a href="#"><span className="icon_heart_alt"></span></a></li>
                                        <li><a href="#"><span className="icon_adjust-horiz"></span></a></li>
                                    </ul>
                                </div>
                                <div className="product__details__widget">
                                    <ul>
                                        <li>
                                            <span>Trạng thái:</span>
                                            <p>{this.state.product.status}</p>
                                        </li>
                                        <li>
                                            <span>Giới tính:</span>
                                            <p>Phái {this.state.product.gender ? 'nam' : 'nữ'} </p>
                                        </li>
                                        <li>
                                            <span>Bảo hành:</span>
                                            <p>{this.state.product.guaranteeTime } tháng  </p>
                                        </li>
                                        <li>
                                            <span>Chống nước:</span>
                                            <p>{ this.state.product.isWaterProof ? 'Có kháng nước' : 'Không kháng nước'}</p>
                                        </li>
                                        <li>
                                            <span>Size:</span>
                                            <p>{this.state.product.size} mm</p>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </section>
        );
    }
}

export default DetailProduct;


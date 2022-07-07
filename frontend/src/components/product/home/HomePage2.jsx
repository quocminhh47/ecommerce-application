import React, { Component } from 'react';
import ProductService from '../../../services/ProductService';

class HomePage2 extends Component {

    constructor(props) {
        super(props)
        this.state = {
            products: []
        }
    }

    componentDidMount() {
        ProductService.getProducts().then((res) => {
            this.setState({ products: res.data.productContent });
        })
    }


    render() {
        return (
            <div className="container-fluid pt-5">
                <div className="text-center mb-4">
                    <h2 className="section-title px-5"><span className="px-2">DANH SÁCH SẢN PHẨM</span></h2>
                </div>
                <div className="row px-xl-5 pb-3">    
                {this.state.products.map((product, index) =>

                    <div className="col-lg-3 col-md-6 col-sm-12 pb-1" key={product.id}>
                        <div className="card product-item border-0 mb-4">
                            <div className="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                                <img className="img-fluid w-100" src={product.thumbnail} alt=""/>
                            </div>
                            <div className="card-body border-left border-right text-center p-0 pt-4 pb-3">
                                <h6 className="text-truncate mb-3">{product.name}</h6>
                                <div className="d-flex justify-content-center">
                                    <h6>$123.00</h6><h6 className="text-muted ml-2"><del>$123.00</del></h6>
                                </div>
                            </div>
                            <div className="card-footer d-flex justify-content-between bg-light border">
                                <a href="" className="btn btn-sm text-dark p-0"><i className="fas fa-eye text-primary mr-1"></i>View Detail</a>
                                <a  className="btn btn-sm text-dark p-0" onClick={() => console.log('Add to cart')}><i className="fas fa-shopping-cart text-primary mr-1"></i>Add To Cart</a>
                            </div>
                        </div>
                    </div>
                )
                }

                </div>
            </div>

        );
    }
}

export default HomePage2;
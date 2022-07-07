import React, { Component } from 'react';
import ProductService from '../../services/ProductService';

class ListProduct extends Component {

    constructor(props) {
        super(props)
        this.state = {
            products: []
        }
        this.getSingleProduct = this.getSingleProduct.bind(this);
    }

    getSingleProduct(id) {
        this.props.history.push(`/detail/${id}`);
    }

    componentDidMount() {
        ProductService.getAllProducts().then((res) => {
            this.setState({ products: res.data.productContent });
        });
    }

    render() {
        return (
            <section className="ftco-section">
                <div className="container">
                    <div className="row justify-content-center">
                        <div className="col-md-6 text-center mb-4">
                            <h2 className="heading-section">ADMIN: PRODUCTS MANAGEMENT</h2>
                        </div>
                    </div>
                    <div className="row">
                        <div className="col-md-12">
                            <h3 className="h5 mb-4 text-center">List Products :</h3>
                            <div className="table-wrap">
                                <table className="table">
                                    <thead className="thead-primary">
                                        <tr>                                            
                                            <th>ID</th>
                                            <th>Product</th>                                            
                                            <th>Name</th>                                            
                                            <th>Brand</th>
                                            <th>Price</th>
                                            <th>Quantity</th>
                                            <th>Show</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            this.state.products.map( product => 
                                                <tr className="alert" role="alert" key={product.id}>
                                            
                                            <td>{product.id}</td>
                                            <td>
                                                <img style={{width: "80px", height:"100px"}} src={product.thumbnail}/>
                                            </td>
                                            <td>
                                                <div className="email">
                                                    <p>{product.name}</p>
                                                </div>
                                            </td>
                                            <td>{product.brandName}</td>
                                            <td>{product.price}</td>
                                            <td className="quantity">
                                                <div className="input-group">
                                                    <input type="text" name="quantity" className="quantity form-control input-number" value={product.quantity} readOnly/>
                                                </div>
                                            </td>                                            
                                            <td>
                                            <button onClick = {() => this.getSingleProduct(product.id)}
                                                className="btn btn-info"
                                                > Show </button>
                                            </td>
                                        </tr>)
                                        }

                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        );
    }
}

//export default withRouter(ListProduct) ;
export default ListProduct;
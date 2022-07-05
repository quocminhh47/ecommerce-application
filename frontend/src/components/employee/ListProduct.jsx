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
        ProductService.getProducts().then( (res) => {
            this.setState({products: res.data.productContent});
        });
    }

    render() {
        return (
            <div>
                <h2 className='text-center'> Product List</h2>
                <div className='row'>
                    <table className='table table-striped table-borded'>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Brand</th>
                                <th>Price</th>
                                <th>Action</th>
                            </tr>

                        </thead>

                        <tbody>
                            {
                                this.state.products.map(
                                    product =>
                                        <tr key={product.id}>
                                            <td>{product.id}</td>
                                            <td>{product.name}</td>
                                            <td>{product.brandName}</td>
                                            <td>{product.price}</td>
                                            <td>
                                                <button onClick = {() => this.getSingleProduct(product.id)}
                                                className="btn btn-info"
                                                > Show </button>
                                                <button style={{marginLeft: "10px"}} className="btn btn-danger"
                                                >Delete </button>                                               
                                            </td>
                                        </tr>
                                )
                            }
                        </tbody>

                    </table>

                </div>
            </div>
        );
    }
}

//export default withRouter(ListProduct) ;
export default ListProduct;
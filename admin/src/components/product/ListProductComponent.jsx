import React, { Component, useEffect } from 'react';
import { Link } from 'react-router-dom'
import ReactPaginate from 'react-paginate'
import HeaderComponent from '../header/HeaderComponent';
import './Pagination.css'
import ProductService from '../../services/ProductService';
import { useNavigate, useParams } from 'react-router-dom'
import { useState } from 'react';
import LoginService from '../../services/LoginService';



function ListProductComponent() {

    const navigate = useNavigate();
    const [loginStatus, setLoginStatus] = useState();

    if (!LoginService.checkAuthorization()) {
       window.location.href = '/login'
    } 
    const [products, setProducts] = useState([]);


    //pagination
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState();
    const [totalPage, setTotalPage] = useState();
    


    //change page
    const changePage = ({ selected }) => {
        setPageNo(selected)
    }

    useEffect(() => {
        const token = localStorage.getItem("accessToken")
        const params = {
            params: {
                pageNo: pageNo
            },
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        ProductService.getAllProducts(params)
            .then(res => {
                console.log(res.data)
                setProducts(res.data.productContent)
                setPageNo(res.data.pageNo)
                setPageSize(res.data.pageSize);
                setTotalPage(res.data.totalPages);
                //setLoginStatus(LoginService.checkLoginStatus(res.data));
            }
            )
            .catch(error => {
                console.log(error)
                if(error.response.status === 403) {
                    navigate('/login')
                }
                else {
                    alert("Something went wrong, try again!")
                    navigate('/login')
                }
            });
    }, [pageNo])

    return (
        <>
            <HeaderComponent status={true} />
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
                            <Link to="/products/add" className="btn btn-primary mb-2" style={{ textAlign: "center" }}> Add Product </Link>
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
                                            <th>Created </th>
                                            <th>Updated</th>
                                            <th>Action</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            products.map(product =>
                                                <tr className="alert" role="alert" key={product.id}>

                                                    <td>{product.id}</td>
                                                    <td>
                                                        <img style={{ width: "80px", height: "100px" }} src={product.thumbnail} />
                                                    </td>
                                                    <td>
                                                        <div className="email">
                                                            <p>{product.name}</p>
                                                        </div>
                                                    </td>
                                                    <td>{product.brandName}</td>
                                                    <td>{product.price}</td>
                                                    <td>{product.quantity}</td>
                                                    <td>{product.createdAt}</td>
                                                    <td>{product.updatedAt}</td>
                                                    <td>
                                                        <button onClick={() => {
                                                            navigate("/products/" + product.id)
                                                        }}
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
            <ReactPaginate
                previousLabel={"Previous"}
                nextLabel={"Next"}
                pageCount={totalPage}
                onPageChange={changePage}
                containerClassName={"paginationBtns"}
                previousLinkClassName={"previousBtn"}
                nextClassName={"nextBtn"}
                disabledClassName={"paginationDisabled"}
                activeClassName={"paginationActive"}

            />
        </>

    )
}

export default ListProductComponent;
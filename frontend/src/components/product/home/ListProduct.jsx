import ReactPaginate from 'react-paginate'
import './Pagination.css'
import Header from '../../header/Header';
import Banner from "../../banner/Banner";
import Services from "../../services/Service";
import { useState, useEffect } from 'react';
import axios from 'axios';



function ListProduct() {

    const PRODUCTS_API_BASE_URL = "http://localhost:8080/user/api/products";

    const [products, setProducts] = useState([]);

    //pagination
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState();
    const [totalPage, setTotalPage] = useState();
    const [loginStatus, setLoginStatus] = useState({});


    const fetchProducts = () => {
        const token = localStorage.getItem("accessToken");
        axios.get(PRODUCTS_API_BASE_URL, {
            params: {
                pageNo: pageNo
            },
            headers:{
                'Authorization': `Bearer ${token}`
            }
        })
            .then(res => {
                setProducts(res.data.productContent);
                setPageNo(res.data.pageNo);
                setPageSize(res.data.pageSize);
                setTotalPage(res.data.totalPages);
                setLoginStatus(res.data.loginStatusResponse);
            })
    }

    useEffect(() => {
        fetchProducts();
    }, [pageNo]);

    const formatter = (price) => {
        const formatter = new Intl.NumberFormat('de-DE', {
            style: 'currency',
            currency: 'VND',
        });
        return formatter.format(price);
    }

    const changePage = ({ selected }) => {
        setPageNo(selected)
    }

    console.log(loginStatus);

    return (
        <>
            <Header status = {loginStatus.roleName}/>
            <Banner />
            <Services />
            <div className="container-fluid pt-5">
                <div className="text-center mb-4">
                    <h2 className="section-title px-5"><span className="px-2">DANH SÁCH SẢN PHẨM</span></h2>
                </div>
                <div className="row px-xl-5 pb-3" style={{ marginLeft: "20px" }}>
                    {products.map((product) =>

                        <div className="col-lg-3 col-md-6 col-sm-12 pb-1" key={product.id}>
                            <div className="card product-item border-0 mb-4" style={{ width: "105%", height: "85%" }}>
                                <div className="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                                    <img className="img-fluid w-100" src={product.thumbnail} alt="" />
                                </div>
                                <div className="card-body border-left border-right text-center p-0 pt-4 pb-3">
                                    <h6 className="text-truncate mb-3">{product.name}</h6>
                                    <div className="d-flex justify-content-center">
                                        <h6>{formatter(product.price)}</h6><h6 className="text-muted ml-2"><del>{formatter(product.price)}</del></h6>
                                    </div>
                                </div>
                                <div className="card-footer d-flex justify-content-between bg-light border">
                                    <a href="" className="btn btn-sm text-dark p-0"><i className="fas fa-eye text-primary mr-1"></i>View Detail</a>
                                    <a className="btn btn-sm text-dark p-0" onClick={() => console.log('Add to cart')}><i className="fas fa-shopping-cart text-primary mr-1"></i>Add To Cart</a>
                                </div>
                            </div>
                        </div>
                    )
                    }

                </div>
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
            </div>
        </>

    )
}

export default ListProduct;
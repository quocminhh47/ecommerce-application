import { useEffect, useState } from "react";
import BrandService from "../../services/BrandService";
import ReactPaginate from 'react-paginate'
import { useNavigate, useParams, Link } from 'react-router-dom'
import HeaderComponent from "../header/HeaderComponent";
import LoginService from "../../services/LoginService";
import './Pagination.css'
import './style.css'

function ListBrandsComponent() {

    const [brands, setBrands] = useState([]);
    const navigate = useNavigate();

    if (!LoginService.checkAuthorization()) {
        navigate('/login')
    }


    //pagination
    const [pageNo, setPageNo] = useState(0);
    const [pageSize, setPageSize] = useState();
    const [totalPage, setTotalPage] = useState();
    const [loginStatus, setLoginStatus] = useState();

    //change page
    const changePage = ({ selected }) => {
        setPageNo(selected)
    }

    //fetch
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
        BrandService.getAllBrands(params)
            .then(res => {
                console.log(res)
                if (res.status === 200) {
                    setBrands(res.data.brandContent)
                    setPageNo(res.data.pageNo)
                    setPageSize(res.data.pageSize);
                    setTotalPage(res.data.totalPages);
                    setLoginStatus(true);
                }
            })
            .catch(err => {
                console.log(err)
                if (err.response.status == '403') {
                    navigate('/login')
                }
                setLoginStatus(false)
            
            })
    }, [pageNo])

    return (
        <>
            <HeaderComponent status={loginStatus} />
            <section className="ftco-section">
                <div className="container">

                    <div className="row justify-content-center">
                        <div className="col-md-6 text-center mb-4">
                            <h2 className="heading-section">ADMIN: BRANDS MANAGEMENT</h2>
                        </div>
                    </div>

                    <div className="row">
                        <div className="col-md-12">
                            <h3 className="h5 mb-4 text-center">List Products :</h3>
                            <Link to="/brands/add" className="btn btn-primary mb-2" > Add new brand </Link>
                            <div className="table-wrap">
                                <table className="table">
                                    <thead className="thead-primary">
                                        <tr>
                                            <th>ID</th>
                                            <th>Logo</th>
                                            <th>Name</th>
                                            <th>Description</th>
                                            <th>Action</th>

                                        </tr>
                                    </thead>
                                    <tbody>
                                        {
                                            brands.map(brand =>

                                                <tr className="alert" role="alert" key={brand.id} style={{ maxHeight: "70%" }}>

                                                    <td>{brand.id}</td>

                                                    <td>
                                                        <img style={{ width: "80px", height: "100px" }} src={brand.thumbnail} />
                                                    </td>

                                                    <td>
                                                        {brand.name}
                                                    </td>

                                                    <td>{brand.description}</td>

                                                    <td>
                                                        <button onClick={() => {
                                                            navigate("/brands/" + brand.id)
                                                        }}
                                                            className="btn btn-info"
                                                        > Show </button>
                                                    </td>

                                                </tr>
                                            )
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

export default ListBrandsComponent;
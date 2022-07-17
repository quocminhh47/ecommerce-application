import React from 'react'
import './css/Header.css'
import { Link, useNavigate } from 'react-router-dom'

export default function HeaderComponent(props) {

    const navigate = useNavigate();
    const statusBIll = ['unsolved', 'canceled', 'accepted', 'purchased']

    const logoutHandler = () => {
        const token = window.localStorage.getItem("accessToken")
        if (token) {
            window.localStorage.removeItem("accessToken");
            alert("Logout success")
        }
        else {
            alert('You has been logout before')
        }
        navigate('/login')

    }

    return (
        <header className="site-navbar js-sticky-header site-navbar-target" role="banner">

            <div className="container">
                <div className="row align-items-center position-relative">


                    <div className="site-logo">
                        <Link to="/" className="text-black"><span className="text-primary">Admin Page </span></Link>
                    </div>

                    <div className="col-12">
                        <nav className="site-navigation text-right ml-auto " role="navigation">

                            <ul className="site-menu main-menu js-clone-nav ml-auto d-none d-lg-block">

                                <li><Link to="/" className="nav-link">Products</Link></li>

                                {/* <li><Link to="/bills" className="nav-link">Bills</Link></li> */}
                                <li className="has-children">
                                    <a href="/bills" className="nav-link">Bills</a>
                                    <ul className="dropdown arrow-top">                                        
                                        {                                            
                                            statusBIll.map(item => 
                                                <li><a href={`/bills/status/${item}`} className="nav-link">{item}</a></li>
                                            )
                                        }
                                    </ul>
                                </li>

                                <li >
                                    <Link to="/brands" className="nav-link">Category</Link>
                                </li>

                                <li><Link to="/customers" className="nav-link">Customers</Link></li>

                                <li><Link to="/signup" className="nav-link">Create Account</Link></li>

                                <li><Link to="/bills/report" className="nav-link">Sale Report</Link></li>

                                <li><Link to="/login" className="nav-link" onClick={() => logoutHandler()}>{props.status ? 'Logout' : ''}</Link></li>

                            </ul>
                        </nav>

                    </div>

                    <div className="toggle-button d-inline-block d-lg-none"><Link to="#" className="site-menu-toggle py-5 js-menu-toggle text-black"><span className="icon-menu h3"></span></Link></div>

                </div>
            </div>

        </header>

    )
}

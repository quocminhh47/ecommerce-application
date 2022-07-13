import React, { useEffect, useState } from "react";
import { Link } from 'react-router-dom';
import axios from "axios";
import './css/HeaderStyle.css'
import './css/Responsive.css'

function Header(props) {
    const BRAND_API_URL = "http://localhost:8080/user/api/brands";

    const [brands, setBrands] = useState([]);

   useEffect(() => {
    axios.get(BRAND_API_URL)
    .then(res => setBrands(res.data.brandContent))    
   }, [])

    return (
        <header className="header">
            <div className="container-fluid">
                <div className="row">
                    <div className="col-xl-3 col-lg-2">
                        <div className="header__logo">
                            <Link to="/"><img src="img/logo.png" alt="" /></Link>
                        </div>
                    </div>
                    <div className="col-xl-6 col-lg-7">
                        <nav className="header__menu">
                            <ul>
                                <li className="active"><a href="/">Home</a></li>
                                <li><a href={`/product/gender/false`}>Women’s</a></li>
                                <li><a href={`/product/gender/true`}>Men’s</a></li>
                                <li><a >Brands</a>
                                    <ul className="dropdown">
                                        {
                                            brands.map(brand =>
                                                <li key={brand.id}><a href={`/product/brand/${brand.name}`}>{brand.name}</a></li>
                                            )
                                        }

                                    </ul>
                                </li>
                                <li><Link to="/blog.html">Blog</Link></li>
                                <li><Link to="/logout">{props.status ? 'Logout' : ''}</Link></li>
                            </ul>
                        </nav>
                    </div>
                    <div className="col-lg-3">
                        <div className="header__right">
                            <div className="header__right__auth">
                                <Link to="/login">{!props.status ? 'Login' : ' '} </Link>
                                <Link to="/sigin">Register</Link>
                            </div>
                            <ul className="header__right__widget">
                                <li><span className="icon_search search-switch"></span></li>
                                <li><Link to="/bill"><span className="icon_heart_alt"></span>
                                    <div className="tip"></div>
                                </Link></li>
                                <li><Link to="/cart"><span className="icon_bag_alt"></span>
                                    <div className="tip"></div>
                                </Link></li>
                            </ul>
                        </div>
                    </div>
                </div>
                <div className="canvas__open">
                    <i className="fa fa-bars"></i>
                </div>
            </div>
        </header>
    )
}

export default Header;
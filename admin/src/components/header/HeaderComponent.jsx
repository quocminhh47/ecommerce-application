import React from "react";
import {Link} from 'react-router-dom';

function HeaderComponent() {
    return (
        <header className="header">
            <div className="container-fluid">
                <div className="row">
                    <div className="col-xl-3 col-lg-2">
                        <div className="header__logo">
                            <Link to="/"><img src="img/logo.png" alt=""/></Link>
                        </div>
                    </div>
                    <div className="col-xl-6 col-lg-7">
                        <nav className="header__menu">
                            <ul>
                                <li className="active"><Link to="/">Home</Link></li>
                                <li><Link to="#">Women’s</Link></li>
                                <li><Link to="#">Men’s</Link></li>
                                <li><Link to="/shop.html">Shop</Link></li>
                                <li><Link to="#">Pages</Link>
                                    <ul className="dropdown">
                                        <li><Link to="/product-details.html">Product Details</Link></li>
                                        <li><Link to="/shop-cart.html">Shop Cart</Link></li>
                                        <li><Link to="/checkout.html">Checkout</Link></li>
                                        <li><Link to="/blog-details.html">Blog Details</Link></li>
                                    </ul>
                                </li>
                                <li><Link to="/blog.html">Blog</Link></li>
                                <li><Link to="/contact.html">Contact</Link></li>
                            </ul>
                        </nav>
                    </div>
                    <div className="col-lg-3">
                        <div className="header__right">
                            <div className="header__right__auth">
                                <Link to="/login">Login </Link>
                                <Link to="#">Register</Link>
                            </div>
                            <ul className="header__right__widget">
                                <li><span className="icon_search search-switch"></span></li>
                                <li><Link to="#"><span className="icon_heart_alt"></span>
                                    <div className="tip">2</div>
                                </Link></li>
                                <li><Link to="#"><span className="icon_bag_alt"></span>
                                    <div className="tip">2</div>
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

export default HeaderComponent;
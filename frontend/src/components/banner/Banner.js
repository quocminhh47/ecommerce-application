import React, { Component } from 'react';

function Banner() {
    return (
        <section className="banner set-bg" data-setbg="./img/banner-1.jpg">
        <div className="container">
             <div className="row">
                 <div className="col-xl-7 col-lg-8 m-auto">
                     <div className="banner__slider owl-carousel">
                         <div className="banner__item">
                             <div className="banner__text">
                                 <span>LAPTOP CHẤT LƯỢNG CAO</span>
                                 <h1>The MT COMPUTER</h1>
                                 <a href="home/index.htm">Shop now</a>
                             </div>
                         </div>
                         <div className="banner__item">
                             <div className="banner__text">
                                 <span>LO LẮNG VẬN CHUYỂN?</span>
                                 <h1>FREE SHIP</h1>
                                 <a href="home/index.htm">Shop now</a>
                             </div>
                         </div>
                         <div className="banner__item">
                             <div className="banner__text">
                                 <span>NHÂN VIÊN TẬN TÌNH</span>
                                 <h1>HỖ TRỢ 24/24</h1>
                                 <a href="home/index.htm">Shop now</a>
                             </div>
                         </div>
                     </div>
                 </div>
             </div>
         </div>
     </section> 
    )
}

export default Banner;
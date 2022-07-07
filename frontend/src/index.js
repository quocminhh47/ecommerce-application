import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom/client';
import Breadcrumb from './components/product/breadcrumb/BreadCrumb';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App';
import DetailProduct from './components/product/detail/DetailProduct';
import HomePage2 from './components/product/home/HomePage2';
import Services from './components/services/Service';
import Footer from './components/footer/Footer';
import Discount from './components/services/Discount';
import Header from './components/header/Header';
import Banner from './components/banner/Banner';
import App4 from './components/hook/TwoWayBinding';


const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    {/* <Header />
    <Banner />
    <Services />
    <HomePage2 />
    <Discount />
    <Footer /> */}
    <App4 />
  </React.StrictMode>
);


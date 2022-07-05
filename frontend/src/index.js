import React, { useEffect, useState } from 'react';
import ReactDOM from 'react-dom/client';

import Banner from './components/banner/Banner';

import Breadcrumb from './components/product/breadcrumb/BreadCrumb';
import App3 from './components/product/detail/DetailProduct';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import App from './App';
import DetailProduct from './components/product/detail/DetailProduct';



const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
  <React.StrictMode>
    {/* <App/> */}
    <DetailProduct />
  </React.StrictMode>
);


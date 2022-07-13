import React from "react";
import './App.css';
import SingleProduct from './pages/product/detail/DetailProduct'
import NotFound from './components/404/NotFound';
import LoginPage from "./pages/login";
import RegistrationPage from "./pages/registration/RegistrationPage";
import HomePage from "./pages/home";
import CartItemsComponent from "./components/cart/CartItemsComponent";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import DetailProduct from "./pages/product/detail/DetailProduct";
import BillComponent from "./components/bill/BillComponent";
import ListBillsComponent from "./components/bill/ListBillsComponent";

function App() {
  return (
    <>
      <Router>
        <div>
          <div>
            <Routes>
              <Route path="/" element={<HomePage />} />                
              <Route path="/product/brand/:brandName" element={<HomePage />} />              
              <Route path="/product/gender/:gender" element={<HomePage />} />              
              <Route path="/login" element={<LoginPage />} />
              <Route path="/logout" element={<LoginPage />} />
              <Route path="/sigin" element={<RegistrationPage />} />
              <Route path="/cart" element={<CartItemsComponent />} />
              <Route path="/detail/:id" element={<DetailProduct/>} />
              <Route path="/bill" element={<ListBillsComponent />} />
              <Route path="/bill/:billId" element={<BillComponent />} />
              {/* <Route path="/bill/" element={<BillComponent />} /> */}
              <Route path="*" element={<NotFound />} />
            </Routes>
            {/* <Routes>
            <Route path="/detail/:id" element={<DetailProduct/>} />
            </Routes> */}
          </div>
        </div>
      </Router>
    </>
  );

}

export default App;

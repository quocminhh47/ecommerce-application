import React from "react";
import './App.css';
import DetailProduct from './pages/product/detail/DetailProduct'
import NotFound from './components/404/NotFound';
import LoginPage from "./pages/login";
import RegistrationPage from "./pages/registration/RegistrationPage";
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';



function App() {
  return (
    <>
      <Router>
        <div>
          <div>
            <Routes>              
              <Route path="/" element={<DetailProduct />} />              
              <Route path="/login" element={<LoginPage />} />              
              <Route path="/logout" element={<LoginPage />} />              
              <Route path="/sigin" element={<RegistrationPage />} />              
              <Route path="/detail/:id" element={<DetailProduct/>} />
              <Route path="/404" element={<NotFound />} />
            </Routes>
          </div>
        </div>
      </Router>
    </>
  );

}

export default App;

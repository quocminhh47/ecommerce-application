import React from "react";
import ListProduct from "./components/employee/ListProduct";
import Footer from './components/footer/Footer';
import Header from './components/header/Header';
import DetailProduct from './components/product/detail/DetailProduct';
import NotFound from './components/404/NotFound';
import { BrowserRouter as Router, Routes , Route} from 'react-router-dom';


function App() {
  return (
    <div>
      <Router>
        <div className="container">
          <Header />
          <div className="container">
            <Routes>
              <Route path="/" element={<ListProduct/>}/>
              <Route path="/detail/:id" element={<DetailProduct/>}/>
              <Route path="/404" element={<NotFound/>}/>              
            </Routes>
          </div>
          <Footer />
        </div>
      </Router>
    </div>
  );

}

export default App;

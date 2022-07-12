import {
  BrowserRouter as Router,
  Routes,
  Route,
  Link
} from 'react-router-dom';
import './App.css';
import HomePage from './pages/home/HomePage'
import NotFoundPage from './pages/NotFound/NotFound';
import LoginComponent from './components/login/LoginComponent';
import AddProductComponent from './components/product/AddProductComponent';
import ListBrandsComponent from './components/brand/ListBrandsComponent';
import SingleBrandComponent from './components/brand/SingleBrandComponent';
import ListCustomersComponent from './components/customer/ListCustomersComponent';
import SingleCustomerComponent from './components/customer/SingleCustomerComponent';
import HeaderComponent from './components/header/HeaderComponent';
import FooterComponent from './components/footer/FooterComponent';
import SignupComponent from './components/signup/SignupComponent';
import ProtectedRoutes from './components/ProtectedRoute';

function App() {
  return (
    <Router>
      <nav>
        {/* <HeaderComponent /> */}
      </nav>
      <Routes>
        {/* <Route element={<ProtectedRoutes/>} /> */}
        <Route path='/login' element={<LoginComponent />} />
        <Route path='/' element={<HomePage />} />
        <Route path='/brands' element={<ListBrandsComponent />} />
        <Route path='/brands/add' element={<SingleBrandComponent />} />
        <Route path='/brands/:brandId' element={<SingleBrandComponent />} />        
        <Route path='/signup' element={<SignupComponent />} />
        <Route path='/products/:productId' element={<AddProductComponent />} />
        <Route path='/products/add' element={<AddProductComponent />} />
        <Route path='/customers' element={<ListCustomersComponent />} />
        <Route path='/customers/:customerId' element={<SingleCustomerComponent />} />
        <Route path='*' element={<NotFoundPage />} />
      </Routes>
      <FooterComponent />
    </Router>
  )

    ;
}

export default App;

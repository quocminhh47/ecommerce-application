import axios from "axios";

const PRODUCTS_API_BASE_URL = "http://localhost:8080/user/api/products";
const PRODUCT_INFO_BASE_URL = "http://localhost:8080/user/api/products/1";

class ProductService {

    getProducts() {
        return axios.get(PRODUCTS_API_BASE_URL);
    }

    getSingleProduct() {
        //return axios.get(PRODUCTS_API_BASE_URL+ '/' + id);
        return axios.get(PRODUCT_INFO_BASE_URL);
    }

}

export default new  ProductService();
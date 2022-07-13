import axios from "axios";

const PRODUCTS_API_BASE_URL = "http://localhost:8080/user/api/products";
const PRODUCT_INFO_BASE_URL = "http://localhost:8080/user/api/products/1";

class ProductService {

    getProducts(url, params) {
        return axios.get(url, params);
    }

    getSingleProduct(url, params) {
        //return axios.get(PRODUCTS_API_BASE_URL+ '/' + id);
        return axios.get(url, params);
    }

}

export default new  ProductService();
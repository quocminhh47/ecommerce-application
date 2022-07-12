import AuthService from "./AuthService";
import axios from "axios";
const PRODUCTS_API_BASE_URL = "http://localhost:8080/user/api/products/";
const ALL_PRODUCTS_API_BASE_URL = "http://localhost:8080/admin/api/products/";
const CREATE_PRODUCT_API_BASE_URL = "http://localhost:8080/admin/api/products/";


class ProductService {
    getAllProducts( params) {
        return axios.get(ALL_PRODUCTS_API_BASE_URL, params);
    }

    getProductById(id) {
        const url = PRODUCTS_API_BASE_URL + id;
        console.log(url);
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        return axios.get(url, {
            params: {
                Headers: {
                    'Authorization': `Bearer ${token}`
                }
            }
        });
    }

    createNewProduct(productPayload) {
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        return axios.post(CREATE_PRODUCT_API_BASE_URL, productPayload, params)
    }

    updateProduct(productPayload , UPDATE_PRODUCT_API_URL){
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        return axios.put(UPDATE_PRODUCT_API_URL, productPayload, params)
    }
}

export default new ProductService();
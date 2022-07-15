import React from "react";
import axios from "axios";
import AuthService from "./AuthService";

class BrandService {

    fetchBrandName() {

        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const BRAND_API_URL = "http://localhost:8080/user/api/brands";
        return axios.get(BRAND_API_URL, {
            params: {
                Headers: {
                    'Authorization': `Bearer ${token}`
                }
            }
        });

    }

    getAllBrands(params) {
        const BRAND_API_URL = "http://localhost:8080/admin/api/brands";
        return axios.get(BRAND_API_URL, params);
    }
    

    fetchBrandById(id) {
        const URL_BRAND_BY_ID = "http://localhost:8080/admin/api/brands/" + id;
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(URL_BRAND_BY_ID, params);
    }

    createNewBrand(brandPayload) {
        const CREATE_BRAND_API_URL =  "http://localhost:8080/admin/api/brands/";
        
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.post(CREATE_BRAND_API_URL,brandPayload, params);
    }

     upDateBrand(brandPayload, id) {
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        const UPDATE_BRAND_API_URL =  "http://localhost:8080/admin/api/brands/" + id;

        return axios.put(UPDATE_BRAND_API_URL, brandPayload, params);
    }
}

export default new BrandService();
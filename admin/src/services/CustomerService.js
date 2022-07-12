import React, { Component } from 'react'
import axios from "axios";
import AuthService from "./AuthService";

class CustomerService {

    fetchAllCustomers() {
        const CUSTOMERS_API_BASE_URL = "http://localhost:8080/admin/api/users/"

        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(CUSTOMERS_API_BASE_URL, params);
    }

    getCustomerInfoById(id) {
        const CUSTOMERS_API_BASE_URL = "http://localhost:8080/admin/api/users/" + id;

        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(CUSTOMERS_API_BASE_URL, params);

    }

    updateCustomer(userPayload, id) {

        const CUSTOMERS_API_BASE_URL = "http://localhost:8080/admin/api/users/" + id
        console.log(CUSTOMERS_API_BASE_URL)

        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.put(CUSTOMERS_API_BASE_URL, userPayload,params);
    }

}

export default new CustomerService();




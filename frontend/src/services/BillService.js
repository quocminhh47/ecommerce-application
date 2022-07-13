import axios from "axios";
import React from "react";

class BillService {
    getAllBills() {
        const LIST_BILL_API_URL = "http://localhost:8080/customer/api/order/bill/all";

        const token = window.localStorage.getItem("accessToken")
        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(LIST_BILL_API_URL, params);

    }
    purchaseProducts(dataPayload) {

        const URL = "http://localhost:8080/customer/api/order"

        const token = window.localStorage.getItem("accessToken")
        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.post(URL, dataPayload, params)

    }

    getBillDetail(id) {
        const BILL_URL = `http://localhost:8080/customer/api/order/bill/${id}`
        //    const BILL_URL =  `http://localhost:8080/customer/api/order/bill/85`
        console.log(BILL_URL);

        const token = window.localStorage.getItem("accessToken")
        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        return axios.get(BILL_URL, params)
    }
}

export default new BillService();
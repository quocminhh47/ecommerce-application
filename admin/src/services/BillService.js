import axios from 'axios'
import AuthService from './AuthService';

class BillService {
    getAllBillsAdmin() {
        const ALL_BILLS_API_URL = "http://localhost:8080/admin/api/bills/all";
        console.log(ALL_BILLS_API_URL);
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(ALL_BILLS_API_URL, params)
    }

    getAllBillsByStatusAdmin(billStatus) {
        const ALL_BILLS_API_URL = `http://localhost:8080/admin/api/bills/${billStatus}`;
        console.log(ALL_BILLS_API_URL);

        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(ALL_BILLS_API_URL, params)
    }
    

    getBillDetail(id) {
        const DETAIL_BILLS_API_URL = "http://localhost:8080/admin/api/bills/detail/" + id;

        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(DETAIL_BILLS_API_URL, params)
    }

    getBillStatus(value) {
         switch (value) {
            case 0:
                value = "Unsolved"
                break;

            case -1:
                value = "Canceled"
                break;
            case 1:
                value = "Accepted"
                break;
            case 2:
                value = "Purchased"
                break;

            default: value = "N/A"
                break;
        }
        return value;
    }

    changeBillStatus(billId, status) {
        const url = `http://localhost:8080/admin/api/bills/change/status?bill=${billId}&status=${status}`
        console.log((url));
       
        
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
       return axios.put(url,{},params)
    }

    getSaleFromDateRange(dateStart, dateEnd) {
        const url = `http://localhost:8080/admin/api/bills/report?dateStart=${dateStart}&dateEnd=${dateEnd}`
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(url, params)
    }



}

export default new BillService()
import axios from 'axios'
import AuthService from './AuthService';

class BillService {
    getAllUnsolvedBills() {
        const UNSOLVED_BILLS_API_URL = "http://localhost:8080/admin/api/bills/all"
        
        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        return axios.get(UNSOLVED_BILLS_API_URL, params)
    }
}

export default new BillService()
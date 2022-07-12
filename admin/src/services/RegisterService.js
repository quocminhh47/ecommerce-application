import axios from "axios";

class RegisterService {

    register(userPayload) {
        const token = window.localStorage.getItem("accessToken")
        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        const REGISTRATION_API_URL = "http://localhost:8080/admin/api/users/registration";
        return axios.post(REGISTRATION_API_URL, userPayload, params);
    }
}

export default new RegisterService();
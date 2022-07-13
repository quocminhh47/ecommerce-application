import axios from "axios";

class RegisterService {

    register(userPayload) {
        const REGISTRATION_API_URL = "http://localhost:8080/v1/api/registration";
        return axios.post(REGISTRATION_API_URL, userPayload);
    }
}

export default new RegisterService();
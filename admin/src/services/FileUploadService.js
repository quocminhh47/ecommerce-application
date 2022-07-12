import React from "react";
import AuthService from "./AuthService";
import axios from "axios";

class FileUploadService {


    fileUploadSetup(e) {
        const IMAGE_UPLOAD_URL =
         "http://localhost:8080/admin/api/products/gallery";

        const token = localStorage.getItem("accessToken")
        AuthService.checkUserAuth(token);

        const params = {        
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        //validate file
        let files = e.target.files[0];
        e.preventDefault();

        const formData = new FormData();
        formData.append('file', files);
        formData.append('fileName', files.name);
        
        return axios.post(IMAGE_UPLOAD_URL, formData, params);
    }
}

export default new FileUploadService();
import React from "react";
import axios from "axios";
import AuthService from "./AuthService";

const COMMENT_BASE_API_URL = "http://localhost:8080/user/api/comments/"

class CommentSerivce {
    fetchListComments(productId) {
        const token = window.localStorage.getItem("accessToken")
        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        // AuthService.checkUserAuth(token);
        const url = COMMENT_BASE_API_URL + "product/"+productId;
        console.log(url);

        return axios.get(url, params)

    }

    comment(productId, dataPayload) {
        const token = window.localStorage.getItem("accessToken")
        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }
        AuthService.checkUserAuth(token);
        const url = COMMENT_BASE_API_URL + 'product/' +productId;
        return axios.post(url, dataPayload, params);

    }

    deleteComment(id) {
        const token = window.localStorage.getItem("accessToken")
        const params = {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        }

        const url = COMMENT_BASE_API_URL + id;
        return axios.delete(url, {},params);
    }
}

export default new CommentSerivce();
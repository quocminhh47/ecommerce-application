class LoginService {
    logout() {
        window.localStorage.removeItem("accessToken");
        alert("Logout success")
    }

    checkLoginStatus(data) {
        if (data.loginStatusResponse) {
            if (data.loginStatusResponse.roleName == 'ADMIN' ) {
                return true;
            }
        }
        else return false;
    }

    checkAuthorization() {
        const token = window.localStorage.getItem("accessToken")
        if (!token) {
            return false;
        }
        else { return true; }
    }

    checkRole(data) {
        if (data) {
            if (data.loginStatusResponse.roleName == "ADMIN" || data.loginStatus.roleName == "ADMIN") {
                return true
            }
        }
        else {
            return false;
        }
    }
}

export default new LoginService();
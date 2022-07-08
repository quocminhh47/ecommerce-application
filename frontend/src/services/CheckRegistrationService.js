import React from "react";
import MailVerifyService from "./MailVerifyService";

class CheckRegistrationService {

    checkInfo(firstName, lastName, email, password, phone, address) {
        if (firstName
            && lastName
           // && email
            && MailVerifyService.ValidateEmail(email)
            && password
            && phone.length >= 10 && phone.length <=12
            && address) {
            return true;
        } else {
            return false;
        }
    }

   
}

export default new CheckRegistrationService();

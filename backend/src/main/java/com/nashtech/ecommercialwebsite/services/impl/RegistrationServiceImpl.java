package com.nashtech.ecommercialwebsite.services.impl;

import com.nashtech.ecommercialwebsite.data.entity.Account;
import com.nashtech.ecommercialwebsite.data.entity.ConfirmationToken;
import com.nashtech.ecommercialwebsite.data.entity.Role;
import com.nashtech.ecommercialwebsite.data.repository.RoleRepository;
import com.nashtech.ecommercialwebsite.data.repository.UserRepository;
import com.nashtech.ecommercialwebsite.dto.request.RegistrationRequest;
import com.nashtech.ecommercialwebsite.dto.response.RegistrationResponse;
import com.nashtech.ecommercialwebsite.dto.response.TokenResponse;
import com.nashtech.ecommercialwebsite.exceptions.BadRequestException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceConfictException;
import com.nashtech.ecommercialwebsite.exceptions.ResourceNotFoundException;
import com.nashtech.ecommercialwebsite.services.ConfirmationTokenService;
import com.nashtech.ecommercialwebsite.services.EmailSender;
import com.nashtech.ecommercialwebsite.services.RegistrationService;
import com.nashtech.ecommercialwebsite.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    private final UserService userService;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final ConfirmationTokenService confirmationTokenService;

    private final EmailSender emailSender;

    private final static String EMAIL_ALREADY_TAKEN = "Email %s already exist!";

    @Override
    public TokenResponse register(RegistrationRequest request, String roleName) {

        Role userRole = roleRepository.findRolesByRoleName(roleName)
                .orElseThrow(
                        () -> new ResourceNotFoundException(roleName + " ROLE IS NOT EXIST"));

        Optional<Account> optionalAccount = userRepository.findAccountByUsername(request.getEmail());

        //check if account already register and confirmed -> throw exception
        if (optionalAccount.isPresent() && optionalAccount.get().getEnabled())
            throw new ResourceConfictException(String.format(EMAIL_ALREADY_TAKEN, request.getEmail()));

        // check if account already register but not confirmed
        // then permit to register again with latest information
        if (optionalAccount.isPresent() && !optionalAccount.get().getEnabled()) {
            Integer userId = optionalAccount.get().getId();
            Account newAccount = new Account(
                    userId,
                    request.getEmail(),
                    request.getFirstName(),
                    request.getLastName(),
                    request.getPassword(),
                    request.getPhone(),
                    request.getAddress(),
                    userRole
            );
            String token = userService.signUpUser(newAccount);
            String confirmLink = "http://localhost:8080/v1/api/registration/confirm?token=" + token;
            emailSender.send(request.getEmail(), buildEmail(request.getLastName(), confirmLink));
            return new TokenResponse("SUCCESS", token);
        }

        //if new -> create new account
        Account userAccount = new Account(
                request.getEmail(),
                request.getFirstName(),
                request.getLastName(),
                request.getPassword(),
                request.getPhone(),
                request.getAddress(),
                userRole
        );
        String token = userService.signUpUser(userAccount);
        String confirmLink = "http://localhost:8080/v1/api/registration/confirm?token=" + token;
        emailSender.send(request.getEmail(), buildEmail(request.getLastName(), confirmLink));
        return new TokenResponse("SUCCESS", token);
    }

    @Transactional
    public RegistrationResponse confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Token is incorrect"));

        Account account = confirmationToken.getAccount();

        if (account.getEnabled()) {
            throw new ResourceConfictException("Your email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiredAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new BadRequestException("Token has been expired!");
        }

        confirmationTokenService.setConfirmedAt(token);
        //enable user
        account.setEnabled(true);
        userRepository.save(account);
        //create shopping cart
        userService.createShoppingCart(confirmationToken.getAccount().getUsername());
        return new RegistrationResponse("Your account has been actived!!");
    }

    public String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}

package agh.iot.controller;

import agh.iot.dto.UserDto;
import agh.iot.entities.User;
import agh.iot.restmodels.responses.SignUpResponse;
import agh.iot.security.JwtTokenUtil;
import agh.iot.services.JwtUserDetailsService;
import agh.iot.restmodels.requests.JwtRequest;
import agh.iot.restmodels.responses.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.validator.routines.EmailValidator;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping (path ="/signin", consumes = {"application/json"})
    public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User doesn't exist");
        }

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping( path = "signup", consumes = {"application/json"}, produces={"application/json"})
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) throws Exception {

        if(!EmailValidator.getInstance().isValid(userDto.getEmail())) {
            SignUpResponse responseBody = new SignUpResponse("Wrong email pattern!");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
        }

        User user = userDetailsService.save(userDto);

        if (user == null) {
            SignUpResponse responseBody = new SignUpResponse("User already exists!");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }
        return ResponseEntity.ok(user);
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}

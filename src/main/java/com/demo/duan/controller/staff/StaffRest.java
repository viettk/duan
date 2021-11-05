package com.demo.duan.controller.staff;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.demo.duan.entity.StaffEntity;
import com.demo.duan.repository.staff.StaffRepository;
import com.demo.duan.service.staff.StaffService;
import com.demo.duan.service.staff.dto.StaffDto;
import com.demo.duan.service.staff.input.StaffInput;
import com.demo.duan.service.staff.param.StaffParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin("*")
@RestController
@AllArgsConstructor
@RequestMapping("/admin/staff")
@Slf4j
public class StaffRest {

    private final StaffRepository repository;
    private final StaffService service;

    @GetMapping("/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")){
            try {
                String  refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                StaffEntity staff = repository.getByEmail(username);
                String access_token = JWT.create()
                        .withSubject(staff.getEmail())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 60 *10))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("role", staff.getRole())
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }catch (Exception e){
                log.info("Error logging in : {}", e.getMessage());
                response.setHeader("error", e.getMessage());
//                    response.sendError(FORBIDDEN.value());
                Map<String, String> error = new HashMap<>();
                error.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), error);
            }
        } else {
            throw new RuntimeException("refresh token is missing");
        }
    }

    @GetMapping()
    public ResponseEntity<Page<StaffDto>>getAll(
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.getStaff(limit,page,field,known);
    }

    @GetMapping("/get-by-email")
    public ResponseEntity<StaffDto>getByEmail(@RequestParam("email") String email){
        return this.service.getByUsername(email);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<StaffDto>>search(
            @RequestBody StaffParam param,
            @RequestParam("_limit") Optional<Integer> limit,
            @RequestParam("_page") Optional<Integer> page,
            @RequestParam(value = "_field", required = false) Optional<String> field,
            @RequestParam(value = "_known", required = false) String known
    ){
        return this.service.searchByParam(param, limit, page, field, known);
    }

    @PostMapping()
    public Object createStaff(@RequestBody StaffInput staffInput){
        return this.service.createStaff(staffInput);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StaffDto>updateStaff(@RequestBody StaffInput staffInput, @PathVariable("id") Integer id){
        return  this.service.updateStaff(id, staffInput);
    }

    @PutMapping("/disable/{id}")
    public ResponseEntity<StaffDto>disableStaff(@PathVariable("id") Integer id){
        return this.service.disableStaff(id);
    }
}

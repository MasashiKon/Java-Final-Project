package com.randombattlegenerator.main.controllers;

import java.io.IOException;

import java.net.URISyntaxException;

import java.net.http.HttpResponse;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import java.util.Base64;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.bouncycastle.util.encoders.Hex;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.randombattlegenerator.main.entities.UserSession;
import com.randombattlegenerator.main.AsyncMethods;
import com.randombattlegenerator.main.entities.User;
import com.randombattlegenerator.main.repositories.UserRepository;

@Controller
@SessionAttributes("userSession") 
public class MainApplicationController {
    
    private final UserRepository userRepository;

    public MainApplicationController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @GetMapping("")
    public String getBattlePage(Model model, SessionStatus sessionStatus, @CookieValue(value = "session_id", required=false) String cookieSession) {
        // UserSession newUser = new UserSession();
        // newUser.setSession_id("testtesttest");
        // newUser.setUsename("testuser");
        // newUser.setAuthenticated(false);
        // model.addAttribute("userSession", newUser);
    
        return "index";
    }

    @GetMapping("/second")
    public String getTest() {
        return "jscssDemo";
    }

    @GetMapping("/randombattle")
    public String getApi(Model model) throws URISyntaxException, IOException, InterruptedException {

        UserSession userSession = (UserSession) model.getAttribute("userSession");

        if(userSession != null && userSession.isAuthenticated()) {
        try{
            // use the client to send the request
            HttpResponse<String> response = AsyncMethods.monsterApi();
            model.addAttribute("monster", response.body());
        } catch(Exception e) {
            model.addAttribute("monster", "??");
        }

        try{

            // use the client to send the request
            HttpResponse<String> response = AsyncMethods.pokeApi();
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(response.body());

            // ArrayList<String> extractedStats = new ArrayList<>();

            // for(JsonNode stats: json.get("stats")) {
            //     extractedStats.add(stats.get("stat").get("name").toString());
            //     extractedStats.add(stats.get("base_stat").toString());
            // }
            
            // // the response:
            // model.addAttribute("stats", extractedStats);
            model.addAttribute("hp", json.get("stats").get(0).get("base_stat"));
            model.addAttribute("attack", json.get("stats").get(1).get("base_stat"));
            model.addAttribute("defense", json.get("stats").get(2).get("base_stat"));
            model.addAttribute("magicAttack", json.get("stats").get(3).get("base_stat"));
            model.addAttribute("magicDefense", json.get("stats").get(4).get("base_stat"));
            model.addAttribute("speed", json.get("stats").get(5).get("base_stat"));
        } catch(Exception e) {
            model.addAttribute("stats", null);
            model.addAttribute("hp", null);
            model.addAttribute("attack", null);
            model.addAttribute("defence", null);
            model.addAttribute("magicAttack", null);
            model.addAttribute("magicDefense", null);
            model.addAttribute("speed", null);
        }

        try {
            HttpResponse<String> response = AsyncMethods.quoteApi();
            //System.out.println(response.body()); 
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode json = objectMapper.readTree(response.body());

            String originalQuote = json.get("content").toString();
            String trimedQuote = originalQuote.substring(1, originalQuote.length() - 1);
            String replacedQuote = trimedQuote.replaceAll("\\\\n|\\\\r", " ").replaceAll("\\\\", "");

            model.addAttribute("quote", replacedQuote);
        } catch (Exception e) {
            model.addAttribute("quote", "......");
        }
        
        return "apitest";

        }

        return "loginPage";

    }

    @PostMapping("/randombattle")
    public String postUserUpdate(Model model, @RequestBody User user) {

        UserSession userSession = (UserSession) model.getAttribute("userSession");

        if(userSession != null) {

            Optional<User> userToUpdateOptional = this.userRepository.findById(userSession.getUserId());

            if(userToUpdateOptional.isPresent()) {
               User userToUpdate =  userToUpdateOptional.get();

               userToUpdate.setTotal(user.getTotal());
               userToUpdate.setWins(user.getWins());
               userToUpdate.setLoses(user.getLoses());
               userToUpdate.setStreak(user.getStreak());

               userSession.setTotal(user.getTotal());
               userSession.setWins(user.getWins());
               userSession.setLoses(user.getLoses());
               userSession.setStreak(user.getStreak());

               this.userRepository.save(userToUpdate);
               model.addAttribute("userSession", userSession);
            }
        }

        return "redirect:/randombattle";
    }


    @GetMapping("/test")
    public String testGet(@ModelAttribute UserSession session, HttpSession httpSession, @CookieValue(value = "session_id", required=false) String cookieSession, HttpServletResponse response) {
        System.out.println(session);
        System.out.println(httpSession.getId());
        //session.setSession_id(httpSession.getId());
        session.setUsename("testuser");
        session.setAuthenticated(true);
        System.out.println(session);
        System.out.println(httpSession.getId());
        System.out.println(cookieSession);
        Cookie cookie = new Cookie("session_id", httpSession.getId());
        cookie.setMaxAge(1);
        response.addCookie(cookie);
        return "test";
    }

    // -H "Content-Type: application/json;charset=UTF-8"
    @PostMapping("/test")
    public String testPost(@RequestBody User user, HttpSession httpSession) {
        System.out.println(httpSession);

        MessageDigest digest;
        try {
            String password = user.getPassword();
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
            String salt = Base64.getEncoder().encodeToString(bytes);

            String saltPassword = salt + password;

            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltPassword.getBytes(StandardCharsets.UTF_8));
            String hashedPassword = new String(Hex.encode(hash));
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            this.userRepository.save(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return "test";
    }

    @PutMapping("/test/{id}")
    public String testPut(@PathVariable("id") Integer id, @RequestBody User user) {
        Optional<User> updateUserOptional = this.userRepository.findById(id);

        if(updateUserOptional.isPresent()) {
           User userToUpdate = updateUserOptional.get(); 
           if(user.getUsername() != null) {
            userToUpdate.setUsername(user.getUsername());
           }
           if(user.getPassword() != null) {
            userToUpdate.setPassword(user.getPassword());
           }
           if(user.getSalt() != null) {
            userToUpdate.setSalt(user.getSalt());
           }
           if(user.getTotal() != null) {
            userToUpdate.setTotal(user.getTotal());
           }
           if(user.getWins() != null) {
            userToUpdate.setWins(user.getWins());
           }
           if(user.getLoses() != null) {
            userToUpdate.setLoses(user.getLoses());
           }
           if(user.getStreak() != null) {
            userToUpdate.setStreak(user.getStreak());
           }
           this.userRepository.save(userToUpdate); 
        }
        return "test";
    }

    @DeleteMapping("/test/{id}")
    public String testDelete(@PathVariable("id") Integer id) {
        Optional<User> deleteUserOptional = this.userRepository.findById(id);

        if(deleteUserOptional.isPresent()) {
           User deleteUser = deleteUserOptional.get();
           this.userRepository.delete(deleteUser); 
        }

        return "test";
    }

    @GetMapping("/signup")
    public String getSignupPage(Model model) {
        return "signupPage";
    }

    @PostMapping("/signup")
    public String postSignupPage(Model model, @RequestBody User user) {

        MessageDigest digest;
        UserSession userSession = new UserSession();
        User newUser;
        List<User> sameUsername = this.userRepository.findByUsername(user.getUsername());

        if(!sameUsername.isEmpty()) {
            model.addAttribute("isSameName", true);
            return "signupPage";
        }

        try {
            String password = user.getPassword();
            SecureRandom random = new SecureRandom();
            byte[] bytes = new byte[20];
            random.nextBytes(bytes);
            String salt = Base64.getEncoder().encodeToString(bytes);

            String saltPassword = salt + password;

            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(saltPassword.getBytes(StandardCharsets.UTF_8));
            String hashedPassword = new String(Hex.encode(hash));
            user.setPassword(hashedPassword);
            user.setSalt(salt);
            user.setWins(0);
            user.setLoses(0);
            user.setTotal(0);
            user.setStreak(0);
            newUser = this.userRepository.save(user);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "redirect:/sighup";
        }

        userSession.setUserId(newUser.getId());
        userSession.setUsename(user.getUsername());
        userSession.setAuthenticated(true);
        userSession.setTotal(0);
        userSession.setWins(0);
        userSession.setLoses(0);
        userSession.setStreak(0);

        model.addAttribute("userSession", userSession);
        
        return "redirect:/randombattle";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "loginPage";
    }

    @PostMapping("/login")
    public String postLoginPage(Model model, @RequestBody User user) {

        MessageDigest digest;
        try {
            List<User> userList = this.userRepository.findByUsername(user.getUsername());

            if(userList == null) {
                return "redirect:/login";
            }

            for(User candidate: userList) {
                candidate.getUsername();
                String savedPassword = candidate.getPassword();
                String savedSalt = candidate.getSalt();

                String password = user.getPassword();
                String saltedPassword = savedSalt + password;

                digest = MessageDigest.getInstance("SHA-256");
                byte[] hash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
                String hashedPassword = new String(Hex.encode(hash));

                if(savedPassword.equals(hashedPassword)) {
                    UserSession userSession = new UserSession();

                    userSession.setUserId(candidate.getId());
                    userSession.setUsename(candidate.getUsername());
                    userSession.setAuthenticated(true);
                    userSession.setTotal(candidate.getTotal());
                    userSession.setWins(candidate.getWins());
                    userSession.setLoses(candidate.getLoses());
                    userSession.setStreak(candidate.getStreak());
                    
                    model.addAttribute("userSession", userSession);

                    return "redirect:/randombattle";
                }
            }

            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "redirect:/login";
        }
        
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String getLogout(Model model, SessionStatus sessionStatus) {
        if(model.getAttribute("userSession") != null) {
            sessionStatus.setComplete();
        }
        return "redirect:/login";
    }
}

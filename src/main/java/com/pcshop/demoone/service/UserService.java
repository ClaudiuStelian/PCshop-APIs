package com.pcshop.demoone.service;

import java.time.LocalDate;
import java.time.Period;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.pcshop.demoone.api.dao.UserDao;
import com.pcshop.demoone.api.model.User;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public ResponseEntity<?> getUser(int id) {
        User user = userDao.findById(id).orElse(null);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } else
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User " + id + " not found");
    }

    public ResponseEntity<?> getUserByEmail(String email) {
        User user = userDao.findByEmail(email);
        if (user != null) {
            return ResponseEntity.status(HttpStatus.OK).body(user);
        }
        // return new ResponseEntity("User not found", HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User " + email + " not found");
    }

    public ResponseEntity<?> registerUser(User user) {
        if ((userDao.findByEmail(user.getEmail()) != null)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(String.format("The email %s is already used", user.getEmail()));
        } else if ((ageCalculator(user.getDob()) >= 18) &&
                (user.getGender().matches("^(male|female)$"))
                && (((int) Math.floor(Math.log10(user.getCard().getCardNo())) + 1) == 16)
                && (((int) Math.floor(Math.log10(user.getCard().getCvv())) + 1) == 3)
                && (user.getCard().getExpDate().isAfter(LocalDate.now()))
                // digit + lowercase char + uppercase char + punctuation + symbol -->
                && (user.getPassword()
                        .matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"))
                && (user.getEmail().matches("^[\\w.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
                && (user.getGender().matches("^(male|female)$"))) {
            User registeredUser = userDao.save(user);

            return ResponseEntity.status(HttpStatus.CREATED).body(registeredUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE)
                    .body(userHint());
        }
    }

    public ResponseEntity<?> deleterUser(int id, String password) {
        if ((id <= 0) || (password == null)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("The password header and/or the path variable is missing.");
        } else if (userDao.findById(id).orElse(null) != null) {
            if (userDao.findById(id).orElse(null).getPassword().equals(password)) {
                String userEmail = userDao.findById(id).orElse(null).getEmail();
                userDao.deleteById(id);
                return new ResponseEntity<>(String.format("User ID: %d was deleted. Email: %s", id, userEmail),
                        HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(String.format("The password header does not match to the registered ID %s", id));
        } else {
            return new ResponseEntity<>(String.format("User with ID: %d was not found", id), HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> getAllUserList() {
        return ResponseEntity.status(HttpStatus.OK).body(userDao.findAll());
    }

    public ResponseEntity<?> updateUser(User user) {
        if (user.getEmail() == null || user.getDob() == null || user.getName() == null || user.getGender() == null
                || user.getPassword() == null || user.getCard().getExpDate() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userHint());
        }
        User existingUser = userDao.findByEmail(user.getEmail());
        if (existingUser != null) {
            if ((ageCalculator(user.getDob()) >= 18) && user.getEmail() != null && user.getDob() != null
                    && user.getName() != null && user.getGender() != null
                    && user.getPassword() != null && user.getCard().getExpDate() != null
                    && (((int) Math.floor(Math.log10(user.getCard().getCardNo())) + 1) == 16)
                    && (((int) Math.floor(Math.log10(user.getCard().getCvv())) + 1) == 3)
                    && (user.getCard().getExpDate().isAfter(LocalDate.now()))
                    && (user.getGender().matches("^(male|female)$"))
                    // digit + lowercase char + uppercase char + punctuation + symbol -->
                    && (user.getPassword()
                            .matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$"))
                    && (user.getEmail().matches("^[\\w.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))) {
                existingUser.setDob(user.getDob());
                existingUser.setEmail(user.getEmail());
                existingUser.setName(user.getName());
                existingUser.getCard().setCardNo(user.getCard().getCardNo());
                existingUser.getCard().setExpDate(user.getCard().getExpDate());
                existingUser.getCard().setCvv(user.getCard().getCvv());
                return ResponseEntity.status(HttpStatus.OK).body(userDao.save(existingUser));
            } else
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userHint());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email " + user.getEmail() + " not found");
        }
    }

    public String getUser(String name) {
        String userName = userDao.findByName(name).getName();
        return userName;
    }

    public int ageCalculator(LocalDate dob) {
        LocalDate curDate = LocalDate.now();
        if ((dob != null) && (curDate != null)) {
            return Period.between(dob, curDate).getYears();
        } else
            return 0;
    }

    public String userHint() {
        String message = "The request body should follow the following criterias:\r\n";
        Object userObject = "{\r\n" + //
                "    \"name\": \"User Name\",\r\n" + //
                "    \"dob\": \"YYYY-MM-DD\",\r\n" + //
                "    \"password\": \"digit + lowercase char + uppercase char + punctuation + symbol\",\r\n" + //
                "    \"email\": \"abcdef@gmail.com\",\r\n" + //
                "    \"gender\": \"male/female\"\r\n" + //
                "    \"card\": {\r\n" + //
                "        \"cardNo\": \"16 digits account number\",\r\n" + //
                "        \"expDate\": \"YYYY-MM-DD\",\r\n" + //
                "        \"cvv\": \"3 digits from the back of the card\"\r\n" + //
                "    }\r\n" + //
                "}";
        return message + userObject;
    }
}

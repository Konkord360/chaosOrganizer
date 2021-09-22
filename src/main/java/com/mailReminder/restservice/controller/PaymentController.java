package com.mailReminder.restservice.controller;

import com.mailReminder.restservice.model.Payment;
import com.mailReminder.restservice.model.User;
import com.mailReminder.restservice.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
public class PaymentController {
    private final UserRepository userRepository;
    private static final Logger logger = LogManager.getLogger(PaymentController.class);

    @Autowired
    public PaymentController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/addPayment")
    public ResponseEntity<Object> addPayment(@RequestBody Payment payment, @RequestParam String ownerLogin) {
        if(logger.isDebugEnabled()) {
            logger.debug("Add payment request received for user: ".concat(ownerLogin));
            logger.debug(payment);
        }
        User databaseUser = userRepository.findByUsername(ownerLogin);
        Optional<List<Payment>> userPayments = Optional.ofNullable(databaseUser.getPayments());

        if (userPayments.isPresent()) {
            payment.setId(ObjectId.get());
            userPayments.get().add(payment);
            databaseUser.setPayments(userPayments.get());
        } else
            databaseUser.setPayments(List.of(payment));

        userRepository.save(databaseUser);
        return ResponseEntity.status(HttpStatus.OK).body(payment);
    }

    @PatchMapping("/modifyPayment")
    public ResponseEntity<Object> modifyPayment(@RequestParam String ownerLogin, @RequestParam int paymentIndex, @RequestBody Payment payment) {
        User user = userRepository.findByUsername(ownerLogin);
        List<Payment> userPayments = user.getPayments();

        userPayments.remove(paymentIndex);
        userPayments.add(paymentIndex, payment);

        userRepository.save(user);

        return ResponseEntity.status(HttpStatus.OK).body(user.getPayments());
    }

    @GetMapping("/getPayments")
    public ResponseEntity<Object> getPayment(@RequestParam String userLogin) {
        User user = userRepository.findByUsername(userLogin);
        List<Payment> userPayments = user.getPayments();

        return ResponseEntity.status(HttpStatus.OK).body(userPayments);
    }

    @DeleteMapping("/deletePayment")
    public ResponseEntity<Object> deletePayment(@RequestParam String ownerLogin, @RequestParam int paymentIndex) {
        User user = userRepository.findByUsername(ownerLogin);
        List<Payment> userPayments = user.getPayments();
        if (userPayments != null && !userPayments.isEmpty() && userPayments.size() >= paymentIndex) {
            user.getPayments().remove(paymentIndex);
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(user.getPayments());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"errorCode\":404,\"errorMessage\":\"Reminder not found\"}");
    }
}

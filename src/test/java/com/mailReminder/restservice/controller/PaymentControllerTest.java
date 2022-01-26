package com.mailReminder.restservice.controller;

import com.mailReminder.restservice.model.ERole;
import com.mailReminder.restservice.model.Payment;
import com.mailReminder.restservice.model.Role;
import com.mailReminder.restservice.model.User;
import com.mailReminder.restservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class PaymentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;


    @BeforeEach
    void init(){

    }

    @Test
    void testGetPaymentsReturnsCorrectPayments() throws Exception {
//        Mock userRopositoryMock = Mockito.mock(UserRepository.class)class;
        var user = createUser();
        var payment = createPayment();
        user.setPayments(List.of(payment));

        when(userRepository.findByEmail(any())).thenReturn(user);

        this.mockMvc.perform(get("/getPayments?userLogin=test")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].paymentTitle", is(payment.getPaymentTitle())))
                .andExpect(jsonPath("$[0].amountOfSinglePayment", is(payment.getAmountOfSinglePayment().doubleValue())))
                .andExpect(jsonPath("$[0].wholeAmount", is(payment.getWholeAmount().doubleValue())))
                .andExpect(jsonPath("$[0].deadline", is(payment.getDeadline())))
                .andExpect(jsonPath("$[0].receiverIBAN", is(payment.getReceiverIBAN())))
                .andExpect(jsonPath("$[0].receiverName", is(payment.getReceiverName())))
                .andExpect(jsonPath("$[0].senderIBAN", is(payment.getSenderIBAN())))
                .andExpect(jsonPath("$[0].payedByNow", is(payment.getPayedByNow().doubleValue())));
    }

    @Test
    void testAddsProperly() throws Exception {
        var user = createUser();
        var payment = createPayment();
//        user.setPayments(List.of(payment));
        when(userRepository.findByEmail(any())).thenReturn(user);

        this.mockMvc.perform(post("/addPayment?ownerLogin=test").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(payment.toString())).andDo(print())
                //.content("{\"paymentTitle\":\"test\",\"amountOfSinglePayment\":100.0,\"wholeAmount\":10.0,\"deadline\":\"21/10/1998\",\"receiverIBAN\":\"testIBAN\",\"receiverName\":\"MrTest\",\"senderIBAN\":\"testIBAN\",\"payedByNow\":10.0}")).andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/getPayments?userLogin=test")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)));
    }

    @Test
    void testDeletesProperly() throws Exception {
        var user = createUser();
        var payments = createPayments(3);
        user.setPayments(payments);

        when(userRepository.findByEmail(any())).thenReturn(user);

        this.mockMvc.perform(delete("/deletePayment?ownerLogin=test&paymentIndex=1").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    void testModifiesProperly() throws Exception {
        var user = createUser();
        var payments = createPayments(3);
        var updatedPayment = createPayment();
        user.setPayments(payments);

        when(userRepository.findByEmail(any())).thenReturn(user);

        this.mockMvc.perform(delete("/deletePayment?ownerLogin=test&paymentIndex=1").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(2)));
    }

    @Test
    void testNewPaymentIsAddedWithDefaultReminder() throws Exception{

        var user = createUser();
        var payment = createPayment();
//        user.setPayments(List.of(payment));
        when(userRepository.findByEmail(any())).thenReturn(user);

        this.mockMvc.perform(post("/addPayment?ownerLogin=test").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8")
                .content(payment.toString())).andDo(print())
                //.content("{\"paymentTitle\":\"test\",\"amountOfSinglePayment\":100.0,\"wholeAmount\":10.0,\"deadline\":\"21/10/1998\",\"receiverIBAN\":\"testIBAN\",\"receiverName\":\"MrTest\",\"senderIBAN\":\"testIBAN\",\"payedByNow\":10.0}")).andDo(print())
                .andExpect(status().isOk());
        this.mockMvc.perform(get("/getPayments?userLogin=test")).andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*]", hasSize(1)))
                .andExpect(jsonPath("$[0].reminder.date", is("1998-10-23")));
    }

    private User createUser(){
        User user = new User();
        user.setRoles(Set.of(new Role(ERole.ROLE_USER)));
        user.setEmail("testemail@gmail.com");
        user.setUsername("test2");
        user.setPassword("test");

        return user;
    }

    private Payment createPayment(){
        Payment payment = new Payment();
        payment.setPaymentTitle("test");
        payment.setAmountOfSinglePayment(BigDecimal.valueOf(100.00));
        payment.setDeadline("1998-10-23");
        payment.setPayedByNow(BigDecimal.valueOf(10.00));
        payment.setReceiverIBAN("testIBAN");
        payment.setReceiverName("MrTest");
        payment.setSenderIBAN("testIBAN");
        payment.setWholeAmount(BigDecimal.valueOf(10.00));

        return payment;
    }

    private List<Payment> createPayments(final int numberOfPayments){
        List<Payment> payments = new ArrayList<>();
        for(int i = 0; i < numberOfPayments; i++){
            payments.add(createPayment());
        }
        return payments;
    }
}

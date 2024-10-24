//package com.example.backendkino.service;
//
//import com.example.backendkino.model.Admin;
//import com.example.backendkino.model.Showing;
//import com.example.backendkino.model.Theatre;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
//@ActiveProfiles("dev")
//class ShowingServiceimplTest {
//
//    @Autowired
//    ShowingServiceimpl showingServiceimpl;
//    @Autowired
//    TheatreService theatreService;
//    @Autowired
//    ApiServiceGetAdmins apiServiceGetAdmins;
//
//    @Test
//    void createShowing() {
//        Theatre theatre= theatreService.createTheatre(1,1);
//        Showing showing = new Showing();
//        showing.setTheatre(theatre);
//        showing.getAdmin().setAdminId(1);
//        showing.getMovie().setMovieId(1);
//        showing.setShowingId(1000);
//        showing.setDateTime(2004-10-10-17-00);
//       // showingServiceimpl.createShowing(showing)
//    }
//
//    @Test
//    void getShowTimesByMovieId() {
//    }
//
//    @Test
//    void isShowtimeValid() {
//        Showing Showing = new Showing(1,);
//    }
//}
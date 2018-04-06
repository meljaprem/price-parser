package com.prem.priceparser;

import com.prem.priceparser.domain.entity.User;
import com.prem.priceparser.domain.enums.RoleEnum;
import com.prem.priceparser.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PriceParserApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void createUser() {
         User user = new User("name",
                        "Surname",
                        "Email@email.ua",
                        "admin",
                        "admin");
         userService.createUser(user,RoleEnum.USER, RoleEnum.ADMIN);
         log.info("User with id {} created \n {}", user.getId(), user);
    }

    @Test
    public void jsoupTest() throws IOException {
        Document doc = Jsoup.connect("https://rozetka.com.ua/offer/5563017/").followRedirects(true).get();
        log.info("================================================================\n"
//                +doc.selectFirst("div.detail-price-uah meta[content]").attr("content"));
                +doc.text());
//        Elements newsHeadlines = doc.wholeText();
//        for (Element headline : newsHeadlines) {
//            log.info("title: {}, absUrl: {}",
//                    headline.attr("title"), headline.absUrl("href"));
//        }
    }



}

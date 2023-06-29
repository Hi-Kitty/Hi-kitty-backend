package io.nuabo.hikitty.user.mock;


import io.nuabo.hikitty.user.application.port.MailSender;

public class FakeMailSender implements MailSender {

    public String email;
    public String title;
    public String content;

    @Override
    public void send(String email, String title, String content) {
        this.title = title;
        this.email = email;
        this.content = content;
    }
}

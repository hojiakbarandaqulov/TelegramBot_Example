package org.example;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TelegramUser {
    private String chatId;
    private String step;
    private String msg;
    private String fullName;
    private String selectedLang;

}

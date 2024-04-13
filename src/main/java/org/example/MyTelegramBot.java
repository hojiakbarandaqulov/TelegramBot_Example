package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class MyTelegramBot extends TelegramLongPollingBot {
    static List<TelegramUser> users = new ArrayList<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String text = message.getText();
            String chatId = message.getChatId().toString();
            TelegramUser telegramUser = saveUser(chatId);
            if (text.equals("/start")) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Assalomu aleykum Botga hush kelibsiz");
                sendMessage.setChatId(chatId);
                sendMsg(sendMessage);
                telegramUser.setStep(BotConstant.SELECT_LANG);
            } else if (telegramUser.getStep().equals(BotConstant.SELECT_LANG)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Hush Vaqt boling");
                sendMessage.setChatId(chatId);
                sendMsg(sendMessage);
                telegramUser.setStep(BotConstant.MSG);
            } else if (telegramUser.getStep().equals(BotConstant.MSG)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("MAZGI !!!!!!!!!!!!!!!");
                sendMessage.setChatId(chatId);
                sendMsg(sendMessage);
                telegramUser.setStep(BotConstant.BUTTON);
            } else if (telegramUser.getStep().equals(BotConstant.BUTTON)) {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Mazgimiszan");
                sendMessage.setChatId(chatId);

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<InlineKeyboardButton> buttonList = new ArrayList<>();
                InlineKeyboardButton button = new InlineKeyboardButton();
                button.setText("MAZGI");
                button.setCallbackData(Query.UZ);

                InlineKeyboardButton button1 = new InlineKeyboardButton();
                button1.setText("Mazgilar Jamosi");
                button1.setCallbackData(Query.ENG);

                buttonList.add(button);
                buttonList.add(button1);

                List<List<InlineKeyboardButton>> tr = new ArrayList<>();
                tr.add(buttonList);
                inlineKeyboardMarkup.setKeyboard(tr);
                sendMessage.setReplyMarkup(inlineKeyboardMarkup);
                sendMsg(sendMessage);
                telegramUser.setStep(BotConstant.BUTTON);
            } else if (update.hasCallbackQuery()) {
                String chatid = update.getCallbackQuery().getFrom().getId().toString();
                String data = update.getCallbackQuery().getData();
                TelegramUser telegramUser1 = saveUser(chatId);

                if (telegramUser1.getStep().equals(BotConstant.BUTTON)) {
                    if (data.equals(Query.UZ)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("EE Mazgi");
                        sendMessage.setChatId(chatid);
                        sendMsg(sendMessage);
                    } else if (data.equals(Query.ENG)) {
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setText("Mazgisanda");
                        sendMessage.setChatId(chatid);
                        sendMsg(sendMessage);
                    }
                }
            }
        }
    }
    private TelegramUser saveUser(String chatId) {
        for (TelegramUser user : users) {
            if (user.getChatId().equals(chatId)) {
                return user;
            }
        }
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        users.add(telegramUser);
        return telegramUser;
    }
    private void sendMsg(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getBotUsername() {
        return "@video_saved_uz_bot";
    }

    @Override
    public String getBotToken() {
        return "6782087231:AAFyD0ihba6opnq2AesT2yPri4JZT0Axd2k";
    }
}

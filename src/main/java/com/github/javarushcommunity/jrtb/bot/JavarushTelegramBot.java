package com.github.javarushcommunity.jrtb.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Telegrambot for Javarush Community from Javarush community.
 */
@Component
public class JavarushTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;


    @Override
    public void onUpdateReceived(Update update) {
        //это и есть точка входа, куда будут поступать сообщения от пользователей. Отсюда будет идти вся новая логика;
        if(update.hasMessage() && update.getMessage().hasText()) {//Здесь все предельно просто: мы проверяем, что сообщение реально существует,
            String message = update.getMessage().getText().trim();// потому извлекаем само сообщение (message) и айдишник чата (chatId)
            String chatId = update.getMessage().getChatId().toString();// в котором идет переписка.

            SendMessage sm = new SendMessage();//Далее мы создаем объект для отправки сообщения SendMessage
            sm.setChatId(chatId);//передаем в него само сообщение и айдишник чата — то есть то, что отправить боту и куда.
            sm.setText(message);

            try {
                execute(sm);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {//здесь нужно добавить username нашего бота, к которому будем соединяться;
        return username;
    }

    @Override
    public String getBotToken() {//а это, соответственно, токен бота.
        return token;
    }
}
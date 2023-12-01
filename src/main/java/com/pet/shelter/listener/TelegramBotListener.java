package com.pet.shelter.listener;

import com.fasterxml.jackson.core.JsonParser;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.*;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.Keyboard;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pet.shelter.entity.ReportTable;
import com.pet.shelter.service.ReportTableService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class TelegramBotListener implements UpdatesListener {

    private final TelegramBot telegramBot;
    private final ReportTableService reportTableService;
    private final Pattern pattern=Pattern.compile("(([А-я\\d\\s.,!?:]+))");
    private Object image;


    public TelegramBotListener(TelegramBot telegramBot, ReportTableService reportTableService) {
        this.telegramBot = telegramBot;
        this.reportTableService = reportTableService;
    }

    @PostConstruct
    private void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {
        // В этом методе делаем обработку всех событий телеграмма. Например, сделаем ответ на сообщение /start
        for (Update update : updates) {
            if (update.message() != null && update.message().text() != null) {
                Message message = update.message();
                Long chatId = message.chat().id();
                String text = message.text();
                if ("/start".equals(text)) {
                    SendMessage sendMessage = new SendMessage(chatId, """
                            Привет. Это бот приюта для кошек.
                            """);

                    InlineKeyboardButton buttonText = new InlineKeyboardButton("Наш Лесной приют");
                    buttonText.callbackData("Наш Лесной приют");
                    InlineKeyboardButton buttonAddress = new InlineKeyboardButton("Как взять животное из приюта");
                    buttonAddress.callbackData("Как взять животное из приюта");
                    Keyboard keyboard = new InlineKeyboardMarkup(buttonText, buttonAddress);
                    sendMessage.replyMarkup(keyboard);
                    telegramBot.execute(sendMessage);
                }
            } else if (update.callbackQuery() != null && update.callbackQuery().data() != null) {
                var chatId = update.callbackQuery().message().chat().id();
                var data = update.callbackQuery().data();
                var massageId = update.callbackQuery().message().messageId();
                sendMessage(update.callbackQuery().message().chat().id(), " Наш Лесной приют ");


                if (data.equals("Наш Лесной приют")) {
                    // ответ на кнопку "Наш лесной приют"

                    SendMessage sendMessage = new SendMessage(chatId, " В нашем приюте живут кошки , " +
                            "которые скучают и ждут своих хозяев. Наш адрес:Московская область, " +
                            "городской округ Истра, деревня Бодрово. Вы можете связаться с " +
                            "нами по телефону 8926-926-03-33 ");

                    telegramBot.execute(sendMessage);

                } else if (data.equals("Как взять животное из приюта")) {
                    // ответ на кнопку "Как взять животное из приюта"
                    SendMessage sendMessage = new SendMessage(chatId, "Необходимо познакомиться с животным, время с 10-12 утра ежедневно. " +
                            "После того как животное останется дома, неободимо прислать отчет с фото питомца.");

                    InlineKeyboardButton buttonText1 = new InlineKeyboardButton("Наши питомцы");
                    buttonText1.callbackData("Наши питомцы");
                    InlineKeyboardButton buttonText2 = new InlineKeyboardButton("Прислать отчёт");
                    buttonText2.callbackData("Прислать отчёт");
                    Keyboard keyboard1 = new InlineKeyboardMarkup(buttonText1, buttonText2);
                    sendMessage.replyMarkup(keyboard1);
                    telegramBot.execute(sendMessage);
                }
                if (data.equals("Наши питомцы")) {
                    try {
                        byte[] photo = Files.readAllBytes(
                                Paths.get(TelegramBotListener.class.getResource("/photo 1.jpg").toURI())
                        );
                        SendPhoto sendPhoto = new SendPhoto(chatId, photo);
                        sendPhoto.caption("Это кошка Люся");
                        telegramBot.execute(sendPhoto);
                    } catch (IOException | URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        byte[] photo = Files.readAllBytes(
                                Paths.get(TelegramBotListener.class.getResource("/photo 3.jpg").toURI())
                        );
                        SendPhoto sendPhoto = new SendPhoto(chatId, photo);
                        sendPhoto.caption("Это котенок Бася");
                        telegramBot.execute(sendPhoto);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {
                        throw new RuntimeException(e);
                    }
                    try {
                        byte[] photo = Files.readAllBytes(
                                Paths.get(TelegramBotListener.class.getResource("/photo 06.jpg").toURI())
                        );
                        SendPhoto sendPhoto = new SendPhoto(chatId, photo);
                        sendPhoto.caption("Это кошка Нюся");
                        telegramBot.execute(sendPhoto);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    } catch (URISyntaxException e) {

                        throw new RuntimeException(e);
                    }

                }
                if (data.equals("Прислать отчёт")) {
                    SendMessage sendMessage = new SendMessage(chatId, "Просьба написать о настроении питомца и подкрепить фото");
                    telegramBot.execute(sendMessage);

                } else if(data !=null) {
                    Matcher matcher = pattern.matcher(data);
                    if (matcher.find()) {

                            String txt =matcher.group();
                            ReportTable reportTable=new ReportTable();
                            reportTable.setChatId(chatId);
                            reportTable.setMessage(txt);
                           // reportTable.setImage(byte[] image);
                            reportTableService.save(reportTable);
                            sendMessage(chatId, "Задача успешно запланирована!");
                        }
                    }else {
                        sendMessage(chatId, "Некорректный формат сообщения! ");
                    }
                }

                }




            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }


    public String sendMessage (Long chatId, String text){
        SendMessage sendMessage = new SendMessage(chatId, text);
        return text;
    }
   // public byte[] getIdAsByte(UUID uuid)
  //  {
    //    ByteBuffer bb = ByteBuffer.wrap(new byte[8]);
     //   bb.putLong(uuid.getMostSignificantBits());
     //   bb.putLong(uuid.getLeastSignificantBits());
     //   return bb.array();
   // }
    //public byte[] getImage(long id) {
    // ReportTable reportTable = reportTableRepository.findById(id).orElseThrow();
    // return reportTable.getImage();
    //}


}
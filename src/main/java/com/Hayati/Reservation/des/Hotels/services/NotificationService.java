package com.Hayati.Reservation.des.Hotels.services;

import java.util.List;
import org.springframework.stereotype.Service;
import com.Hayati.Reservation.des.Hotels.entity.Admin;
import com.Hayati.Reservation.des.Hotels.entity.Client;
import com.Hayati.Reservation.des.Hotels.entity.Hotel;
import com.Hayati.Reservation.des.Hotels.entity.Notification;
import com.Hayati.Reservation.des.Hotels.repositoriy.NotificationRepository;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    // Créer une notification pour l'Admin
    public void createNotificationForAdmin(Admin admin, Hotel hotel, String message) {
        Notification notification = new Notification(message, admin, hotel);
        notificationRepository.save(notification);
    }

    // Créer une notification pour le Client
    // public void createNotificationForClient(Client client, String message) {
    //     Notification notification = new Notification(message, client);
    //     notificationRepository.save(notification);
    // }

    // Récupérer les notifications pour l'Admin
    public List<Notification> getNotificationsForAdmin(Integer adminId) {
        return notificationRepository.findByAdminId(adminId);
    }

    // // Récupérer les notifications pour le Client
    // public List<Notification> getNotificationsForClient(Integer clientId) {
    //     return notificationRepository.findByClientId(clientId);
    // }
}

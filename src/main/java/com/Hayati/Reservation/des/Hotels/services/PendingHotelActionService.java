// package com.Hayati.Reservation.des.Hotels.services;


// import com.Hayati.Reservation.des.Hotels.entity.PendingHotelAction;
// import com.Hayati.Reservation.des.Hotels.repositoriy.PendingHotelActionRepository;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class PendingHotelActionService {

//     @Autowired
//     private PendingHotelActionRepository pendingHotelActionRepository;

//     // Approve a pending hotel action
//     public PendingHotelAction approveAction(Long actionId, String adminComment) {
//         Optional<PendingHotelAction> actionOpt = pendingHotelActionRepository.findById(actionId);

//         if (actionOpt.isPresent()) {
//             PendingHotelAction action = actionOpt.get();
//             action.setApproved(true);
//             action.setAdminComment(adminComment);
//             // Update status to 'Approved' and save the action
//             return pendingHotelActionRepository.save(action);
//         } else {
//             throw new RuntimeException("Action not found");
//         }
//     }

//     // Reject a pending hotel action
//     public PendingHotelAction rejectAction(Long actionId, String adminComment) {
//         Optional<PendingHotelAction> actionOpt = pendingHotelActionRepository.findById(actionId);

//         if (actionOpt.isPresent()) {
//             PendingHotelAction action = actionOpt.get();
//             action.setApproved(false);
//             action.setAdminComment(adminComment);
//             // Update status to 'Rejected' and save the action
//             return pendingHotelActionRepository.save(action);
//         } else {
//             throw new RuntimeException("Action not found");
//         }
//     }

//     // Retrieve all pending actions
//     public List<PendingHotelAction> getAllPending() {
//         return pendingHotelActionRepository.findAllByApprovedIsNull(); // Assuming `null` means pending
//     }
// }


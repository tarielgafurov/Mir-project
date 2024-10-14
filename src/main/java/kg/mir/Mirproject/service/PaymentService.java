package kg.mir.Mirproject.service;

import kg.mir.Mirproject.dto.SimpleResponse;
import kg.mir.Mirproject.dto.WorldDto.DebtRequest;
import kg.mir.Mirproject.dto.WorldDto.DebtResponse;
import kg.mir.Mirproject.dto.WorldUserResponse;
import kg.mir.Mirproject.dto.payment.PaymentRequest;
import kg.mir.Mirproject.dto.payment.SumRequest;

public interface PaymentService {

    SimpleResponse payDebtsOfUser(Long userId, PaymentRequest paymentRequest);
    SimpleResponse addSumToUser(SumRequest sumRequest);
    SimpleResponse refundAllSumOfUser(SumRequest sumRequest);
    WorldUserResponse getUserWorldById(Long userId);
    SimpleResponse giveDebtToUser(Long id, DebtRequest debtRequest);
}


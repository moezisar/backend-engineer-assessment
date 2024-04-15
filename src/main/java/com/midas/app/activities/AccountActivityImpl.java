package com.midas.app.activities;

import com.midas.app.enums.ProviderType;
import com.midas.app.models.Account;
import com.midas.app.providers.payment.CreateAccount;
import com.midas.app.providers.payment.PaymentProvider;
import com.midas.app.repositories.AccountRepository;
import com.midas.app.services.PaymentFactory;
import io.temporal.spring.boot.ActivityImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@ActivityImpl(taskQueues = "create-account-workflow")
public class AccountActivityImpl implements AccountActivity {

  @Autowired private AccountRepository accountRepository;

  @Override
  public Account saveAccount(Account account) {
    return accountRepository.save(account);
  }

  @Override
  public Account createPaymentAccount(Account account) {
    PaymentProvider paymentProvider = PaymentFactory.getPaymentProvider(ProviderType.stripe);
    return paymentProvider.createAccount(
        CreateAccount.builder()
            .firstName(account.getFirstName())
            .lastName(account.getLastName())
            .email(account.getEmail())
            .userId(String.valueOf(account.getId()))
            .build());
  }
}

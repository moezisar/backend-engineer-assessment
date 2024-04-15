package com.midas.app.services;

import com.midas.app.enums.ProviderType;
import com.midas.app.providers.payment.PaymentProvider;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentFactory {

  /**
   * This is a factory class that creates a payment provider based on the payment provider type. The
   * factory class is responsible for creating the payment provider based on the payment provider
   * type. The factory class uses a map to store the payment provider instances. The factory class
   * initializes
   */
  @Autowired List<PaymentProvider> paymentProviderList;

  private static final Map<String, PaymentProvider> serviceCache = new HashMap<>();

  @PostConstruct
  public void initCache() {
    for (PaymentProvider service : paymentProviderList) {
      serviceCache.put(service.providerName(), service);
    }
  }

  public static PaymentProvider getPaymentProvider(ProviderType paymentProviderType) {
    PaymentProvider paymentProvider = serviceCache.get(paymentProviderType.name().toLowerCase());
    if (Objects.isNull(paymentProvider))
      throw new RuntimeException("Unknown  provider type: " + paymentProviderType);
    return paymentProvider;
  }
}

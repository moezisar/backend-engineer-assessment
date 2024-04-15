package com.midas.app.providers.payment;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class CreateAccount {
  private String userId;
  private String firstName;
  private String lastName;
  private String email;
}

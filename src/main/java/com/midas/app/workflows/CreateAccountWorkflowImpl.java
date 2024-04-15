package com.midas.app.workflows;

import com.midas.app.activities.AccountActivity;
import com.midas.app.models.Account;
import io.temporal.activity.ActivityOptions;
import io.temporal.common.RetryOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@WorkflowImpl(taskQueues = "create-account-workflow")
public class CreateAccountWorkflowImpl implements CreateAccountWorkflow {

  private final RetryOptions retryoptions =
      RetryOptions.newBuilder()
          .setInitialInterval(Duration.ofSeconds(1))
          .setMaximumInterval(Duration.ofSeconds(100))
          .setBackoffCoefficient(2)
          .setMaximumAttempts(50000)
          .build();
  private final ActivityOptions options =
      ActivityOptions.newBuilder()
          .setStartToCloseTimeout(Duration.ofSeconds(30))
          .setRetryOptions(retryoptions)
          .build();

  private final AccountActivity activity = Workflow.newActivityStub(AccountActivity.class, options);

  @Override
  public Account createAccount(Account details) {
    Account account = activity.saveAccount(details);
    log.info("Account Created");
    Account paymentAccount = activity.createPaymentAccount(account);

    account.setProviderId(paymentAccount.getProviderId());
    account.setProviderType(paymentAccount.getProviderType());

    log.info("Account Updated");
    return activity.saveAccount(account);
  }
}


# bank-account-insights-stub

This project provides a stub for the Bank Account Insights API, allowing developers to simulate interactions with the API without needing access to a live environment. It is particularly useful for testing and development purposes.

# Testing locally
To test the stub locally, you can use the following command:

```sbt run```

## Returns true for an account that is to be rejected
```bash
curl -X POST http://localhost:9000/reject/bank-account \
  -H "Content-Type: application/json" \
  -d '{"sortCode": "393358", "accountNumber": "13902323"}'
```

## Returns false for an account that is NOT to be rejected
```bash
curl -X POST http://localhost:9000/reject/bank-account \
  -H "Content-Type: application/json" \
  -d '{"sortCode": "393358", "accountNumber": "12345678"}'
```


### License

This code is open source software licensed under the [Apache 2.0 License]("http://www.apache.org/licenses/LICENSE-2.0.html").
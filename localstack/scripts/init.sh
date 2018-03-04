#!/usr/bin/env bash

aws --endpoint-url=http://localhost:4575 sns create-topic --name items
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name invoices
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name orders
aws --endpoint-url=http://localhost:4575 sns subscribe --topic-arn arn:aws:sns:us-east-1:123456789012:items --protocol sqs --notification-endpoint http://localhost:4576/queue/invoices
aws --endpoint-url=http://localhost:4575 sns subscribe --topic-arn arn:aws:sns:us-east-1:123456789012:items --protocol sqs --notification-endpoint http://localhost:4576/queue/orders
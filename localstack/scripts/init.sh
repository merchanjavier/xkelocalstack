#!/usr/bin/env bash

aws --endpoint-url=http://localhost:4575 sns create-topic --name items
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name invoices
aws --endpoint-url=http://localhost:4576 sqs create-queue --queue-name orders
aws --endpoint-url=http://localhost:4575 sns subscribe --topic-arn arn:aws:sns:eu-west-1:123456789012:items --protocol sqs --notification-endpoint http://localhost:4576/queue/invoices
aws --endpoint-url=http://localhost:4575 sns subscribe --topic-arn arn:aws:sns:eu-west-1:123456789012:items --protocol sqs --notification-endpoint http://localhost:4576/queue/orders
aws --endpoint-url=http://localhost:4569 dynamodb create-table --table-name Invoice --attribute-definitions AttributeName=Body,AttributeType=S --key-schema AttributeName=Body,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
aws --endpoint-url=http://localhost:4569 dynamodb create-table --table-name Order --attribute-definitions AttributeName=Body,AttributeType=S --key-schema AttributeName=Body,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5
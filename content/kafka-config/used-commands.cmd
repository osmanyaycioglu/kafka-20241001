.\kafka-topics.bat --create --bootstrap-server 127.0.0.1:9092 --topic first-topic --partitions 5 --replication-factor 2
.\kafka-topics.bat --describe --bootstrap-server 127.0.0.1:9092 --topic first-topic
.\kafka-topics.bat --create --bootstrap-server 127.0.0.1:9092 --topic first-topic-3 --partitions 5 --replication-factor 3
.\kafka-topics.bat --describe --bootstrap-server 127.0.0.1:9092 --topic first-topic-3
.\kafka-console-producer.bat --bootstrap-server 127.0.0.1:9092 --topic first-topic-3

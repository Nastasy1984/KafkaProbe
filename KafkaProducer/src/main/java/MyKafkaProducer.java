import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class MyKafkaProducer {
    static final String TOPIC = "probe-topic";

    public static void main(String[] args) {
        //creating properties for producer
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //creating producer using properties
        Producer<String, String> producer = new KafkaProducer<>(props);

        //sending first probe message
        producer.send(new ProducerRecord<String, String>(TOPIC,"Probe message"));

        //sending from console
        System.out.println("Write smth or enter exit");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        try {
            while ((!(line = reader.readLine()).equals("exit"))){
                producer.send(new ProducerRecord<String, String>(TOPIC, line));
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }

        /*
        //sending loop
        for (int i = 0; i < 100; i++) {
            producer.send(new ProducerRecord<String, String>(TOPIC, "Hello from " + TOPIC + " " + i));
        }*/
        //closing
        producer.close();
    }
}

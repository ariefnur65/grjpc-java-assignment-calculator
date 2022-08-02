package calulator.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.omg.SendingContext.RunTime;

import java.io.IOException;

public class CalculatorServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 55001;
        Server server = ServerBuilder
                .forPort(port)
                .addService(new CalculatorServiceImpl())
                .build();
        server.start();
        System.out.println("Server started");
        System.out.println("Listening on port: " + port);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Receive shutdown request");
            server.shutdown();
            System.out.println("Server stopped");
        }));

        server.awaitTermination();
    }
}

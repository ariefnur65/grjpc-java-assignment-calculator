package calulator.client;

import com.proto.calculator.CalculatorServiceGrpc;
import com.proto.calculator.SumRequest;
import com.proto.calculator.SumResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;

public class CalculatorClient {

    public static void doSum(ManagedChannel managedChannel) throws IOException {
        InputStreamReader inputStream = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(inputStream);
        System.out.print("Enter 1st operand: ");
        String firstOperand = reader.readLine();
        System.out.print("Enter 2nd operand: ");
        String secondOperand = reader.readLine();
        int firstOperandValue = Integer.parseInt(firstOperand);
        int secondOperandValue = Integer.parseInt(secondOperand);
        CalculatorServiceGrpc.CalculatorServiceBlockingStub stub = CalculatorServiceGrpc.newBlockingStub(managedChannel);
        SumRequest sumRequest = SumRequest.newBuilder()
                .setFirstOperand(firstOperandValue)
                .setSecondOperand(secondOperandValue)
                .build();
        SumResponse sumResponse = stub.sum(sumRequest);
        String resultString = String.format("%s + %s = %s", firstOperand, secondOperand, sumResponse.getResult());
        System.out.println(resultString);

    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Need more arguments");
            return;
        }
        ManagedChannel managedChannel = ManagedChannelBuilder
                .forAddress("localhost", 55001)
                .usePlaintext()
                .build();

        if("sum".equals(args[0])){
            doSum(managedChannel);
        } else {
            System.out.println("Invalid keyword" + args[0]);
        }

        System.out.println("Shutting down....");

        managedChannel.shutdown();
    }
}
